package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.model.person.Remark;

public class RemarkCommandParserTest {

    private static final String NON_EMPTY_REMARK = "Some remark.";
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE);

    private RemarkCommandParser parser = new RemarkCommandParser();

    @Test
    public void parse_indexSpecified_success() {
        Index targetIndex = INDEX_FIRST_PERSON;

        // with a remark
        String userInput = targetIndex.getOneBased() + " " + PREFIX_REMARK + NON_EMPTY_REMARK;
        assertParseSuccess(parser, userInput, new RemarkCommand(targetIndex, new Remark(NON_EMPTY_REMARK)));

        // no remark -> treated as removing the remark
        userInput = targetIndex.getOneBased() + " " + PREFIX_REMARK;
        assertParseSuccess(parser, userInput, new RemarkCommand(targetIndex, new Remark("")));
    }

    @Test
    public void parse_prefixMissing_success() {
        // the remark prefix is optional; its absence is the same as an empty remark
        String userInput = INDEX_FIRST_PERSON.getOneBased() + "";
        assertParseSuccess(parser, userInput, new RemarkCommand(INDEX_FIRST_PERSON, new Remark("")));
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        // no parameters
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);

        // no index
        assertParseFailure(parser, " " + PREFIX_REMARK + NON_EMPTY_REMARK, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidIndex_failure() {
        assertParseFailure(parser, "a " + PREFIX_REMARK + NON_EMPTY_REMARK, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "0 " + PREFIX_REMARK + NON_EMPTY_REMARK, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "-1 " + PREFIX_REMARK + NON_EMPTY_REMARK, MESSAGE_INVALID_FORMAT);
    }
}
