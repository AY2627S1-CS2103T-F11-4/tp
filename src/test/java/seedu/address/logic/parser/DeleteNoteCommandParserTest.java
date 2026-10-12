package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LESSON_INDEX;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.DeleteNoteCommand;

public class DeleteNoteCommandParserTest {

    private static final String LESSON_INDEX_DESC = " " + PREFIX_LESSON_INDEX + INDEX_SECOND_PERSON.getOneBased();

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteNoteCommand.MESSAGE_USAGE);

    private DeleteNoteCommandParser parser = new DeleteNoteCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        String userInput = INDEX_FIRST_PERSON.getOneBased() + LESSON_INDEX_DESC;

        assertParseSuccess(parser, userInput, new DeleteNoteCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON));
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        // no student index
        assertParseFailure(parser, LESSON_INDEX_DESC, MESSAGE_INVALID_FORMAT);

        // no lesson index prefix
        assertParseFailure(parser, String.valueOf(INDEX_FIRST_PERSON.getOneBased()), MESSAGE_INVALID_FORMAT);

        // prefix present but no value
        assertParseFailure(parser, INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_LESSON_INDEX,
                MESSAGE_INVALID_FORMAT);

        // nothing at all
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidStudentIndex_failure() {
        assertParseFailure(parser, "a" + LESSON_INDEX_DESC, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "0" + LESSON_INDEX_DESC, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "-1" + LESSON_INDEX_DESC, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidLessonIndex_failure() {
        String index = String.valueOf(INDEX_FIRST_PERSON.getOneBased());
        assertParseFailure(parser, index + " " + PREFIX_LESSON_INDEX + "a", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, index + " " + PREFIX_LESSON_INDEX + "0", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, index + " " + PREFIX_LESSON_INDEX + "-1", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_duplicatePrefix_failure() {
        assertParseFailure(parser, INDEX_FIRST_PERSON.getOneBased() + LESSON_INDEX_DESC + LESSON_INDEX_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_LESSON_INDEX));
    }
}
