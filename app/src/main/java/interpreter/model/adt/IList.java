package interpreter.model.adt;

import interpreter.exceptions.ADTException;

import java.util.List;

public interface IList<T> {
    void add(T v);
    T get(int n) throws ADTException;
    int size();
    List<T> toList();
}
