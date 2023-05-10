package org.simulation.ui;

import org.simulation.Mediator;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ComponentMenu implements Component {
    private List<Button> buttons;
    private Point windowSize;
    private Mediator mediator;
    public ComponentMenu(Point windowSize) {
        this.windowSize = windowSize;
        buttons = new ArrayList<>();
        initButtons();
    }

    private void initButtons() {
        addButton(windowSize.x - 190, windowSize.y - 70, 180, 50, () -> mediator.startSorting(), " Run");
        addButton(windowSize.x - 190, windowSize.y - 140, 180, 50, () -> mediator.reset(), " Stop");
        addButton(windowSize.x - 190, windowSize.y - 210, 180, 50, () -> System.out.println("step!!"), " Step");
        addButton(windowSize.x - 190, windowSize.y - 280, 180, 50, () -> System.out.println("sound!!"), "Sound");

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
        float y = 0;
        for(Button button : buttons) {
            GL11.glBegin(GL11.GL_LINE_STRIP);
            float x = (float)windowSize.x / button.xScale;
            y = (float)windowSize.y / button.yScale;
            float width = (float)windowSize.x / button.widthScale;
            float height = (float)windowSize.y / button.heightScale;
            GL11.glVertex2f(x,y);
            GL11.glVertex2f(x + width,y);
            GL11.glVertex2f(x + width,y + height);
            GL11.glVertex2f(x,y + height);
            GL11.glVertex2f(x,y);
            GL11.glEnd();
            mediator.drawString(button.text, (int) ((int) x + width / 2 - 25), (int) y + 10, Color.WHITE, Color.BLACK);
        }
        int x = windowSize.x - windowSize.x / 6 + 5;
        y -= 50;
        mediator.drawString("S - Shuffle", x, (int) y, Color.WHITE, Color.BLACK);
        mediator.drawString("1 - Bubble Sort",  x, (int) y - 25, Color.WHITE, Color.BLACK);
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public String getName() {
        return "ComponentMenu";
    }
}
