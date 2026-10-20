package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

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

public class ViewCommandTest {

    @Test
    public void execute_validIndex_showsFullProfileWithSortedSubjectsAndLessonCount() {
        LessonNote firstNote = new LessonNote(
                new LessonDate("2026-09-01"),
                new Subject("Math"),
                new NoteText("Practised algebra"));
        LessonNote secondNote = new LessonNote(
                new LessonDate("2026-10-01"),
                new Subject("Science"),
                new NoteText("Covered cells"));

        Student student = new PersonBuilder()
                .withName("Alex Yeoh")
                .withAcademicLevel("Sec 2")
                .withSubjects("Science", "English", "Math")
                .withPhone("91234567")
                .withEmail("alex@example.com")
                .withLessonNotes(firstNote, secondNote)
                .build();

        Model model = modelWith(student);
        Model expectedModel = modelWith(student);
        String expectedMessage = "Student: Alex Yeoh\n"
                + "Level: Sec 2\n"
                + "Subjects: English, Math, Science\n"
                + "Parent phone: 91234567\n"
                + "Parent email: alex@example.com\n"
                + "Lessons recorded: 2";

        assertCommandSuccess(new ViewCommand(Index.fromOneBased(1)),
                model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noLessons_showsSubjectsAndZeroCount() {
        Student student = new PersonBuilder()
                .withName("Beth Lim")
                .withAcademicLevel("Pri 5")
                .withSubjects("Math")
                .withPhone("92345678")
                .withEmail("beth@example.com")
                .build();

        Model model = modelWith(student);
        Model expectedModel = modelWith(student);
        String expectedMessage = "Student: Beth Lim\n"
                + "Level: Pri 5\n"
                + "Subjects: Math\n"
                + "Parent phone: 92345678\n"
                + "Parent email: beth@example.com\n"
                + "Lessons recorded: 0";

        assertCommandSuccess(new ViewCommand(Index.fromOneBased(1)),
                model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexFilteredList_usesDisplayedIndex() {
        Student alex = new PersonBuilder().withName("Alex Yeoh").build();
        Student beth = new PersonBuilder()
                .withName("Beth Lim")
                .withPhone("92345678")
                .withEmail("beth@example.com")
                .build();

        Model model = modelWith(alex, beth);
        model.updateFilteredStudentList(student -> student.equals(beth));

        Model expectedModel = modelWith(alex, beth);
        expectedModel.updateFilteredStudentList(student -> student.equals(beth));

        String expectedMessage = "Student: Beth Lim\n"
                + "Level: Sec 2\n"
                + "Subjects: Math\n"
                + "Parent phone: 92345678\n"
                + "Parent email: beth@example.com\n"
                + "Lessons recorded: 0";

        assertCommandSuccess(new ViewCommand(Index.fromOneBased(1)),
                model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_indexBeyondUnfilteredList_throwsCommandException() {
        Model model = modelWith(new PersonBuilder().build());

        assertCommandFailure(new ViewCommand(Index.fromOneBased(2)),
                model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_indexBeyondFilteredList_throwsCommandException() {
        Student alex = new PersonBuilder().withName("Alex Yeoh").build();
        Student beth = new PersonBuilder().withName("Beth Lim").build();
        Model model = modelWith(alex, beth);
        model.updateFilteredStudentList(student -> student.equals(beth));

        // Index 2 exists in the full list but not in the displayed list.
        assertCommandFailure(new ViewCommand(Index.fromOneBased(2)),
                model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_emptyList_throwsCommandException() {
        Model model = modelWith();

        assertCommandFailure(new ViewCommand(Index.fromOneBased(1)),
                model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    private static Model modelWith(Student... students) {
        AddressBook addressBook = new AddressBook();
        for (Student student : students) {
            addressBook.addStudent(student);
        }
        return new ModelManager(addressBook, new UserPrefs());
    }
}
