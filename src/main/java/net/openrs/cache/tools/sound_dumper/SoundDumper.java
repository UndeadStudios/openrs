package net.openrs.cache.tools.sound_dumper;

import net.openrs.cache.*;
import net.openrs.cache.sound.SoundEffect;
import net.openrs.cache.type.CacheIndex;
import net.openrs.cache.util.CompressionUtils;

import javax.sound.sampled.*;
import java.io.*;
import java.nio.file.Paths;

public class SoundDumper {

    public static void main(String[] args) {
        try (Cache cache = new Cache(FileStore.open(Constants.CACHE_PATH))) {
            File dir = Paths.get("E:/dump/226sounds").toFile();
            if (!dir.exists()) {
                dir.mkdirs();
            }

            export(cache, dir, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void export(Cache cache, File dir, boolean wav) {
        ReferenceTable table = cache.getReferenceTable(CacheIndex.SOUNDEFFECTS);

        for (int i = 0; i < table.capacity(); i++) {
            try {
                SoundEffect effect = SoundEffect.decode(cache, i);

                // creates the raw audio binary in signed 8-bit PCM
                byte[] data = effect.mix();

                // converting to wave format
                if (wav) {

                    // used audacity to determine the binary format encoding
                    AudioFormat audioFormat = new AudioFormat(22_050, 8, 1, true, false);

                    AudioInputStream ais = new AudioInputStream(new ByteArrayInputStream(data), audioFormat, data.length);

                    ByteArrayOutputStream bos = new ByteArrayOutputStream();

                    AudioSystem.write(ais, AudioFileFormat.Type.WAVE, bos);

                    // binary of audio in wav format
                    data = bos.toByteArray();
                }

                try (FileOutputStream fos = new FileOutputStream(new File(dir, i + (".gz")))) {
                    fos.write(CompressionUtils.gzip(data));}

            } catch (Exception ex) {
                System.out.println("error decoding sound: " + i);
                continue;
            }

            System.out.println("decoding sound: " + i);
        }

        System.out.println("decoded: " + table.capacity() + " sounds");
    }

}
