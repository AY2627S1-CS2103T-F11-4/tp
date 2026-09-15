package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.RemarkCommand;
import seedu.address.model.person.Remark;

/**
 * Tests remark argument parsing and command dispatch.
 */
public class RemarkCommandParserTest {

    private final RemarkCommandParser parser = new RemarkCommandParser();

    @Test
    public void parse_text_returnsRemarkCommand() {
        assertParseSuccess(parser, "1 r/Call next week",
                new RemarkCommand(INDEX_FIRST_PERSON, new Remark("Call next week")));
    }

    @Test
    public void parse_emptyOrMissingRemark_clearsRemark() {
        RemarkCommand clear = new RemarkCommand(INDEX_FIRST_PERSON, new Remark(""));
        assertParseSuccess(parser, "1 r/", clear);
        assertParseSuccess(parser, "1", clear);
    }

    @Test
    public void parse_repeatedPrefix_usesLastValue() {
        assertParseSuccess(parser, "1 r/First r/Last",
                new RemarkCommand(INDEX_FIRST_PERSON, new Remark("Last")));
    }

    @Test
    public void parse_invalidIndex_fails() {
        String message = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE);
        for (String input : new String[] {"", "r/Note", "0 r/Note", "-1 r/Note", "abc r/Note",
            "2147483648 r/Note"}) {
            assertParseFailure(parser, input, message);
        }
    }

    @Test
    public void parseCommand_remark_dispatchesToRemarkParser() throws Exception {
        assertEquals(new RemarkCommand(INDEX_FIRST_PERSON, new Remark("Note")),
                new AddressBookParser().parseCommand("remark 1 r/Note"));
    }
}
