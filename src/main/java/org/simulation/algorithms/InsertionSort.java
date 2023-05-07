package org.simulation.algorithms;

import org.simulation.Unit;

public class InsertionSort implements SortingAlgorithm {
    private Unit[] arr;
    private int i;
    private int j;
    private int n;
    private Unit key;

    public InsertionSort(Unit[] arr) {
        this.arr = arr;
        this.i = 1;
        n = arr.length;
        key = arr[i];
        j = i - 1;
    }

    @Override
    public boolean sort() {
        System.out.println("i = " + i + " j = " + j);
        for (; i < n; ++i) {
            while (j >= 0 && arr[j].getValue() > key.getValue()) {
                arr[j + 1] = arr[j];
                j = j - 1;
                return true;
            }
            arr[j + 1] = key;
            i++;
            if(i < n) {
                key = arr[i];
                j = i - 1;
                return true;
            }
        }
        return false;
    }

    @Override
    public int getCurrentIndex() {
        return j + 1;
    }

    @Override
    public int getComparedIndex() {
        if(j >= 0)
            return j;
        return j + 1;
    }

    @Override
    public boolean isCycleCompleted() {
        return !(i < n);
    }
}
