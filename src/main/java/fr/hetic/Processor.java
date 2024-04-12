package fr.hetic;

public class Processor {
    private final DatabaseProcessor dbProcessor = new DatabaseProcessor(new OperationFactory());
    private final DirectoryProcessor dirProcessor = new DirectoryProcessor(new FileProcessor(new OperationFactory()));

    public void process(String[] args) {
        if (args.length > 0) {
            switch (args[0]) {
                case "db" -> dbProcessor.processDatabase();
                default -> dirProcessor.process(args[0]);
            }
        } else {
            System.out.println("Please provide an argument: 'db' for database processing or a file path for file processing.");
        }
    }
}
