package org.simulator;

import org.lwjgl.*;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class Window {
    private static final int WINDOW_WIDTH = 600;
    private static final int WINDOW_HEIGHT = 600;
    private static final int N = 100;
    private Unit[] array;
    private long window;

    public Window() {
        init();
        render();
    }

    private void init() {
        GLFW.glfwInit();
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE);
        window = GLFW.glfwCreateWindow(WINDOW_WIDTH, WINDOW_HEIGHT, "Sorting Visualization", 0, 0);
        GLFW.glfwMakeContextCurrent(window);
        GL.createCapabilities();
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, WINDOW_WIDTH, 0, WINDOW_HEIGHT, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);

        array = new Unit[N];
        UnitUtil.fillWithRandomValues(array, 100);
        for(int i = 0; i < array.length; i++)
            System.out.println(array[i].getValue());
    }

    private void render() {
        while (!GLFW.glfwWindowShouldClose(window)) {
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
            GL11.glClearColor(1f, 1f, 1f, 1f);
            ////
            drawGrid();

            ////
            GLFW.glfwSwapBuffers(window);
            GLFW.glfwPollEvents();
        }
        GLFW.glfwTerminate();
    }

    private void drawGrid() {
        GL11.glColor3f(0f, 0f, 0f);
        GL11.glBegin(GL11.GL_LINE_STRIP);
        GL11.glVertex2f(0, WINDOW_HEIGHT / 2);
        GL11.glVertex2f(WINDOW_WIDTH, WINDOW_HEIGHT / 2);
        GL11.glEnd();
    }

    static public void main(String[] args) {
        new Window();
    }
}
