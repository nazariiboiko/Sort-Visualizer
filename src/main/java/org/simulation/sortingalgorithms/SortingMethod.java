package org.simulation.sortingalgorithms;

import org.simulation.Unit;

public interface SortingMethod {
    boolean sort();
    int getCurrentIndex();
    boolean isCycleComplete();
}
