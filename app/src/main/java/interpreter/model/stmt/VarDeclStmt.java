package interpreter.model.stmt;

import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.type.IType;
import interpreter.model.value.IValue;

public record VarDeclStmt(String name, IType type) implements IStmt {
    //S3
    @Override
    public PrgState execute(PrgState state) throws StatementException {
        var symTable = state.getSymTable();
        if (symTable.isDefined(name))
            throw new StatementException("Variable" + name + "is already defined");
        symTable.put(name, type.defaultValue());
        return state;
    }

    @Override
    public String toString() {
        return name + " " + type;
    }
}

