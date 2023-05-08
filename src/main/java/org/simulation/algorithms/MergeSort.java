package org.simulation.algorithms;

import org.simulation.Unit;

public class MergeSort implements SortingAlgorithm {

    private Unit[] arr;
    private final int l;
    private final int r;
    private int m;
    private int i;
    private int j;
    private int n1;
    private int n2;
    private Unit L[];
    private Unit R[];
    private int k;
    private int cycleRemains;

    public MergeSort(Unit[] arr) {
        this.arr = arr;
        l = 0;
        r = arr.length - 1;
        i = 0;
        j = 0;
        m = l + (r - l) / 2;
        n1 = m - l + 1;
        n2 = r - m;
        L = new Unit[n1];
        R = new Unit[n2];
        for (int i = 0; i < n1; ++i)
            L[i] = arr[l + i];
        for (int j = 0; j < n2; ++j)
            R[j] = arr[m + 1 + j];
        k = l;
        cycleRemains = 0;
    }

    private boolean merge()
    {
        while (i < n1 && j < n2) {
            if (L[i].getValue() <= R[j].getValue()) {
                arr[k] = L[i];
                i++;
                k++;
            }
            else {
                arr[k] = R[j];
                j++;
                k++;

            }
            return true;
        }

            while (i < n1) {
                arr[k] = L[i];
                i++;
                k++;
                return true;
            }

            while (j < n2) {
                arr[k] = R[j];
                j++;
                k++;
                return true;
            }
//        System.out.println("i=" + i + " j=" + j + " n1=" + n1 + " n2=" + n2 + " k=" + k);
        if(n1 == 1)
            cycleRemains++;
        if(cycleRemains >= 3)
            return false;
        System.out.println(cycleRemains);
        i = 0;
        j = 0;
        n1 = m - l + 1;
        n2 = r - m;
        L = new Unit[n1];
        R = new Unit[n2];
        for (int z = 0; z < n1; ++z)
            L[z] = arr[l + z];
        for (int x = 0; x < n2; ++x)
            R[x] = arr[m + 1 + x];
        k = l;
        return true;
    }

    @Override
    public boolean sort() {
        if (l < r) {
            sort(l, m);
            sort(m + 1, r);
            return merge();
        }
        return false;
    }

    private boolean sort(int l, int r)
    {
        if (l < r) {
            m = l + (r - l) / 2;
            sort(l, m);
            sort(m + 1, r);
            return merge();
        }
        return false;
    }

    @Override
    public int getCurrentIndex() {
        return i;
    }

    @Override
    public int getComparedIndex() {
        return j;
    }

    @Override
    public boolean isCycleCompleted() {
        return false;
    }
}
