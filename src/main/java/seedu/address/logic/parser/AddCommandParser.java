package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEVEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARENT_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARENT_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;

import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.AcademicLevel;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Student;
import seedu.address.model.subject.Subject;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_LEVEL, PREFIX_SUBJECT,
                        PREFIX_PARENT_PHONE, PREFIX_PARENT_EMAIL);

        boolean hasRequiredFields = arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_LEVEL,
                PREFIX_PARENT_PHONE, PREFIX_PARENT_EMAIL)
                && !argMultimap.getAllValues(PREFIX_SUBJECT).isEmpty()
                && argMultimap.getPreamble().isEmpty();

        if (!hasRequiredFields) {
            throw new ParseException(AddCommand.MESSAGE_WRONG_FORMAT);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_LEVEL,
                PREFIX_PARENT_PHONE, PREFIX_PARENT_EMAIL);

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        AcademicLevel level = ParserUtil.parseAcademicLevel(argMultimap.getValue(PREFIX_LEVEL).get());
        Phone parentPhone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PARENT_PHONE).get());
        Email parentEmail = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_PARENT_EMAIL).get());
        Set<Subject> subjects = ParserUtil.parseSubjects(argMultimap.getAllValues(PREFIX_SUBJECT));

        return new AddCommand(new Student(name, level, subjects, parentPhone, parentEmail));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        for (Prefix prefix : prefixes) {
            if (argumentMultimap.getValue(prefix).isEmpty()) {
                return false;
            }
        }
        return true;
    }

}
