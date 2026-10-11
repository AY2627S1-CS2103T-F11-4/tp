package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Student;

/**
 * Displays the profile of a student by index
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Views full profile of the student at the specified INDEX.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 2";

    private final Index targetIndex;

    /**
     * Creates a ViewCommand for the specified student index.
     */
    public ViewCommand(Index targetIndex) {
        this.targetIndex = requireNonNull(targetIndex);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Student> lastShownList = model.getFilteredStudentList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Student student = lastShownList.get(targetIndex.getZeroBased());
        return new CommandResult(formatProfile(student));
    }

    /**
     * Formats the student's profile for display in the result box.
     */
    private String formatProfile(Student student) {
        String subjects = student.getSubjects().stream()
                .map(subject -> subject.subjectName)
                .sorted(String.CASE_INSENSITIVE_ORDER)
                .collect(Collectors.joining(", "));

        return String.format(
                "Student: %s\n"
                        + "Level: %s\n"
                        + "Subjects: %s\n"
                        + "Parent phone: %s\n"
                        + "Parent email: %s\n"
                        + "Lessons recorded: %d",
                student.getName(),
                student.getAcademicLevel(),
                subjects,
                student.getParentPhone(),
                student.getParentEmail(),
                student.getLessonNotes().size());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ViewCommand otherViewCommand)) {
            return false;
        }

        return targetIndex.equals(otherViewCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
