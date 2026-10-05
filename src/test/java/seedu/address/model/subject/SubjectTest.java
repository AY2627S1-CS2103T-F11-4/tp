package seedu.address.model.subject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class SubjectTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Subject(null));
    }

    @Test
    public void constructor_invalidSubjectName_throwsIllegalArgumentException() {
        String invalidSubjectName = "";
        assertThrows(IllegalArgumentException.class, () -> new Subject(invalidSubjectName));
    }

    @Test
    public void isValidSubjectName() {
        // null subject name
        assertThrows(NullPointerException.class, () -> Subject.isValidSubjectName(null));

        // invalid subject name
        assertFalse(Subject.isValidSubjectName("")); // empty string
        assertFalse(Subject.isValidSubjectName(" ")); // spaces only
        assertFalse(Subject.isValidSubjectName("M")); // 1 character
        assertFalse(Subject.isValidSubjectName("a".repeat(31))); // 31 characters
        assertFalse(Subject.isValidSubjectName("Math2")); // contains a digit
        assertFalse(Subject.isValidSubjectName("A-Math")); // contains a symbol
        assertFalse(Subject.isValidSubjectName(" Math")); // leading space
        assertFalse(Subject.isValidSubjectName("Math ")); // trailing space

        // valid subject name
        assertTrue(Subject.isValidSubjectName("PE")); // 2 characters
        assertTrue(Subject.isValidSubjectName("a".repeat(30))); // 30 characters
        assertTrue(Subject.isValidSubjectName("Math")); // letters only
        assertTrue(Subject.isValidSubjectName("Additional Mathematics")); // letters and spaces
    }

    @Test
    public void toStringMethod() {
        assertEquals("Math", new Subject("Math").toString());
    }

    @Test
    public void equals() {
        Subject subject = new Subject("Math");

        // same values -> returns true
        assertTrue(subject.equals(new Subject("Math")));

        // same object -> returns true
        assertTrue(subject.equals(subject));

        // same name in a different case -> returns true
        assertTrue(subject.equals(new Subject("math")));

        // null -> returns false
        assertFalse(subject.equals(null));

        // different types -> returns false
        assertFalse(subject.equals(5.0f));

        // different values -> returns false
        assertFalse(subject.equals(new Subject("Science")));
    }

    @Test
    public void hashCode_sameNameDifferentCase_sameHashCode() {
        assertEquals(new Subject("Math").hashCode(), new Subject("math").hashCode());
    }
}
