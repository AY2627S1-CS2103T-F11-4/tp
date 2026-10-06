package seedu.address.model.person.exceptions;

/** Signals that an operation would create a student with a duplicate name. */
public class DuplicateStudentException extends RuntimeException {
    public DuplicateStudentException() {
        super("Operation would result in duplicate students");
    }
}
