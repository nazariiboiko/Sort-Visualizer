package org.simulation;

import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class SortArray {
    private ReentrantLock mutex = new ReentrantLock();
    private final int size;
    private final int[] arr;
    private final Color[] mark_arr;
    private boolean isSorting;
    private int delay;
    private int accesses;
    private int comparisons;
    private boolean canUnmark;

    public SortArray(int size) {
        this.size = size;
        arr = SortArrayUtil.initializeArray(size);
        mark_arr = new Color[size];
        isSorting = false;
        delay = 100;
        accesses = 0;
        comparisons = 0;
        canUnmark = false;
    }

    public void resetCounter() {
        unmarkAll();
        comparisons = 0;
        accesses = 0;
    }

    public boolean getAccess() {
        if(!isSorting)
            return false;
        lock();
        return true;
    }

    public void lock() {
        mutex.lock();
    }

    public void unlock() {
        mutex.unlock();
    }

    public void swap(int i, int j) {
        mark(i, Color.RED);
        mark(j, Color.RED);
        canUnmark = true;
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
        accesses += 2;
        unlock();
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean compare(int i, int j) {
        canUnmark = true;
        mark(i, Color.RED);
        mark(j, Color.RED);
        accesses += 2;
        comparisons++;
        return arr[i] > arr[j];
    }

    public void mark(int i, Color color) {
        mark_arr[i] = color;
    }

    public void unmark(int i) {
        mark_arr[i] = Color.DEFAULT;

    }

    public void swapMark(int i, int j) {
        Color tmp = mark_arr[i];
        mark_arr[i] = mark_arr[j];
        mark_arr[j] = tmp;
    }

    public void unmarkAll() {
        if(canUnmark)
            for(int i = 0; i < mark_arr.length; i++)
                mark_arr[i] = Color.DEFAULT;
    }

    public Color getMark(int i) {
        Color color;
        if(mark_arr[i] == null)
            color = Color.WHITE;
        else
            color = mark_arr[i];
        return color;
    }

    public void instantShuffle() {
        Random random = new Random();
        for(int i = 0; i < size; i++) {
            int randomIndex = Math.abs(random.nextInt() % size);
            int tmp = arr[i];
            arr[i] = arr[randomIndex];
            arr[randomIndex] = tmp;
        }
    }

    public void setSorting(boolean flag) {
        isSorting = flag;
    }

    public void setDelay(int ms) {
        delay = ms;
    }

    public int getValue(int i) {
        mark(i, Color.RED);
        return getValue(i, true);
    }

    public int getValue(int i, boolean flag) {
        if(flag)
            accesses++;
        return arr[i];
    }

    public int getComparisons() {
        return comparisons;
    }

    public  int getAccesses() {
        return accesses;
    }

    public int getSize() {
        return size;
    }

    public int getDelay() {
        return delay;
    }

    public boolean isSorting() {
        return isSorting;
    }

}
