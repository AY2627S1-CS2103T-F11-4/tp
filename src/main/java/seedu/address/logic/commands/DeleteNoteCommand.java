package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LESSON_INDEX;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.lesson.LessonNote;
import seedu.address.model.person.Student;

/**
 * Deletes a lesson note from an existing student in the address book.
 */
public class DeleteNoteCommand extends Command {

    public static final String COMMAND_WORD = "deletenote";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes a lesson note from the student identified by the index number "
            + "used in the displayed student list.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_LESSON_INDEX + "LESSON_INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_LESSON_INDEX + "2";

    public static final String MESSAGE_DELETE_NOTE_SUCCESS = "Deleted lesson note from %1$s (%2$s, %3$s): %4$s";

    public static final String MESSAGE_INVALID_LESSON_NOTE_INDEX = "The lesson note index provided is invalid.";

    public static final String MESSAGE_NO_LESSON_NOTES = "%1$s has no lesson notes to delete.";

    private final Index index;
    private final Index lessonIndex;

    /**
     * @param index of the student in the filtered student list to delete the lesson note from
     * @param lessonIndex of the lesson note in that student's lesson note list
     */
    public DeleteNoteCommand(Index index, Index lessonIndex) {
        requireAllNonNull(index, lessonIndex);

        this.index = index;
        this.lessonIndex = lessonIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Student> lastShownList = model.getFilteredStudentList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Student studentToEdit = lastShownList.get(index.getZeroBased());
        List<LessonNote> lessonNotes = studentToEdit.getLessonNotes();

        if (lessonNotes.isEmpty()) {
            throw new CommandException(String.format(MESSAGE_NO_LESSON_NOTES, studentToEdit.getName()));
        }

        if (lessonIndex.getZeroBased() >= lessonNotes.size()) {
            throw new CommandException(MESSAGE_INVALID_LESSON_NOTE_INDEX);
        }

        // getLessonNotes() is unmodifiable, so the note is removed from a copy
        List<LessonNote> updatedLessonNotes = new ArrayList<>(lessonNotes);
        LessonNote deletedNote = updatedLessonNotes.remove(lessonIndex.getZeroBased());

        Student editedStudent = new Student(studentToEdit.getName(), studentToEdit.getAcademicLevel(),
                studentToEdit.getSubjects(), studentToEdit.getParentPhone(), studentToEdit.getParentEmail(),
                updatedLessonNotes);

        model.setStudent(studentToEdit, editedStudent);

        return new CommandResult(generateSuccessMessage(editedStudent, deletedNote));
    }

    /**
     * Generates a command execution success message naming the student and the lesson note deleted.
     */
    private String generateSuccessMessage(Student editedStudent, LessonNote deletedNote) {
        return String.format(MESSAGE_DELETE_NOTE_SUCCESS, editedStudent.getName(), deletedNote.getDate(),
                deletedNote.getSubject(), deletedNote.getNoteText());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteNoteCommand otherDeleteNoteCommand)) {
            return false;
        }

        return index.equals(otherDeleteNoteCommand.index)
                && lessonIndex.equals(otherDeleteNoteCommand.lessonIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("lessonIndex", lessonIndex)
                .toString();
    }
}
