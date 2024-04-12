package fr.hetic;

import java.io.*;
import java.nio.file.*;

public class FileProcessor {
    private OperationFactory operationFactory;

    public FileProcessor(OperationFactory operationFactory) {
        this.operationFactory = operationFactory;
    }

    public void process(String filePath) {
        String resultPath = getResultFilePath(filePath);
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath)); PrintWriter writer = new PrintWriter(new FileWriter(resultPath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                processLineAndWriteResult(line, writer);
            }
            System.out.println("The result has been written to " + resultPath);
        } catch (IOException e) { e.printStackTrace(); }
    }

    private String getResultFilePath(String inputFilePath) {
        Path path = Paths.get(inputFilePath);
        String resultFileName = path.getFileName().toString().replaceFirst("\\.op$", ".res");
        return path.getParent().resolve(resultFileName).toString();
    }

    public void processLineAndWriteResult(String line, PrintWriter writer) {
        int result = processLine(line);
        writer.println(result == Integer.MIN_VALUE ? "ERROR" : result);
    }

    public int processLine(String line) {
        String[] parts = line.split("\\s+");
        if (parts.length != 3) return Integer.MIN_VALUE;
        int num1, num2;
        try { num1 = Integer.parseInt(parts[0]); num2 = Integer.parseInt(parts[1]); } 
        catch (NumberFormatException e) { return Integer.MIN_VALUE; }
        String operator = parts[2];
        Operation operation = operationFactory.getOperation(operator);
        if (operation == null) { System.out.println("Invalid operator: " + operator); return Integer.MIN_VALUE; }
        return operation.execute(num1, num2);
    }
    
}