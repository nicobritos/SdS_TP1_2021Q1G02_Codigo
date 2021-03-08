package ar.edu.itba.ss.y2021_q1_g123.parsers;

import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.util.Properties;

public abstract class CommandUtils {
    public static final String JAVA_OPT = "D";

    public static Properties parseCommandLine(String[] args, Option... optionArray) throws ParseException {
        Options options = new Options();

        for (Option option : optionArray) {
            option.setArgs(2);
            options.addOption(option);
        }

        return new DefaultParser().parse(options, args, true).getOptionProperties(JAVA_OPT);
    }
}
