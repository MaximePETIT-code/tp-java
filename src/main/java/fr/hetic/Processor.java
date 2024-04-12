package fr.hetic;

public class Processor {
    private DatabaseProcessor dbProcessor;
    private DirectoryProcessor dirProcessor;

    public Processor() {
        OperationFactory operationFactory = new OperationFactory();
        FileProcessor fileProcessor = new FileProcessor(operationFactory);
        this.dirProcessor = new DirectoryProcessor(fileProcessor);
        this.dbProcessor = new DatabaseProcessor(operationFactory);
    }

    public void process(String[] args) {
        if (args.length > 0 && args[0].equals("db")) {
            dbProcessor.processDatabase();
        } else if (args.length > 0) {
            dirProcessor.process(args[0]);
        } else {
            System.out.println("Please provide an argument: 'db' for database processing or a file path for file processing.");
        }
    }
}