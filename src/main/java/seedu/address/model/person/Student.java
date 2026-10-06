package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.subject.Subject;

/** Represents a student managed by the tutor. */
public class Student {

    private final Name name;
    private final AcademicLevel academicLevel;
    private final Set<Subject> subjects = new HashSet<>();
    private final Phone parentPhone;
    private final Email parentEmail;

    public Student(Name name, AcademicLevel academicLevel, Set<Subject> subjects,
            Phone parentPhone, Email parentEmail) {
        requireAllNonNull(name, academicLevel, subjects, parentPhone, parentEmail);
        this.name = name;
        this.academicLevel = academicLevel;
        this.subjects.addAll(subjects);
        this.parentPhone = parentPhone;
        this.parentEmail = parentEmail;
    }

    public Name getName() {
        return name;
    }

    public AcademicLevel getAcademicLevel() {
        return academicLevel;
    }

    public Set<Subject> getSubjects() {
        return Collections.unmodifiableSet(subjects);
    }

    public Phone getParentPhone() {
        return parentPhone;
    }

    public Email getParentEmail() {
        return parentEmail;
    }

    /** Returns true if both students have the same name. */
    public boolean isSameStudent(Student otherStudent) {
        return otherStudent != null && name.equals(otherStudent.name);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Student otherStudent)) {
            return false;
        }
        return name.equals(otherStudent.name)
                && academicLevel.equals(otherStudent.academicLevel)
                && subjects.equals(otherStudent.subjects)
                && parentPhone.equals(otherStudent.parentPhone)
                && parentEmail.equals(otherStudent.parentEmail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, academicLevel, subjects, parentPhone, parentEmail);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("academicLevel", academicLevel)
                .add("subjects", subjects)
                .add("parentPhone", parentPhone)
                .add("parentEmail", parentEmail)
                .toString();
    }
}
