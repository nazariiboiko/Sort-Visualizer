package org.experimental.ui;

import org.experimental.Window;
import org.experimental.Color;
import org.lwjgl.opengl.GL11;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class WindowMenu {
    private Window window;
    private List<Button> buttons;
    private Point windowSize;
    public WindowMenu(Window window, Point windowSize) {
        this.windowSize = windowSize;
        this.window = window;
        buttons = new ArrayList<>();
        initButtons();
    }

    private void initButtons() {
        addButton(windowSize.x - 190, windowSize.y - 70, 180, 50, () -> System.out.println("run!!"), "Run");
        addButton(windowSize.x - 190, windowSize.y - 140, 180, 50, () -> System.out.println("reset!!"), "Reset");
        addButton(windowSize.x - 190, windowSize.y - 210, 180, 50, () -> System.out.println("step!!"), "Step");

    }

    public void addButton(int x, int y, int width, int height, ButtonAction buttonAction, String text) {
        Button button = new Button(x, y, width, height, windowSize.x, windowSize.y, buttonAction, text);
        buttons.add(button);
    }

    public void invokeButton(int x, int y) {
        for(Button button : buttons) {
            if(button.checkButton(x, y, windowSize.x, windowSize.y))
                button.onClick();
        }
    }

    public void checkButton(int x, int y) {
        for(Button button : buttons) {
            if(button.checkButton(x, y, windowSize.x, windowSize.y)) {

            }
        }
    }

    public void update() {
        GL11.glColor3f(1f, 1f, 1f);
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(windowSize.x - windowSize.x / 6,0);
        GL11.glVertex2f(windowSize.x - windowSize.x / 6,windowSize.y);
        GL11.glVertex2f(windowSize.x,windowSize.y);
        GL11.glVertex2f(windowSize.x,0);
        GL11.glEnd();
        GL11.glColor3f(0f, 0f, 0f);
        for(Button button : buttons) {
            GL11.glBegin(GL11.GL_LINE_STRIP);
            float x = (float)windowSize.x / button.xScale;
            float y = (float)windowSize.y / button.yScale;
            float width = (float)windowSize.x / button.widthScale;
            float height = (float)windowSize.y / button.heightScale;
            GL11.glVertex2f(x,y);
            GL11.glVertex2f(x + width,y);
            GL11.glVertex2f(x + width,y + height);
            GL11.glVertex2f(x,y + height);
            GL11.glVertex2f(x,y);
            GL11.glEnd();
            window.drawString(button.text, (int) ((int) x + width / 2 - 25), (int) y + 10);
        }
    }
}
