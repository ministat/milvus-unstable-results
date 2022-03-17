package io.milvus.bench;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

public class ArgumentsParser {
    enum Action {
        LISTCOLLECTIONS,
        LISTPARTS,
        PERF,
        QUICKSEARCH,
        RECALL,
        STAT,
        STRESSTEST,
        TESTLOAD
    }
    @Option(name = "-c", aliases = "--collection", usage = "Specify the collection name")
    public String collection;

    @Option(name = "-a", aliases = "--action", usage = "Specify the actions", required = true)
    public Action action;

    @Option(name = "-t", aliases = "--threads", usage = "Specify the threads number, default is the CPU cores")
    public int threads = Runtime.getRuntime().availableProcessors();

    @Option(name = "-d", aliases = "--duration", usage = "Specify the duration in milli-seconds, default is 60000")
    public int durationMs = 60000;

    public boolean parseArgs(final String[] args) {
        final CmdLineParser parser = new CmdLineParser(this);
        if (args.length < 1) {
            parser.printUsage(System.out);
            System.exit(-1);
        }
        boolean ret = true;
        try {
            parser.parseArgument(args);
        } catch (CmdLineException ex) {
            System.out.println("Error: failed to parser command-line opts: " + ex);
            ret = false;
        }
        return ret;
    }
}
