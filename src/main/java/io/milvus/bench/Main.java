package io.milvus.bench;

public class Main {

    public static void milvusBench(String[] args) {
        final ArgumentsParser argsParser = new ArgumentsParser();
        if (!argsParser.parseArgs(args)) {
            return;
        }

        switch (argsParser.action) {
            case LISTCOLLECTIONS:
                Milvus.showCollections();
                break;
            case QUICKSEARCH:
                if (argsParser.collection == null || argsParser.collection.isEmpty()) {
                    System.out.println("Please specify the collection name");
                    System.exit(1);
                }
                Milvus.runQuickSearch(argsParser.collection);
                break;
        }
    }

    public static void main(String[] args) {
        //testConsoleBar();
        milvusBench(args);
    }
}
