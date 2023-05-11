package org.simulation;

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
    private int delay;
    private int accesses;
    private int comparisons;

    public SortArray(int size) {
        this.size = size;
        arr = SortArrayUtil.initializeArray(size);
        buffer_mark = new Mark[size];
        current_mark = new Mark[size];
        isSorting = false;
        delay = 1;
        accesses = 0;
        comparisons = 0;
        isSortingComplete = true;
    }

    public void resetCounter() {
        resetCurrentMark();
        comparisons = 0;
        accesses = 0;
    }

    public boolean getAccess() {
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
            throw new RuntimeException(e);
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
    }

    public boolean compare(int i, int j) {
        resetCurrentMark();
        mark(i, Mark.RED);
        waitDelay();
        mark(j, Mark.RED);
        accesses += 2;
        comparisons++;
        return arr[i] > arr[j];
    }

    public void mark(int i, Mark mark) {
        current_mark[i] = mark;
    }

    public void swapMark(int i, int j) {
        Mark tmp = current_mark[i];
        current_mark[i] = current_mark[j];
        current_mark[j] = tmp;
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

    public void setSorting(boolean flag) {
        isSorting = flag;
    }

    public void setDelay(int ms) {
        delay = ms;
    }

    public int getValue(int i) {
        mark(i, Mark.RED);
        return getValue(i, true);
    }

    public int getValue(int i, boolean flag) {
        if(flag)
            accesses++;
        return arr[i];
    }

    public void setValue(int i, int val) {
        arr[i] = i;
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
