package interpreter.controller;
import interpreter.exceptions.ADTException;
import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.stmt.IStmt;
import interpreter.model.stmt.NopStmt;
import interpreter.model.value.IntValue;
import interpreter.repository.Repository;
import interpreter.view.Examples;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
    private static PrgState run(IStmt program) throws MyException {
        PrgState state = PrgState.of(program);
        new Controller(new Repository(state), false).allStep();
        return state;
    }

    @Test
    void example1PrintsTwo() throws MyException {
        PrgState state = run(Examples.ex1);
        assertTrue(state.getExeStack().isEmpty());
        assertEquals(new IntValue(2), state.getOut().get(0));
    }

    @Test
    void example2PrintsEighteen() throws MyException {
        assertEquals(new IntValue(18), run(Examples.ex2).getOut().get(0));
    }

    @Test
    void example3PrintsTwo() throws MyException {
        assertEquals(new IntValue(2), run(Examples.ex3).getOut().get(0));
    }

    @Test
    void oneStepOnEmptyStackThrows() throws ADTException {
        var state = PrgState.of(new NopStmt());
        var controller = new Controller(new Repository(state), false);
        state.getExeStack().pop(); // needs try/catch or "throws ADTException"
        assertThrows(StatementException.class, () -> controller.oneStep(state));
    }
}