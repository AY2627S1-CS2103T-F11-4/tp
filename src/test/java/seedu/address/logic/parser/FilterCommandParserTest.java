package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.model.person.AcademicLevel;
import seedu.address.model.person.StudentMatchesFilterPredicate;
import seedu.address.model.subject.Subject;

public class FilterCommandParserTest {

    private final FilterCommandParser parser =
            new FilterCommandParser();

    @Test
    public void parse_emptyArguments_throwsParseException() {
        assertParseFailure(
                parser,
                "   ",
                String.format(
                        MESSAGE_INVALID_COMMAND_FORMAT,
                        FilterCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_unexpectedPreamble_throwsParseException() {
        assertParseFailure(
                parser,
                "unexpected s/Math",
                String.format(
                        MESSAGE_INVALID_COMMAND_FORMAT,
                        FilterCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_repeatedLevel_throwsParseException() {
        assertParseFailure(
                parser,
                " lvl/Sec 1 lvl/Sec 2",
                "Multiple values specified for the following "
                        + "single-valued field(s): lvl/");
    }

    @Test
    public void parse_subjectOnly_returnsFilterCommand() {
        StudentMatchesFilterPredicate predicate =
                new StudentMatchesFilterPredicate(
                        Optional.empty(),
                        Set.of(new Subject("Math")));

        assertParseSuccess(
                parser,
                " s/Math",
                new FilterCommand(predicate));
    }

    @Test
    public void parse_levelOnly_returnsFilterCommand() {
        StudentMatchesFilterPredicate predicate =
                new StudentMatchesFilterPredicate(
                        Optional.of(new AcademicLevel("Sec 2")),
                        Set.of());

        assertParseSuccess(
                parser,
                " lvl/Sec 2",
                new FilterCommand(predicate));
    }

    @Test
    public void parse_multipleSubjectsAndLevel_returnsFilterCommand() {
        StudentMatchesFilterPredicate predicate =
                new StudentMatchesFilterPredicate(
                        Optional.of(new AcademicLevel("Sec 2")),
                        Set.of(
                                new Subject("Math"),
                                new Subject("Science")));

        assertParseSuccess(
                parser,
                " s/Math s/Science lvl/Sec 2",
                new FilterCommand(predicate));
    }

    @Test
    public void parse_invalidSubject_throwsParseException() {
        assertParseFailure(
                parser,
                " s/M",
                Subject.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidLevel_throwsParseException() {
        assertParseFailure(
                parser,
                " lvl/Secondary 2",
                AcademicLevel.MESSAGE_CONSTRAINTS);
    }
}
