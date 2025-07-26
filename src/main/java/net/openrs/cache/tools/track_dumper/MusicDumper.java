package net.openrs.cache.tools.track_dumper;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import net.openrs.cache.Cache;
import net.openrs.cache.Constants;
import net.openrs.cache.FileStore;
import net.openrs.cache.track.Track;
import net.openrs.cache.track.Tracks;
import net.openrs.cache.util.CompressionUtils;

public final class MusicDumper {

	public static void main(String[] args) throws Exception {
		File track1Dir = new File("E:/dump/index3/");

		if (!track1Dir.exists()) {
			track1Dir.mkdirs();
		}


		try (Cache cache = new Cache(FileStore.open(Constants.CACHE_PATH))) {
			Tracks list = new Tracks();
			list.initialize(cache);

			for (int i = 0; i < list.getTrack1Count(); i++) {
				Track track1 = list.getTrack1(i);

				if (track1 == null) {
					continue;
				}

				try (DataOutputStream dos = new DataOutputStream(
						new FileOutputStream(new File(track1Dir, i + ".gz")))) {
					dos.write(CompressionUtils.gzip(track1.getDecoded()));
				}

				double progress = (double) (i + 1) / list.getTrack1Count() * 100;
				System.out.printf("dumping track1 %d out of %d %.2f%s\n", (i + 1), list.getTrack1Count(), progress, "%");
			}

		}
	}

}
