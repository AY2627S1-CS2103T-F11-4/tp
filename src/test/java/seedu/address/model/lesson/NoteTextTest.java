package seedu.address.model.lesson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NoteTextTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new NoteText(null));
    }

    @Test
    public void constructor_invalidNoteText_throwsIllegalArgumentException() {
        String invalidNoteText = "";
        assertThrows(IllegalArgumentException.class, () -> new NoteText(invalidNoteText));
    }

    @Test
    public void isValidNoteText() {
        // null note text
        assertThrows(NullPointerException.class, () -> NoteText.isValidNoteText(null));

        // blank note text
        assertFalse(NoteText.isValidNoteText("")); // empty string
        assertFalse(NoteText.isValidNoteText(" ")); // spaces only

        // too long
        assertFalse(NoteText.isValidNoteText("a".repeat(NoteText.MAX_LENGTH + 1))); // 501 characters

        // contains a line break
        assertFalse(NoteText.isValidNoteText("Covered algebra\nAssign Ex 4A")); // line feed
        assertFalse(NoteText.isValidNoteText("Covered algebra\rAssign Ex 4A")); // carriage return

        // valid note text
        assertTrue(NoteText.isValidNoteText("a")); // 1 character
        assertTrue(NoteText.isValidNoteText("a".repeat(NoteText.MAX_LENGTH))); // 500 characters
        assertTrue(NoteText.isValidNoteText("Covered quadratic equations")); // letters and spaces
        assertTrue(NoteText.isValidNoteText("Recapped Ch. 3; quiz next week (20%)!")); // symbols and digits
    }

    @Test
    public void toStringMethod() {
        assertEquals("Covered quadratic equations", new NoteText("Covered quadratic equations").toString());
    }

    @Test
    public void equals() {
        NoteText noteText = new NoteText("Valid note text");

        // same values -> returns true
        assertTrue(noteText.equals(new NoteText("Valid note text")));

        // same object -> returns true
        assertTrue(noteText.equals(noteText));

        // null -> returns false
        assertFalse(noteText.equals(null));

        // different types -> returns false
        assertFalse(noteText.equals(5.0f));

        // different values -> returns false
        assertFalse(noteText.equals(new NoteText("Other valid note text")));
    }

    @Test
    public void hashCode_sameNoteText_sameHashCode() {
        assertEquals(new NoteText("Valid note text").hashCode(), new NoteText("Valid note text").hashCode());
    }
}
