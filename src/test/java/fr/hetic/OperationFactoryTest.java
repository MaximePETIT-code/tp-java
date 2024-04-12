package fr.hetic;

import org.junit.Test;
import static org.assertj.core.api.Assertions.*;

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
        assertThat(operation)
            .as("Operation should not be null for operator: %s", operator)
            .isNotNull();
        assertThat(operation.execute(operand1, operand2))
            .as("Unexpected result for %s", operator)
            .isEqualTo(expected);
    }

    private void assertOperationNotFound(String operator) {
        Operation operation = operationFactory.getOperation(operator);
        assertThat(operation)
            .as("Operation should be null for unknown operator: %s", operator)
            .isNull();
    }
}