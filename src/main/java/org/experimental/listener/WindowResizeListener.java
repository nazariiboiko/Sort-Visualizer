package org.experimental.listener;

import org.experimental.Window;
import org.lwjgl.glfw.GLFWWindowSizeCallback;

public class WindowResizeListener extends GLFWWindowSizeCallback {
    private Window window;

    public WindowResizeListener(Window window) {
        this.window = window;
    }

    @Override
    public void invoke(long window, int width, int height) {
        this.window.setWindowSize(width, height);
    }
}
