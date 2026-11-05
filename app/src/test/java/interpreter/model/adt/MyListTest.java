package interpreter.model.adt;

import static org.junit.jupiter.api.Assertions.*;

import interpreter.exceptions.ADTException;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MyListTest {
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
    void testSize() {
        list.add(2);
        list.add(18);
        assertEquals(2, list.size());
    }

    @Test
    void testGet() {
        list.add(13);
        assertThrows(ADTException.class, () -> list.get(1));
        assertThrows(ADTException.class, () -> list.get(-1));
    }
}
