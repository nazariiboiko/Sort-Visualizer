package org.experimental.listener;

import org.experimental.Window;
import org.experimental.algorithms.BubbleSort;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

public class KeyListener extends GLFWKeyCallback{
    private Window window;

    public KeyListener(Window window) {
        this.window = window;
    }

    @Override
    public void invoke(long window, int key, int scancode, int action, int mods) {
        if (key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_PRESS) {
            GLFW.glfwSetWindowShouldClose(window, true);
        }
        handleKeyEvent(key, action);
    }

    private void handleKeyEvent(int key, int action) {
        if (key == GLFW.GLFW_KEY_S && action == GLFW.GLFW_PRESS) {
            //array.shuffle();
            window.setSorting(false);
        } else if (key == GLFW.GLFW_KEY_D && action == GLFW.GLFW_PRESS) {
            //UnitUtil.fillWithRandomValues(array, 100);
            window.setSorting(false);
        } else if (key == GLFW.GLFW_KEY_1 && action == GLFW.GLFW_PRESS) {
            window.setSortingAlgorithm(new BubbleSort());
            window.setSorting(true);
        } else if (key == GLFW.GLFW_KEY_2 && action == GLFW.GLFW_PRESS) {
            //sortingMethod = new InsertionSort(array);
            //isSorting = true;
        } else if (key == GLFW.GLFW_KEY_3 && action == GLFW.GLFW_PRESS) {
            //sortingMethod = new MergeSort(array);
            //isSorting = true;
        }
    }
}
