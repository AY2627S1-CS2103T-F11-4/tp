package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Shows the number of persons in the address book.
 */
public class CountCommand extends Command {

    public static final String COMMAND_WORD = "count";

    public static final String MESSAGE_SUCCESS = "There are %1$d persons in the address book.";

    @Override
    public CommandResult execute(Model model) {
        int count = model.getFilteredPersonList().size();
        return new CommandResult(String.format(MESSAGE_SUCCESS, count));
    }
}
