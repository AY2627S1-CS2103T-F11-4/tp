package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.exceptions.DuplicateStudentException;
import seedu.address.model.person.exceptions.StudentNotFoundException;
import seedu.address.testutil.PersonBuilder;

public class UniqueStudentListTest {

    @Test
    public void add_duplicateStudent_throwsDuplicateStudentException() {
        UniqueStudentList list = new UniqueStudentList();
        list.add(ALICE);

        assertThrows(DuplicateStudentException.class, () -> list.add(new PersonBuilder(ALICE).build()));
    }

    @Test
    public void set_missingStudent_throwsStudentNotFoundException() {
        UniqueStudentList list = new UniqueStudentList();

        assertThrows(StudentNotFoundException.class, () -> list.setStudent(ALICE, BENSON));
    }

    @Test
    public void set_duplicateStudent_throwsDuplicateStudentException() {
        UniqueStudentList list = new UniqueStudentList();
        list.setStudents(List.of(ALICE, BENSON));

        assertThrows(DuplicateStudentException.class, () -> list.setStudent(ALICE, BENSON));
    }

    @Test
    public void remove_missingStudent_throwsStudentNotFoundException() {
        UniqueStudentList list = new UniqueStudentList();

        assertThrows(StudentNotFoundException.class, () -> list.remove(ALICE));
    }

    @Test
    public void iterator_equals_hashCode() {
        UniqueStudentList list = new UniqueStudentList();
        list.setStudents(List.of(ALICE, BENSON));
        UniqueStudentList sameList = new UniqueStudentList();
        sameList.setStudents(List.of(ALICE, BENSON));

        assertEquals(ALICE, list.iterator().next());
        assertTrue(list.equals(list));
        assertTrue(list.equals(sameList));
        assertFalse(list.equals("not a student list"));
        assertEquals(list.hashCode(), sameList.hashCode());
    }
}
