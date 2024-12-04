package net.openrs.cache.tools.track_dumper;

import net.openrs.cache.Cache;
import net.openrs.cache.Constants;
import net.openrs.cache.FileStore;
import net.openrs.cache.track.Track;
import net.openrs.cache.track.Tracks;
import net.openrs.cache.util.CompressionUtils;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public final class JinglesDumper {

	public static void main(String[] args) throws Exception {

		File track2Dir = new File("E:/dump/Jingles/");

		if (!track2Dir.exists()) {
			track2Dir.mkdirs();
		}

		try (Cache cache = new Cache(FileStore.open(Constants.CACHE_PATH))) {
			Tracks list = new Tracks();
			list.initialize(cache);

			for (int i = 0; i < list.getTrack2Count(); i++) {
				Track track2 = list.getTrack2(i);

				if (track2 == null) {
					continue;
				}

				try (DataOutputStream dos = new DataOutputStream(
						new FileOutputStream(new File(track2Dir, i + ".gz")))) {
					dos.write(CompressionUtils.gzip(track2.getDecoded()));
				}

				double progress = (double) (i + 1) / list.getTrack2Count() * 100;
				System.out.printf("dumping track2 %d out of %d %.2f%s\n", (i + 1), list.getTrack2Count(), progress, "%");
			}
		}
	}

}
