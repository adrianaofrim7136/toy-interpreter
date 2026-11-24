package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.IHeap;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyHeap;
import interpreter.model.type.IntType;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.RefValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ReadHeapExpTest {
    private final IDictionary<String, IValue> symTable = new MyDictionary<>();
    private final IHeap heap = new MyHeap();

    @Test
    void readsTheValueStoredAtAddress() throws MyException {
        int address = heap.allocate(new IntValue(19)); // heap {1 -> 19}
        symTable.put("v", new RefValue(address, new IntType())); // v -> (1, int)

        IValue result = new ReadHeapExp(new VarExp("v")).eval(symTable, heap);
        assertEquals(new IntValue(19), result);
    }

    @Test
    void nonReferenceExpressionThrows() {
        var exp = new ReadHeapExp(new ValueExp(new IntValue(1)));
        assertThrows(ExpressionException.class, () -> exp.eval(symTable, heap));
    }

    @Test
    void addressNotInTheHeapThrows() {
        symTable.put("v", new RefValue(3, new IntType())); // the heap is empty
        var exp = new ReadHeapExp(new VarExp("v"));
        assertThrows(ExpressionException.class, () -> exp.eval(symTable, heap));
    }
}
