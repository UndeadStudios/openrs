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
package net.openrs.cache.type.hitmarks;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
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
import net.openrs.util.Preconditions;

/**
 * @author Kyle Friz
 * 
 * @since May 26, 2015
 */
public class HitMarkTypeList implements TypeList<HitMarkType> {

	private Logger logger = Logger.getLogger(HitMarkTypeList.class.getName());

	private HitMarkType[] hitmarks;

	@Override
	public void initialize(Cache cache) {
		int count = 0;
		try {
			ReferenceTable table = cache.getReferenceTable(CacheIndex.CONFIGS);
			Entry entry = table.getEntry(ConfigArchive.HITSPLAT);
			Archive archive = Archive.decode(cache.read(CacheIndex.CONFIGS, ConfigArchive.HITSPLAT).getData(),
					entry.size());

			hitmarks = new HitMarkType[entry.capacity()];
			for (int id = 0; id < entry.capacity(); id++) {
				ChildEntry child = entry.getEntry(id);
				if (child == null)
					continue;

				ByteBuffer buffer = archive.getEntry(child.index());
				HitMarkType type = new HitMarkType(id);
				type.decode(buffer);
				hitmarks[id] = type;
				count++;
			}
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Error Loading HitMarkType(s)!", e);
		}
		logger.info("Loaded " + count + " HitMarksType(s)!");
	}

	@Override
	public HitMarkType list(int id) {
		Preconditions.checkArgument(id >= 0, "ID can't be negative!");
		Preconditions.checkArgument(id < hitmarks.length, "ID can't be greater than the max hitmark id!");
		return hitmarks[id];
	}

	@Override
	public void print() {
	      
	  File dir = new File(Constants.TYPE_PATH);

	  if (!dir.exists()) {
	        dir.mkdir();
	  }
	      
		File file = new File(Constants.TYPE_PATH, "hitmarks.txt");
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			Arrays.stream(hitmarks).filter(Objects::nonNull).forEach((HitMarkType t) -> {
				TypePrinter.print(t, writer);
			});
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public int size() {
		return hitmarks.length;
	}

}
