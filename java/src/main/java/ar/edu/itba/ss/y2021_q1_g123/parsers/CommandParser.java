package ar.edu.itba.ss.y2021_q1_g123.parsers;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.ParseException;

import java.util.Properties;

import static ar.edu.itba.ss.y2021_q1_g123.parsers.CommandUtils.JAVA_OPT;

public final class CommandParser {
    public static final String STATIC_FILE_PATH_ARG_NAME = "staticPath";
    public static final String DYNAMIC_FILE_PATH_ARG_NAME = "dynamicPath";

    private static CommandParser instance;

    private boolean parsed;
    private String staticPath;
    private String dynamicPath;

    private CommandParser() {
        this.parsed = false;
        instance = this;
    }

    public String getStaticPath() throws IllegalStateException {
        this.assertParsed();
        return this.staticPath;
    }

    public String getDynamicPath() throws IllegalStateException {
        this.assertParsed();
        return this.dynamicPath;
    }

    private void assertParsed() throws IllegalStateException {
        if (!this.parsed)
            throw new IllegalStateException();
    }

    public static void parse(String[] args) throws ParseException {
        Properties properties = parseArgs(args);

        instance.staticPath = properties.getProperty(STATIC_FILE_PATH_ARG_NAME);
        instance.dynamicPath = properties.getProperty(DYNAMIC_FILE_PATH_ARG_NAME);

        instance.parsed = true;
    }

    public static CommandParser getInstance() {
        return instance;
    }

    private static Properties parseArgs(String[] args) throws ParseException {
        Option filepathOption = new Option(JAVA_OPT, "specifies the static file's file path");
        filepathOption.setArgName(STATIC_FILE_PATH_ARG_NAME);
        filepathOption.setRequired(true);

        return CommandUtils.parseCommandLine(args, filepathOption);
    }
}
