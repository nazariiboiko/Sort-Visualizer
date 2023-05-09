package org.experimental;

import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class SortArray {
    private ReentrantLock mutex = new ReentrantLock();
    private final int size;
    private final int[] arr;
    private final Color[] mark_arr;

    public SortArray(int size) {
        this.size = size;
        arr = SortArrayUtil.initializeArray(size);
        mark_arr = new Color[size];
    }

    public void lock() {
        mutex.lock();
    }

    public void unlock() {
        mutex.unlock();
    }

    public int getValue(int i) {
        return arr[i];
    }

    public int getSize() {
        return size;
    }
    public void swap(int i, int j) {
            int tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        try {
            Thread.sleep(3);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void mark(int i, Color color) {
        mark_arr[i] = color;
    }

    public void shuffle() {
        Random random = new Random();
        for(int i = 0; i < size; i++) {
            int randomIndex = Math.abs(random.nextInt() % size);
            int tmp = arr[i];
            arr[i] = arr[randomIndex];
            arr[randomIndex] = tmp;
        }
    }
}
