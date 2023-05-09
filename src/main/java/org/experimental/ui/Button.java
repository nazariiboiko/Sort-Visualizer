package org.experimental.ui;

import org.experimental.Color;

public class Button {
    public final float xScale;
    public final float yScale;
    public final float widthScale;
    public final float heightScale;
    public final String text;
    private ButtonAction buttonAction;
    private Color color;

    public Button(int x, int y, int width, int height, int windowWidth, int windowHeight, ButtonAction buttonAction, String text) {
        this.xScale = (float)windowWidth / x;
        this.yScale = (float)windowHeight / y;
        this.widthScale = (float)windowWidth / width;
        this.heightScale = (float)windowHeight / height;
        this.buttonAction = buttonAction;
        this.text = text;
    }

    public boolean checkButton(int mousePosX, int mousePosY, int windowWidth, int windowHeight) {
        float x = windowWidth / xScale;
        float y = windowHeight / yScale;
        float width = windowWidth / widthScale;
        float height = windowHeight / heightScale;
        return (mousePosX >= x && mousePosX <= x + width && mousePosY >= y && mousePosY <= y + height);
    }

    public void onClick() {
        buttonAction.onClick();
    }
}
