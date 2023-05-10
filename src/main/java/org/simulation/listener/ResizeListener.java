package org.simulation.listener;

import org.simulation.Window;
import org.lwjgl.glfw.GLFWWindowSizeCallback;

public class ResizeListener extends GLFWWindowSizeCallback {
    private Window window;

    public ResizeListener(Window window) {
        this.window = window;
    }

    @Override
    public void invoke(long window, int width, int height) {
        this.window.setWindowSize(width, height);
    }
}
