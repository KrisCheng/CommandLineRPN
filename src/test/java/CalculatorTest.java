/**
 * @author: Peng Cheng
 * @description:
 * @since: 2020/10/14 00:36
 */

import org.junit.Test;
import rpn.CalculateException;
import rpn.Calculator;

import static org.junit.Assert.assertEquals;

public class CalculatorTest {

    @Test
    public void testAritmeticOperators() throws Exception {
        Calculator calculator = new Calculator();

        calculator.processLine("5 2");
        assertEquals(5, calculator.getValueStack().get(0), 0);
        assertEquals(2, calculator.getStackItem(1), 0);

        // substraction
        calculator.processLine("clear");
        calculator.processLine("5 2 -");
        assertEquals(1, calculator.getValueStack().size());
        assertEquals(3, calculator.getStackItem(0), 0);
        calculator.processLine("3 -");
        assertEquals(1, calculator.getValueStack().size());
        assertEquals(0, calculator.getStackItem(0), 0);

        // negative
        calculator.processLine("clear");
        calculator.processLine("1 2 3 4 5 *");
        assertEquals(4, calculator.getValueStack().size());
        calculator.processLine("clear 3 4 -");
        assertEquals(1, calculator.getValueStack().size());
        assertEquals(-1, calculator.getStackItem(0), 0);


        // division
        calculator.processLine("clear");
        calculator.processLine("7 12 2 /");
        assertEquals(7, calculator.getStackItem(0), 0);
        assertEquals(6, calculator.getStackItem(1), 0);
        calculator.processLine("*");
        assertEquals(1, calculator.getValueStack().size());
        assertEquals(42, calculator.getStackItem(0), 0);
        calculator.processLine("4 /");
        assertEquals(1, calculator.getValueStack().size());
        assertEquals(10.5, calculator.getStackItem(0), 0);

        //multiplication
        calculator.processLine("clear");
        calculator.processLine("1 2 3 4 5");
        calculator.processLine("* * * *");
        assertEquals(1, calculator.getValueStack().size());
        assertEquals(120, calculator.getStackItem(0), 0);

    }

    @Test
    public void testSqrt() throws Exception {
        Calculator calculator = new Calculator();
        calculator.processLine("2 sqrt");
        calculator.processLine("clear 9 sqrt");
        assertEquals(1, calculator.getValueStack().size());
        assertEquals(3, calculator.getStackItem(0), 0);
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
        assertEquals(11, calculator.getStackItem(0), 0);
    }

    @Test
    public void testUndo() throws Exception {
        Calculator calculator = new Calculator();
        calculator.processLine("5 4 3 2");
        assertEquals(4, calculator.getValueStack().size());
        calculator.processLine("undo undo *");
        assertEquals(1, calculator.getValueStack().size());
        assertEquals(20, calculator.getStackItem(0), 0);
        calculator.processLine("5 *");
        assertEquals(1, calculator.getValueStack().size());
        assertEquals(100, calculator.getStackItem(0), 0);
        calculator.processLine("undo");
        assertEquals(2, calculator.getValueStack().size());
        assertEquals(20, calculator.getStackItem(0), 0);
        assertEquals(5, calculator.getStackItem(1), 0);
        calculator.processLine("+ undo - undo / undo * undo sqrt undo pow undo");
        assertEquals(2, calculator.getValueStack().size());
        assertEquals(20, calculator.getStackItem(0), 0.0000000001);
        assertEquals(5, calculator.getStackItem(1), 0.0000000001);
    }

    @Test(expected = CalculateException.class)
    public void testOnlyOperators() throws Exception {
        Calculator calculator = new Calculator();
        calculator.processLine("+ +");
    }

    @Test(expected = CalculateException.class)
    public void testInvalidCharacters() throws Exception {
        Calculator calculator = new Calculator();
        calculator.processLine("2 a +");
    }

    @Test(expected = CalculateException.class)
    public void testNoSpaces() throws Exception {
        Calculator calculator = new Calculator();
        calculator.processLine("22+");
    }

    @Test(expected = CalculateException.class)
    public void testNoSpaces2() throws Exception {
        Calculator calculator = new Calculator();
        calculator.processLine("2 2+ 3");
    }

    @Test(expected = CalculateException.class)
    public void testDivideByZero() throws Exception {
        Calculator calculator = new Calculator();
        calculator.processLine("1 0 /");
    }
}
