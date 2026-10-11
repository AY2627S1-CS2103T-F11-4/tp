package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.lesson.LessonNote;
import seedu.address.model.person.Student;

/**
 * Displays the lesson history of a student identified by the displayed index.
 */
public class HistoryCommand extends Command {

    public static final String COMMAND_WORD = "history";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays the lesson history of the student identified by the displayed index.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_NO_LESSON_NOTES =
            "No lesson notes found for %1$s.";

    public static final String MESSAGE_LESSON_HISTORY =
            "Lesson history for %1$s:\n%2$s";

    private final Index targetIndex;

    /**
     * Creates a HistoryCommand for the specified student index.
     *
     * @param targetIndex Index of the student whose lesson history is displayed.
     */
    public HistoryCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Student> lastShownList = model.getFilteredStudentList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Student student = lastShownList.get(targetIndex.getZeroBased());
        List<LessonNote> lessonNotes = student.getLessonNotes();

        if (lessonNotes.isEmpty()) {
            return new CommandResult(String.format(MESSAGE_NO_LESSON_NOTES, student.getName()));
        }

        String formattedLessonNotes = lessonNotes.stream()
                .map(lessonNote -> String.format("%s | %s | %s",
                        lessonNote.getDate(),
                        lessonNote.getSubject(),
                        lessonNote.getNoteText()))
                .collect(Collectors.joining("\n"));

        return new CommandResult(String.format(
                MESSAGE_LESSON_HISTORY,
                student.getName(),
                formattedLessonNotes));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof HistoryCommand otherHistoryCommand)) {
            return false;
        }

        return targetIndex.equals(otherHistoryCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
