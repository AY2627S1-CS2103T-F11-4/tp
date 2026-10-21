package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEVEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.AcademicLevel;
import seedu.address.model.person.StudentMatchesFilterPredicate;
import seedu.address.model.subject.Subject;

/**
 * Parses input arguments and creates a {@code FilterCommand}.
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    @Override
    public FilterCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(
                        args, PREFIX_SUBJECT, PREFIX_LEVEL);

        List<String> subjectValues =
                argMultimap.getAllValues(PREFIX_SUBJECT);
        Optional<String> levelValue =
                argMultimap.getValue(PREFIX_LEVEL);

        boolean hasFilter = !subjectValues.isEmpty()
                || levelValue.isPresent();
        boolean hasUnexpectedPreamble =
                !argMultimap.getPreamble().isEmpty();

        if (!hasFilter || hasUnexpectedPreamble) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT,
                    FilterCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_LEVEL);

        Set<Subject> subjects =
                ParserUtil.parseSubjects(subjectValues);

        Optional<AcademicLevel> academicLevel;
        if (levelValue.isPresent()) {
            academicLevel = Optional.of(
                    ParserUtil.parseAcademicLevel(levelValue.get()));
        } else {
            academicLevel = Optional.empty();
        }

        StudentMatchesFilterPredicate predicate =
                new StudentMatchesFilterPredicate(
                        academicLevel, subjects);

        return new FilterCommand(predicate);
    }
}
