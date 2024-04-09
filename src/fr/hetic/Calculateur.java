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
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath));
             PrintWriter writer = new PrintWriter(resultPath, "UTF-8")) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Process the line and write the result to the file
                int result = processLine(line);
                if (result == Integer.MIN_VALUE) {
                    writer.println("ERROR");
                } else {
                    writer.println(result);
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
