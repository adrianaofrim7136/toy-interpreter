package interpreter.model.stmt;

import interpreter.controller.Controller;
import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.*;
import interpreter.model.type.IntType;
import interpreter.model.value.IntValue;
import interpreter.repository.Repository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class WhileStmtTest {
    @TempDir
    Path dir;

    private PrgState runLoop(int n) throws MyException {   // int v; v = n; while (v > 0) (print(v); v = v - 1)
        IExp v = new VarExp("v");
        IStmt body = new CompStmt(new PrintStmt(v),
                new AssignStmt("v", new ArithExp(ArithOp.SUB, v, new ValueExp(new IntValue(1)))));
        IStmt program = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(n))),
                        new WhileStmt(new RelationalExp(RelOp.GT, v, new ValueExp(new IntValue(0))), body)));
        PrgState state = PrgState.of(program);
        new Controller(new Repository(state, dir.resolve("log.txt").toString()), false).allStep();
        return state;
    }

    @Test
    void loopRunsZeroTimes() throws MyException { assertEquals(0, runLoop(0).getOut().size()); }
    @Test void loopRunsOnce() throws MyException { assertEquals(1, runLoop(1).getOut().size()); }
    @Test void loopRunsSeveralTimes() throws MyException {
        var state = runLoop(3);
        assertEquals(3, state.getOut().size());
        assertEquals(new IntValue(0), state.getSymTable().lookup("v"));
    }

    @Test
    void nonBooleanConditionThrows() {
        var state = PrgState.of(new NopStmt());
        var loop = new WhileStmt(new ValueExp(new IntValue(1)), new NopStmt());
        assertThrows(StatementException.class, () -> loop.execute(state));
    }
}
