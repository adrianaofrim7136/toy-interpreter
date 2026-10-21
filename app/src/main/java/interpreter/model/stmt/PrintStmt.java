package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.value.IValue;

public record PrintStmt(IExp exp) implements IStmt {
    // S4
    @Override
    public PrgState execute(PrgState state) throws MyException {
        var symTable = state.getSymTable();
        IValue val = exp.eval(symTable);
        state.getOut().add(val);
        return state;
    }

    @Override
    public String toString() {
        return "print(" + exp + ")";
    }
}