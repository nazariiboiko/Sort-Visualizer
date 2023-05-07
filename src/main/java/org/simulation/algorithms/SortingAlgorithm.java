package org.simulation.algorithms;

public interface SortingAlgorithm {
    boolean sort();
    int getCurrentIndex();
    int getComparedIndex();
    boolean isCycleCompleted();
}
