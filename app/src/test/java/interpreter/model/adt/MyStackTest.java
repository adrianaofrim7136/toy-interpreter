package interpreter.model.adt;

import static org.junit.jupiter.api.Assertions.*;

import interpreter.exceptions.ADTException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class MyStackTest {
    private IStack<Integer> stack;

    @BeforeEach
    void setStack() {
        stack = new MyStack<>();
    }

    @Test
    void popOnEmptyStackThrows() {
        assertThrows(ADTException.class, () -> stack.pop());
    }

    @Test
    void popReturnsLastPushedElement() throws ADTException {
        stack.push(7);
        stack.push(8);
        assertEquals(8, stack.pop());
        assertEquals(7, stack.pop());
    }

    @Test
    void testStackWithElementIsNotEmpty() {
        stack.push(2);
        assertFalse(stack.isEmpty());
    }

    @Test
    void newStackIsEmpty() {
        assertTrue(stack.isEmpty());
    }
}
