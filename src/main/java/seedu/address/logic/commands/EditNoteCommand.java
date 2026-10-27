package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LESSON_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.lesson.LessonDate;
import seedu.address.model.lesson.LessonNote;
import seedu.address.model.lesson.NoteText;
import seedu.address.model.person.Student;
import seedu.address.model.subject.Subject;

/**
 * Edits a lesson note belonging to a student in the address book.
 */
public class EditNoteCommand extends Command {

    public static final String COMMAND_WORD = "editnote";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits a lesson note belonging to the student at the displayed index.\n"
            + "Parameters: INDEX "
            + PREFIX_LESSON_INDEX + "LESSON_INDEX "
            + "[" + PREFIX_DATE + "DATE] "
            + "[" + PREFIX_SUBJECT + "SUBJECT] "
            + "[" + PREFIX_NOTE + "NOTE_TEXT]\n"
            + "At least one of d/, s/, or note/ must be provided.\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_LESSON_INDEX + "2 "
            + PREFIX_NOTE + "Recapped photosynthesis.";

    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    public static final String MESSAGE_NO_CHANGES =
            "No changes were made. Provide at least one different lesson note detail.";

    public static final String MESSAGE_INVALID_LESSON_NOTE_INDEX =
            "The lesson note index provided is invalid.";

    public static final String MESSAGE_EDIT_NOTE_SUCCESS =
            "Edited lesson note %1$s for %2$s.";

    private final Index studentIndex;
    private final Index lessonIndex;
    private final Optional<LessonDate> date;
    private final Optional<Subject> subject;
    private final Optional<NoteText> noteText;

    /**
     * Creates an edit command for the specified student and lesson-note indices.
     *
     * @param studentIndex index of the student in the displayed student list
     * @param lessonIndex index of the lesson note in that student's history
     * @param date replacement date, if supplied
     * @param subject replacement subject, if supplied
     * @param noteText replacement note text, if supplied
     */
    public EditNoteCommand(Index studentIndex, Index lessonIndex, Optional<LessonDate> date,
                           Optional<Subject> subject, Optional<NoteText> noteText) {
        requireAllNonNull(studentIndex, lessonIndex, date, subject, noteText);
        this.studentIndex = studentIndex;
        this.lessonIndex = lessonIndex;
        this.date = date;
        this.subject = subject;
        this.noteText = noteText;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Student> displayedStudents = model.getFilteredStudentList();

        if (studentIndex.getZeroBased() >= displayedStudents.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Student studentToEdit = displayedStudents.get(studentIndex.getZeroBased());
        List<LessonNote> lessonNotes = studentToEdit.getLessonNotes();

        if (lessonIndex.getZeroBased() >= lessonNotes.size()) {
            throw new CommandException(MESSAGE_INVALID_LESSON_NOTE_INDEX);
        }

        LessonNote oldNote = lessonNotes.get(lessonIndex.getZeroBased());
        LessonNote editedNote = new LessonNote(
                date.orElse(oldNote.getDate()),
                subject.orElse(oldNote.getSubject()),
                noteText.orElse(oldNote.getNoteText()));

        if (editedNote.equals(oldNote)) {
            throw new CommandException(MESSAGE_NO_CHANGES);
        }

        List<LessonNote> updatedLessonNotes = new ArrayList<>(lessonNotes);
        updatedLessonNotes.set(lessonIndex.getZeroBased(), editedNote);

        // Student sorts lesson notes by date when it is constructed.
        Student editedStudent = new Student(studentToEdit.getName(), studentToEdit.getAcademicLevel(),
                studentToEdit.getSubjects(), studentToEdit.getParentPhone(), studentToEdit.getParentEmail(),
                updatedLessonNotes);

        model.setStudent(studentToEdit, editedStudent);

        return new CommandResult(String.format(MESSAGE_EDIT_NOTE_SUCCESS,
                lessonIndex.getOneBased(), studentToEdit.getName()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof EditNoteCommand otherCommand)) {
            return false;
        }
        return studentIndex.equals(otherCommand.studentIndex)
                && lessonIndex.equals(otherCommand.lessonIndex)
                && date.equals(otherCommand.date)
                && subject.equals(otherCommand.subject)
                && noteText.equals(otherCommand.noteText);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("studentIndex", studentIndex)
                .add("lessonIndex", lessonIndex)
                .add("date", date)
                .add("subject", subject)
                .add("noteText", noteText)
                .toString();
    }
}
