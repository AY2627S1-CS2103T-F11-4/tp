package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class StudentTest {

    @Test
    public void isSameStudent_null_returnsFalse() {
        Student student = new PersonBuilder().build();

        assertFalse(student.isSameStudent(null));
    }

    @Test
    public void equals_nonStudent_returnsFalse() {
        Student student = new PersonBuilder().build();

        assertFalse(student.equals("student"));
    }

    @Test
    public void hashCode_sameStudents_returnsSameHashCode() {
        Student student = new PersonBuilder().build();

        assertEquals(student.hashCode(), new PersonBuilder(student).build().hashCode());
    }
}
