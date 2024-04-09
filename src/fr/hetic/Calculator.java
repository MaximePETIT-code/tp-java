package fr.hetic;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


/**
 * This class represents a calculator that reads operations from a file,
 * performs the operations, and writes the results to another file.
 * If an operation is invalid, it writes "ERROR" to the result file.
 */
public class Calculator {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java fr.hetic.Calculateur <path-to-file>");
            return;
        }
        
    String filePath = args[0];
        if (!isValidFilePath(filePath)) {
            System.out.println("Invalid file path: " + filePath);
            return;
        }
    String resultPath = getResultFilePath(filePath);

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath));
             PrintWriter writer = new PrintWriter(new FileWriter(resultPath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                int result = processLine(line);
                if (result == Integer.MIN_VALUE) {
                    writer.println("ERROR");
                } else {
                    writer.println(result);
                }
            }
            System.out.println("The result has been written to " + resultPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if the given file path is valid.
     * A file path is valid if it exists and is not a directory.
     *
     * @param filePath the file path to check
     * @return true if the file path is valid, false otherwise
    */
    private static boolean isValidFilePath(String filePath) {
        Path path = Paths.get(filePath);
        return Files.exists(path) && !Files.isDirectory(path);
    }

    /**
     * Generates the result file path based on the input file path.
     * The result file will be in the same directory as the input file,
     * and its name will be the same as the input file but with a ".res" extension.
     *
     * @param inputFilePath the path to the input file
     * @return the path to the result file
     */
    private static String getResultFilePath(String inputFilePath) {
        Path path = Paths.get(inputFilePath);
        String fileName = path.getFileName().toString();
        String resultFileName = fileName.substring(0, fileName.lastIndexOf('.')) + ".res";
        return path.getParent().resolve(resultFileName).toString();
    }

    /**
     * Processes a line from the input file. The line should contain two integers
     * and an operator (+, -, or *) separated by spaces.
     * If the line is invalid or the operator is unknown, it returns Integer.MIN_VALUE.
     *
     * @param line the line to process
     * @return the result of the operation or Integer.MIN_VALUE if the line is invalid
     */
    private static int processLine(String line) {
        String[] parts = line.split(" ");
        if (parts.length != 3) {
            return Integer.MIN_VALUE;
        }
        int num1 = Integer.parseInt(parts[0]);
        int num2 = Integer.parseInt(parts[1]);
        String operator = parts[2];
        switch (operator) {
            case "+":
                return num1 + num2;
            case "-":
                return num1 - num2;
            case "*":
                return num1 * num2;
            default:
                return Integer.MIN_VALUE;
        }
    }
}
