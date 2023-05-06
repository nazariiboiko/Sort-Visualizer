package org.simulation;

import java.util.Random;

public class UnitUtil {
    private static Random random = new Random();

    public static void fillWithRandomValues(Unit[] array, int edge) {
        for(int i = 0; i < array.length; i++) {
            array[i] = new Unit(random.nextInt() % edge);
        }
    }

    public static void fillFrom1ToN(Unit[] array) {
        for(int i = 0; i < array.length; i++) {
            array[i] = new Unit(i);
        }
    }

    public static void shuffle(Unit[] array) {
        for(int i = array.length - 1; i >= 5; i--) {
            int randomPosition = Math.abs(random.nextInt(i));
            Unit tmp = array[i];
            array[i] = array[randomPosition];
            array[randomPosition] = tmp;
        }
    }

}
