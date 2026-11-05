package interpreter.model.value;
import static org.junit.jupiter.api.Assertions.*;
import interpreter.model.type.StringType;
import org.junit.jupiter.api.Test;

class StringValueTest {
    @Test
    void typeIsString() {
        assertEquals(new StringType(), new StringValue("seven").getType());
    }

    @Test
    void equalsValuesAreEqual() {
        assertEquals(new StringValue("seven"), new StringValue("seven"));
        assertNotEquals(new StringValue("1"), new StringValue("2"));
    }

    @Test
    void toStringShowsTheTextInQuotes() {
        assertEquals("\"seven\"", new StringValue("seven").toString());
    }

    @Test
    void equalValuesHaveTheSameHashCode() {
        assertEquals(new StringValue("seven").hashCode(), new StringValue("seven").hashCode());
    }
}

