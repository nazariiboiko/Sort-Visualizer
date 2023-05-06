package org.simulation;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.simulation.sortingalgorithms.BubbleSort;
import org.simulation.sortingalgorithms.SortingMethod;

public class Window {
    private static final int WINDOW_WIDTH = 1000;
    private static final int WINDOW_HEIGHT = 600;
    private static final int N = 100;
    private Unit[] array;
    private long window;
    private SortingMethod sortingMethod;
    private boolean isSorting;
    private int timeDelay = 5;
    private int scale = 2;
    private int offset;
    private int borderX = WINDOW_WIDTH / 10;
    private int centerY;
    private int centerX;

    public Window() {
        init();
        draw();
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
        GLFW.glfwSetKeyCallback(window, keyCallback);

        array = new Unit[N];
        UnitUtil.fillFrom1ToN(array);
        isSorting = false;
        offset = (WINDOW_WIDTH - (3 * borderX)) / N;
        centerY = WINDOW_HEIGHT / 2;
        centerX = WINDOW_WIDTH / 2;
    }

    private void draw() {
        while (!GLFW.glfwWindowShouldClose(window)) {
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
            GL11.glClearColor(1f, 1f, 1f, 1f);
            ////
            drawGrid();

            if(isSorting)
                doOneIteration();

            GL11.glColor3f(0f, 0f, 0f);
            GL11.glBegin(GL11.GL_LINE_STRIP);
            for(int i = 0; i < array.length; i++) {
                int startPoint = i + i * offset + borderX;
                GL11.glVertex2f(startPoint, centerY);
                GL11.glVertex2f(startPoint, centerY + array[i].getValue() * scale);
                GL11.glVertex2f(startPoint + offset, centerY + array[i].getValue() * scale);
                GL11.glVertex2f(startPoint + offset, centerY);
            }

            GL11.glEnd();
            ////
            GLFW.glfwSwapBuffers(window);
            GLFW.glfwPollEvents();
        }

        keyCallback.free();
        GLFW.glfwTerminate();
    }

    private void drawGrid() {
        GL11.glColor3f(0f, 0f, 0f);
        GL11.glBegin(GL11.GL_LINE_STRIP);
        GL11.glVertex2f(0 + borderX, WINDOW_HEIGHT / 2);
        GL11.glVertex2f(WINDOW_WIDTH - borderX, WINDOW_HEIGHT / 2);

        GL11.glVertex2f(WINDOW_WIDTH - borderX, 0);
        GL11.glVertex2f(WINDOW_WIDTH - borderX, WINDOW_HEIGHT);
        GL11.glVertex2f(0 + borderX, WINDOW_HEIGHT);
        GL11.glVertex2f(0 + borderX, 0);

        GL11.glEnd();
    }

    private void doOneIteration() {
        try {
            Thread.sleep(timeDelay);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        isSorting = sortingMethod.sort();
        GL11.glColor3f(0.2f, 1f, 0.2f);

        GL11.glBegin(GL11.GL_QUADS);
        int i = sortingMethod.getCurrentIndex();
        int startPoint = i + i * offset + borderX;
        int top = array[i].getValue() * scale;

        GL11.glVertex2f(startPoint, centerY);
        GL11.glVertex2f(startPoint, centerY + top);
        GL11.glVertex2f(startPoint + offset, centerY + top);
        GL11.glVertex2f(startPoint + offset, centerY);
        i += 1;
        if(!sortingMethod.isCycleComplete()) {
            GL11.glColor3f(1f, 0.5f, 0.2f);
            startPoint = i + i * offset + borderX;
            top = array[i].getValue() * scale;

            GL11.glVertex2f(startPoint, centerY);
            GL11.glVertex2f(startPoint, centerY + top);
            GL11.glVertex2f(startPoint + offset, centerY + top);
            GL11.glVertex2f(startPoint + offset, centerY);
        }
        GL11.glEnd();
    }

    private void handleKeyEvent(int key, int action) {
        if (key == GLFW.GLFW_KEY_S && action == GLFW.GLFW_PRESS) {
            UnitUtil.shuffle(array);
            isSorting = false;
        } else if (key == GLFW.GLFW_KEY_D && action == GLFW.GLFW_PRESS) {
            UnitUtil.fillWithRandomValues(array, 100);
            isSorting = false;
        } else if (key == GLFW.GLFW_KEY_1 && action == GLFW.GLFW_PRESS) {
            sortingMethod = new BubbleSort(array);
            isSorting = true;
        } else if (key == GLFW.GLFW_KEY_RIGHT && action == GLFW.GLFW_PRESS) {
            System.out.println("Right arrow key pressed");
        }
    }

    private GLFWKeyCallback keyCallback = new GLFWKeyCallback() {
        @Override
        public void invoke(long window, int key, int scancode, int action, int mods) {
            if (key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_PRESS) {
                GLFW.glfwSetWindowShouldClose(window, true);
            }
            handleKeyEvent(key, action);
        }
    };

    static public void main(String[] args) {
        new Window();
    }
}
