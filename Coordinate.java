package cmp3004;

public class Coordinate {
    private double x;
    private double y;
    private int index;

    public Coordinate(double x, double y, int index) {
        this.x = x;
        this.y = y;
        this.index = index;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getIndex() {
        return index;
    }
}
