package org.simulation.sound;

public class Oscillator {
    private static double phase = 0;

    public static double getNextSample(double frequency, int sampleRate, Waveform waveform) {
        double sample = 0;

        double phaseIncrement = frequency / sampleRate;

        switch (waveform) {
            case SINE:
                sample = Math.sin(2 * Math.PI * phase);
                break;
            case SQUARE:
                sample = Math.signum(Math.sin(2 * Math.PI * phase));
                break;
            case TRIANGLE:
                sample = 2 * Math.abs(2 * (phase - Math.floor(phase + 0.5))) - 1;
                if (sample <= 0.25) sample = 4.0d * sample;
                else if (sample <= 0.75) sample = 2.0d - 4.0d * sample;
                else sample = 4d * sample - 4.0d;
                break;
        }

        phase += phaseIncrement;
        if (phase >= 1.0) {
            phase -= 1.0;
        }

        return sample;
    }
}
