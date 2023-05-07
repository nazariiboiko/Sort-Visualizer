package org.simulation.algorithms;

import org.simulation.Unit;

public class BubbleSort implements SortingAlgorithm {
    private Unit[] arr;
    private int n;
    private int i;
    private int j;
    private int totalCount;

    public BubbleSort(Unit[] arr) {
        this.arr = arr;
        this.n = arr.length;
        this.i = 0;
        this.j = 0;
        this.totalCount = 0;
    }

    @Override
    public boolean sort() {
        System.out.println("i = " + i + " j = " + j);
        n = arr.length;
        for (i = i; i < n - 1; i++) {
            for (; j < n - i - 1; j++) {
                if (arr[j].getValue() > arr[j + 1].getValue()) {
                    Unit temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    totalCount += j;
                }
                j++;
                return true;
            }
            j = 0;
        }
        return false;
    }

    @Override
    public int getCurrentIndex() {
        return j;
    }

    @Override
    public int getComparedIndex() {
        return j + 1;
    }

    @Override
    public boolean isCycleCompleted() {
        return !(j < n - i - 2);
    }
}


