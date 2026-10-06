package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.commands.CommandTestUtil.LEVEL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PARENT_EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PARENT_PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.SUBJECT_DESC_MATH;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.testutil.EditPersonDescriptorBuilder;

public class EditCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format("Invalid command format!\n%1$s", EditCommand.MESSAGE_USAGE);
    private final EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);
        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "1 invalid preamble", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        String userInput = INDEX_FIRST_PERSON.getOneBased() + NAME_DESC_AMY + LEVEL_DESC_AMY
                + SUBJECT_DESC_MATH + PARENT_PHONE_DESC_AMY + PARENT_EMAIL_DESC_AMY;
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName("Amy Bee")
                .withAcademicLevel("Sec 2").withSubjects("Math").withPhone("91111111")
                .withEmail("amy@example.com").build();

        assertParseSuccess(parser, userInput, new EditCommand(INDEX_FIRST_PERSON, descriptor));
    }

    @Test
    public void parse_eachFieldSpecified_success() {
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName("Amy Bee").build();
        assertParseSuccess(parser, "1" + NAME_DESC_AMY, new EditCommand(INDEX_FIRST_PERSON, descriptor));

        descriptor = new EditPersonDescriptorBuilder().withAcademicLevel("Sec 2").build();
        assertParseSuccess(parser, "1" + LEVEL_DESC_AMY, new EditCommand(INDEX_FIRST_PERSON, descriptor));

        descriptor = new EditPersonDescriptorBuilder().withSubjects("Math").build();
        assertParseSuccess(parser, "1" + SUBJECT_DESC_MATH, new EditCommand(INDEX_FIRST_PERSON, descriptor));

        descriptor = new EditPersonDescriptorBuilder().withPhone("91111111").build();
        assertParseSuccess(parser, "1" + PARENT_PHONE_DESC_AMY,
                new EditCommand(INDEX_FIRST_PERSON, descriptor));

        descriptor = new EditPersonDescriptorBuilder().withEmail("amy@example.com").build();
        assertParseSuccess(parser, "1" + PARENT_EMAIL_DESC_AMY,
                new EditCommand(INDEX_FIRST_PERSON, descriptor));
    }

    @Test
    public void parse_duplicateSingleValueField_failure() {
        assertParseFailure(parser, "1" + NAME_DESC_AMY + NAME_DESC_AMY,
                "Multiple values specified for the following single-valued field(s): n/");
        assertParseFailure(parser, "1" + LEVEL_DESC_AMY + LEVEL_DESC_AMY,
                "Multiple values specified for the following single-valued field(s): lvl/");
        assertParseFailure(parser, "1" + PARENT_PHONE_DESC_AMY + PARENT_PHONE_DESC_AMY,
                "Multiple values specified for the following single-valued field(s): pn/");
        assertParseFailure(parser, "1" + PARENT_EMAIL_DESC_AMY + PARENT_EMAIL_DESC_AMY,
                "Multiple values specified for the following single-valued field(s): pe/");
    }

    @Test
    public void parse_resetSubjects_success() {
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withSubjects().build();
        assertParseSuccess(parser, "1 s/", new EditCommand(INDEX_FIRST_PERSON, descriptor));
        assertEquals(java.util.Set.of(), descriptor.getSubjects().orElseThrow());
    }

    @Test
    public void parse_invalidName_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> parser.parse("1 n/!!!"));
    }

    @Test
    public void parse_invalidAcademicLevel_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> parser.parse("1 lvl/Secondary 2"));
    }

    @Test
    public void parse_invalidPhone_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> parser.parse("1 pn/123"));
    }

    @Test
    public void parse_invalidEmail_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> parser.parse("1 pe/not-an-email"));
    }
}
