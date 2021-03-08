package ar.edu.itba.ss.y2021_q1_g123.models;

import java.util.*;

public class Particle {
    private final double radius;
    private final double property;
    private final Set<Particle> neighbors;
    private Position position;
    private Velocity velocity;

    public Particle(double radius, double property) {
        this.radius = radius;
        this.property = property;
        this.neighbors = new HashSet<>();
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

    public Collection<Particle> getNeighbors() {
        return this.neighbors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Particle)) return false;
        Particle particle = (Particle) o;
        return Double.compare(particle.getRadius(), this.getRadius()) == 0 && Double.compare(particle.getProperty(), this.getProperty()) == 0 && Objects.equals(this.getPosition(), particle.getPosition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getRadius(), this.getProperty(), this.getPosition());
    }
}
