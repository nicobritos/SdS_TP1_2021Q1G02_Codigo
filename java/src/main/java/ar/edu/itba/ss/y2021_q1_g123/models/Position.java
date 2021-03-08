package ar.edu.itba.ss.y2021_q1_g123.models;

import java.util.Objects;

public class Position {
    private final double x;
    private final double y;

    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position)) return false;
        Position position = (Position) o;
        return Double.compare(position.getX(), this.getX()) == 0 && Double.compare(position.getY(), this.getY()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getX(), this.getY());
    }
}
