package org.simulator;

import java.util.Random;

public class UnitUtil {
    private static Random random = new Random();

    public static void fillWithRandomValues(Unit[] array, int edge) {
        for(int i = 0; i < array.length; i++) {
            array[i] = new Unit(random.nextInt() % edge);
        }
    }
}
