/**
* Copyright (c) Kyle Fricilone
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package net.openrs.cache.type.objects;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.openrs.cache.Archive;
import net.openrs.cache.Cache;
import net.openrs.cache.Constants;
import net.openrs.cache.Container;
import net.openrs.cache.ReferenceTable;
import net.openrs.cache.ReferenceTable.ChildEntry;
import net.openrs.cache.ReferenceTable.Entry;
import net.openrs.cache.type.CacheIndex;
import net.openrs.cache.type.ConfigArchive;
import net.openrs.cache.type.TypeList;
import net.openrs.cache.type.TypePrinter;
import net.openrs.cache.type.sequences.SequenceType;
import net.openrs.util.Preconditions;

/**
 * @author Kyle Friz
 * 
 * @since May 26, 2015
 */
public class ObjectTypeList implements TypeList<ObjectType> {

	private Logger logger = Logger.getLogger(ObjectTypeList.class.getName());

	private ObjectType[] objs;

	public int maxSize = 0;

	@Override
	public void initialize(Cache cache) {
		int count = 0;
		int maxGlobalId = 0;

		ReferenceTable table = cache.getReferenceTable(19); // Index 20 = Sequences
		Map<Integer, ObjectType> sequenceMap = new HashMap<>();

		for (int archiveId = 0; archiveId < table.size(); archiveId++) {
			Entry entry = table.getEntry(archiveId);
			if (entry == null) continue;

			Container container;
			try {
				container = cache.read(19, archiveId);
			} catch (Exception e) {
				continue;
			}

			Archive archive;
			archive = Archive.decode(container.getData(), entry.size());
			objs = new ObjectType[maxGlobalId + 1];
			for (int childId = 0; childId < entry.capacity(); childId++) {
				ChildEntry child = entry.getEntry(childId);
				if (child == null) continue;

				ByteBuffer buffer = archive.getEntry(child.index());
				if (buffer == null) continue;

				try {
					int globalId = (archiveId << 8) | childId;
					ObjectType type = new ObjectType(globalId);
					type.decode(buffer);
					sequenceMap.put(globalId, type);

					if (maxGlobalId < globalId) {
						maxGlobalId = globalId;
					}


					count++;
				} catch (Exception e) {
					System.err.println("⚠️ Failed to decode ObjectType " + archiveId + ":" + childId);
				}
			}
		}
		// Allocate exact array size based on highest global ID
		objs = new ObjectType[maxGlobalId + 1];
		for (Map.Entry<Integer, ObjectType> entry : sequenceMap.entrySet()) {
			objs[entry.getKey()] = entry.getValue();
		}
		logger.info("✅ Loaded " + count + " SequenceType(s), max global ID = " + maxGlobalId + "!");
	}

	@Override
	public ObjectType list(int id) {
		Preconditions.checkArgument(id >= 0, "ID can't be negative!");
		Preconditions.checkArgument(id < objs.length, "ID can't be greater than the max object id!");
		return objs[id];
	}

	@Override
	public void print() {
	      
	      File dir = new File(Constants.TYPE_PATH);

	      if (!dir.exists()) {
	            dir.mkdir();
	      }
	      
		File file = new File(Constants.TYPE_PATH, "objects.txt");
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			Arrays.stream(objs).filter(Objects::nonNull).forEach((ObjectType t) -> {
				TypePrinter.print(t, writer);
			});
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public int size() {
		return objs.length;
	}

}
