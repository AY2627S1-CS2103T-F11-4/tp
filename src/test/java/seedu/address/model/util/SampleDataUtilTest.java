package seedu.address.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Student;
import seedu.address.model.subject.Subject;

public class SampleDataUtilTest {

    @Test
    public void getSampleStudents_returnsExpectedStudents() {
        Student[] students = SampleDataUtil.getSampleStudents();

        assertEquals(6, students.length);
        assertEquals("Alex Yeoh", students[0].getName().fullName);
        assertEquals("Sec 2", students[0].getAcademicLevel().value);
        assertEquals(Set.of(new Subject("Math"), new Subject("Science")), students[0].getSubjects());
    }

    @Test
    public void getSampleAddressBook_returnsAllSampleStudents() {
        ReadOnlyAddressBook addressBook = SampleDataUtil.getSampleAddressBook();

        assertEquals(6, addressBook.getStudentList().size());
        assertEquals("Alex Yeoh", addressBook.getStudentList().get(0).getName().fullName);
    }

    @Test
    public void getSubjectSet_returnsSetOfSubjects() {
        assertEquals(Set.of(new Subject("Math"), new Subject("Science")),
                SampleDataUtil.getSubjectSet("Math", "Science"));
    }
}
