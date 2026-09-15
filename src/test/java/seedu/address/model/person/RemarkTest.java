package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests the optional remark value.
 */
public class RemarkTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Remark(null));
    }

    @Test
    public void constructor_emptyAndFreeFormText_preservesValue() {
        assertEquals("", new Remark("").value);
        assertEquals("Call next week!", new Remark("Call next week!").toString());
    }

    @Test
    public void equals_comparesValue() {
        Remark remark = new Remark("Note");
        assertEquals(remark, new Remark("Note"));
        assertEquals(remark.hashCode(), new Remark("Note").hashCode());
        assertNotEquals(remark, new Remark("Other"));
        assertNotEquals(remark, null);
        assertNotEquals(remark, "Note");
    }
}
