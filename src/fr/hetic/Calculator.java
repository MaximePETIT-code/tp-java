package fr.hetic;
import java.io.*;
import java.nio.file.*;
import java.util.*;

/**
 * The Calculator class processes arithmetic operations from files in a specified directory.
 * The operations are read from files with the ".op" extension.
 * The results are written to corresponding files with the ".res" extension.
 */
public class Calculator {
    /**
     * The main method of the Calculator class.
     * It expects a single argument: the path to the directory containing the files to process.
     * It walks through the directory, processing each ".op" file it finds.
     */
    public static void main(String[] args) {
        String SUFFIX_FILE_EXTENSION = ".op";
        if (args.length != 1) { System.out.println("Usage: java fr.hetic.Calculator <path-to-directory>"); return; }
        String dirPath = args[0];
        if (!Files.isDirectory(Paths.get(dirPath))) { System.out.println("Invalid directory path: " + dirPath); return; }
        try { Files.walk(Paths.get(dirPath)).filter(path -> path.toString().endsWith(SUFFIX_FILE_EXTENSION)).forEach(path -> processFile(path.toString())); } 
        catch (IOException e) { e.printStackTrace(); }
    }

    /**
     * Processes a single file.
     * It reads each line of the file, processes it as an arithmetic operation, and writes the result to a ".res" file.
     */
    private static void processFile(String filePath) {
        String resultPath = getResultFilePath(filePath);
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath)); PrintWriter writer = new PrintWriter(new FileWriter(resultPath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                int result = processLine(line);
                writer.println(result == Integer.MIN_VALUE ? "ERROR" : result);
            }
            System.out.println("The result has been written to " + resultPath);
        } catch (IOException e) { e.printStackTrace(); }
    }

    /**
     * Returns the path to the result file corresponding to a given input file.
     * The result file has the same name as the input file, but with the ".op" extension replaced by ".res".
     */
    private static String getResultFilePath(String inputFilePath) {
        Path path = Paths.get(inputFilePath);
        String resultFileName = path.getFileName().toString().replaceFirst("\\.op$", ".res");
        return path.getParent().resolve(resultFileName).toString();
    }

    /**
     * Processes a single line as an arithmetic operation.
     * The line should contain two integers and an operator, separated by spaces.
     * The operator can be "+", "-", or "*".
     * If the line is not in this format, or if the operator is not one of the allowed ones, it returns Integer.MIN_VALUE.
     */
    private static int processLine(String line) {
        String[] parts = line.split("\\s+");
        if (parts.length != 3) return Integer.MIN_VALUE;
        int num1, num2;
        try { num1 = Integer.parseInt(parts[0]); num2 = Integer.parseInt(parts[1]); } 
        catch (NumberFormatException e) { return Integer.MIN_VALUE; }
        String operator = parts[2];
        List<String> validOperators = Arrays.asList("+", "-", "*", "/");
        if (!validOperators.contains(operator)) { System.out.println("Invalid operator: " + operator); return Integer.MIN_VALUE; }
        switch (operator) {
            case "+": return num1 + num2;
            case "-": return num1 - num2;
            case "*": return num1 * num2;
            default: return Integer.MIN_VALUE;
        }
    }
}