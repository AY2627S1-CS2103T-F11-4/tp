package seedu.address.model.lesson;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.subject.Subject;

/**
 * Represents a note about a lesson in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class LessonNote {

    private final LessonDate date;
    private final Subject subject;
    private final NoteText noteText;

    /**
     * Every field must be present and not null.
     */
    public LessonNote(LessonDate date, Subject subject, NoteText noteText) {
        requireAllNonNull(date, subject, noteText);
        this.date = date;
        this.subject = subject;
        this.noteText = noteText;
    }

    public LessonDate getDate() {
        return date;
    }

    public Subject getSubject() {
        return subject;
    }

    public NoteText getNoteText() {
        return noteText;
    }

    /**
     * Returns true if both lesson notes have the same date, subject and note text.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof LessonNote otherLessonNote)) {
            return false;
        }

        return date.equals(otherLessonNote.date)
                && subject.equals(otherLessonNote.subject)
                && noteText.equals(otherLessonNote.noteText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, subject, noteText);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("date", date)
                .add("subject", subject)
                .add("noteText", noteText)
                .toString();
    }

}
