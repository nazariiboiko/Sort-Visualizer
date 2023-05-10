package org.simulation.algorithm;

import org.simulation.SortArray;

public class NoneSort implements SortingAlgorithm {
    @Override
    public String getName() {
        return "None";
    }

    @Override
    public boolean sort(SortArray arr) {
        return false;
    }
}
