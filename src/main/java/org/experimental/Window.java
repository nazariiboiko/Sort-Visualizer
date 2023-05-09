package org.experimental;

import org.experimental.algorithms.SortingAlgorithm;
import org.experimental.listener.KeyListener;
import org.experimental.listener.MouseListener;
import org.experimental.listener.WindowResizeListener;
import org.experimental.ui.WindowMenu;
import org.experimental.ui.WindowSortArray;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class Window {
    private int windowWidth = 1200;
    private int windowHeight = 600;
    private Point windowSize;
    private long window;
    private GLFWKeyCallback keyCallback;
    private GLFWWindowSizeCallback sizeCallback;
    private GLFWMouseButtonCallback mouseCallback;
    private WindowMenu menu;
    private WindowSortArray windowSortArray;
    private int borderX;

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
        mouseCallback = new MouseListener(this);

        windowSize = new Point(windowWidth, windowHeight);
        menu = new WindowMenu(this, windowSize);
        windowSortArray = new WindowSortArray(this, windowSize, 100);
    }

    private void registerCallback() {
        GLFW.glfwSetWindowSizeCallback(window, sizeCallback);
        GLFW.glfwSetKeyCallback(window, keyCallback);
        GLFW.glfwSetMouseButtonCallback(window, mouseCallback);
    }

    public void setSortingAlgorithm(SortingAlgorithm sortingAlgorithm) {

    }

    public void setSorting(boolean flag) {

    }

    public void setWindowSize(int windowWidth, int windowHeight) {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        GLFW.glfwSetWindowSize(window, windowWidth, windowHeight);
        GL11.glViewport(0, 0, windowWidth, windowHeight);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, windowWidth, 0, windowHeight, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        windowSize.setLocation(windowWidth, windowHeight);
    }

    public void onClick() {
        double[] xpos = new double[1];
        double[] ypos = new double[1];
        GLFW.glfwGetCursorPos(window, xpos, ypos);
        ypos[0] = windowHeight - ypos[0];
        menu.invokeButton((int)xpos[0], (int)ypos[0]);
    }

    private void draw() {
        while (!GLFW.glfwWindowShouldClose(window)) {
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
            //GL11.glClearColor(1f, 1f, 1f, 1f);

            menu.update();
            windowSortArray.update();

            GL11.glColor3f(0f, 0f, 0f);
            GL11.glBegin(GL11.GL_LINE_STRIP);
            GL11.glVertex2f(windowWidth - (windowWidth / 6),0);
            GL11.glVertex2f(windowWidth - windowWidth / 6,windowHeight);

            GL11.glEnd();
            ////
            GLFW.glfwSwapBuffers(window);
            GLFW.glfwPollEvents();
        }

        keyCallback.free();
        sizeCallback.free();
        mouseCallback.free();

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

    public void drawString(String str, int x, int y) {
        int width = str.length() * 20;
        int height = 30;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) image.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        g.setColor(Color.BLACK);

        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString(str, 0, 25);
        g.dispose();
        width = g.getFontMetrics().stringWidth(str) + 7;
        BufferedImage flippedImage = new BufferedImage(width, height, image.getType());
        AffineTransform transform = AffineTransform.getTranslateInstance(0, height);
        transform.scale(1, -1);
        Graphics2D gt = flippedImage.createGraphics();
        gt.drawImage(image, transform, null);
        gt.dispose();
        ByteBuffer pixels = ByteBuffer.allocateDirect(width * height * 4);
        int[] imagePixels = flippedImage.getRGB(0, 0, width, height, null, 0, width);
        for (int pixel : imagePixels) {
            pixels.put((byte) ((pixel >> 16) & 0xFF));  // red
            pixels.put((byte) ((pixel >> 8) & 0xFF));   // green
            pixels.put((byte) (pixel & 0xFF));          // blue
            pixels.put((byte) ((pixel >> 24) & 0xFF));  // alpha
        }
        pixels.flip();

        GL11.glRasterPos2i(x, y);
        GL11.glDrawPixels(width, height, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, pixels);
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
