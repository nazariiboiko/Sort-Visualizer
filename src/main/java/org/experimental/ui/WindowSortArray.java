package org.experimental.ui;

import org.experimental.SortArray;
import org.experimental.Window;
import org.lwjgl.opengl.GL11;

import java.awt.*;


public class WindowSortArray {
    private Window window;
    private SortArray sortArray;
    private Point windowSize;

    public WindowSortArray(Window window, Point windowSize, int size) {
        this.window = window;
        this.sortArray = new SortArray(size);
        this.windowSize = windowSize;
    }

    public void update() {
        int padding = 5;

        float columnWidth = (windowSize.x - (windowSize.x / 6f) - padding * 2) / sortArray.getSize() - 1;
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glColor3f(0f, 1f, 1f);
        for(int i = 0; i < sortArray.getSize(); i++) {
            float startPoint = i + i * columnWidth + padding;
            GL11.glVertex2f(startPoint, padding);
            GL11.glVertex2f(startPoint, padding + sortArray.getValue(i) * 2f);
            GL11.glVertex2f(startPoint + columnWidth, padding + sortArray.getValue(i) * 2f);
            GL11.glVertex2f(startPoint + columnWidth, padding);
        }
        GL11.glEnd();
    }
}
