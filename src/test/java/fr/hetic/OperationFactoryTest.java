package fr.hetic;

import org.junit.Test;
import static org.junit.Assert.*;

public class OperationFactoryTest {

    private OperationFactory operationFactory = new OperationFactory();

    @Test
    public void testGetOperation() {
        assertOperation("+", 1, 2, 3);
        assertOperation("-", 1, 2, -1);
        assertOperation("*", 1, 2, 2);
        assertOperationNotFound("/");
        assertOperationNotFound("?");
    }

    private void assertOperation(String operator, int operand1, int operand2, int expected) {
        Operation operation = operationFactory.getOperation(operator);
        assertNotNull("Operation should not be null for operator: " + operator, operation);
        assertEquals("Unexpected result for " + operator, expected, operation.execute(operand1, operand2));
    }

    private void assertOperationNotFound(String operator) {
        Operation operation = operationFactory.getOperation(operator);
        assertNull("Operation should be null for unknown operator: " + operator, operation);
    }
}
