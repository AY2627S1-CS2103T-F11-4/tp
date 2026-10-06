package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEVEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARENT_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARENT_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
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
        argMultimap.getValue(PREFIX_NAME).ifPresent(value -> setName(descriptor, value));
        argMultimap.getValue(PREFIX_LEVEL).ifPresent(value -> setAcademicLevel(descriptor, value));
        argMultimap.getValue(PREFIX_PARENT_PHONE).ifPresent(value -> setParentPhone(descriptor, value));
        argMultimap.getValue(PREFIX_PARENT_EMAIL).ifPresent(value -> setParentEmail(descriptor, value));
        parseSubjectsForEdit(argMultimap.getAllValues(PREFIX_SUBJECT)).ifPresent(descriptor::setSubjects);

        if (!descriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }
        return new EditCommand(index, descriptor);
    }

    private void setName(EditPersonDescriptor descriptor, String value) {
        try {
            descriptor.setName(ParserUtil.parseName(value));
        } catch (ParseException exception) {
            throw new IllegalArgumentException(exception);
        }
    }

    private void setAcademicLevel(EditPersonDescriptor descriptor, String value) {
        try {
            descriptor.setAcademicLevel(ParserUtil.parseAcademicLevel(value));
        } catch (ParseException exception) {
            throw new IllegalArgumentException(exception);
        }
    }

    private void setParentPhone(EditPersonDescriptor descriptor, String value) {
        try {
            descriptor.setParentPhone(ParserUtil.parsePhone(value));
        } catch (ParseException exception) {
            throw new IllegalArgumentException(exception);
        }
    }

    private void setParentEmail(EditPersonDescriptor descriptor, String value) {
        try {
            descriptor.setParentEmail(ParserUtil.parseEmail(value));
        } catch (ParseException exception) {
            throw new IllegalArgumentException(exception);
        }
    }

    private Optional<Set<Subject>> parseSubjectsForEdit(Collection<String> subjects) throws ParseException {
        if (subjects.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> subjectValues = subjects.size() == 1 && subjects.contains("")
                ? Collections.emptySet() : subjects;
        return Optional.of(ParserUtil.parseSubjects(subjectValues));
    }
}
