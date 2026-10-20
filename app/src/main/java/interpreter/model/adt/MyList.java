package interpreter.model.adt;

import interpreter.exceptions.ADTException;

import java.util.ArrayList;
import java.util.List;

public class MyList<T> implements IList<T> {
    private final List<T> list = new ArrayList<>(); // java.util

    @Override
    public void add(T v) {
        list.add(v);
    }

    @Override
    public T get(int n) throws ADTException {
        if (n < 0 || n >= list.size())
            throw new ADTException("index" + n + "is out of bounds");
        return list.get(n);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public String toString() {
        return list.toString();
    }

}
