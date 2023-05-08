package org.simulation.algorithms;

import org.simulation.Unit;

public class QuickSort implements SortingAlgorithm {
    private Unit[] arr;

    public QuickSort(Unit[] arr) {
        this.arr = arr;
    }

    @Override
    public boolean sort() {
        return false;
    }

    @Override
    public int getCurrentIndex() {
        return 0;
    }

    @Override
    public int getComparedIndex() {
        return 0;
    }

    @Override
    public boolean isCycleCompleted() {
        return false;
    }
}
