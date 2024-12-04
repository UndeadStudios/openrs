package net.openrs.cache.tools;

import java.io.IOException;
import java.nio.ByteBuffer;

import net.openrs.cache.Cache;
import net.openrs.cache.Constants;
import net.openrs.cache.FileStore;
import net.openrs.cache.ReferenceTable;

public final class CacheDefragmenter {

	public static void main(String[] args) throws IOException {
		try (Cache in = new Cache(FileStore.open(Constants.CACHE_PATH));
				Cache out = new Cache(FileStore.create(Constants.CACHETMP_PATH, in.getTypeCount()))) {
			for (int type = 0; type < in.getTypeCount(); type++) {
				ByteBuffer buf = in.store.read(255, type);
				buf.mark();
				out.store.write(255, type, buf);
				buf.reset();

				ReferenceTable rt = in.getReferenceTable(type);
				for (int file = 0; file < rt.capacity(); file++) {
					if (rt.getEntry(file) == null) {
						System.out.println(type + ", " + file);
						continue;
					}

					out.store.write(type, file, in.store.read(type, file));
				}
			}
		}
	}

}