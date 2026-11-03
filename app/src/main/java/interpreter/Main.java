package interpreter;

import interpreter.view.Examples;
import interpreter.view.TextMenu;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        new TextMenu(List.of(Examples.ex1, Examples.ex2, Examples.ex3)).show();
    }
}
