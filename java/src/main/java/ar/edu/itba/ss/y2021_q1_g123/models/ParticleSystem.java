package ar.edu.itba.ss.y2021_q1_g123.models;

import java.util.*;

public class ParticleSystem implements Iterable<Particle> {
    private final int length;
    private final ArrayList<Particle> particles;
    private int timeZero;
    private double maxRadius;

    public ParticleSystem(int length, int totalParticles) {
        this.length = length;
        this.particles = new ArrayList<>(totalParticles);
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

    public void addParticle(Particle particle) {
        this.particles.add(particle);
        if (particle.getRadius() > this.maxRadius)
            this.maxRadius = particle.getRadius();
    }

    public Particle getParticle(int i) {
        return this.particles.get(i);
    }

    public double getMaxRadius() {
        return this.maxRadius;
    }

    public ArrayList<Particle> bruteForce(boolean periodic, double radius) {
        ArrayList<Particle> particles = new ArrayList<>(this.particles.size());

        for (int i = 0; i < this.particles.size(); i++) {
            Particle particle = this.particles.get(i).copy();
            particles.add(particle);

            for (int j = i + 1; j < this.particles.size(); j++) {
                Particle neighbor = this.particles.get(j);
                if (ParticleSystem.isNeighborInsideRadius(particle, neighbor, periodic, this.length, radius)) {
                    particle.getNeighbors().add(neighbor);
                    neighbor.getNeighbors().add(particle);
                }
            }
        }

        return particles;
    }

    public Pair<Collection<Particle>, Collection<Particle>[][]> cellIndexMethod(boolean periodic, double radius) {
        return this.internalCellIndexMethod(this.calculateMatrixSize(radius), periodic, radius);
    }

    public Pair<Collection<Particle>, Collection<Particle>[][]> cellIndexMethod(int size, boolean periodic, double radius) {
        this.assertValidMatrixSize(size, radius);
        return this.internalCellIndexMethod(size, periodic, radius);
    }

    @Override
    public Iterator<Particle> iterator() {
        return this.particles.iterator();
    }

    private int calculateMatrixSize(double radius) {
        // L/M > rc + r
        // => L / (rc + r) < M
        // => M >= Math.ceil(L / (rc + r) + eps)
        return (int) Math.ceil((this.length / (radius + this.maxRadius)) + Double.MIN_NORMAL);
    }

    private void assertValidMatrixSize(int size, double radius) {
        // L/M > rc + r
        // => L / (rc + r) < M
        // => M > Math.ceil(L / (rc + r))
        if (size < Math.ceil(this.length / (radius + this.maxRadius))) {
            throw new InvalidMatrixSize();
        }
    }

    private Pair<Collection<Particle>, Collection<Particle>[][]> internalCellIndexMethod(int size, boolean periodic, double radius) {
        Collection<Particle>[][] matrix = (Collection<Particle>[][]) new Collection[size][size];

        ParticleSystem.initializeMatrix(matrix);
        Collection<Particle> particles = this.populateMatrix(matrix);
        ParticleSystem.calculateNeighbors(matrix, periodic, this.length, radius);

        return new Pair<>(particles, matrix);
    }

    private Collection<Particle> populateMatrix(Collection<Particle>[][] matrix) {
        double cellSize = this.length / (double) matrix.length;

        Collection<Particle> particles = new ArrayList<>(this.particles.size());
        for (Particle particle : this.particles) {
            int xCell = (int) Math.floor(particle.getPosition().getX() / cellSize);
            int yCell = (int) Math.floor(particle.getPosition().getY() / cellSize);

            matrix[xCell][yCell].add(particle);
            particles.add(particle);
        }
        return particles;
    }

    private static void initializeMatrix(Collection<Particle>[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                matrix[i][j] = new LinkedList<>();
            }
        }
    }

    private static void calculateNeighbors(
            Collection<Particle>[][] matrix,
            boolean isPeriodic,
            int length,
            double radius)
    {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                Collection<Collection<Particle>> neighbors = ParticleSystem.getNeighbors(matrix, i, j, isPeriodic);
                ParticleSystem.putNeighbors(matrix[i][j], neighbors, isPeriodic, length, radius);
            }
        }
    }

    private static Collection<Collection<Particle>> getNeighbors(
            Collection<Particle>[][] matrix,
            final int i,
            final int j,
            boolean isPeriodic)
    {
        Collection<Collection<Particle>> neighbors = new LinkedList<>();

        if (isPeriodic) {
            L.POSITIONS.forEach(position -> {
                neighbors.add(matrix
                        [Math.floorMod(i + (int) position.getX(), matrix.length)]
                        [Math.floorMod(j + (int) position.getY(), matrix.length)]
                );
            });
        } else {
            L.POSITIONS.forEach(position -> {
                if (
                        (i + position.getX() >= 0)
                        && (i + position.getX() < matrix.length)
                        && (j + position.getY() >= 0)
                        && (j + position.getY() < matrix.length))
                {
                    neighbors.add(matrix[i + (int) position.getX()][j + (int) position.getY()]);
                }
            });
        }

        return neighbors;
    }

    private static void putNeighbors(
            Collection<Particle> particles,
            Collection<Collection<Particle>> neighborsList,
            boolean isPeriodic,
            int length,
            double radius)
    {
        for (Particle particle : particles) {
            for (Collection<Particle> neighbors : neighborsList) {
                neighbors.forEach(neighbor -> {
                    if (isNeighborInsideRadius(particle, neighbor, isPeriodic, length, radius)) {
                        particle.getNeighbors().add(neighbor);
                        neighbor.getNeighbors().add(particle);
                    }
                });
            }
        }
    }

    private static boolean isNeighborInsideRadius(
            Particle particle,
            Particle neighbor,
            boolean isPeriodic,
            int length,
            double radius)
    {
        double distance = isPeriodic ? particle.periodicDistanceTo(neighbor, length) : particle.distanceTo(neighbor);
        return !(distance > radius);
    }
}
