package interpreter.model.adt;

import static org.junit.jupiter.api.Assertions.*;

import interpreter.exceptions.ADTException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MyDictionaryTest {
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
    void testDictLookUpThrows() {
        assertThrows(ADTException.class, () -> dict.lookup("7"));
    }

    @Test
    void testDictUpdateThrows() {
        assertThrows(ADTException.class, () -> dict.update("7", "july"));
    }




}
