package org.simulation.algorithm;

import org.simulation.Mark;
import org.simulation.SortArray;

public class InsertionSort implements SortingAlgorithm {
    @Override
    public String getName() {
        return "InsertionSort";
    }

    @Override
    public boolean sort(SortArray arr) {
        for (int i = 1; i < arr.getSize(); ++i) {
            if(!arr.getAccess())
                return false;
            int key = arr.getValue(i);
            arr.mark(i, Mark.GREEN);
            int j = i - 1;
            while(j >= 0 && arr.getValue(j) > key) {
                arr.increaseCompare();
                if(!arr.getAccess())
                    return false;
                arr.swap(j, j+1);
                arr.mark(i, Mark.GREEN);
                j--;
            }
            arr.unmark(i);
        }
        arr.resetCurrentMark();
        return false;
    }
}
