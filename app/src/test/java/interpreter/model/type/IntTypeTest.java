package interpreter.model.type;

import static org.junit.jupiter.api.Assertions.*;

import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

public class IntTypeTest {
    @Test
    void defaultValueIsZero() {
        assertEquals(new IntValue(0), new IntType().defaultValue());
    }

    @Test
    void toStringIsInt() {
        assertEquals("int", new IntType().toString());
    }

    @Test
    void equalTypesAreEqual() {
        assertEquals(new IntType(), new IntType());
        assertNotEquals(new IntType(), new BoolType());
    }
}
