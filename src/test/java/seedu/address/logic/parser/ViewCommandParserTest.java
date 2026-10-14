package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ViewCommand;

public class ViewCommandParserTest {

    private final ViewCommandParser parser = new ViewCommandParser();

    @Test
    public void parse_validIndexWithWhitespace_returnsViewCommand() {
        assertParseSuccess(parser, "  1  ",
                new ViewCommand(Index.fromOneBased(1)));
    }

    @Test
    public void parse_invalidIndices_throwsParseException() {
        String expectedMessage = String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE);

        for (String input : List.of("", "0", "-1", "+1",
                "abc", "1 2", "2147483648")) {
            assertParseFailure(parser, input, expectedMessage);
        }
    }
}