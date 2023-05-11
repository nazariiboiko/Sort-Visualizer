package org.simulation;

public enum Mark {

    WHITE(new float[]{1f, 1f, 1f}),
    GREEN(new float[]{0.2f, 1f, 0.2f}),
    ORANGE(new float[]{1f, 0.5f, 0.2f}),
    RED(new float[]{1f, 0f, 0f}),
    DEFAULT(WHITE);

    private final float[] mark;

    Mark(float[] mark) {
        this.mark = mark;
    }

    Mark(Mark mark) {
        this.mark = mark.mark;
    }

    public float[] getValue() {
        return mark;
    }
}