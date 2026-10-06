package seedu.address.model.person.exceptions;

/** Signals that the requested student could not be found. */
public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException() {
        super("The requested student could not be found");
    }
}
