package org.simulation;

import org.simulation.algorithm.SortingAlgorithm;
import org.simulation.ui.Component;

import java.awt.Color;

public interface Mediator {
    void registerComponent(Component component);
    void updateAllComponents();
    void sendClickIvent(int x, int y);
    void drawString(String str, int x, int y, java.awt.Color background, Color fontColor);
    void setSortingAlgorithm(SortingAlgorithm sortingAlgorithm);
    void shuffle();
    void startSorting();
    void reset();
}
