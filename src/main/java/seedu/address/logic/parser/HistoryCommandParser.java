package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a HistoryCommand object.
 */
public class HistoryCommandParser implements Parser<HistoryCommand> {

    /**
     * Parses the given arguments and returns a HistoryCommand.
     *
     * @throws ParseException if the arguments are invalid.
     */
    public HistoryCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new HistoryCommand(index);
        } catch (ParseException exception) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, HistoryCommand.MESSAGE_USAGE),
                    exception);
        }
    }
}
