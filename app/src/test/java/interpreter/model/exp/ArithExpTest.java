package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ArithExpTest {
    private final IDictionary<String, IValue> symTable = new MyDictionary<>();

    private static IExp integer(int n) {
        return new ValueExp(new IntValue(n));
    }

    @Test
    void testAdd() throws MyException {
        assertEquals(new IntValue(7), new ArithExp(ArithOp.ADD, integer(3), integer(4)).eval(symTable));
    }

    @Test
    void substractWorksWithTwoNumbers() throws MyException {
        IExp e = new ArithExp(ArithOp.SUB, integer(7), integer(4));
        assertEquals(new IntValue(3), e.eval(symTable));
    }

    @Test
    void multiplyWorksWithTwoNumbers() throws MyException {
        IExp e = new ArithExp(ArithOp.MUL, integer(3), integer(7));
        assertEquals(new IntValue(21), e.eval(symTable));
    }

    @Test
    void divideWorksWithTwoNumbers() throws MyException {
        IExp e = new ArithExp(ArithOp.DIV, integer(8), integer(2));
        assertEquals(new IntValue(4), e.eval(symTable));
    }

    @Test
    void divisionByZeroThrows() {
        IExp e = new ArithExp(ArithOp.DIV, integer(1), integer(0));
        assertThrows(ExpressionException.class, () -> e.eval(symTable));
    }
}
