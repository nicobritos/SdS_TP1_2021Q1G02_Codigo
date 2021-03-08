package ar.edu.itba.ss.y2021_q1_g123.models;

public class Particle {
    private final double radius;
    private final double property;
    private Position position;
    private Velocity velocity;

    public Particle(double radius, double property) {
        this.radius = radius;
        this.property = property;
    }

    public double getRadius() {
        return this.radius;
    }

    public double getProperty() {
        return this.property;
    }

    public Position getPosition() {
        return this.position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Velocity getVelocity() {
        return this.velocity;
    }

    public void setVelocity(Velocity velocity) {
        this.velocity = velocity;
    }
}
