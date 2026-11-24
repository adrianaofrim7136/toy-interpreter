package interpreter.model.exp;
import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.IHeap;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyHeap;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RelationalExpTest {
    private final IDictionary<String, IValue> symTable = new MyDictionary<>();
    private final IHeap heap = new MyHeap();
    private static IExp num(int n) {
        return new ValueExp(new IntValue(n));
    }

    // for LT
    @Test
    void trueWhenLeftIsSmaller() throws MyException {
        assertEquals(new BoolValue(true), new RelationalExp(RelOp.LT, num(3), num(7)).eval(symTable, heap));
    }

    @Test
    void falseWhenEqual() throws MyException {
        assertEquals(new BoolValue(false), new RelationalExp(RelOp.LT, num(7), num(7)).eval(symTable, heap));
    }

    // for LE
    @Test
    void trueWhenSmallerOrEqual() throws MyException {
        assertEquals(new BoolValue(true), new RelationalExp(RelOp.LE, num(7), num(7)).eval(symTable, heap));
    }

    @Test
    void falseWhenGreater() throws MyException {
        assertEquals(new BoolValue(false), new RelationalExp(RelOp.LE, num(9), num(7)).eval(symTable, heap));
    }

    //for EQ
    @Test
    void trueWhenEqual() throws MyException {
        assertEquals(new BoolValue(true), new RelationalExp(RelOp.EQ, num(7), num(7)).eval(symTable, heap));
    }

    @Test
    void falseWhenNotEqual() throws MyException {
        assertEquals(new BoolValue(false), new RelationalExp(RelOp.EQ, num(8), num(3)).eval(symTable, heap));
    }

    // for NQ
    @Test
    void trueWhenNotEqual() throws MyException {
        assertEquals(new BoolValue(true), new RelationalExp(RelOp.NE, num(1), num(2)).eval(symTable, heap));
    }

    @Test
    void falseWhenNeEqual() throws MyException {
        assertEquals(new BoolValue(false), new RelationalExp(RelOp.NE, num(1), num(1)).eval(symTable, heap));
    }

    // for GT
    @Test
    void trueWhenGreater() throws MyException {
        assertEquals(new BoolValue(true), new RelationalExp(RelOp.GT, num(7), num(4)).eval(symTable, heap));
    }

    @Test
    void falseWhenNotGreater() throws MyException {
        assertEquals(new BoolValue(false), new RelationalExp(RelOp.GT, num(1), num(4)).eval(symTable, heap));
    }

    //for GE
    @Test
    void trueWhenGreaterOrEqual() throws MyException {
        assertEquals(new BoolValue(true), new RelationalExp(RelOp.GE, num(7), num(7)).eval(symTable, heap));
    }

    @Test
    void falseWhenNotGreaterOrEqual() throws MyException {
        assertEquals(new BoolValue(false), new RelationalExp(RelOp.GE, num(7), num(8)).eval(symTable, heap));
    }

    // error case
    @Test
    void nonIntegerOperandThrows() {
        IExp bool = new ValueExp(new BoolValue(true));
        assertThrows(ExpressionException.class,
                () -> new RelationalExp(RelOp.LT, bool, num(1)).eval(symTable, heap));
        assertThrows(ExpressionException.class,
                () -> new RelationalExp(RelOp.LT, num(1), bool).eval(symTable, heap));
    }
}