package interpreter.controller;
import interpreter.exceptions.ADTException;
import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.stmt.IStmt;
import interpreter.model.stmt.NopStmt;
import interpreter.model.type.IntType;
import interpreter.model.type.RefType;
import interpreter.model.value.IntValue;
import interpreter.model.value.RefValue;
import interpreter.repository.Repository;
import interpreter.view.Examples;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
    @TempDir
    Path dir;

    private PrgState run(IStmt program) throws MyException {
        PrgState state = PrgState.of(program);
        var repo = new Repository(state, dir.resolve("log.txt").toString());
        new Controller(repo, false).allStep();
        return state;
    }

    @Test
    void example1PrintsTwo() throws MyException {
        PrgState state = run(Examples.EX1);
        assertTrue(state.getExeStack().isEmpty());
        assertEquals(new IntValue(2), state.getOut().get(0));
    }

    @Test
    void example2PrintsEighteen() throws MyException {
        assertEquals(new IntValue(18), run(Examples.EX2).getOut().get(0));
    }

    @Test
    void example3PrintsTwo() throws MyException {
        assertEquals(new IntValue(2), run(Examples.EX3).getOut().get(0));
    }

    @Test
    void example5PrintsFive() throws MyException {
        assertEquals(new IntValue(5), run(Examples.EX5).getOut().get(0));
    }

    @Test
    void oneStepOnEmptyStackThrows() throws ADTException {
        var state = PrgState.of(new NopStmt());
        var repo = new Repository(state, dir.resolve("log.txt").toString());
        var controller = new Controller(repo, false);
        state.getExeStack().pop(); // needs try/catch or "throws ADTException"
        assertThrows(StatementException.class, () -> controller.oneStep(state));
    }

    @Test
    void fileExampleReadsBothNumbersAndClosesTheFile() throws Exception {
        Path in = dir.resolve("test.in");
        Files.writeString(in, "15\n50\n");
        PrgState s = run(Examples.fileExample(in.toString()));
        assertEquals(new IntValue(15), s.getOut().get(0));
        assertEquals(new IntValue(50), s.getOut().get(1));
        assertTrue(s.getFileTable().toMap().isEmpty());
    }

    @Test
    void logFileContainsTheFourSectionsInOrder() throws Exception {
        run(Examples.EX1);
        String log = Files.readString(dir.resolve("log.txt"));
        int a = log.indexOf("ExeStack:"), b = log.indexOf("SymTable:"),
                c = log.indexOf("Out:"), d = log.indexOf("FileTable:"),
                e = log.indexOf("Heap:");
        assertTrue(0 <= a && a < b && b < c && c < d && d < e);
    }


    @Test void example6() throws MyException {
        var s = run(Examples.EX6);
        assertEquals(new RefValue(1, new IntType()), s.getOut().get(0));
        assertEquals(new RefValue(2, new RefType(new IntType())), s.getOut().get(1));
        assertEquals(Map.of(1, new IntValue(20), 2, new RefValue(1, new IntType())), s.getHeap().getContent());
    }
    @Test void example7() throws MyException {
        var s = run(Examples.EX7);
        assertEquals(new IntValue(20), s.getOut().get(0));
        assertEquals(new IntValue(25), s.getOut().get(1));
    }
    @Test void example8() throws MyException {
        var s = run(Examples.EX8);
        assertEquals(new IntValue(20), s.getOut().get(0));
        assertEquals(new IntValue(35), s.getOut().get(1));
        assertEquals(Map.of(1, new IntValue(30)), s.getHeap().getContent());
    }
    @Test void example9PrintsTwentyWithTheSafeCollector() throws MyException {
        assertEquals(new IntValue(20), run(Examples.EX9).getOut().get(0));
    }
    @Test void example10CountsDown() throws MyException {
        var s = run(Examples.EX10);
        assertEquals(List.of(new IntValue(4), new IntValue(3), new IntValue(2), new IntValue(1), new IntValue(0)),
                s.getOut().toList());
    }
}