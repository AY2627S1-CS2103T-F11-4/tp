package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEVEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARENT_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARENT_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.Messages;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.subject.Subject;

/** Parses input arguments and creates an {@link EditCommand}. */
public class EditCommandParser implements Parser<EditCommand> {

    @Override
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_LEVEL, PREFIX_SUBJECT,
                PREFIX_PARENT_PHONE, PREFIX_PARENT_EMAIL);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_LEVEL, PREFIX_PARENT_PHONE, PREFIX_PARENT_EMAIL);

        EditPersonDescriptor descriptor = new EditPersonDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            descriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_LEVEL).isPresent()) {
            descriptor.setAcademicLevel(ParserUtil.parseAcademicLevel(argMultimap.getValue(PREFIX_LEVEL).get()));
        }
        if (argMultimap.getValue(PREFIX_PARENT_PHONE).isPresent()) {
            descriptor.setParentPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PARENT_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_PARENT_EMAIL).isPresent()) {
            descriptor.setParentEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_PARENT_EMAIL).get()));
        }
        List<String> subjectValues = argMultimap.getAllValues(PREFIX_SUBJECT);
        parseSubjectsForEdit(subjectValues).ifPresent(descriptor::setSubjects);
        String duplicateSubjectWarning = Messages.getWarningMessageForDuplicateSubjects(
                ParserUtil.findDuplicateSubjects(subjectValues));

        if (!descriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }
        return new EditCommand(index, descriptor, duplicateSubjectWarning);
    }

    private Optional<Set<Subject>> parseSubjectsForEdit(Collection<String> subjects) throws ParseException {
        if (subjects.isEmpty()) {
            return Optional.empty();
        }
        if (subjects.stream().anyMatch(String::isBlank)) {
            throw new ParseException(EditCommand.MESSAGE_SUBJECT_REQUIRED);
        }
        return Optional.of(ParserUtil.parseSubjects(subjects));
    }
}
