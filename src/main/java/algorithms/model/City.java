package algorithms.model;

public class City {

    private final int id;

    private final double x;

    private final double y;

    public City(int id, double x, double y) {

        this.id = id;

        this.x = x;

        this.y = y;
    }

    public int getId() {

        return id;
    }

    public double getX() {

        return x;
    }

    public double getY() {

        return y;
    }

    @Override
    public String toString() {

        return "City{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
    //
}