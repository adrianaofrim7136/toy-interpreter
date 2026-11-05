package interpreter.model.adt;

import static org.junit.jupiter.api.Assertions.*;

import interpreter.exceptions.ADTException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

class MyDictionaryTest {
    private IDictionary<String, String> dict;

    @BeforeEach
    void setDict() {
        dict = new MyDictionary<>();
    }

    @Test
    void testDictPut() throws ADTException {
        dict.put("7", "july");
        assertTrue(dict.isDefined("7"));
        assertEquals("july", dict.lookup("7"));
    }

    @Test
    void testDictIsDefined() {
        assertFalse(dict.isDefined("7"));
    }

    @Test
    void testDictUpdate() throws ADTException {
        dict.put("7", "june");
        dict.update("7", "july");
        assertEquals("july", dict.lookup("7"));
    }

    @Test
    void testDictRemove() throws ADTException {
        dict.put("7", "july");
        dict.remove("7");
        assertThrows(ADTException.class,()-> dict.lookup("7"));
    }

    @Test
    void testDictLookUpThrows() {
        assertThrows(ADTException.class, () -> dict.lookup("7"));
    }

    @Test
    void testDictUpdateThrows() {
        assertThrows(ADTException.class, () -> dict.update("7", "july"));
    }

    @Test
    void removeOfMissingKeyThrows() {
        assertThrows(ADTException.class, () -> dict.remove("7"));
    }

    @Test
    void toMapReturnsCopy() {
        dict.put("7", "july");
        var copy = dict.toMap();
        assertEquals(Map.of("7", "july"), copy);
        dict.put("8", "august");
        assertEquals(1, copy.size()); // copy was made before putting 8 in the map
    }
}
