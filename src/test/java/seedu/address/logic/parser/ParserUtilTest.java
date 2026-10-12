package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.lesson.LessonDate;
import seedu.address.model.lesson.NoteText;
import seedu.address.model.person.AcademicLevel;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.subject.Subject;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_LEVEL = "Sec 6";
    private static final String INVALID_PHONE = "9123456";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_SUBJECT = "M";
    private static final String INVALID_LESSON_DATE = "01-01-2026";
    private static final String NON_EXISTENT_LESSON_DATE = "2026-02-30";
    private static final String FUTURE_LESSON_DATE = "2099-01-01";
    private static final String BLANK_NOTE_TEXT = "   ";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_LEVEL = "Sec 2";
    private static final String VALID_PHONE = "91234567";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_SUBJECT_1 = "Math";
    private static final String VALID_SUBJECT_2 = "Computer Science";
    private static final String VALID_LESSON_DATE = "2026-10-01";
    private static final String VALID_NOTE_TEXT = "Revised simultaneous equations";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, () ->
                ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1L)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_invalidAndValidValues() throws Exception {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName(null));
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
        assertEquals(new Name(VALID_NAME), ParserUtil.parseName("  " + VALID_NAME + "  "));
    }

    @Test
    public void parseAcademicLevel_invalidAndValidValues() throws Exception {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAcademicLevel(null));
        assertThrows(ParseException.class, () -> ParserUtil.parseAcademicLevel(INVALID_LEVEL));
        assertEquals(new AcademicLevel(VALID_LEVEL), ParserUtil.parseAcademicLevel("  " + VALID_LEVEL + "  "));
    }

    @Test
    public void parsePhone_invalidAndValidValues() throws Exception {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone(null));
        assertThrows(ParseException.class, () -> ParserUtil.parsePhone(INVALID_PHONE));
        assertEquals(new Phone(VALID_PHONE), ParserUtil.parsePhone("  " + VALID_PHONE + "  "));
    }

    @Test
    public void parseEmail_invalidAndValidValues() throws Exception {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseEmail(null));
        assertThrows(ParseException.class, () -> ParserUtil.parseEmail(INVALID_EMAIL));
        assertEquals(new Email(VALID_EMAIL), ParserUtil.parseEmail("  " + VALID_EMAIL + "  "));
    }

    @Test
    public void parseSubject_invalidAndValidValues() throws Exception {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseSubject(null));
        assertThrows(ParseException.class, () -> ParserUtil.parseSubject(INVALID_SUBJECT));
        assertEquals(new Subject(VALID_SUBJECT_2), ParserUtil.parseSubject(VALID_SUBJECT_2));
    }

    @Test
    public void parseSubjects_nullCollection_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseSubjects(null));
    }

    @Test
    public void parseSubjects_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseSubjects(List.of()).isEmpty());
    }

    @Test
    public void parseSubjects_validCollection_returnsSubjectSet() throws Exception {
        Set<Subject> actualSubjects = ParserUtil.parseSubjects(List.of(VALID_SUBJECT_1, VALID_SUBJECT_2));
        Set<Subject> expectedSubjects = Set.of(new Subject(VALID_SUBJECT_1), new Subject(VALID_SUBJECT_2));
        assertEquals(expectedSubjects, actualSubjects);
    }

    @Test
    public void parseLessonDate_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseLessonDate(null));
    }

    @Test
    public void parseLessonDate_invalidValue_throwsParseException() {
        // wrong format
        assertThrows(ParseException.class, LessonDate.MESSAGE_CONSTRAINTS, () ->
                ParserUtil.parseLessonDate(INVALID_LESSON_DATE));

        // correct format but not a real calendar date
        assertThrows(ParseException.class, LessonDate.MESSAGE_CONSTRAINTS, () ->
                ParserUtil.parseLessonDate(NON_EXISTENT_LESSON_DATE));

        // a lesson cannot have happened in the future
        assertThrows(ParseException.class, LessonDate.MESSAGE_CONSTRAINTS, () ->
                ParserUtil.parseLessonDate(FUTURE_LESSON_DATE));
    }

    @Test
    public void parseLessonDate_validValue_returnsLessonDate() throws Exception {
        assertEquals(new LessonDate(VALID_LESSON_DATE), ParserUtil.parseLessonDate(VALID_LESSON_DATE));

        // surrounding whitespace is trimmed
        assertEquals(new LessonDate(VALID_LESSON_DATE), ParserUtil.parseLessonDate("  " + VALID_LESSON_DATE + "  "));
    }

    @Test
    public void parseNoteText_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseNoteText(null));
    }

    @Test
    public void parseNoteText_invalidValue_throwsParseException() {
        // blank
        assertThrows(ParseException.class, NoteText.MESSAGE_CONSTRAINTS, () ->
                ParserUtil.parseNoteText(BLANK_NOTE_TEXT));

        // longer than the maximum allowed length
        assertThrows(ParseException.class, NoteText.MESSAGE_CONSTRAINTS, () ->
                ParserUtil.parseNoteText("a".repeat(NoteText.MAX_LENGTH + 1)));
    }

    @Test
    public void parseNoteText_validValue_returnsNoteText() throws Exception {
        assertEquals(new NoteText(VALID_NOTE_TEXT), ParserUtil.parseNoteText(VALID_NOTE_TEXT));

        // surrounding whitespace is trimmed
        assertEquals(new NoteText(VALID_NOTE_TEXT), ParserUtil.parseNoteText("  " + VALID_NOTE_TEXT + "  "));
    }
}
