package org.simulation.sound;

import org.simulation.SortArray;
import org.simulation.SortArrayUtil;

import javax.sound.sampled.*;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Sound extends Thread {
    final static public int SAMPLING_RATE = 44100;
    final static public int SAMPLE_SIZE = 2;
    static public double BUFFER_DURATION = 0.10;
    final static public int SINE_PACKET_SIZE = (int)(BUFFER_DURATION*SAMPLING_RATE*SAMPLE_SIZE);

    SourceDataLine line;
    private ConcurrentLinkedQueue<Integer> freqs;
    private boolean terminate = false;

    {
        freqs = new ConcurrentLinkedQueue<>();
        System.out.println("init");
    }

    public void onSound(int valueOfArray) {
        freqs.add(getFrequency(valueOfArray));
    }

    public void setDelay(int ms) {
        this.BUFFER_DURATION = ms;
    }

    private int getFrequency(int val) {
        return Math.abs(val) * 8 + 200;
    }

    private int getLineSampleCount() {
        return line.getBufferSize() - line.available();
    }

    public void run() {
        double fCyclePosition = 0;

        try {
            AudioFormat format = new AudioFormat(44100, 16, 1, true, true);
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format, SINE_PACKET_SIZE*2 * 16);

            if (!AudioSystem.isLineSupported(info))
                throw new LineUnavailableException();

            line = (SourceDataLine)AudioSystem.getLine(info);
            line.open(format);
            line.start();
        }
        catch (LineUnavailableException e) { }

        ByteBuffer cBuf = ByteBuffer.allocate(SINE_PACKET_SIZE);

        while (!terminate) {
            if(!freqs.isEmpty()) {
                double frequency = (double)freqs.poll();
                System.out.println(freqs.size());

                double fCycleInc = frequency / SAMPLING_RATE;

                cBuf.clear();

                for (int i = 0; i < SINE_PACKET_SIZE / SAMPLE_SIZE; i++) {
                    cBuf.putShort((short) (Short.MAX_VALUE * Math.sin(2 * Math.PI * fCyclePosition)));

                    fCyclePosition += fCycleInc;
                    if (fCyclePosition > 1)
                        fCyclePosition -= 1;
                }

                line.write(cBuf.array(), 0, cBuf.position());

                try {
                    while (getLineSampleCount() > SINE_PACKET_SIZE)
                        Thread.sleep(1);
                } catch (InterruptedException e) {
                }
            }
        }

        line.drain();
        line.close();
    }

    public void exit() {
        terminate = true;
    }
}
