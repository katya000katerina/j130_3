package org.j130;

import java.util.Random;

public class Utilities {
    public static int getRandomNumberOfProducts() {
        return getRandomNumber(1, 5);
    }

    public static int getRandomNumberOfMillis() {
        return getRandomNumber(1000, 3000);
    }

    private static int getRandomNumber(int min, int max) {
        return new Random().ints(min, max).findFirst().getAsInt();
    }
}
