package ar.edu.itba.ss.y2021_q1_g123.models;

import java.util.*;

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

    public Collection<Particle>[][] createMatrix(int size) {
        Collection<Particle>[][] matrix = (Collection<Particle>[][]) new Collection[size][size];

        ParticleSystem.initializeMatrix(matrix);
        this.populateMatrix(matrix);
        ParticleSystem.calculateNeighbors(matrix);

        return matrix;
    }

    private void populateMatrix(Collection<Particle>[][] matrix) {
        double cellSize = this.length / (double) matrix.length;

        for (Particle particle : this.particles) {
            int xCell = (int) Math.floor(particle.getPosition().getX() / cellSize);
            int yCell = (int) Math.floor(particle.getPosition().getY() / cellSize);

            matrix[xCell][yCell].add(particle);
        }
    }

    private static void initializeMatrix(Collection<Particle>[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                matrix[i][j] = new LinkedList<>();
            }
        }
    }

    private static void calculateNeighbors(Collection<Particle>[][] matrix) {
        // Consider sub matrix of rows 1...size - 1, cols 0...size - 1.
        // Here all the adjacent cells always exist, ie. we will never go out of bounds.
        for (int i = 1; i < matrix.length - 1; i++) {
            for (int j = 0; j < matrix.length - 1; j++) {
                // South
                ParticleSystem.putNeighbors(matrix[i][j], matrix[i+1][j]);
                // Southeast
                ParticleSystem.putNeighbors(matrix[i][j], matrix[i+1][j+1]);
                // East
                ParticleSystem.putNeighbors(matrix[i][j], matrix[i][j+1]);
                // Northeast
                ParticleSystem.putNeighbors(matrix[i][j], matrix[i-1][j+1]);
            }
        }

        // Calculate the remaining neighbors
        // Calculate top row neighbors
        for (int j = 0; j < matrix[0].length - 1; j++) {
            // South
            ParticleSystem.putNeighbors(matrix[0][j], matrix[1][j]);
            // Southeast
            ParticleSystem.putNeighbors(matrix[0][j], matrix[1][j+1]);
            // East
            ParticleSystem.putNeighbors(matrix[0][j], matrix[0][j+1]);
        }
        // South
        ParticleSystem.putNeighbors(matrix[0][matrix.length - 1], matrix[1][matrix.length - 1]);

        // Calculate right col neighbors (except first row, already done it in previous step)
        for (int i = 1; i < matrix.length - 1; i++) {
            // South
            ParticleSystem.putNeighbors(matrix[i][matrix.length - 1], matrix[i + 1][matrix.length - 1]);
        }

        // Calculate bottom row neighbors
        for (int j = 0; j < matrix[matrix.length - 1].length - 1; j++) {
            // East
            ParticleSystem.putNeighbors(matrix[matrix.length - 1][j], matrix[matrix.length - 1][j+1]);
        }
    }

    private static void putNeighbors(Collection<Particle> particles, Collection<Particle> neighbors) {
        for (Particle particle : particles) {
            particle.getNeighbors().addAll(neighbors);
        }
        for (Particle particle : neighbors) {
            particle.getNeighbors().add(particle);
        }
    }
}
