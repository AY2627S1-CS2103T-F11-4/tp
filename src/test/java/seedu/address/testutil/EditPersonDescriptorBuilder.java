package seedu.address.testutil;

import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.person.AcademicLevel;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Student;
import seedu.address.model.subject.Subject;

/** A utility class for building student edit descriptors in tests. */
public class EditPersonDescriptorBuilder {

    private final EditPersonDescriptor descriptor;

    public EditPersonDescriptorBuilder() {
        descriptor = new EditPersonDescriptor();
    }

    public EditPersonDescriptorBuilder(EditPersonDescriptor descriptor) {
        this.descriptor = new EditPersonDescriptor(descriptor);
    }

    /** Initializes the builder with all fields from a student. */
    public EditPersonDescriptorBuilder(Student student) {
        descriptor = new EditPersonDescriptor();
        descriptor.setName(student.getName());
        descriptor.setAcademicLevel(student.getAcademicLevel());
        descriptor.setSubjects(student.getSubjects());
        descriptor.setParentPhone(student.getParentPhone());
        descriptor.setParentEmail(student.getParentEmail());
    }

    /** Sets the name field. */
    public EditPersonDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /** Sets the academic level field. */
    public EditPersonDescriptorBuilder withAcademicLevel(String academicLevel) {
        descriptor.setAcademicLevel(new AcademicLevel(academicLevel));
        return this;
    }

    /** Sets the subjects field. */
    public EditPersonDescriptorBuilder withSubjects(String... subjects) {
        descriptor.setSubjects(java.util.Arrays.stream(subjects)
                .map(Subject::new).collect(java.util.stream.Collectors.toSet()));
        return this;
    }

    /** Sets the parent phone field. */
    public EditPersonDescriptorBuilder withPhone(String phone) {
        descriptor.setParentPhone(new Phone(phone));
        return this;
    }

    /** Sets the parent email field. */
    public EditPersonDescriptorBuilder withEmail(String email) {
        descriptor.setParentEmail(new Email(email));
        return this;
    }

    public EditPersonDescriptor build() {
        return descriptor;
    }
}
