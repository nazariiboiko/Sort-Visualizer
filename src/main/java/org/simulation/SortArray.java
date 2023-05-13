package org.simulation;

import org.simulation.sound.Sound;

import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class SortArray {
    private ReentrantLock mutex = new ReentrantLock();
    private final int size;
    private final int[] arr;
    private final Mark[] buffer_mark;
    private final Mark[] current_mark;
    private boolean isSorting;
    private boolean isSortingComplete;
    private Sound sound;
    private int delay;
    private int accesses;
    private int comparisons;
    private int lastMark;

    public SortArray(int size) {
        this.size = size;
        arr = SortArrayUtil.initializeArray(size);
        buffer_mark = new Mark[size];
        current_mark = new Mark[size];
        isSorting = false;
        delay = 100;
        accesses = 0;
        comparisons = 0;
        isSortingComplete = true;
        lastMark = 0;
    }

    public void resetCounter() {
        resetCurrentMark();
        comparisons = 0;
        accesses = 0;
    }

    public boolean getAccess(){
        waitDelay();
        if(!isSorting)
            return false;
        return true;
    }

    public void lock() {
        mutex.lock();
    }

    public void unlock() {
        mutex.unlock();
    }

    public void waitDelay() {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
        }
    }

    public void swap(int i, int j) {
        resetCurrentMark();
        mark(i, Mark.RED);
        mark(j, Mark.RED);
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
        accesses += 2;
        onSound(arr[i]);
    }

    public boolean compare(int i, int j) {
        markTilNextMove(i, Mark.RED);
        waitDelay();
        markTilNextMove(j, Mark.RED);
        accesses += 2;
        comparisons++;
        onSound(arr[j]);
        return arr[i] > arr[j];
    }

    public void increaseCompare() {
        comparisons++;
    }

    public void mark(int i, Mark mark) {
        current_mark[i] = mark;
    }

    public void markTilNextMove(int i, Mark mark) {
        if(current_mark[i] != null)
            return;
        current_mark[i] = mark;
        current_mark[lastMark] = Mark.DEFAULT;
        lastMark = i;
    }

    public void unmark(int i) {
        current_mark[i] = Mark.DEFAULT;
    }

    public void resetCurrentMark() {
        fillBufferMark();
        for(int i = 0; i < size; i++)
            if(current_mark[i] != null)
                current_mark[i] = null;

    }

    public Mark getMark(int i) {
        fillBufferMark();
        Mark mark;
        if(buffer_mark[i] == null)
            mark = Mark.WHITE;
        else {
            mark = buffer_mark[i];
            buffer_mark[i] = null;
        }
        return mark;
    }

    public void fillBufferMark() {
        for(int i = 0; i < size; i++)
            if(current_mark[i] != null)
                buffer_mark[i] = current_mark[i];
    }

    public void instantShuffle() {
        resetCurrentMark();
        Random random = new Random();
        for(int i = 0; i < size; i++) {
            int randomIndex = Math.abs(random.nextInt() % size);
            int tmp = arr[i];
            arr[i] = arr[randomIndex];
            arr[randomIndex] = tmp;
        }
    }

    private void onSound(int val) {
        if(sound != null)
            sound.onSound(val);
    }

    public void setSortingComplete(boolean flag) {
        isSortingComplete = flag;
    }
    public boolean checkSorting() {
        if(!isSortingComplete)
            return  false;
        for(int i = 0; i < size - 1; i++) {
            int a = getValue(i, false);
            int b = getValue(i + 1, false);
            if(a > b)
                return false;
            mark(i, Mark.GREEN);
            mark(i + 1, Mark.RED);
            waitDelay();
        }
        mark(size - 1, Mark.GREEN);
        resetCurrentMark();
        return true;
    }

    public void setSound(Sound sound) {
        this.sound = sound;
    }

    public void setSorting(boolean flag) {
        isSorting = flag;
    }

    public void setDelay(int ms) {
        delay = ms;
    }

    public int getValue(int i) {
        markTilNextMove(i, Mark.RED);
        return getValue(i, true);
    }

    public int getValue(int i, boolean flag) {
        if(flag)
            accesses++;
        return arr[i];
    }

    public void setValue(int i, int val) {
        arr[i] = val;
        markTilNextMove(i, Mark.RED);
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
