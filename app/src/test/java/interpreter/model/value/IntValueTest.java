package interpreter.model.value;

import static org.junit.jupiter.api.Assertions.*;

import interpreter.model.type.IntType;
import org.junit.jupiter.api.Test;

public class IntValueTest {
    @Test
    void typeIsInt() {
        assertEquals(new IntType(), new IntValue(1).getType());
    }

    @Test
    void equalIntegersAreEqual() {
        assertEquals(new IntValue(1), new IntValue(1));
        assertNotEquals(new IntValue(1), new IntValue(2));
    }

    @Test
    void toStringsShowsInt() {
        assertEquals("1", new IntValue(1).toString());
    }
}
