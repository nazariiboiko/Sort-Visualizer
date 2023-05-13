package org.simulation.algorithm;

import org.simulation.Mark;
import org.simulation.SortArray;

public class MergeSort implements SortingAlgorithm {
    @Override
    public String getName() {
        return "MergeSort";
    }

    @Override
    public boolean sort(SortArray arr) {
        sort(arr, 0, arr.getSize());
        return false;
    }

    private void sort(SortArray arr, int l, int r) {
        if (l + 1 < r) {
            if(!arr.getAccess())
                return;
            int m = (l + r) / 2;
            sort(arr, l, m);
            sort(arr, m, r);
            merge(arr, l, m, r);
        }
    }

    private void merge(SortArray arr, int l, int m, int r) {
        if(!arr.getAccess())
            return;
        arr.mark(l, Mark.GREEN);
        arr.mark(m,Mark.CYAN);
        arr.mark(r-1, Mark.GREEN);

        int[] out = new int[r - l];

        int i = l, j = m, o = 0;
        while (i < m && j < r) {
            if(!arr.getAccess())
                return;
            if(arr.compare(j, i)) {
                out[o++] = arr.getValue(i, false);
                i++;
            } else {
                out[o++] = arr.getValue(j, false);
                j++;
            }
        }

        while (i < m) out[o++] = arr.getValue(i++, false);
        while (j < r) out[o++] = arr.getValue(j++, false);

        for (i = 0; i < r-l; ++i) {
            if (!arr.getAccess())
                return;
                arr.setValue(l + i, out[i]);
        }
        arr.resetCurrentMark();
    }
}
