package ar.edu.itba.ss.y2021_q1_g123.parsers;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.ParseException;

import java.util.Properties;

import static ar.edu.itba.ss.y2021_q1_g123.parsers.CommandUtils.JAVA_OPT;

public final class CommandParser {
    public static final String STATIC_FILE_PATH_ARG_NAME = "staticPath";
    public static final String DYNAMIC_FILE_PATH_ARG_NAME = "dynamicPath";
    public static final String PARSE_VELOCITY_ARG_NAME = "parseVelocity";
    public static final String OUTPUT_FILE_PATH_ARG_NAME = "outputPath";
    public static final String MATRIX_SIZE_ARG_NAME = "M";
    public static final String RADIUS_ARG_NAME = "r";
    public static final String PERIODIC_ARG_NAME = "periodic";
    public static final String BRUTEFORCE_ARG_NAME = "bruteforce";

    private static CommandParser instance;

    private boolean parsed;
    private String staticPath;
    private String dynamicPath;
    private String outputPath;
    private boolean parseVelocity;
    private boolean periodic;
    private boolean bruteforce;
    private Integer matrixSize;
    private double radius;

    private CommandParser() {
        this.parsed = false;
    }

    public void parse(String[] args) throws ParseException {
        Properties properties = parseArgs(args);

        this.staticPath = properties.getProperty(STATIC_FILE_PATH_ARG_NAME);
        this.dynamicPath = properties.getProperty(DYNAMIC_FILE_PATH_ARG_NAME);
        this.outputPath = properties.getProperty(OUTPUT_FILE_PATH_ARG_NAME);
        this.parseVelocity = properties.containsKey(PARSE_VELOCITY_ARG_NAME);
        this.periodic = properties.containsKey(PERIODIC_ARG_NAME);
        this.bruteforce = properties.containsKey(BRUTEFORCE_ARG_NAME);

        if (properties.containsKey(MATRIX_SIZE_ARG_NAME)) {
            try {
                this.matrixSize = Integer.parseInt(properties.getProperty(MATRIX_SIZE_ARG_NAME));
            } catch (NumberFormatException e) {
                throw new ParseException("Matrix size is not a valid number");
            }
        }

        try {
            this.radius = Double.parseDouble(properties.getProperty(RADIUS_ARG_NAME));
        } catch (NumberFormatException e) {
            throw new ParseException("Radius is not a valid number");
        }

        if (this.matrixSize <= 0)
            throw new ParseException("Matrix size is not a positive integer");
        if (this.radius <= 0)
            throw new ParseException("Radius is not a positive number");

        this.parsed = true;
    }

    public String getStaticPath() throws IllegalStateException {
        this.assertParsed();
        return this.staticPath;
    }

    public String getDynamicPath() throws IllegalStateException {
        this.assertParsed();
        return this.dynamicPath;
    }

    public boolean getParseVelocity() {
        return this.parseVelocity;
    }

    public Integer getMatrixSize() {
        return this.matrixSize;
    }

    public double getRadius() {
        return this.radius;
    }

    public String getOutputPath() {
        return this.outputPath;
    }

    public boolean getPeriodic() {
        return this.periodic;
    }

    public boolean getBruteforce() {
        return this.bruteforce;
    }

    private void assertParsed() throws IllegalStateException {
        if (!this.parsed)
            throw new IllegalStateException();
    }

    public static CommandParser getInstance() {
        if (instance == null)
            instance = new CommandParser();
        return instance;
    }

    private static Properties parseArgs(String[] args) throws ParseException {
        Option parseVelocityOption = new Option(JAVA_OPT, "if present indicates that it should parse the velocity " +
                "inside the dynamic file");
        parseVelocityOption.setArgName(PARSE_VELOCITY_ARG_NAME);
        parseVelocityOption.setRequired(false);

        Option parsePeriodicOption = new Option(JAVA_OPT, "if present indicates that it should calculate the distance" +
                " between particles using a periodic function");
        parsePeriodicOption.setArgName(PERIODIC_ARG_NAME);
        parsePeriodicOption.setRequired(false);

        Option parseBruteforceOption = new Option(JAVA_OPT, "if present it finds the neighbors of a particle by " +
                "bruteforce (checks every other particle). If not, it finds them using the Cell Index Method");
        parseBruteforceOption.setArgName(BRUTEFORCE_ARG_NAME);
        parseBruteforceOption.setRequired(false);

        Option staticFilepathOption = new Option(JAVA_OPT, "specifies the static file's file path");
        staticFilepathOption.setArgName(STATIC_FILE_PATH_ARG_NAME);
        staticFilepathOption.setRequired(true);

        Option dynamicFilepathOption = new Option(JAVA_OPT, "specifies the dynamic file's file path");
        dynamicFilepathOption.setArgName(DYNAMIC_FILE_PATH_ARG_NAME);
        dynamicFilepathOption.setRequired(true);

        Option matrixSizeOption = new Option(JAVA_OPT, "specifies the matrix size");
        matrixSizeOption.setArgName(MATRIX_SIZE_ARG_NAME);
        matrixSizeOption.setRequired(false);

        Option radiusOption = new Option(JAVA_OPT, "specifies the neighbor radius");
        radiusOption.setArgName(RADIUS_ARG_NAME);
        radiusOption.setRequired(true);

        Option outputFilepathOption = new Option(JAVA_OPT, "specifies the output file's file path");
        outputFilepathOption.setArgName(OUTPUT_FILE_PATH_ARG_NAME);
        outputFilepathOption.setRequired(true);

        return CommandUtils.parseCommandLine(
                args,
                parseVelocityOption,
                parsePeriodicOption,
                parseBruteforceOption,
                staticFilepathOption,
                dynamicFilepathOption,
                radiusOption,
                matrixSizeOption,
                outputFilepathOption
        );
    }
}
