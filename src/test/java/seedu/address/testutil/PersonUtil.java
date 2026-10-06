package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.person.Student;
import seedu.address.model.tag.Tag;

/**
 * A utility class for Student.
 */
public class PersonUtil {

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddCommand(Student person) {
        return AddCommand.COMMAND_WORD + " " + getPersonDetails(person);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getPersonDetails(Student person) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + person.getName().fullName + " ");
        sb.append(PREFIX_PHONE + person.getParentPhone().value + " ");
        sb.append(PREFIX_EMAIL + person.getParentEmail().value + " ");
        sb.append(PREFIX_ADDRESS + person.getAcademicLevel().value + " ");
        person.getSubjects().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditPersonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getParentPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getParentEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getAcademicLevel().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));
        if (descriptor.getSubjects().isPresent()) {
            Set<Tag> tags = descriptor.getSubjects().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
