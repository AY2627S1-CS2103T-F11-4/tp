package seedu.address.model.subject;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class SubjectTest {

    @Test
    public void constructor_invalidSubject_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Subject("1"));
    }

    @Test
    public void equals() {
        Subject subject = new Subject("Math");

        assertTrue(subject.equals(subject));
        assertTrue(subject.equals(new Subject("math")));
        assertFalse(subject.equals(null));
        assertFalse(subject.equals("Math"));
    }
}
