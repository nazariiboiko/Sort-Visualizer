package org.experimental.algorithms;

import org.experimental.ColorMark;
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
                    arr.mark(j, ColorMark.GREEN);
                    arr.mark(j + 1, ColorMark.ORANGE);
                    arr.unlock();
                }
            }
        return false;
    }
}


