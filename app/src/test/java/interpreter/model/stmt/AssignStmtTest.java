package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.*;
import interpreter.model.type.IntType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AssignStmtTest {
    private PrgState state;

    private static IExp num(int n) {
        return new ValueExp(new IntValue(n));
    }

    @BeforeEach
    void setup() {
        state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new NopStmt());
    }

    @Test
    void assignInt() throws MyException {
        new VarDeclStmt("v", new IntType()).execute(state);
        new AssignStmt("v", new ArithExp(ArithOp.ADD, num(3), num(4))).execute(state);
        assertEquals(new IntValue(7), state.getSymTable().lookup("v"));
    }

    @Test
    void assigningUndeclaredVarThrows() {
        var stmt = new AssignStmt("v", num(1));
        assertThrows(StatementException.class, ()-> stmt.execute(state));
    }

    @Test
    void assigningWrongTypeThrows() throws MyException {
        new VarDeclStmt("v", new IntType()).execute(state);
        var stmt = new AssignStmt("v", new ValueExp(new BoolValue(true)));
        assertThrows(StatementException.class, () -> stmt.execute(state));
    }
}