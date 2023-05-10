package org.simulation;

import org.simulation.Unit;

import java.util.Random;

public class SortArrayUtil {
    private static Random random = new Random();

    public static int[] initializeArray(int size) {
        int[] array = new int[size];
        for(int i = 0; i < size; i++) {
            array[i] = i + 1;
        }
        return array;
    }

    public static int[] randomShuffle(int size) {
        int[] array = initializeArray(size);
        for(int i = 0; i < size; i++) {
            int randomIndex = random.nextInt() % size;
            int tmp = array[i];
            array[i] = array[randomIndex];
            array[randomIndex] = tmp;
        }
        return array;
    }

    public static int[] initializeArrayWithRandomValues(int size, int edge) {
        int[] array = new int[size];
        for(int i = 0; i < size; i++) {
            array[i] = random.nextInt() % edge;
        }
        return array;
    }

    public static void shuffle(SortArray array) {
        int edge = array.getSize();
        for(int i = 0; i < array.getSize(); i++)
            array.swap(i, Math.abs(random.nextInt() % edge));
    }

}
