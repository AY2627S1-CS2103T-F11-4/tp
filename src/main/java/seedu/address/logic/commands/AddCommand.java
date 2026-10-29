package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEVEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARENT_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARENT_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Student;

/**
 * Adds a student to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "addstudent";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a student to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_LEVEL + "LEVEL "
            + PREFIX_SUBJECT + "SUBJECT... "
            + PREFIX_PARENT_PHONE + "PARENT_PHONE "
            + PREFIX_PARENT_EMAIL + "PARENT_EMAIL\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_LEVEL + "Sec 2 "
            + PREFIX_SUBJECT + "Math "
            + PREFIX_PARENT_PHONE + "98765432 "
            + PREFIX_PARENT_EMAIL + "johnd@example.com";

    public static final String MESSAGE_WRONG_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE);

    public static final String MESSAGE_SUCCESS = "New student added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This student already exists in the address book.";

    private final Student toAdd;
    private final String duplicateSubjectWarning;

    /**
     * Creates an AddCommand to add the specified {@code Student}
     */
    public AddCommand(Student student) {
        this(student, "");
    }

    /**
     * Creates an AddCommand with an optional warning about duplicate subjects in the input.
     */
    public AddCommand(Student student, String duplicateSubjectWarning) {
        requireNonNull(student);
        requireNonNull(duplicateSubjectWarning);
        toAdd = student;
        this.duplicateSubjectWarning = duplicateSubjectWarning;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasStudent(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.addStudent(toAdd);
        String successMessage = String.format(MESSAGE_SUCCESS, Messages.format(toAdd));
        return new CommandResult(duplicateSubjectWarning.isEmpty()
                ? successMessage : successMessage + "\n" + duplicateSubjectWarning);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddCommand otherAddCommand)) {
            return false;
        }

        return toAdd.equals(otherAddCommand.toAdd)
                && duplicateSubjectWarning.equals(otherAddCommand.duplicateSubjectWarning);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
