package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.lesson.LessonDate;
import seedu.address.model.lesson.LessonNote;
import seedu.address.model.lesson.NoteText;
import seedu.address.model.person.Student;
import seedu.address.model.subject.Subject;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteNoteCommand}.
 */
public class DeleteNoteCommandTest {

    // NEWER_NOTE has the later date, so Student sorts it to index 1 of the lesson note list
    private static final LessonNote NEWER_NOTE = new LessonNote(new LessonDate("2026-10-01"),
            new Subject("Math"), new NoteText("Revised simultaneous equations"));
    private static final LessonNote OLDER_NOTE = new LessonNote(new LessonDate("2026-09-20"),
            new Subject("Science"), new NoteText("Covered photosynthesis"));

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    /** Replaces the first student with a copy carrying the given notes, and returns that copy. */
    private Student seedFirstStudentWith(LessonNote... notes) {
        Student firstStudent = model.getFilteredStudentList().get(INDEX_FIRST_PERSON.getZeroBased());
        Student seeded = new PersonBuilder(firstStudent).withLessonNotes(notes).build();
        model.setStudent(firstStudent, seeded);
        return seeded;
    }

    @Test
    public void execute_deleteOnlyNote_success() {
        Student seeded = seedFirstStudentWith(NEWER_NOTE);
        Student editedStudent = new PersonBuilder(seeded).withLessonNotes().build();

        DeleteNoteCommand command = new DeleteNoteCommand(INDEX_FIRST_PERSON, INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteNoteCommand.MESSAGE_DELETE_NOTE_SUCCESS,
                editedStudent.getName(), NEWER_NOTE.getDate(), NEWER_NOTE.getSubject(), NEWER_NOTE.getNoteText());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setStudent(seeded, editedStudent);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertTrue(model.getFilteredStudentList().get(INDEX_FIRST_PERSON.getZeroBased())
                .getLessonNotes().isEmpty());
    }

    @Test
    public void execute_deleteSecondNote_keepsTheOther() {
        Student seeded = seedFirstStudentWith(NEWER_NOTE, OLDER_NOTE);
        Student editedStudent = new PersonBuilder(seeded).withLessonNotes(NEWER_NOTE).build();

        // notes are sorted newest-first, so lesson index 2 is the older note
        DeleteNoteCommand command = new DeleteNoteCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);

        String expectedMessage = String.format(DeleteNoteCommand.MESSAGE_DELETE_NOTE_SUCCESS,
                editedStudent.getName(), OLDER_NOTE.getDate(), OLDER_NOTE.getSubject(), OLDER_NOTE.getNoteText());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setStudent(seeded, editedStudent);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(NEWER_NOTE, model.getFilteredStudentList().get(INDEX_FIRST_PERSON.getZeroBased())
                .getLessonNotes().get(0));
    }

    @Test
    public void execute_studentWithNoNotes_throwsCommandException() {
        Student firstStudent = model.getFilteredStudentList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteNoteCommand command = new DeleteNoteCommand(INDEX_FIRST_PERSON, INDEX_FIRST_PERSON);

        assertCommandFailure(command, model,
                String.format(DeleteNoteCommand.MESSAGE_NO_LESSON_NOTES, firstStudent.getName()));
    }

    @Test
    public void execute_lessonIndexOutOfRange_throwsCommandException() {
        seedFirstStudentWith(NEWER_NOTE);
        DeleteNoteCommand command = new DeleteNoteCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);

        assertCommandFailure(command, model, DeleteNoteCommand.MESSAGE_INVALID_LESSON_NOTE_INDEX);
    }

    @Test
    public void execute_filteredList_success() {
        Student seeded = seedFirstStudentWith(NEWER_NOTE);
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Student editedStudent = new PersonBuilder(seeded).withLessonNotes().build();

        DeleteNoteCommand command = new DeleteNoteCommand(INDEX_FIRST_PERSON, INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteNoteCommand.MESSAGE_DELETE_NOTE_SUCCESS,
                editedStudent.getName(), NEWER_NOTE.getDate(), NEWER_NOTE.getSubject(), NEWER_NOTE.getNoteText());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setStudent(seeded, editedStudent);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidStudentIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredStudentList().size() + 1);
        DeleteNoteCommand command = new DeleteNoteCommand(outOfBoundIndex, INDEX_FIRST_PERSON);

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Deletes a lesson note from a student in a filtered list where the student index is larger than
     * the size of the filtered list, but smaller than the size of the address book.
     */
    @Test
    public void execute_invalidStudentIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getStudentList().size());

        DeleteNoteCommand command = new DeleteNoteCommand(outOfBoundIndex, INDEX_FIRST_PERSON);

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final DeleteNoteCommand standardCommand = new DeleteNoteCommand(INDEX_FIRST_PERSON, INDEX_FIRST_PERSON);

        // same values -> returns true
        assertTrue(standardCommand.equals(new DeleteNoteCommand(INDEX_FIRST_PERSON, INDEX_FIRST_PERSON)));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different student index -> returns false
        assertFalse(standardCommand.equals(new DeleteNoteCommand(INDEX_SECOND_PERSON, INDEX_FIRST_PERSON)));

        // different lesson index -> returns false
        assertFalse(standardCommand.equals(new DeleteNoteCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON)));
    }

    @Test
    public void toStringMethod() {
        DeleteNoteCommand command = new DeleteNoteCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);
        String expected = DeleteNoteCommand.class.getCanonicalName()
                + "{index=" + INDEX_FIRST_PERSON + ", lessonIndex=" + INDEX_SECOND_PERSON + "}";
        assertEquals(expected, command.toString());
    }
}
