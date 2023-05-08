package org.experimental;

public enum ColorMark {
    WHITE (new float[]{1f, 1f, 1f}),
    GREEN (new float[]{0.2f, 1f, 0.2f}),
    ORANGE(new float[]{1f, 0.5f, 0.2f});

    private float[] mark;
    ColorMark(float[] mark) {
        this.mark = mark;
    }
    public float[] getValue() {
        return mark;
    }
}
