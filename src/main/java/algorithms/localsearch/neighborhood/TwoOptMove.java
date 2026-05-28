package algorithms.localsearch.neighborhood;

public class TwoOptMove {

    private final int left;

    private final int right;

    private final double delta;

    private final long creationTime;

    public TwoOptMove(int left, int right, double delta) {

        this.left = left;

        this.right = right;

        this.delta = delta;

        this.creationTime = System.nanoTime();
    }

    public int getLeft() {

        return left;
    }

    public int getRight() {

        return right;
    }

    public double getDelta() {

        return delta;
    }

    public long getCreationTime() {

        return creationTime;
    }

    public boolean isImproving() {

        return delta < 0.0;
    }

    public double getImprovement() {

        return -delta;
    }

    @Override
    public String toString() {

        return "TwoOptMove{" +
                "left=" + left +
                ", right=" + right +
                ", delta=" + delta +
                '}';
    }
}