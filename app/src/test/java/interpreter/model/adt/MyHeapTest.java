package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class MyHeapTest {
    private IHeap heap;

    @BeforeEach
    void setUp() {
        heap = new MyHeap();
    }

    @Test
    void allocateReturnsConsecutiveAddresses() {
        assertEquals(1, heap.allocate(new IntValue(10)));
        assertEquals(2, heap.allocate(new IntValue(20)));
        assertEquals(3, heap.allocate(new IntValue(30)));

    }

    @Test
    void getReturnsTheAllocatedValue() throws ADTException {
        int a = heap.allocate(new IntValue(10));
        heap.update(a, new IntValue(17));
        assertEquals(new IntValue(17), heap.get(a));
    }

    @Test
    void setContentReplacesTheHeap() {
        heap.allocate(new IntValue(7));
        heap.setContent(Map.of());
        assertFalse(heap.isDefined(1));
        assertEquals(2, heap.allocate(new IntValue(5)));

    }
}

