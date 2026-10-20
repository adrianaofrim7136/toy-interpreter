package interpreter.model.type;

import static org.junit.jupiter.api.Assertions.*;

import interpreter.model.value.BoolValue;
import org.junit.jupiter.api.Test;

public class BoolTypeTest {
    @Test
    void defaultValueIsFalse() {
        assertEquals(new BoolValue(false), new BoolType().defaultValue());
    }

    @Test
    void toStringIsBool() {
        assertEquals("bool", new BoolType().toString());
    }

    @Test
    void equalBoolTypes() {
        assertEquals(new BoolType(), new BoolType());
        assertNotEquals(new BoolType(), new IntType());
    }
}
