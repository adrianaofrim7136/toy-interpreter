package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public record ReadFileStmt(IExp exp, String varName) implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws MyException {
        var symTable = state.getSymTable();
        if (!(symTable.isDefined(varName)))
            throw new StatementException("variable " + varName + " was not declared");
        if (!(symTable.lookup(varName) instanceof IntValue))
            throw new StatementException(varName + " is not an IntValue");

        if (!(exp.eval(symTable) instanceof StringValue fileName))
            throw new StatementException("readFile: " + exp + " is not a string");

        var fileTable = state.getFileTable();
        if (!(fileTable.isDefined(fileName)))
            throw new StatementException("readRFile: " + fileName + " is not open");

        try {
            BufferedReader reader = fileTable.lookup(fileName);
            String line = reader.readLine();
            int n = (line == null) ? 0 : Integer.parseInt(line.strip());
            symTable.update(varName, new IntValue(n));
        }
        catch (IOException e) {
            throw new StatementException("readFile: cannot read " + fileName + ": " + e.getMessage());
        }
        catch (NumberFormatException e) {
            throw new StatementException("readFile: line is not an integer: " + e.getMessage());
        }
        return state;
    }

    @Override
    public String toString() {
        return "readFile(" + exp + ", " + varName + ")";
    }
}

