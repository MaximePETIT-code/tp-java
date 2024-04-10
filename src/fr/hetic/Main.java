package fr.hetic;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) { 
            System.out.println("Usage: java fr.hetic.Main <path-to-directory>"); 
            return; 
        }

        String dirPath = args[0];
        OperationFactory operationFactory = new OperationFactory();
        FileProcessor fileProcessor = new FileProcessor(operationFactory);
        DirectoryProcessor directoryProcessor = new DirectoryProcessor(fileProcessor);

        directoryProcessor.process(dirPath);
    }
}