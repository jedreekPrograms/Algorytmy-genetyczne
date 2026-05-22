package algorithms.util;

import java.util.Random;

public class RandomUtils {

    private static final Random random =
            new Random();

    public static int randomInt(
            int bound) {

        return random.nextInt(bound);
    }

    public static int randomInt(
            int left,
            int right) {

        return left
                + random.nextInt(
                right - left + 1
        );
    }

    public static double randomDouble() {

        return random.nextDouble();
    }

    public static boolean chance(
            double probability) {

        return random.nextDouble()
                < probability;
    }

    public static void shuffle(
            int[] array) {

        for (int i = array.length - 1;
             i > 0;
             i--) {

            int j =
                    random.nextInt(i + 1);

            int temp =
                    array[i];

            array[i] =
                    array[j];

            array[j] =
                    temp;
        }
    }
}