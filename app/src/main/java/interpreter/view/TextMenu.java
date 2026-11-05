package interpreter.view;

import interpreter.controller.Controller;
import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.stmt.IStmt;
import interpreter.repository.Repository;
import java.util.List;
import java.util.Scanner;

public class TextMenu {
    private final List<IStmt> programs;

    public TextMenu(List<IStmt> programs) {
        this.programs = programs;
    }

    public void show() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            printMenu();
            if (!scanner.hasNextLine()) return;
            String input = scanner.nextLine().trim();
            if (input.equals("0")) return;
            try {
                int choice = Integer.parseInt(input);
                if (choice < 1 || choice > programs.size())
                    System.out.println("Invalid option.");
                else
                    run(programs.get(choice - 1));
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }

    private void printMenu() {
        System.out.println("\n -- Toy interpreter --");
        for (int i = 0; i < programs.size(); i++)
            System.out.println((i + 1) + ". " + programs.get(i));
        System.out.println("0. Exit");
        System.out.print("Choose a program: ");
    }

    private void run(IStmt program) {
        try {
            PrgState state = PrgState.of(program);
            new Controller(new Repository(state), true).allStep();
        } catch (MyException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
