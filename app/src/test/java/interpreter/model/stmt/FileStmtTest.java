package interpreter.model.stmt;

import static org.junit.jupiter.api.Assertions.*;
import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.ValueExp;
import interpreter.model.type.BoolType;
import interpreter.model.type.IntType;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.Files;
import java.nio.file.Path;


class FileStmtTest {
    @TempDir
    Path dir; // a new empty folder for every test

    private PrgState state;
    private ValueExp file; // the expression "<dir>/numbers.in"

    @BeforeEach
    void setUp() throws Exception {
        Path f = dir.resolve("numbers.in");
        Files.writeString(f, "7\n");
        file = new ValueExp(new StringValue(f.toString()));
        state = PrgState.of(new NopStmt());
        new VarDeclStmt("x", new IntType()).execute(state);
    }

    @AfterEach
    void closeOpenFiles() throws Exception {
        for (var reader : state.getFileTable().toMap().values())
            reader.close();
    }

    @Test
    void closeRemovesTheFileFromTheFileTable() throws MyException {
        new OpenRFileStmt(file).execute(state);
        new CloseRFileStmt(file).execute(state);
        assertTrue(state.getFileTable().toMap().isEmpty());
    }

    @Test
    void closingAFileThatIsNotOpenThrows() {
        assertThrows(StatementException.class, () -> new CloseRFileStmt(file).execute(state));
    }

    @Test
    void openingTheSameFileTwiceThrows() throws MyException {
        new OpenRFileStmt(file).execute(state);
        assertThrows(StatementException.class, () -> new OpenRFileStmt(file).execute(state));
        new CloseRFileStmt(file).execute(state);   // release the file
    }

    @Test
    void readGivesTheNextNumberThenZeroAtTheEnd() throws MyException {
        new OpenRFileStmt(file).execute(state);
        new ReadFileStmt(file, "x").execute(state);
        assertEquals(new IntValue(7), state.getSymTable().lookup("x"));
        new ReadFileStmt(file, "x").execute(state);
        assertEquals(new IntValue(0), state.getSymTable().lookup("x"));
    }

    @Test
    void readingIntoAnUndeclaredVariableThrows() throws MyException {
        new OpenRFileStmt(file).execute(state);
        assertThrows(StatementException.class,
                () -> new ReadFileStmt(file, "nope").execute(state));
    }

    @Test
    void readingIntoANonIntVariableThrows() throws MyException {
        new VarDeclStmt("b", new BoolType()).execute(state);
        new OpenRFileStmt(file).execute(state);
        assertThrows(StatementException.class,
                () -> new ReadFileStmt(file, "b").execute(state));
    }

    @Test
    void readingFromAFileThatIsNotOpenThrows() {
        assertThrows(StatementException.class,
                () -> new ReadFileStmt(file, "x").execute(state));
    }

    @Test
    void nonIntegerLineThrows() throws Exception {
        Path bad = dir.resolve("bad.in");
        Files.writeString(bad, "abc\n");
        ValueExp badFile = new ValueExp(new StringValue(bad.toString()));
        new OpenRFileStmt(badFile).execute(state);
        assertThrows(StatementException.class,
                () -> new ReadFileStmt(badFile, "x").execute(state));
    }
}

