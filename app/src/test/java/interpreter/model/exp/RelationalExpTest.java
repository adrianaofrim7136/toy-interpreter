package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RelationalExpTest {
    private final IDictionary<String, IValue> symTable = new MyDictionary<>();

    private static IExp num(int n) {
        return new ValueExp(new IntValue(n));
    }

    // for LT
    @Test
    void trueWhenLeftIsSmaller() throws MyException {
        assertEquals(new BoolValue(true), new RelationalExp(RelOp.LT, num(3), num(7)).eval(symTable));
    }

    @Test
    void falseWhenEqual() throws MyException {
        assertEquals(new BoolValue(false), new RelationalExp(RelOp.LT, num(7), num(7)).eval(symTable));
    }

    // for LE
    @Test
    void trueWhenSmallerOrEqual() throws MyException {
        assertEquals(new BoolValue(true), new RelationalExp(RelOp.LE, num(7), num(7)).eval(symTable));
    }

    @Test
    void falseWhenGreater() throws MyException {
        assertEquals(new BoolValue(false), new RelationalExp(RelOp.LE, num(9), num(7)).eval(symTable));
    }

    //for EQ
    @Test
    void trueWhenEqual() throws MyException {
        assertEquals(new BoolValue(true), new RelationalExp(RelOp.EQ, num(7), num(7)).eval(symTable));
    }

    @Test
    void falseWhenNotEqual() throws MyException {
        assertEquals(new BoolValue(false), new RelationalExp(RelOp.EQ, num(8), num(3)).eval(symTable));
    }

    // for NQ
    @Test
    void trueWhenNotEqual() throws MyException {
        assertEquals(new BoolValue(true), new RelationalExp(RelOp.NE, num(1), num(2)).eval(symTable));
    }

    @Test
    void falseWhenNeEqual() throws MyException {
        assertEquals(new BoolValue(false), new RelationalExp(RelOp.NE, num(1), num(1)).eval(symTable));
    }

    // for GT
    @Test
    void trueWhenGreater() throws MyException {
        assertEquals(new BoolValue(true), new RelationalExp(RelOp.GT, num(7), num(4)).eval(symTable));
    }

    @Test
    void falseWhenNotGreater() throws MyException {
        assertEquals(new BoolValue(false), new RelationalExp(RelOp.GT, num(1), num(4)).eval(symTable));
    }

    //for GE
    @Test
    void trueWhenGreaterOrEqyal() throws MyException {
        assertEquals(new BoolValue(true), new RelationalExp(RelOp.GE, num(7), num(7)).eval(symTable));
    }

    @Test
    void falseWhenNotGreaterOrEqyal() throws MyException {
        assertEquals(new BoolValue(false), new RelationalExp(RelOp.GE, num(7), num(8)).eval(symTable));
    }

    // error case
    @Test
    void nonIntegerOperandThrows() {
        IExp bool = new ValueExp(new BoolValue(true));
        assertThrows(ExpressionException.class,
                () -> new RelationalExp(RelOp.LT, bool, num(1)).eval(symTable));
        assertThrows(ExpressionException.class,
                () -> new RelationalExp(RelOp.LT, num(1), bool).eval(symTable));
    }
}