package org.simulation;

import org.simulation.algorithm.BubbleSort;
import org.simulation.algorithm.InsertionSort;
import org.simulation.listener.KeyListener;
import org.simulation.listener.MouseListener;
import org.simulation.listener.ResizeListener;
import org.simulation.ui.Component;
import org.simulation.ui.ComponentMenu;
import org.simulation.ui.ComponentArray;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

public class Window implements Component {
    private int windowWidth = 1200;
    private int windowHeight = 600;
    private int arraySize = 100;
    private Point windowSize;
    private long window;
    private ComponentMenu componentMenu;
    private ComponentArray componentSortArray;
    private KeyListener keyListener;
    private MouseListener mouseListener;
    private ResizeListener sizeListener;
    private Mediator mediator;

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

        windowSize = new Point(windowWidth, windowHeight);
        componentMenu = new ComponentMenu(windowSize);
        componentSortArray = new ComponentArray(windowSize, arraySize);


        mediator = new ComponentCommunicationMediator();
        mediator.registerComponent(this);
        mediator.registerComponent(componentMenu);
        mediator.registerComponent(componentSortArray);

    }

    private void registerCallback() {
        sizeListener = new ResizeListener(this);
        keyListener = new KeyListener(this);
        mouseListener = new MouseListener(this);

        GLFW.glfwSetWindowSizeCallback(window, sizeListener);
        GLFW.glfwSetMouseButtonCallback(window, mouseListener);
        GLFW.glfwSetKeyCallback(window, keyListener);
    }

    private void terminate() {
        sizeListener.free();
        mouseListener.free();
        keyListener.free();
        mediator.stop();
        GLFW.glfwTerminate();
    }

    public void handleKeyEvent(int key, int action) {
        if (key == GLFW.GLFW_KEY_S && action == GLFW.GLFW_PRESS) {
            mediator.shuffle();
        } else if (key == GLFW.GLFW_KEY_D && action == GLFW.GLFW_PRESS) {
            //UnitUtil.fillWithRandomValues(array, 100);
        } else if (key == GLFW.GLFW_KEY_1 && action == GLFW.GLFW_PRESS) {
            mediator.setSortingAlgorithm(new BubbleSort());
        } else if (key == GLFW.GLFW_KEY_2 && action == GLFW.GLFW_PRESS) {
            mediator.setSortingAlgorithm(new InsertionSort());
        } else if (key == GLFW.GLFW_KEY_3 && action == GLFW.GLFW_PRESS) {
            //sortingMethod = new MergeSort(array);
        }
    }

    public long getWindow() {
        return this.window;
    }

    public void onClick() {
        int[] pos = getCursorPos();
        mediator.sendClickIvent(pos[0], pos[1]);
    }

    public void setWindowSize(int windowWidth, int windowHeight) {
        if(windowWidth < 825)
            windowWidth = 825;
        if(windowHeight < 510)
            windowHeight = 510;

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

    public int[] getCursorPos() {
        double[] xpos = new double[1];
        double[] ypos = new double[1];
        GLFW.glfwGetCursorPos(window, xpos, ypos);
        ypos[0] = windowHeight - ypos[0];
        return new int[] {(int)xpos[0], (int)ypos[0]};
    }

    private void draw() {
        while (!GLFW.glfwWindowShouldClose(window)) {
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
            mediator.updateAllComponents();
            GLFW.glfwSwapBuffers(window);
            GLFW.glfwPollEvents();
        }
        terminate();
    }
    public void drawString(String str, int x, int y, Color background, Color fontColor) {
        int width = str.length() * 20;
        int height = 30;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) image.getGraphics();
        g.setColor(background);
        g.fillRect(0, 0, width, height);
        g.setColor(fontColor);

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

    static public void main(String[] args) {
        new Window();
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public String getName() {
        return "Window";
    }
}
