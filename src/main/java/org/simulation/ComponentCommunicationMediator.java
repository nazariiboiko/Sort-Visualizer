package org.simulation;

import org.simulation.algorithm.SortingAlgorithm;
import org.simulation.ui.Component;
import org.simulation.ui.ComponentArray;
import org.simulation.ui.ComponentMenu;

import java.awt.Color;

public class ComponentCommunicationMediator implements Mediator {
    private Window window;
    private ComponentArray componentArray;
    private ComponentMenu componentMenu;

    @Override
    public void registerComponent(Component component) {
        component.setMediator(this);
        switch (component.getName()) {
            case "Window":
                window = (Window) component;
                break;
            case "ComponentArray":
                componentArray = (ComponentArray) component;
                break;
            case "ComponentMenu":
                componentMenu = (ComponentMenu) component;
                break;
        }
    }

    @Override
    public void updateAllComponents() {
        componentArray.update();
        componentMenu.update();
    }

    @Override
    public void setSortingAlgorithm(SortingAlgorithm sortingAlgorithm) {
        componentArray.setSortingAlgorithm(sortingAlgorithm);
    }

    @Override
    public void setSoundStatus(boolean flag) {
        componentArray.setSoundStatus(flag);
    }

    @Override
    public void shuffle() {
       componentArray.shuffle();
    }

    @Override
    public void startSorting() {
        componentArray.startSorting();
    }

    @Override
    public void stop() {
        componentArray.stopSorting();
    }

    @Override
    public void sendClickIvent(int x, int y) {
        componentMenu.invokeButton(x, y);
    }

    @Override
    public void drawString(String str, int x, int y, Color background, Color fontColor) {
        window.drawString(str,x,y,background,fontColor);
    }
}
