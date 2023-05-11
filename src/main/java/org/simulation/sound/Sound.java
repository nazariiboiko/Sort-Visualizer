package org.simulation.sound;

import org.simulation.SortArray;
import org.simulation.SortArrayUtil;

import javax.sound.sampled.*;
import java.util.Arrays;

public class Sound {

    public static void main(String[] args) {
        int[] arr = SortArrayUtil.initializeArray(100);
        for(int i = 0; i < arr.length; i++) {
            createWave(i);
        }
    }

    public static void createWave(int val) {
        int sampleRate = 44100;  // Sample rate in Hz
        double duration = 0.05;        // Duration in seconds
        double numSamples = sampleRate * duration;

        double[] waveform = new double[(int) numSamples];

        double phase = 0;
        for (int i = 0; i < numSamples; i++) {
            double frequency = 120 + 12 * (i*i);
            double phaseIncrement = frequency / sampleRate;
            waveform[i] += Oscillator.getNextSample(frequency, sampleRate, Waveform.TRIANGLE);
            phase += phaseIncrement;
        }

        // Print the first 10 samples of the mixed waveform
        System.out.println("Mixed Waveform:");
        System.out.println(Arrays.toString(Arrays.copyOfRange(waveform, 0, 10)));
        Sound.playAudio(waveform, sampleRate);
    }

    public static void playAudio(double[] audioData, int sampleRate) {
        try {
            // Set up audio format
            AudioFormat audioFormat = new AudioFormat(sampleRate, 16, 1, true, false);
            DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, audioFormat);

            // Open audio output line
            SourceDataLine line = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
            line.open(audioFormat);
            line.start();

            // Convert double audio data to bytes
            byte[] audioBytes = new byte[audioData.length * 2];
            for (int i = 0; i < audioData.length; i++) {
                short sample = (short) (audioData[i] * Short.MAX_VALUE);
                audioBytes[i * 2] = (byte) (sample & 0xFF);
                audioBytes[i * 2 + 1] = (byte) ((sample >> 8) & 0xFF);
            }

            // Write audio data to the line
            line.write(audioBytes, 0, audioBytes.length);

            // Wait until all data is played
            line.drain();

            line.stop();
            line.close();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
