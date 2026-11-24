package interpreter.model.type;

import interpreter.model.value.IntValue;
import interpreter.model.value.RefValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class RefTypeTest {
    @Test
    void defaultValueZero() {
        assertEquals(new RefValue(0, new IntType()), new RefType(new IntType()).defaultValue());
    }

    @Test
    void toStringIsRef() {
        assertEquals("Ref(" + new IntType().toString() + ")", new RefType(new IntType()).toString());
    }

    @Test
    void equalTestsAreEq() {
        assertEquals(new RefType(new IntType()), new RefType(new IntType()));
        assertNotEquals(new RefType(new IntType()), new RefType(new StringType()));
    }

    @Test
    void nestedRefTypeComparison() {
        var a = new RefType(new RefType(new IntType()));
        var b = new RefType(new RefType(new IntType()));
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, new RefType(new IntType()));
    }
}