package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.lesson.LessonDate;
import seedu.address.model.lesson.LessonNote;
import seedu.address.model.lesson.NoteText;
import seedu.address.model.subject.Subject;
import seedu.address.testutil.PersonBuilder;

public class PersonTest {

    private static final LessonNote EARLIEST_NOTE = new LessonNote(new LessonDate("2024-09-04"),
            new Subject("Math"), new NoteText("Introduced factorisation"));
    private static final LessonNote MIDDLE_NOTE = new LessonNote(new LessonDate("2024-09-11"),
            new Subject("Science"), new NoteText("Recapped photosynthesis"));
    private static final LessonNote LATEST_NOTE = new LessonNote(new LessonDate("2024-09-18"),
            new Subject("Math"), new NoteText("Covered quadratic equations"));

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Person person = new PersonBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> person.getTags().remove(0));
    }

    @Test
    public void constructor_nullLessonNotes_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Person(ALICE.getName(), ALICE.getPhone(),
                ALICE.getEmail(), ALICE.getAddress(), ALICE.getTags(), null));
    }

    @Test
    public void constructor_withoutLessonNotes_hasNoLessonNotes() {
        Person person = new Person(ALICE.getName(), ALICE.getPhone(), ALICE.getEmail(), ALICE.getAddress(),
                ALICE.getTags());
        assertTrue(person.getLessonNotes().isEmpty());
    }

    @Test
    public void getLessonNotes_notesGivenOutOfOrder_sortedByMostRecentDateFirst() {
        Person person = new PersonBuilder().withLessonNotes(MIDDLE_NOTE, EARLIEST_NOTE, LATEST_NOTE).build();
        assertEquals(List.of(LATEST_NOTE, MIDDLE_NOTE, EARLIEST_NOTE), person.getLessonNotes());
    }

    @Test
    public void getLessonNotes_notesOnSameDate_keepGivenOrder() {
        LessonNote firstGiven = new LessonNote(new LessonDate("2024-09-18"), new Subject("Science"),
                new NoteText("Make-up lesson"));
        LessonNote secondGiven = LATEST_NOTE;

        Person person = new PersonBuilder().withLessonNotes(EARLIEST_NOTE, firstGiven, secondGiven).build();
        assertEquals(List.of(firstGiven, secondGiven, EARLIEST_NOTE), person.getLessonNotes());
    }

    @Test
    public void getLessonNotes_modifyList_throwsUnsupportedOperationException() {
        Person person = new PersonBuilder().withLessonNotes(LATEST_NOTE).build();
        assertThrows(UnsupportedOperationException.class, () -> person.getLessonNotes().add(EARLIEST_NOTE));
        assertThrows(UnsupportedOperationException.class, () -> person.getLessonNotes().remove(0));
    }

    @Test
    public void getLessonNotes_originalListModified_personUnchanged() {
        List<LessonNote> originalList = new ArrayList<>(List.of(LATEST_NOTE));
        Person person = new Person(ALICE.getName(), ALICE.getPhone(), ALICE.getEmail(), ALICE.getAddress(),
                ALICE.getTags(), originalList);

        originalList.add(EARLIEST_NOTE);

        assertEquals(List.of(LATEST_NOTE), person.getLessonNotes());
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSamePerson(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSamePerson(null));

        // same name, all other attributes different -> returns true
        Person editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // different name, all other attributes same -> returns false
        editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSamePerson(editedAlice));

        // name differs in case, all other attributes same -> returns false
        Person editedBob = new PersonBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertFalse(BOB.isSamePerson(editedBob));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new PersonBuilder(BOB).withName(nameWithTrailingSpaces).build();
        assertFalse(BOB.isSamePerson(editedBob));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Person aliceCopy = new PersonBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different person -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Person editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));

        // different lesson notes -> returns false
        editedAlice = new PersonBuilder(ALICE).withLessonNotes(LATEST_NOTE).build();
        assertFalse(ALICE.equals(editedAlice));

        // same lesson notes given in a different order -> returns true
        Person aliceWithNotes = new PersonBuilder(ALICE).withLessonNotes(EARLIEST_NOTE, LATEST_NOTE).build();
        Person aliceWithReorderedNotes = new PersonBuilder(ALICE).withLessonNotes(LATEST_NOTE, EARLIEST_NOTE).build();
        assertTrue(aliceWithNotes.equals(aliceWithReorderedNotes));
    }

    @Test
    public void hashCode_sameValues_sameHashCode() {
        Person aliceWithNotes = new PersonBuilder(ALICE).withLessonNotes(EARLIEST_NOTE, LATEST_NOTE).build();
        Person aliceWithNotesCopy = new PersonBuilder(aliceWithNotes).build();
        assertEquals(aliceWithNotes.hashCode(), aliceWithNotesCopy.hashCode());
    }

    @Test
    public void toStringMethod() {
        String expected = Person.class.getCanonicalName() + "{name=" + ALICE.getName() + ", phone=" + ALICE.getPhone()
                + ", email=" + ALICE.getEmail() + ", address=" + ALICE.getAddress() + ", tags=" + ALICE.getTags()
                + ", lessonNotes=" + ALICE.getLessonNotes() + "}";
        assertEquals(expected, ALICE.toString());
    }
}
