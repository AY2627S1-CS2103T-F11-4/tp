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

import java.util.List;

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
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Student studentToDelete = model.getFilteredStudentList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_STUDENT_SUCCESS,
                studentToDelete.getName(), studentToDelete.getLessonNotes().size());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteStudent(studentToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredStudentList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_SECOND_PERSON);

        Student studentToDelete = model.getFilteredStudentList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_STUDENT_SUCCESS,
                studentToDelete.getName(), studentToDelete.getLessonNotes().size());

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteStudent(studentToDelete);
        showNoStudent(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getStudentList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_studentWithNotes_preservesOtherStudentsAndNotes() {
        Student deletedStudent = studentWithNotes("Alex Yeoh", "Algebra", "Geometry");
        Student remainingStudent = studentWithNotes("Betty Lim", "Fractions");
        AddressBook initialData = new AddressBook();
        initialData.addStudent(deletedStudent);
        initialData.addStudent(remainingStudent);
        model = new ModelManager(initialData, new UserPrefs());

        AddressBook expectedData = new AddressBook();
        expectedData.addStudent(remainingStudent);
        Model expectedModel = new ModelManager(expectedData, new UserPrefs());

        assertCommandSuccess(new DeleteCommand(INDEX_FIRST_PERSON), model,
                "Deleted student: Alex Yeoh, and their 2 lesson note(s).", expectedModel);
    }

    @Test
    public void execute_lastStudentWithoutNotes_leavesEmptyList() {
        AddressBook initialData = new AddressBook();
        initialData.addStudent(new PersonBuilder().withName("Alex Yeoh").build());
        model = new ModelManager(initialData, new UserPrefs());
        Model expectedModel = new ModelManager(new AddressBook(), new UserPrefs());

        assertCommandSuccess(new DeleteCommand(INDEX_FIRST_PERSON), model,
                "Deleted student: Alex Yeoh, and their 0 lesson note(s).", expectedModel);
    }

    @Test
    public void execute_invalidIndexWithNotes_preservesAllData() {
        Student student = studentWithNotes("Alex Yeoh", "Algebra", "Geometry");
        AddressBook initialData = new AddressBook();
        initialData.addStudent(student);
        model = new ModelManager(initialData, new UserPrefs());

        assertCommandFailure(new DeleteCommand(INDEX_SECOND_PERSON), model,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        assertEquals(List.of(student), model.getAddressBook().getStudentList());
    }

    private Student studentWithNotes(String name, String... texts) {
        LessonNote[] notes = java.util.Arrays.stream(texts)
                .map(text -> new LessonNote(new LessonDate("2026-09-18"),
                        new Subject("Math"), new NoteText(text)))
                .toArray(LessonNote[]::new);
        return new PersonBuilder().withName(name).withLessonNotes(notes).build();
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_PERSON);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_PERSON);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different student -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        DeleteCommand deleteCommand = new DeleteCommand(targetIndex);
        String expected = DeleteCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, deleteCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no students.
     */
    private void showNoStudent(Model model) {
        model.updateFilteredStudentList(p -> false);

        assertTrue(model.getFilteredStudentList().isEmpty());
    }
}
