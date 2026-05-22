package algorithms.util;

import algorithms.model.City;

public class DistanceUtils {

    public static double euclidean(
            City a,
            City b) {

        double dx =
                a.getX() - b.getX();

        double dy =
                a.getY() - b.getY();

        return Math.sqrt(
                dx * dx + dy * dy
        );
    }

    public static double squaredEuclidean(
            City a,
            City b) {

        double dx =
                a.getX() - b.getX();

        double dy =
                a.getY() - b.getY();

        return dx * dx + dy * dy;
    }

    public static double manhattan(
            City a,
            City b) {

        return Math.abs(
                a.getX() - b.getX()
        )
                +
                Math.abs(
                        a.getY() - b.getY()
                );
    }
}