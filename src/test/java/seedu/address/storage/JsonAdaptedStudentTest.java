package seedu.address.storage;

import static seedu.address.testutil.Assert.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;

public class JsonAdaptedStudentTest {

    private static final List<String> VALID_SUBJECTS = List.of("Math");

    @Test
    public void toModelType_missingName_throwsIllegalValueException() {
        assertThrows(IllegalValueException.class, () -> new JsonAdaptedStudent(
                null, "Sec 2", VALID_SUBJECTS, "91234567", "student@example.com").toModelType());
    }

    @Test
    public void toModelType_invalidAcademicLevel_throwsIllegalValueException() {
        assertThrows(IllegalValueException.class, () -> new JsonAdaptedStudent(
                "Student", "Secondary 2", VALID_SUBJECTS, "91234567", "student@example.com").toModelType());
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
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        assertThrows(IllegalValueException.class, () -> new JsonAdaptedStudent(
                "Student", "Sec 2", VALID_SUBJECTS, "91234567", "not-an-email").toModelType());
    }
}
