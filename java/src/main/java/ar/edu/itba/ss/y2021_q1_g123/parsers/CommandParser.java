package ar.edu.itba.ss.y2021_q1_g123.parsers;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.ParseException;

import java.util.Properties;

import static ar.edu.itba.ss.y2021_q1_g123.parsers.CommandUtils.JAVA_OPT;

public final class CommandParser {
    public static final String STATIC_FILE_PATH_ARG_NAME = "staticPath";
    public static final String DYNAMIC_FILE_PATH_ARG_NAME = "dynamicPath";
    public static final String PARSE_VELOCITY_ARG_NAME = "withVelocity";
    public static final String OUTPUT_FILE_PATH_ARG_NAME = "outputPath";
    public static final String MATRIX_SIZE_ARG_NAME = "M";
    public static final String RADIUS_ARG_NAME = "r";

    private static CommandParser instance;

    private boolean parsed;
    private String staticPath;
    private String dynamicPath;
    private String outputPath;
    private boolean parseVelocity;
    private int matrixSize;
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

        try {
            this.matrixSize = Integer.parseInt(properties.getProperty(MATRIX_SIZE_ARG_NAME));
        } catch (NumberFormatException e) {
            throw new ParseException("Matrix size is not a valid number");
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

    public int getMatrixSize() {
        return this.matrixSize;
    }

    public double getRadius() {
        return this.radius;
    }

    public String getOutputPath() {
        return this.outputPath;
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
        Option parseVelocityOption = new Option(JAVA_OPT, "if present indicates that it should parse the velocity inside the dynamic file");
        parseVelocityOption.setArgName(PARSE_VELOCITY_ARG_NAME);
        parseVelocityOption.setRequired(false);

        Option staticFilepathOption = new Option(JAVA_OPT, "specifies the static file's file path");
        staticFilepathOption.setArgName(STATIC_FILE_PATH_ARG_NAME);
        staticFilepathOption.setRequired(true);

        Option dynamicFilepathOption = new Option(JAVA_OPT, "specifies the dynamic file's file path");
        dynamicFilepathOption.setArgName(DYNAMIC_FILE_PATH_ARG_NAME);
        dynamicFilepathOption.setRequired(true);

        Option matrixSizeOption = new Option(JAVA_OPT, "specifies the matrix size");
        matrixSizeOption.setArgName(MATRIX_SIZE_ARG_NAME);
        matrixSizeOption.setRequired(true);

        Option radiusOption = new Option(JAVA_OPT, "specifies the neighbor radius");
        radiusOption.setArgName(RADIUS_ARG_NAME);
        radiusOption.setRequired(true);

        Option outputFilepathOption = new Option(JAVA_OPT, "specifies the output file's file path");
        outputFilepathOption.setArgName(OUTPUT_FILE_PATH_ARG_NAME);
        outputFilepathOption.setRequired(true);

        return CommandUtils.parseCommandLine(
                args,
                parseVelocityOption,
                staticFilepathOption,
                dynamicFilepathOption,
                radiusOption,
                matrixSizeOption,
                outputFilepathOption
        );
    }
}
