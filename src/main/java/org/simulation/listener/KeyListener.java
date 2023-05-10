package org.simulation.listener;

import org.simulation.Window;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

public class KeyListener extends GLFWKeyCallback {
    private Window window;

    public KeyListener(Window window) {
        this.window = window;
    }

    @Override
    public void invoke(long window, int key, int scancode, int action, int mods) {
        if (key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_PRESS) {
            GLFW.glfwSetWindowShouldClose(window, true);
        }
        this.window.handleKeyEvent(key, action);
    }
}
