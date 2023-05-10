package org.simulation.ui;

import org.simulation.Mediator;
import org.simulation.SortArray;
import org.simulation.Window;
import org.simulation.algorithm.NoneSort;
import org.simulation.algorithm.SortingAlgorithm;
import org.lwjgl.opengl.GL11;

import java.awt.*;


public class ComponentArray implements Component {
    private SortArray sortArray;
    private Point windowSize;
    private SortingAlgorithm sortingAlgorithm;
    private Mediator mediator;

    public ComponentArray(Window window, Point windowSize, int size) {
        this.sortArray = new SortArray(size);
        this.windowSize = windowSize;
        this.sortingAlgorithm = new NoneSort();
    }

    public void setSortingAlgorithm(SortingAlgorithm sortingAlgorithm) {
        this.sortingAlgorithm = sortingAlgorithm;
    }

    public void startSorting() {
        if(sortArray.isSorting())
            return;
        sortArray.resetCounter();
        sortArray.setSorting(true);
        Runnable runSort = () -> this.sortArray.setSorting(sortingAlgorithm.sort(sortArray));
        Thread thread = new Thread(runSort);
        thread.start();

    }

    public void stopSorting() {
        sortArray.setSorting(false);
        sortArray.unmarkAll();
    }

    public void shuffle() {
        sortArray.setSorting(false);
        sortArray.lock();
        sortArray.instantShuffle();
        sortArray.unlock();
    }

    public void update() {
        float padding = 9;
        float topPadding = 20;
        float scale = (windowSize.y - topPadding * 2)/ sortArray.getSize();
        float columnWidth = (windowSize.x - (windowSize.x / 6f) - padding * 2) / sortArray.getSize() - 1;
        sortArray.lock();
        GL11.glBegin(GL11.GL_QUADS);
        for(int i = 0; i < sortArray.getSize(); i++) {
            float[] rgb = sortArray.getMark(i).getValue();
            GL11.glColor3f(rgb[0], rgb[1], rgb[2]);
            float startPoint = i + i * columnWidth + padding;
            GL11.glVertex2f(startPoint, padding);
            GL11.glVertex2f(startPoint, sortArray.getValue(i, false) * scale + padding);
            GL11.glVertex2f(startPoint + columnWidth,sortArray.getValue(i, false) * scale + padding);
            GL11.glVertex2f(startPoint + columnWidth, padding);
        }
        sortArray.unlock();
        GL11.glEnd();
        //sortArray.unmarkAll();
        StringBuilder sb = new StringBuilder();
        sb.append("Algorithm: " + sortingAlgorithm.getName() + "     ");
        sb.append("Delay: " + "0." + sortArray.getDelay() + "ms     ");
        sb.append("Comparisons: " + sortArray.getComparisons() + "     ");
        sb.append("Array Accesses: " + sortArray.getAccesses() + "     ");
        mediator.drawString(sb.toString(), 10, windowSize.y - 25, Color.BLACK, Color.WHITE);
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public String getName() {
        return "ComponentArray";
    }
}
