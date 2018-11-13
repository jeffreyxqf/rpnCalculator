/**
 * Created by jeffrey on 2018/7/24.
 */

import calculator.Calculator;
import calculator.CalculatorException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static junit.framework.TestCase.assertEquals;
import static org.junit.rules.ExpectedException.none;


public class CalculatorTest {

    @Test
    public void test() throws CalculatorException {
        Calculator calculator = new Calculator();
        calculator.preform("5 2");
        assertEquals("Stack: 5 2 ", calculator.parseResult());
    }

    @Test
    public void testSqrt() throws Exception {
        Calculator calculator = new Calculator();
        calculator.preform("2 sqrt");
        assertEquals("Stack: 1.4142135623 ", calculator.parseResult());
        calculator.preform("clear 9 sqrt");
        assertEquals("Stack: 3 ", calculator.parseResult());
    }

    @Test
    public void testSubtraction() throws Exception {
        Calculator calculator = new Calculator();
        calculator.preform("5 2    -");
        assertEquals("Stack: 3 ", calculator.parseResult());
        calculator.preform("3 -");
        assertEquals("Stack: 0 ", calculator.parseResult());
        calculator.preform("clear");
        assertEquals("Stack: ", calculator.parseResult());
    }

    @Test
    public void testUndo() throws Exception {
        Calculator calculator = new Calculator();
        calculator.preform("5 4  3  2");
        assertEquals("Stack: 5 4 3 2 ", calculator.parseResult());
        calculator.preform("undo undo *");
        assertEquals("Stack: 20 ", calculator.parseResult());
        calculator.preform("5   *");
        assertEquals("Stack: 100 ", calculator.parseResult());
        calculator.preform("undo");
        assertEquals("Stack: 20 5 ", calculator.parseResult());
    }

    @Test
    public void testDivision() throws Exception {
        Calculator calculator = new Calculator();
        calculator.preform("7  12  2   /");
        assertEquals("Stack: 7 6 ", calculator.parseResult());
        calculator.preform("*");
        assertEquals("Stack: 42 ", calculator.parseResult());
        calculator.preform("4 /");
        assertEquals("Stack: 10.5 ", calculator.parseResult());
    }


    @Test
    public void testMultiplication() throws Exception {
        Calculator calculator = new Calculator();
        calculator.preform("1 2  3  4  5");
        assertEquals("Stack: 1 2 3 4 5 ", calculator.parseResult());
        calculator.preform("*");
        assertEquals("Stack: 1 2 3 20 ", calculator.parseResult());
        calculator.preform("clear 3 4 -");
        assertEquals("Stack: -1 ", calculator.parseResult());
    }

    @Test
    public void testOperations() throws CalculatorException {
        Calculator calculator = new Calculator();
        calculator.preform("1 2  3  4  5");
        assertEquals("Stack: 1 2 3 4 5 ", calculator.parseResult());
        calculator.preform("* * * * ");
        assertEquals("Stack: 120 ", calculator.parseResult());
    }

    @Rule
    public ExpectedException expectedEx = none();

    @Test
    public void testInsufficientParametersException() throws Exception {
        Calculator calculator = new Calculator();
        expectedEx.expect(CalculatorException.class);
        expectedEx.expectMessage("operator * (position: 15): insufficient parameters");
        calculator.preform("1 2 3 * 5 + * * 6 5 ");
    }

    @Test
    public void testDivByZeroException() throws Exception {
        Calculator calculator = new Calculator();
        expectedEx.expect(CalculatorException.class);
        expectedEx.expectMessage("Dividend can't be 0.");
        calculator.preform("9 0  / ");
    }

    @Test
    public void testInvalidInstruction() throws Exception {
        Calculator calculator = new Calculator();
        expectedEx.expect(CalculatorException.class);
        expectedEx.expectMessage("Operator ( & ) is not supported yet.");
        calculator.preform("9 0  & ");
    }

    @Test
    public void testEmptyInput() throws Exception {
        Calculator calculator = new Calculator();
        expectedEx.expect(CalculatorException.class);
        expectedEx.expectMessage("Input is null. Please input operands.");
        calculator.preform("       ");
    }
}
