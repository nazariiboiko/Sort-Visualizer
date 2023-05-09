package org.experimental.algorithms;

import org.experimental.Color;
import org.experimental.SortArray;

public class BubbleSort implements SortingAlgorithm {
    @Override
    public boolean sort(SortArray arr) {
        int n = arr.getSize();
        for (int i = 0; i < n - 1; i++)
            for (int j = 0; j < n - i - 1; j++) {
                System.out.println("i = " + i + " j = " + j);
                if (arr.getValue(j) > arr.getValue(j + 1)) {
                    arr.lock();
                    arr.swap(j, j + 1);
                    arr.mark(j, Color.GREEN);
                    arr.mark(j + 1, Color.ORANGE);
                    arr.unlock();
                }
            }
        return false;
    }
}


