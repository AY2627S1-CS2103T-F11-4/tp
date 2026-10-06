package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.PersonBuilder;

public class UniquePersonListTest {

    private final UniquePersonList uniquePersonList = new UniquePersonList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.contains(null));
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniquePersonList.contains(ALICE));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniquePersonList.add(ALICE);
        assertTrue(uniquePersonList.contains(ALICE));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniquePersonList.add(ALICE);
        Student editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniquePersonList.contains(editedAlice));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.add(null));
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        uniquePersonList.add(ALICE);
        assertThrows(DuplicatePersonException.class, () -> uniquePersonList.add(ALICE));
    }

    @Test
    public void setStudent_nullTargetPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.setStudent(null, ALICE));
    }

    @Test
    public void setStudent_nullEditedPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.setStudent(ALICE, null));
    }

    @Test
    public void setStudent_targetPersonNotInList_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> uniquePersonList.setStudent(ALICE, ALICE));
    }

    @Test
    public void setStudent_editedPersonIsSamePerson_success() {
        uniquePersonList.add(ALICE);
        uniquePersonList.setStudent(ALICE, ALICE);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        expectedUniquePersonList.add(ALICE);
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setStudent_editedPersonHasSameIdentity_success() {
        uniquePersonList.add(ALICE);
        Student editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniquePersonList.setStudent(ALICE, editedAlice);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        expectedUniquePersonList.add(editedAlice);
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setStudent_editedPersonHasDifferentIdentity_success() {
        uniquePersonList.add(ALICE);
        uniquePersonList.setStudent(ALICE, BOB);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        expectedUniquePersonList.add(BOB);
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setStudent_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniquePersonList.add(ALICE);
        uniquePersonList.add(BOB);
        assertThrows(DuplicatePersonException.class, () -> uniquePersonList.setStudent(ALICE, BOB));
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.remove(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> uniquePersonList.remove(ALICE));
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniquePersonList.add(ALICE);
        uniquePersonList.remove(ALICE);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setStudents_nullUniquePersonList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.setStudents((UniquePersonList) null));
    }

    @Test
    public void setStudents_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        uniquePersonList.add(ALICE);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        expectedUniquePersonList.add(BOB);
        uniquePersonList.setStudents(expectedUniquePersonList);
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setStudents_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.setStudents((List<Student>) null));
    }

    @Test
    public void setStudents_list_replacesOwnListWithProvidedList() {
        uniquePersonList.add(ALICE);
        List<Student> personList = List.of(BOB);
        uniquePersonList.setStudents(personList);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        expectedUniquePersonList.add(BOB);
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setStudents_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Student> listWithDuplicatePersons = List.of(ALICE, ALICE);
        assertThrows(DuplicatePersonException.class, () -> uniquePersonList.setStudents(listWithDuplicatePersons));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniquePersonList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniquePersonList.asUnmodifiableObservableList().toString(), uniquePersonList.toString());
    }
}
