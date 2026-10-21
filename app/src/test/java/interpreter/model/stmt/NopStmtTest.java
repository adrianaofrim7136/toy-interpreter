package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NopStmtTest {
    @Test
    void leavesTheStateUnchanged() throws MyException {
        var state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new NopStmt());
        state.getSymTable().put("v", new IntValue(1));

        new NopStmt().execute(state);

        assertEquals(new IntValue(1), state.getSymTable().lookup("v"));
        assertEquals(0, state.getOut().size());
    }
}