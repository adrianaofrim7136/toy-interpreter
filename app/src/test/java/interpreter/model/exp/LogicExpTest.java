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

class LogicExpTest {
    private final IDictionary<String, IValue> symTable = new MyDictionary<>();

    private static IExp bool(boolean b) { return new ValueExp(new BoolValue(b)); }

    @Test
    void andOfTrueAndFalseIsFalse() throws MyException {
        assertEquals(new BoolValue(false), new LogicExp(LogicOp.AND, bool(true), bool(false)).eval(symTable));
    }

    @Test
    void orOfTrueAndFalseIsTrue() throws MyException {
        assertEquals(new BoolValue(true), new LogicExp(LogicOp.OR, bool(true), bool(false)).eval(symTable));
    }

    @Test
    void nonBooleanOperandThrows() {
        IExp number = new ValueExp(new IntValue(1));
        assertThrows(ExpressionException.class, () -> new LogicExp(LogicOp.AND, number, bool(true)).eval(symTable));
    }
}
