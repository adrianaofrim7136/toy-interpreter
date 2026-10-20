package interpreter.model.value;

import static org.junit.jupiter.api.Assertions.*;

import interpreter.model.type.BoolType;
import org.junit.jupiter.api.Test;

public class BoolValueTest {
    @Test
    void typeIsBool() {
        assertEquals(new BoolType(), new BoolValue(true).getType());
    }

    @Test
    void equalsValuesAreEqual() {
        assertEquals(new BoolValue(true), new BoolValue(true));
        assertNotEquals(new BoolValue(true), new BoolValue(false));
    }

    @Test
    void toStringShowsTheValue() {
        assertEquals("false", new BoolValue(false).toString());
    }
}
