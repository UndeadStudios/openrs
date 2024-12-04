package net.openrs.cache.tools.track_dumper;

import java.io.File;
import java.io.IOException;

public final class OggDumper {

    public static void main(String[] args) throws Exception {
        // Directories
        File midiDir = new File("E:/dump/midi/");
        File oggDir = new File("E:/dump/ogg/");
        File soundfont = new File("src/main/resources/soundbank.sf2");

        // Ensure directories exist
        if (!soundfont.exists()) {
            soundfont.mkdirs();
        }
        if (!midiDir.exists()) {
            midiDir.mkdirs();
        }
        if (!oggDir.exists()) {
            oggDir.mkdirs();
        }

        // Process MIDI files
        File[] midiFiles = midiDir.listFiles((dir, name) -> name.toLowerCase().endsWith(".mid"));
        if (midiFiles == null || midiFiles.length == 0) {
            System.err.println("No MIDI files found in directory: " + midiDir.getAbsolutePath());
            return;
        }

        int totalFiles = midiFiles.length;
        int processedFiles = 0;

        for (File midiFile : midiFiles) {
            processedFiles++;
            File oggFile = new File(oggDir, midiFile.getName().replace(".mid", ".ogg"));
            System.out.printf("Processing file %d/%d: %s%n", processedFiles, totalFiles, midiFile.getName());
            try {
                convertMidiToOgg(midiFile, soundfont, oggFile);
                System.out.printf("Successfully converted %s to %s (%d/%d)%n", midiFile.getName(), oggFile.getName(), processedFiles, totalFiles);
            } catch (Exception e) {
                System.err.printf("Failed to convert %s (%d/%d): %s%n", midiFile.getName(), processedFiles, totalFiles, e.getMessage());
            }
        }

        System.out.printf("Conversion completed: %d/%d files successfully processed.%n", processedFiles, totalFiles);
    }

    /**
     * Converts a MIDI file to OGG format using Fluidsynth and FFmpeg.
     *
     * @param midiFile   The input MIDI file.
     * @param soundfont  The soundfont (.sf2) file for Fluidsynth.
     * @param oggFile    The output OGG file.
     * @throws IOException          If an I/O error occurs.
     * @throws InterruptedException If the process is interrupted.
     */
    private static void convertMidiToOgg(File midiFile, File soundfont, File oggFile) throws IOException, InterruptedException {
        // Temporary WAV file
        File tempWavFile = File.createTempFile("temp", ".wav");
        System.out.printf("  [1/2] Converting %s to WAV...%n", midiFile.getName());
        // Step 1: Convert MIDI to WAV using Fluidsynth
        ProcessBuilder fluidsynth = new ProcessBuilder(
                "C:\\Users\\Sgsrocks\\Downloads\\fluidsynth-2.4.0-win10-x64\\bin\\fluidsynth.exe", "-F", tempWavFile.getAbsolutePath(), soundfont.getAbsolutePath(), midiFile.getAbsolutePath()
        );
        Process fluidsynthProcess = fluidsynth.start();

        if (fluidsynthProcess.waitFor() != 0) {
            throw new IOException("Fluidsynth failed to generate WAV file for: " + midiFile.getName());
        }
        System.out.printf("  [1/2] Successfully converted %s to WAV%n", midiFile.getName());

        // Step 2: Convert WAV to OGG using FFmpeg
        System.out.printf("  [2/2] Converting %s to OGG...%n", midiFile.getName());
        ProcessBuilder ffmpeg = new ProcessBuilder(
                "D:\\downloads\\ffmpeg-master-latest-win64-gpl\\ffmpeg-master-latest-win64-gpl\\bin\\ffmpeg.exe", "-i", tempWavFile.getAbsolutePath(), "-c:a", "libvorbis", oggFile.getAbsolutePath()
        );
        Process ffmpegProcess = ffmpeg.start();
        if (ffmpegProcess.waitFor() != 0) {
            throw new IOException("FFmpeg failed to generate OGG file for: " + midiFile.getName());
        }
        System.out.printf("  [2/2] Successfully converted %s to OGG%n", midiFile.getName());
        // Clean up temporary WAV file
        if (!tempWavFile.delete()) {
            System.err.printf("Failed to delete temporary WAV file: %s%n", tempWavFile.getAbsolutePath());
        }

    }
}
