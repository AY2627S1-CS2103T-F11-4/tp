package seedu.address.model.lesson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.subject.Subject;

public class LessonNoteTest {

    private static final LessonDate DATE = new LessonDate("2024-09-18");
    private static final Subject SUBJECT = new Subject("Math");
    private static final NoteText NOTE_TEXT = new NoteText("Covered quadratic equations");

    private static final LessonNote LESSON_NOTE = new LessonNote(DATE, SUBJECT, NOTE_TEXT);

    @Test
    public void constructor_nullField_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new LessonNote(null, SUBJECT, NOTE_TEXT));
        assertThrows(NullPointerException.class, () -> new LessonNote(DATE, null, NOTE_TEXT));
        assertThrows(NullPointerException.class, () -> new LessonNote(DATE, SUBJECT, null));
    }

    @Test
    public void getters_returnFieldsGivenToConstructor() {
        assertEquals(DATE, LESSON_NOTE.getDate());
        assertEquals(SUBJECT, LESSON_NOTE.getSubject());
        assertEquals(NOTE_TEXT, LESSON_NOTE.getNoteText());
    }

    @Test
    public void equals() {
        // same values -> returns true
        assertTrue(LESSON_NOTE.equals(new LessonNote(DATE, SUBJECT, NOTE_TEXT)));

        // same object -> returns true
        assertTrue(LESSON_NOTE.equals(LESSON_NOTE));

        // null -> returns false
        assertFalse(LESSON_NOTE.equals(null));

        // different type -> returns false
        assertFalse(LESSON_NOTE.equals(5));

        // different date -> returns false
        assertFalse(LESSON_NOTE.equals(new LessonNote(new LessonDate("2024-09-04"), SUBJECT, NOTE_TEXT)));

        // different subject -> returns false
        assertFalse(LESSON_NOTE.equals(new LessonNote(DATE, new Subject("Science"), NOTE_TEXT)));

        // different note text -> returns false
        assertFalse(LESSON_NOTE.equals(new LessonNote(DATE, SUBJECT, new NoteText("Introduced factorisation"))));
    }

    @Test
    public void hashCode_sameValues_sameHashCode() {
        assertEquals(LESSON_NOTE.hashCode(), new LessonNote(DATE, SUBJECT, NOTE_TEXT).hashCode());
    }

    @Test
    public void toStringMethod() {
        String expected = LessonNote.class.getCanonicalName() + "{date=" + DATE + ", subject=" + SUBJECT
                + ", noteText=" + NOTE_TEXT + "}";
        assertEquals(expected, LESSON_NOTE.toString());
    }
}
