package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.StudentMatchesFilterPredicate;

/**
 * Filters students according to their academic level and subjects.
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Filters students by subject and/or academic level.\n"
            + "At least one subject or academic level must be supplied.\n"
            + "Students must match the level and take every specified subject.\n"
            + "Parameters: [s/SUBJECT]... [lvl/LEVEL]\n"
            + "Examples:\n"
            + COMMAND_WORD + " s/Math\n"
            + COMMAND_WORD + " lvl/Sec 2\n"
            + COMMAND_WORD + " s/Math s/Science lvl/Sec 2";

    public static final String MESSAGE_NO_STUDENTS_FOUND =
            "No students match the specified filters.";

    private final StudentMatchesFilterPredicate predicate;

    public FilterCommand(StudentMatchesFilterPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        model.updateFilteredStudentList(predicate);

        if (model.getFilteredStudentList().isEmpty()) {
            return new CommandResult(MESSAGE_NO_STUDENTS_FOUND);
        }

        return new CommandResult(String.format(
                Messages.MESSAGE_PERSONS_LISTED_OVERVIEW,
                model.getFilteredStudentList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof FilterCommand otherCommand)) {
            return false;
        }

        return predicate.equals(otherCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
