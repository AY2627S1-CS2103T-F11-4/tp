package seedu.address.model.lesson;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the text of a lesson note in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidNoteText(String)}
 */
public class NoteText {

    public static final int MAX_LENGTH = 500;

    public static final String MESSAGE_CONSTRAINTS =
            "Note text cannot be blank and must not exceed " + MAX_LENGTH + " characters.";

    public final String value;

    /**
     * Constructs a {@code NoteText}.
     *
     * @param noteText A valid note text.
     */
    public NoteText(String noteText) {
        requireNonNull(noteText);
        checkArgument(isValidNoteText(noteText), MESSAGE_CONSTRAINTS);
        value = noteText;
    }

    /**
     * Returns true if a given string is not blank, is at most {@code MAX_LENGTH} characters long
     * and does not contain any line breaks.
     */
    public static boolean isValidNoteText(String test) {
        return !test.isBlank()
                && test.length() <= MAX_LENGTH
                && !test.contains("\n")
                && !test.contains("\r");
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NoteText otherNoteText)) {
            return false;
        }

        return value.equals(otherNoteText.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
