package interpreter.model.value;

import interpreter.model.type.IntType;
import interpreter.model.type.RefType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class RefValueTest {
    @Test
    void typeIsRef() {
        assertEquals(new RefType(new IntType()), new RefValue(1, new IntType()).getType());
    }

    @Test
    void equalRefsAreEqual() {
        assertEquals(new RefValue(1, new IntType()), new RefValue(1, new IntType()));
        assertNotEquals(new RefValue(1, new IntType()), new RefValue(2, new IntType()));
        assertNotEquals(new RefValue(0, new IntType()),
                new RefValue(1, new RefType(new IntType())));
    }

    @Test
    void toStringsRefType() {
        assertEquals("(" + 1 + ", "+ new IntType().toString() + ")", new RefValue(1, new IntType()).toString());
    }
}