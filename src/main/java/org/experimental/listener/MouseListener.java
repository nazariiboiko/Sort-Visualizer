package org.experimental.listener;

import org.experimental.Window;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

import static org.lwjgl.glfw.GLFW.*;

public class MouseListener extends GLFWMouseButtonCallback {
    private Window window;

    public MouseListener(Window window) {
        this.window = window;
    }

    @Override
    public void invoke(long window, int button, int action, int mods) {
        if (button == GLFW_MOUSE_BUTTON_LEFT && action == GLFW_PRESS) {
            // Left mouse button pressed
        } else if (button == GLFW_MOUSE_BUTTON_RIGHT && action == GLFW_PRESS) {
            // Right mouse button pressed
        } else if (button == GLFW_MOUSE_BUTTON_LEFT && action == GLFW_RELEASE) {
            this.window.onClick();
        } else if (button == GLFW_MOUSE_BUTTON_RIGHT && action == GLFW_RELEASE) {
            // Right mouse button released
        }
    }
}
