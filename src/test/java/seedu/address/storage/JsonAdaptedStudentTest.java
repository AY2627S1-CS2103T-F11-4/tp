package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.lesson.LessonDate;
import seedu.address.model.lesson.LessonNote;
import seedu.address.model.lesson.NoteText;
import seedu.address.model.person.Student;
import seedu.address.model.subject.Subject;
import seedu.address.testutil.PersonBuilder;

public class JsonAdaptedStudentTest {

    private static final List<String> VALID_SUBJECTS = List.of("Math");

    private static final LessonNote EARLIER_NOTE = new LessonNote(new LessonDate("2024-09-04"),
            new Subject("Math"), new NoteText("Introduced factorisation"));
    private static final LessonNote LATER_NOTE = new LessonNote(new LessonDate("2024-09-18"),
            new Subject("Science"), new NoteText("Recapped photosynthesis"));

    @Test
    public void toModelType_studentWithLessonNotes_returnsStudentWithSameLessonNotes() throws Exception {
        Student bensonWithLessonNotes = new PersonBuilder(BENSON).withLessonNotes(LATER_NOTE, EARLIER_NOTE).build();
        JsonAdaptedStudent student = new JsonAdaptedStudent(bensonWithLessonNotes);
        assertEquals(bensonWithLessonNotes, student.toModelType());
    }

    @Test
    public void toModelType_lessonNotesGivenOutOfOrder_returnsLessonNotesMostRecentFirst() throws Exception {
        List<JsonAdaptedLessonNote> unorderedLessonNotes = List.of(
                new JsonAdaptedLessonNote(EARLIER_NOTE), new JsonAdaptedLessonNote(LATER_NOTE));
        JsonAdaptedStudent student = new JsonAdaptedStudent("Student", "Sec 2", VALID_SUBJECTS, "91234567",
                "student@example.com", unorderedLessonNotes);
        assertEquals(List.of(LATER_NOTE, EARLIER_NOTE), student.toModelType().getLessonNotes());
    }

    @Test
    public void toModelType_nullLessonNotes_returnsStudentWithNoLessonNotes() throws Exception {
        JsonAdaptedStudent student = new JsonAdaptedStudent("Student", "Sec 2", VALID_SUBJECTS, "91234567",
                "student@example.com", null);
        assertTrue(student.toModelType().getLessonNotes().isEmpty());
    }

    @Test
    public void toModelType_invalidLessonNotes_throwsIllegalValueException() {
        List<JsonAdaptedLessonNote> invalidLessonNotes = List.of(
                new JsonAdaptedLessonNote("18-09-2024", "Math", "Covered algebra"));
        JsonAdaptedStudent student = new JsonAdaptedStudent("Student", "Sec 2", VALID_SUBJECTS, "91234567",
                "student@example.com", invalidLessonNotes);
        assertThrows(IllegalValueException.class, LessonDate.MESSAGE_CONSTRAINTS, student::toModelType);
    }

    @Test
    public void constructor_nullSubjects_createsEmptySubjectList() {
        assertThrows(IllegalValueException.class, () -> new JsonAdaptedStudent(
                "Student", "Sec 2", null, "91234567", "student@example.com").toModelType());
    }

    @Test
    public void toModelType_missingName_throwsIllegalValueException() {
        assertThrows(IllegalValueException.class, () -> new JsonAdaptedStudent(
                null, "Sec 2", VALID_SUBJECTS, "91234567", "student@example.com").toModelType());

        assertThrows(IllegalValueException.class, () -> new JsonAdaptedStudent(
                "Invalid!", "Sec 2", VALID_SUBJECTS, "91234567", "student@example.com").toModelType());
    }

    @Test
    public void toModelType_invalidAcademicLevel_throwsIllegalValueException() {
        assertThrows(IllegalValueException.class, () -> new JsonAdaptedStudent(
                "Student", "Secondary 2", VALID_SUBJECTS, "91234567", "student@example.com").toModelType());

        assertThrows(IllegalValueException.class, () -> new JsonAdaptedStudent(
                "Student", null, VALID_SUBJECTS, "91234567", "student@example.com").toModelType());
    }

    @Test
    public void toModelType_emptySubjects_throwsIllegalValueException() {
        assertThrows(IllegalValueException.class, () -> new JsonAdaptedStudent(
                "Student", "Sec 2", List.of(), "91234567", "student@example.com").toModelType());
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        assertThrows(IllegalValueException.class, () -> new JsonAdaptedStudent(
                "Student", "Sec 2", VALID_SUBJECTS, "123", "student@example.com").toModelType());

        assertThrows(IllegalValueException.class, () -> new JsonAdaptedStudent(
                "Student", "Sec 2", VALID_SUBJECTS, null, "student@example.com").toModelType());
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        assertThrows(IllegalValueException.class, () -> new JsonAdaptedStudent(
                "Student", "Sec 2", VALID_SUBJECTS, "91234567", "not-an-email").toModelType());

        assertThrows(IllegalValueException.class, () -> new JsonAdaptedStudent(
                "Student", "Sec 2", VALID_SUBJECTS, "91234567", null).toModelType());
    }
}
