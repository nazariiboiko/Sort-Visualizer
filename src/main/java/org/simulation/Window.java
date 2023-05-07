package org.simulation;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.simulation.algorithms.BubbleSort;
import org.simulation.algorithms.InsertionSort;
import org.simulation.algorithms.MergeSort;
import org.simulation.algorithms.SortingAlgorithm;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

public class Window {
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 600;
    private static final int N = 200;
    private Unit[] array;
    private long window;
    private SortingAlgorithm sortingMethod;
    private boolean isSorting;
    private int timeDelay = 1;
    private float scale = 1f;
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

            drawInfo();

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
        GL11.glVertex2f(0 , WINDOW_HEIGHT / 2);
        GL11.glVertex2f(WINDOW_WIDTH , WINDOW_HEIGHT / 2);

        GL11.glVertex2f(WINDOW_WIDTH, 0);
        GL11.glVertex2f(WINDOW_WIDTH, WINDOW_HEIGHT);
        GL11.glVertex2f(0, WINDOW_HEIGHT);
        GL11.glVertex2f(0, 0);

        GL11.glEnd();
    }

    private void drawInfo() {
        String text = "ABCD";
        int s = 256; //Take whatever size suits you.
        BufferedImage b = new BufferedImage(s, s, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g = b.createGraphics();
        g.drawString(text, 0, 0);

        int co = b.getColorModel().getNumComponents();

        byte[] data = new byte[co * s * s];
        b.getRaster().getDataElements(0, 0, s, s, data);

        ByteBuffer pixels = BufferUtils.createByteBuffer(data.length);
        pixels.put(data);
        pixels.rewind();
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, s, s, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, pixels);
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
        float top = array[i].getValue() * scale;

        GL11.glVertex2f(startPoint, centerY);
        GL11.glVertex2f(startPoint, centerY + top);
        GL11.glVertex2f(startPoint + offset, centerY + top);
        GL11.glVertex2f(startPoint + offset, centerY);
        i = sortingMethod.getComparedIndex();
        if(!sortingMethod.isCycleCompleted()) {
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
        } else if (key == GLFW.GLFW_KEY_2 && action == GLFW.GLFW_PRESS) {
            sortingMethod = new InsertionSort(array);
            isSorting = true;
        } else if (key == GLFW.GLFW_KEY_3 && action == GLFW.GLFW_PRESS) {
            sortingMethod = new MergeSort(array);
            isSorting = true;
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
