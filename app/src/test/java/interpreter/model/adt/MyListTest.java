package interpreter.model.adt;

import static org.junit.jupiter.api.Assertions.*;

import interpreter.exceptions.ADTException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MyListTest {
    private IList<Integer> list;

    @BeforeEach
    void setUp() {
        list = new MyList<>();
    }

    @Test
    void testAdd() throws ADTException {
        list.add(7);
        list.add(17);
        assertEquals(2, list.size());
        assertEquals(7, list.get(0));
        assertEquals(17, list.get(1));
    }

    @Test
    void testGet() {
        list.add(13);
        assertThrows(ADTException.class, () -> list.get(1));
        assertThrows(ADTException.class, () -> list.get(-1));
    }
}
