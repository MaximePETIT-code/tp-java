package fr.hetic;

public class OperationFactory {
    public Operation getOperation(String operator) {
        switch (operator) {
            case "+": return new Addition();
            case "-": return new Subtraction();
            case "*": return new Multiplication();
            default: return null;
        }
    }
}