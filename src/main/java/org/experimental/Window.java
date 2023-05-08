package org.experimental;

import org.experimental.algorithms.BubbleSort;
import org.experimental.algorithms.SortingAlgorithm;
import org.experimental.listener.KeyListener;
import org.experimental.listener.WindowResizeListener;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class Window {
    private int windowWidth = 1200;
    private int windowHeight = 600;
    private static final int N = 100;
    private SortArray array;
    private long window;
    private GLFWKeyCallback keyCallback;
    private GLFWWindowSizeCallback sizeCallback;
    private SortingAlgorithm sortingAlgorithm;
    private boolean isSorting;
    private int timeDelay = 3;
    private float scale = 2f;
    private int offset;
    private int borderX;
    private int centerY;
    private int centerX;

    public Window() {
        init();
        registerCallback();
        draw();
    }

    private void init() {
        GLFW.glfwInit();
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);
        window = GLFW.glfwCreateWindow(windowWidth, windowHeight, "Sorting Visualization", 0, 0);
        GLFW.glfwMakeContextCurrent(window);
        GL.createCapabilities();
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, windowWidth, 0, windowHeight, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);

        sizeCallback = new WindowResizeListener(this);
        keyCallback = new KeyListener(this);

        array = new SortArray(N);
        isSorting = false;
    }

    private void registerCallback() {
        GLFW.glfwSetWindowSizeCallback(window, sizeCallback);
        GLFW.glfwSetKeyCallback(window, keyCallback);
    }

    public void setSortingAlgorithm(SortingAlgorithm sortingAlgorithm) {
        this.sortingAlgorithm = sortingAlgorithm;
    }

    public void setWindowSize(int windowWidth, int windowHeight) {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
    }

    public void setSorting(boolean flag) {
        isSorting = flag;
    }

    private void draw() {
        while (!GLFW.glfwWindowShouldClose(window)) {
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
            GL11.glClearColor(1f, 1f, 1f, 1f);
            IntBuffer w = BufferUtils.createIntBuffer(1);
            IntBuffer h = BufferUtils.createIntBuffer(1);
            GLFW.glfwGetWindowSize(window, w, h);
            windowWidth = w.get(0);
            windowHeight = h.get(0);
            offset = (windowWidth - (3 * borderX)) / N;
            centerY = windowHeight / 2;
            centerX = windowWidth / 2;
            borderX = windowWidth / 10;
            ////
            GL11.glColor3f(0f, 0f, 0f);
            GL11.glBegin(GL11.GL_LINE_STRIP);

            GL11.glVertex2f(w.get(0) - (w.get(0) / 6),0);
            GL11.glVertex2f(w.get(0) - w.get(0) / 6,windowHeight);



            for(int i = 0; i < array.getSize(); i++) {
                int startPoint = i + i * offset + borderX;
                GL11.glVertex2f(startPoint, centerY);
                GL11.glVertex2f(startPoint, centerY + array.getValue(i) * scale);
                GL11.glVertex2f(startPoint + offset, centerY + array.getValue(i) * scale);
                GL11.glVertex2f(startPoint + offset, centerY);
            }


            GL11.glEnd();
            ////
            GLFW.glfwSwapBuffers(window);
            GLFW.glfwPollEvents();
        }

        keyCallback.free();
        sizeCallback.free();
        GLFW.glfwTerminate();
    }

//    private void drawGrid() {
//        GL11.glColor3f(0f, 0f, 0f);
//        GL11.glBegin(GL11.GL_LINE_STRIP);
//        GL11.glVertex2f(0 , WINDOW_HEIGHT / 2);
//        GL11.glVertex2f(WINDOW_WIDTH , WINDOW_HEIGHT / 2);
//
//        GL11.glVertex2f(WINDOW_WIDTH, 0);
//        GL11.glVertex2f(WINDOW_WIDTH, WINDOW_HEIGHT);
//        GL11.glVertex2f(0, WINDOW_HEIGHT);
//        GL11.glVertex2f(0, 0);
//
//        GL11.glEnd();
//    }

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

//    private void doOneIteration() {
//        if(isSorting) {
//            Runnable t = () -> {
//               isSorting = sortingMethod.sort();
//            };
//            new Thread(t).start();
//        }
//        array.lock();
//        GL11.glColor3f(0.2f, 1f, 0.2f);
//
//        GL11.glBegin(GL11.GL_QUADS);
//        int i = sortingMethod.getCurrentIndex();
//        int startPoint = i + i * offset + borderX;
//        float top = array.getValue(i) * scale;
//
//        GL11.glVertex2f(startPoint, centerY);
//        GL11.glVertex2f(startPoint, centerY + top);
//        GL11.glVertex2f(startPoint + offset, centerY + top);
//        GL11.glVertex2f(startPoint + offset, centerY);
//        i = sortingMethod.getComparedIndex();
//        if(!sortingMethod.isCycleCompleted()) {
//            GL11.glColor3f(1f, 0.5f, 0.2f);
//            startPoint = i + i * offset + borderX;
//            top = array.getValue(i) * scale;
//
//            GL11.glVertex2f(startPoint, centerY);
//            GL11.glVertex2f(startPoint, centerY + top);
//            GL11.glVertex2f(startPoint + offset, centerY + top);
//            GL11.glVertex2f(startPoint + offset, centerY);
//        }
//        array.unlock();
//        GL11.glEnd();
//    }

    static public void main(String[] args) {
        new Window();
    }
}
