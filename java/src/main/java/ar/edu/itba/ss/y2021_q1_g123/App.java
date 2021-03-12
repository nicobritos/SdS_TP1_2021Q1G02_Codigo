package ar.edu.itba.ss.y2021_q1_g123;

import ar.edu.itba.ss.y2021_q1_g123.models.Pair;
import ar.edu.itba.ss.y2021_q1_g123.models.Particle;
import ar.edu.itba.ss.y2021_q1_g123.models.ParticleSystem;
import ar.edu.itba.ss.y2021_q1_g123.parsers.CommandParser;
import ar.edu.itba.ss.y2021_q1_g123.parsers.ParticleParser;
import org.apache.commons.cli.ParseException;

import java.io.IOException;
import java.util.Collection;

public class App {
    public static void main(String[] args) throws IOException, ParseException {
        CommandParser.getInstance().parse(args);

        ParticleSystem system = ParticleParser.parseStatic(CommandParser.getInstance().getStaticPath());
        ParticleParser.parseDynamic(CommandParser.getInstance().getDynamicPath(), system, CommandParser.getInstance().getParseVelocity());

        Pair<Long, Collection<Particle>> result;
        if (CommandParser.getInstance().getBruteforce()) {
            result = App.computeNeighborsBruteForce(system);
        } else {
            result = App.computeNeighborsCellIndexMethod(system);
        }

        ParticleSystemSerializer.serialize(result.getLeft(), result.getRight(), CommandParser.getInstance().getOutputPath());
    }

    private static Pair<Long, Collection<Particle>> computeNeighborsCellIndexMethod(ParticleSystem system) {
        long startTime;
        Pair<Collection<Particle>, Collection<Particle>[][]> result;

        if (CommandParser.getInstance().getMatrixSize() == null) {
            startTime = System.currentTimeMillis();
            result = system.cellIndexMethod(
                    CommandParser.getInstance().getPeriodic(),
                    CommandParser.getInstance().getRadius()
            );
        } else {
            startTime = System.currentTimeMillis();
            result = system.cellIndexMethod(
                    CommandParser.getInstance().getMatrixSize(),
                    CommandParser.getInstance().getPeriodic(),
                    CommandParser.getInstance().getRadius()
            );
        }

        long endTime = System.currentTimeMillis();

        return new Pair<>(endTime - startTime, result.getLeft());
    }

    private static Pair<Long, Collection<Particle>> computeNeighborsBruteForce(ParticleSystem system) {
        long startTime = System.currentTimeMillis();
        Collection<Particle> result = system.bruteForce(
                CommandParser.getInstance().getPeriodic(),
                CommandParser.getInstance().getRadius()
        );
        long endTime = System.currentTimeMillis();

        return new Pair<>(endTime - startTime, result);
    }
}
