package fr.hetic;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * This class represents a calculator that reads operations from a file,
 * performs the operations, and writes the results to another file.
 */
public class Calculateur {
    public static void main(String[] args) {
        // The path to the file containing the operations
        String filePath = "/Users/max/Desktop/hetic/java/TP1/src/fr/hetic/additions.op";
        // The path to the file where the results will be written
        String resultPath = "/Users/max/Desktop/hetic/java/TP1/src/fr/hetic/results.res";

        // Use try-with-resources to automatically close the resources after use
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath)); // BufferedReader for efficient reading
             PrintWriter writer = new PrintWriter(resultPath, "UTF-8")) { // PrintWriter for easy writing of text to a file
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    // Process each line and write the result to the file
                    int result = processLine(line);
                    writer.println(result);
                } catch (IllegalArgumentException e) {
                    System.out.println("ERROR: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Processes a line from the input file. The line should contain two integers
     * and an operator (+, -, or *) separated by spaces.
     *
     * @param line the line to process
     * @return the result of the operation
     * @throws IllegalArgumentException if the line format is invalid
     */
    private static int processLine(String line) {
        String[] parts = line.split(" ");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid line format: " + line);
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
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }
}
