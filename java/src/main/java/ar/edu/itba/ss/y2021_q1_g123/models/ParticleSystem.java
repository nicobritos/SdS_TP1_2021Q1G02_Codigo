package ar.edu.itba.ss.y2021_q1_g123.models;

import java.util.ArrayDeque;
import java.util.Queue;

public class ParticleSystem {
    private final int length;
    private final Queue<Particle> particles;
    private int timeZero;

    public ParticleSystem(int length, int totalParticles) {
        this.length = length;
        this.particles = new ArrayDeque<>(totalParticles);
    }

    public int getLength() {
        return this.length;
    }

    public int getTimeZero() {
        return this.timeZero;
    }

    public int getTotalParticles() {
        return this.particles.size();
    }

    public void setTimeZero(int timeZero) {
        this.timeZero = timeZero;
    }

    public void enqueueParticle(Particle particle) {
        this.particles.add(particle);
    }

    public Particle dequeueParticle() {
        return this.particles.poll();
    }
}
