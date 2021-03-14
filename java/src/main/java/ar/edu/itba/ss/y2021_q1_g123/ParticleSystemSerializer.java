package ar.edu.itba.ss.y2021_q1_g123;

import ar.edu.itba.ss.y2021_q1_g123.models.Particle;

import java.io.*;
import java.util.Collection;

public abstract class ParticleSystemSerializer {
    public static void serialize(long elapsedTimeMs, Collection<Particle> particles, String outputPath) throws IOException {
        // Outputs as TSV:
        // executionTime in ms
        // particle1 [ \t neighbor1 \t neighbor2... neighborN ]
        // ...
        // particleN [ \t neighbor1 \t neighbor2... neighborN ]

        BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath, false));
        writer.append(String.valueOf(elapsedTimeMs));
        writer.append('\n');

        for (Particle particle : particles) {
            ParticleSystemSerializer.writeLine(writer, particle);
        }

        writer.append('\n');

        writer.close();
    }

    private static void writeLine(Writer writer, Particle particle) throws IOException {
        writer.append(String.valueOf(particle.getId()));

        for (Particle neighbor : particle.getNeighbors()) {
            writer.append('\t');
            writer.append(String.valueOf(neighbor.getId()));
        }

        writer.append('\n');
    }
}
