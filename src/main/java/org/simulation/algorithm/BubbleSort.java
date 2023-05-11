package org.simulation.algorithm;

import org.simulation.SortArray;

public class BubbleSort implements SortingAlgorithm {
    @Override
    public String getName() {
        return "BubbleSort\t";
    }

    @Override
    public boolean sort(SortArray arr) {
        int n = arr.getSize();
        for (int i = 0; i < n - 1; i++)
            for (int j = 0; j < n - i - 1; j++) {
                if (arr.compare(j, j+1)) {
                    if(!arr.getAccess())
                        return false;
                    arr.swap(j, j + 1);
                }
            }
        return false;
    }

}


