package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class RemarkTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Remark(null));
    }

    @Test
    public void constructor_emptyAndText_preservesValue() {
        assertEquals("", new Remark("").value);
        assertEquals("Some remark", new Remark("Some remark").toString());
    }

    @Test
    public void equals_comparesValue() {
        Remark remark = new Remark("Some remark");
        assertEquals(remark, new Remark("Some remark"));
        assertEquals(remark.hashCode(), new Remark("Some remark").hashCode());
        assertNotEquals(remark, new Remark("Different remark"));
        assertNotEquals(remark, null);
        assertNotEquals(remark, "Some remark");
    }
}
