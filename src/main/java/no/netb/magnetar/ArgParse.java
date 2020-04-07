package no.netb.magnetar;

import org.apache.commons.cli.*;

import java.util.List;
import java.util.Optional;

public class ArgParse {

    private String[] args;
    private boolean serverMode;
    private Integer depth = null;
    private List<String> paths;

    public ArgParse(String[] args) {
        this.args = args;
    }

    public void parse() {
        Options options = new Options();

        Option depth = Option.builder("d")
                .hasArg()
                .required(false)
                .argName("N")
                .longOpt("depth")
                .desc("limit recursive indexing to the given depth")
                .type(Integer.class)
                .build();

        options.addOption(depth);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();

        CommandLine cmdLine = null;
        try {
            cmdLine = parser.parse(options, args);
        } catch (ParseException e) {
            formatter.printHelp(
                    "magnetar [OPTION]... [PATH]...\n"
                    + "  when arguments are provided, magnetar will run in one-shot mode.\n"
                    + "  when no argument is provided, it will start in server mode.\n\n",
                    options);
            System.exit(ExitCode.ARGS_INVALID.value);
        }

        serverMode = args.length == 0;

        if (cmdLine.hasOption(depth.getLongOpt())) {
            this.depth = Integer.parseInt(cmdLine.getOptionValue(depth.getLongOpt()));
        }

        paths = cmdLine.getArgList();
    }

    public boolean isServerMode() {
        return serverMode;
    }

    public Optional<Integer> getDepth() {
        return Optional.ofNullable(depth);
    }

    public List<String> getPaths() {
        return paths;
    }
}
