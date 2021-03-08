package ar.edu.itba.ss.y2021_q1_g123.parsers;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.ParseException;

import java.util.Properties;

import static ar.edu.itba.ss.y2021_q1_g123.parsers.CommandUtils.JAVA_OPT;

public final class CommandParser {
    public static final String STATIC_FILE_PATH_ARG_NAME = "staticPath";
    public static final String DYNAMIC_FILE_PATH_ARG_NAME = "dynamicPath";
    public static final String PARSE_VELOCITY_ARG_NAME = "withVelocity";

    private static CommandParser instance;

    private boolean parsed;
    private String staticPath;
    private String dynamicPath;
    private boolean parseVelocity;

    private CommandParser() {
        this.parsed = false;
    }

    public void parse(String[] args) throws ParseException {
        Properties properties = parseArgs(args);

        this.staticPath = properties.getProperty(STATIC_FILE_PATH_ARG_NAME);
        this.dynamicPath = properties.getProperty(DYNAMIC_FILE_PATH_ARG_NAME);
        this.parseVelocity = properties.containsKey(PARSE_VELOCITY_ARG_NAME);

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

        return CommandUtils.parseCommandLine(args, parseVelocityOption, staticFilepathOption, dynamicFilepathOption);
    }
}
