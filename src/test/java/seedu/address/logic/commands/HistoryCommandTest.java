package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.lesson.LessonDate;
import seedu.address.model.lesson.LessonNote;
import seedu.address.model.lesson.NoteText;
import seedu.address.model.person.Student;
import seedu.address.model.subject.Subject;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.PersonBuilder;

public class HistoryCommandTest {

    private static final LessonNote EARLIER_NOTE = new LessonNote(
            new LessonDate("2024-09-04"),
            new Subject("Science"),
            new NoteText("Covered cells"));

    private static final LessonNote LATER_NOTE = new LessonNote(
            new LessonDate("2024-09-18"),
            new Subject("Math"),
            new NoteText("Covered quadratic equations"));

    private static final Student STUDENT_WITH_NOTES = new PersonBuilder()
            .withName("Alex Yeoh")
            .withLessonNotes(EARLIER_NOTE, LATER_NOTE)
            .build();

    private final Model model = new ModelManager(
            new AddressBookBuilder()
                    .withPerson(STUDENT_WITH_NOTES)
                    .build(),
            new UserPrefs());

    @Test
    public void execute_validIndexWithLessonNotes_success() {
        HistoryCommand historyCommand = new HistoryCommand(Index.fromOneBased(1));

        String expectedMessage = String.format(
                HistoryCommand.MESSAGE_LESSON_HISTORY,
                STUDENT_WITH_NOTES.getName(),
                "2024-09-18 | Math | Covered quadratic equations\n"
                        + "2024-09-04 | Science | Covered cells");

        Model expectedModel = new ModelManager(
                new AddressBookBuilder()
                        .withPerson(STUDENT_WITH_NOTES)
                        .build(),
                new UserPrefs());

        assertCommandSuccess(historyCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexWithoutLessonNotes_returnsNoNotesMessage() {
        Student studentWithoutNotes = new PersonBuilder()
                .withName("Student Without Notes")
                .build();

        Model modelWithoutNotes = new ModelManager(
                new AddressBookBuilder()
                        .withPerson(studentWithoutNotes)
                        .build(),
                new UserPrefs());

        HistoryCommand historyCommand = new HistoryCommand(Index.fromOneBased(1));

        String expectedMessage = String.format(
                HistoryCommand.MESSAGE_NO_LESSON_NOTES,
                studentWithoutNotes.getName());

        Model expectedModel = new ModelManager(
                new AddressBookBuilder()
                        .withPerson(studentWithoutNotes)
                        .build(),
                new UserPrefs());

        assertCommandSuccess(historyCommand, modelWithoutNotes, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        HistoryCommand historyCommand = new HistoryCommand(Index.fromOneBased(2));

        assertCommandFailure(
                historyCommand,
                model,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        HistoryCommand historyFirstCommand = new HistoryCommand(Index.fromOneBased(1));
        HistoryCommand historyFirstCommandCopy = new HistoryCommand(Index.fromOneBased(1));
        HistoryCommand historySecondCommand = new HistoryCommand(Index.fromOneBased(2));

        assertTrue(historyFirstCommand.equals(historyFirstCommand));
        assertTrue(historyFirstCommand.equals(historyFirstCommandCopy));
        assertFalse(historyFirstCommand.equals(historySecondCommand));
        assertFalse(historyFirstCommand.equals(null));
        assertFalse(historyFirstCommand.equals(1));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        HistoryCommand historyCommand = new HistoryCommand(targetIndex);

        String expected = HistoryCommand.class.getCanonicalName()
                + "{targetIndex=" + targetIndex + "}";

        assertEquals(expected, historyCommand.toString());
    }
}
