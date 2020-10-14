/**
 * @author: Peng Cheng
 * @description: functionality test
 * @since: 2020/10/14 00:36
 */

import org.junit.Test;
import rpn.CalculateException;
import rpn.Calculator;

import static org.junit.Assert.assertEquals;

public class CalculatorTest {

    @Test
    public void testBasicOperators() {
        Calculator calculator = new Calculator();

        calculator.processLine("5 2");
        assertEquals(5, calculator.getValueStack().get(0), 0);
        assertEquals(2, calculator.getValueStack().get(1), 0);

        // sqrt
        calculator.processLine("clear");
        calculator.processLine("2 sqrt");
        assertEquals(1.4142135623, calculator.getValueStack().get(0), 0.0000000001);
        calculator.processLine("clear 9 sqrt");
        assertEquals(3, calculator.getValueStack().get(0), 0.0000000001);

        // basic calculate 1
        calculator.processLine("clear");
        calculator.processLine("5 2 -");
        assertEquals(1, calculator.getValueStack().size(), 0);
        assertEquals(3, calculator.getValueStack().get(0), 0);
        calculator.processLine("3 -");
        assertEquals(1, calculator.getValueStack().size(), 0);
        assertEquals(0, calculator.getValueStack().get(0), 0);
        calculator.processLine("clear");
        assertEquals(0, calculator.getValueStack().size(), 0);

        // basic calculate 2
        calculator.processLine("clear");
        calculator.processLine("7 12 2 /");
        assertEquals(2, calculator.getValueStack().size(), 0);
        assertEquals(7, calculator.getValueStack().get(0), 0);
        calculator.processLine("*");
        assertEquals(1, calculator.getValueStack().size(), 0);
        assertEquals(42, calculator.getValueStack().get(0), 0);
        calculator.processLine("4 /");
        assertEquals(1, calculator.getValueStack().size(), 0);
        assertEquals(10.5, calculator.getValueStack().get(0), 0.0000000001);

        // basic calculate 3
        calculator.processLine("clear");
        calculator.processLine("1 2 3 4 5");
        assertEquals(5, calculator.getValueStack().size(), 0);
        calculator.processLine("*");
        assertEquals(4, calculator.getValueStack().size(), 0);
        calculator.processLine("clear 3 4 -");
        assertEquals(1, calculator.getValueStack().size(), 0);
        assertEquals(-1, calculator.getValueStack().get(0), 0.0000000001);

        // undo
        calculator.processLine("clear");
        calculator.processLine("5 4 3 2");
        assertEquals(4, calculator.getValueStack().size(), 0);
        calculator.processLine("undo undo *");
        assertEquals(1, calculator.getValueStack().size(), 0);
        assertEquals(20, calculator.getValueStack().get(0), 0);
        calculator.processLine("5 *");
        assertEquals(1, calculator.getValueStack().size(), 0);
        assertEquals(100, calculator.getValueStack().get(0), 0);
        calculator.processLine("undo");
        assertEquals(2, calculator.getValueStack().size(), 0);
        assertEquals(20, calculator.getValueStack().get(0), 0);
        assertEquals(5, calculator.getValueStack().get(1), 0);
    }

    @Test
    public void testInsuficientParameters() {
        Calculator calculator = new Calculator();
        try {
            calculator.processLine("1 2 3 * 5 + * * 6 5");
        } catch (CalculateException e) {
            assertEquals("operator * (position: 8): insufficient parameters", e.getMessage());
        }
        assertEquals(1, calculator.getValueStack().size());
        assertEquals(11, calculator.getValueStack().get(0), 0);
    }
}