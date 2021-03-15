package ar.edu.itba.ss.y2021_q1_g123.parsers;

import ar.edu.itba.ss.y2021_q1_g123.models.Particle;
import ar.edu.itba.ss.y2021_q1_g123.models.ParticleSystem;
import ar.edu.itba.ss.y2021_q1_g123.models.Position;
import ar.edu.itba.ss.y2021_q1_g123.models.Velocity;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;

public abstract class ParticleParser {
    public static ParticleSystem parseStatic(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        Scanner scanner = new Scanner(file);
        scanner.useLocale(Locale.ENGLISH);

        int total = scanner.nextInt();
        int length = scanner.nextInt();

        ParticleSystem particleSystem = new ParticleSystem(length, total);

        for (int i = 0; i < total; i++) {
            double radius = scanner.nextDouble();
            double property = scanner.nextDouble();

            particleSystem.addParticle(new Particle(i, radius, property));
        }

        return particleSystem;
    }

    public static void parseDynamic(String filePath, ParticleSystem particleSystem, boolean parseVelocity) throws FileNotFoundException {
        File file = new File(filePath);
        Scanner scanner = new Scanner(file);
        scanner.useLocale(Locale.ENGLISH);

        particleSystem.setTimeZero(scanner.nextInt());

        for (int i = 0; i < particleSystem.getTotalParticles(); i++) {
            Particle particle = particleSystem.getParticle(i);

            double x = scanner.nextDouble();
            double y = scanner.nextDouble();
            particle.setPosition(new Position(x, y));

            if (parseVelocity) {
                double xSpeed = scanner.nextDouble();
                double ySpeed = scanner.nextDouble();
                particle.setVelocity(new Velocity(xSpeed, ySpeed));
            }
        }
    }
}
