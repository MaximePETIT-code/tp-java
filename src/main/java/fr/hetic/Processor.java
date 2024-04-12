package fr.hetic;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Processor {
    private final Map<String, ProcessorFunction> processors = new HashMap<>();

    @FunctionalInterface
    public interface ProcessorFunction {
        void process(String arg);
    }

    public Processor() {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("/Users/max/Desktop/hetic/java/maven-project/my-app/src/main/ressources/application.properties"));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load properties file", e);
        }

        String implementation = properties.getProperty("implementation");
        String filePath = properties.getProperty("filePath");

        OperationFactory operationFactory = new OperationFactory();
        processors.put("JDBC", (arg) -> new DatabaseProcessor(operationFactory).processDatabase());
        processors.put("FILE", (arg) -> new DirectoryProcessor(new FileProcessor(operationFactory)).process(arg));

        process(implementation, filePath);
    }

    public void process(String implementation, String filePath) {
        ProcessorFunction processor = processors.getOrDefault(implementation, (arg) -> System.out.println("Invalid implementation or implementation not supported."));
        processor.process(filePath);
    }
}