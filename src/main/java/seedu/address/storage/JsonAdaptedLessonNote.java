package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.lesson.LessonDate;
import seedu.address.model.lesson.LessonNote;
import seedu.address.model.lesson.NoteText;
import seedu.address.model.subject.Subject;

/**
 * Jackson-friendly version of {@link LessonNote}.
 */
class JsonAdaptedLessonNote {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Lesson note's %s field is missing!";

    private final String date;
    private final String subject;
    private final String noteText;

    /**
     * Constructs a {@code JsonAdaptedLessonNote} with the given lesson note details.
     */
    @JsonCreator
    public JsonAdaptedLessonNote(@JsonProperty("date") String date, @JsonProperty("subject") String subject,
            @JsonProperty("noteText") String noteText) {
        this.date = date;
        this.subject = subject;
        this.noteText = noteText;
    }

    /**
     * Converts a given {@code LessonNote} into this class for Jackson use.
     */
    public JsonAdaptedLessonNote(LessonNote source) {
        date = source.getDate().toString();
        subject = source.getSubject().subjectName;
        noteText = source.getNoteText().value;
    }

    /**
     * Converts this Jackson-friendly adapted lesson note object into the model's {@code LessonNote} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted lesson note.
     */
    public LessonNote toModelType() throws IllegalValueException {
        if (date == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, LessonDate.class.getSimpleName()));
        }
        if (!LessonDate.isValidLessonDate(date)) {
            throw new IllegalValueException(LessonDate.MESSAGE_CONSTRAINTS);
        }
        final LessonDate modelDate = new LessonDate(date);

        if (subject == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Subject.class.getSimpleName()));
        }
        if (!Subject.isValidSubjectName(subject)) {
            throw new IllegalValueException(Subject.MESSAGE_CONSTRAINTS);
        }
        final Subject modelSubject = new Subject(subject);

        if (noteText == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, NoteText.class.getSimpleName()));
        }
        if (!NoteText.isValidNoteText(noteText)) {
            throw new IllegalValueException(NoteText.MESSAGE_CONSTRAINTS);
        }
        final NoteText modelNoteText = new NoteText(noteText);

        return new LessonNote(modelDate, modelSubject, modelNoteText);
    }

}
