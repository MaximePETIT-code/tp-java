package fr.hetic;

public class Addition implements Operation {
    @Override
    public int execute(int num1, int num2) {
        return num1 + num2;
    }
}
