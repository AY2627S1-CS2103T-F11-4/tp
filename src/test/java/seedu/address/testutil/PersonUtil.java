package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_LEVEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARENT_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARENT_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.person.Student;

/** Utility methods for building student command strings in tests. */
public class PersonUtil {

    public static String getAddCommand(Student student) {
        return AddCommand.COMMAND_WORD + " " + getStudentDetails(student);
    }

    public static String getStudentDetails(Student student) {
        StringBuilder builder = new StringBuilder();
        builder.append(PREFIX_NAME).append(student.getName().fullName).append(" ");
        builder.append(PREFIX_LEVEL).append(student.getAcademicLevel().value).append(" ");
        student.getSubjects().forEach(subject -> builder.append(PREFIX_SUBJECT)
                .append(subject.subjectName).append(" "));
        builder.append(PREFIX_PARENT_PHONE).append(student.getParentPhone().value).append(" ");
        builder.append(PREFIX_PARENT_EMAIL).append(student.getParentEmail().value);
        return builder.toString();
    }

    public static String getEditPersonDescriptorDetails(EditPersonDescriptor descriptor) {
        StringBuilder builder = new StringBuilder();
        descriptor.getName().ifPresent(name -> builder.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getAcademicLevel().ifPresent(level -> builder.append(PREFIX_LEVEL).append(level.value).append(" "));
        descriptor.getSubjects().ifPresent(subjects -> subjects.forEach(subject -> builder.append(PREFIX_SUBJECT)
                .append(subject.subjectName).append(" ")));
        descriptor.getParentPhone().ifPresent(phone -> builder.append(PREFIX_PARENT_PHONE)
                .append(phone.value).append(" "));
        descriptor.getParentEmail().ifPresent(email -> builder.append(PREFIX_PARENT_EMAIL)
                .append(email.value).append(" "));
        return builder.toString();
    }
}
