package org.simulation;

public enum Color {

    WHITE(new float[]{1f, 1f, 1f}),
    GREEN(new float[]{0.2f, 1f, 0.2f}),
    ORANGE(new float[]{1f, 0.5f, 0.2f}),
    RED(new float[]{1f, 0f, 0f}),
    DEFAULT(WHITE);

    private final float[] mark;

    Color(float[] mark) {
        this.mark = mark;
    }

    Color(Color color) {
        this.mark = color.mark;
    }

    public float[] getValue() {
        return mark;
    }
}