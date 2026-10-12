package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LESSON_INDEX;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteNoteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteNoteCommand object
 */
public class DeleteNoteCommandParser implements Parser<DeleteNoteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteNoteCommand
     * and returns a DeleteNoteCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public DeleteNoteCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_LESSON_INDEX);

        if (argMultimap.getValue(PREFIX_LESSON_INDEX).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteNoteCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_LESSON_INDEX);

        Index index;
        Index lessonIndex;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
            lessonIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_LESSON_INDEX).get());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteNoteCommand.MESSAGE_USAGE), pe);
        }

        return new DeleteNoteCommand(index, lessonIndex);
    }

}
