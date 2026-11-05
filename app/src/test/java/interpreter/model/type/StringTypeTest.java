package interpreter.model.type;

import static org.junit.jupiter.api.Assertions.*;
import interpreter.model.value.StringValue;
import org.junit.jupiter.api.Test;

class StringTypeTest {
    @Test
    void defaultValueIsEmptyString() {
        assertEquals(new StringValue(""), new StringType().defaultValue());
    }

    @Test
    void toStringIsString() {
        assertEquals("string", new StringType().toString());
    }

    @Test
    void equalTypesAreEqual() {
        assertEquals(new StringType(), new StringType());
        assertNotEquals(new StringType(), new IntType());
    }
}