package seedu.address.testutil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.AcademicLevel;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Student;
import seedu.address.model.subject.Subject;

/** A utility class for building {@link Student} objects in tests. */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_LEVEL = "Sec 2";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_SUBJECT = "Math";

    private Name name;
    private AcademicLevel academicLevel;
    private Set<Subject> subjects;
    private Phone parentPhone;
    private Email parentEmail;

    /** Creates a builder with valid default student details. */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        academicLevel = new AcademicLevel(DEFAULT_LEVEL);
        subjects = new HashSet<>(Set.of(new Subject(DEFAULT_SUBJECT)));
        parentPhone = new Phone(DEFAULT_PHONE);
        parentEmail = new Email(DEFAULT_EMAIL);
    }

    /** Initializes this builder using an existing student. */
    public PersonBuilder(Student studentToCopy) {
        name = studentToCopy.getName();
        academicLevel = studentToCopy.getAcademicLevel();
        subjects = new HashSet<>(studentToCopy.getSubjects());
        parentPhone = studentToCopy.getParentPhone();
        parentEmail = studentToCopy.getParentEmail();
    }

    /** Sets the name field. */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /** Sets the academic level field. */
    public PersonBuilder withAcademicLevel(String academicLevel) {
        this.academicLevel = new AcademicLevel(academicLevel);
        return this;
    }

    /** Sets the subjects field. */
    public PersonBuilder withSubjects(String... subjects) {
        this.subjects = new HashSet<>(Arrays.stream(subjects).map(Subject::new).toList());
        return this;
    }

    /** Sets the parent phone field. */
    public PersonBuilder withPhone(String phone) {
        this.parentPhone = new Phone(phone);
        return this;
    }

    /** Sets the parent email field. */
    public PersonBuilder withEmail(String email) {
        this.parentEmail = new Email(email);
        return this;
    }

    public Student build() {
        return new Student(name, academicLevel, subjects, parentPhone, parentEmail);
    }
}
