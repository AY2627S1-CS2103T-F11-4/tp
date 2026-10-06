package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.AcademicLevel;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Student;
import seedu.address.model.subject.Subject;

/** Jackson-friendly version of {@link Student}. */
class JsonAdaptedStudent {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Student's %s field is missing!";

    private final String name;
    private final String academicLevel;
    private final List<String> subjects = new ArrayList<>();
    private final String parentPhone;
    private final String parentEmail;

    @JsonCreator
    public JsonAdaptedStudent(@JsonProperty("name") String name,
            @JsonProperty("academicLevel") String academicLevel,
            @JsonProperty("subjects") List<String> subjects,
            @JsonProperty("parentPhone") String parentPhone,
            @JsonProperty("parentEmail") String parentEmail) {
        this.name = name;
        this.academicLevel = academicLevel;
        if (subjects != null) {
            this.subjects.addAll(subjects);
        }
        this.parentPhone = parentPhone;
        this.parentEmail = parentEmail;
    }

    public JsonAdaptedStudent(Student source) {
        name = source.getName().fullName;
        academicLevel = source.getAcademicLevel().value;
        source.getSubjects().forEach(subject -> subjects.add(subject.subjectName));
        parentPhone = source.getParentPhone().value;
        parentEmail = source.getParentEmail().value;
    }

    public Student toModelType() throws IllegalValueException {
        if (name == null || !Name.isValidName(name)) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (academicLevel == null || !AcademicLevel.isValidAcademicLevel(academicLevel)) {
            throw new IllegalValueException(AcademicLevel.MESSAGE_CONSTRAINTS);
        }
        if (subjects.isEmpty()) {
            throw new IllegalValueException("Student must have at least one subject.");
        }
        if (parentPhone == null || !Phone.isValidPhone(parentPhone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        if (parentEmail == null || !Email.isValidEmail(parentEmail)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }

        Set<Subject> modelSubjects = new HashSet<>();
        for (String subject : subjects) {
            if (!Subject.isValidSubjectName(subject)) {
                throw new IllegalValueException(Subject.MESSAGE_CONSTRAINTS);
            }
            modelSubjects.add(new Subject(subject));
        }

        return new Student(new Name(name), new AcademicLevel(academicLevel), modelSubjects,
                new Phone(parentPhone), new Email(parentEmail));
    }
}
