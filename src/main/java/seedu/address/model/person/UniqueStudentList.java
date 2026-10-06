package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.exceptions.DuplicateStudentException;
import seedu.address.model.person.exceptions.StudentNotFoundException;

/** A sorted list of students with unique names. */
public class UniqueStudentList implements Iterable<Student> {

    private final ObservableList<Student> internalList = FXCollections.observableArrayList();
    private final ObservableList<Student> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    public boolean contains(Student toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameStudent);
    }

    public void add(Student toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateStudentException();
        }
        internalList.add(toAdd);
        sort();
    }

    public void setStudent(Student target, Student editedStudent) {
        requireAllNonNull(target, editedStudent);
        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new StudentNotFoundException();
        }
        if (!target.isSameStudent(editedStudent) && contains(editedStudent)) {
            throw new DuplicateStudentException();
        }
        internalList.set(index, editedStudent);
        sort();
    }

    public void remove(Student toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new StudentNotFoundException();
        }
    }

    public void setStudents(List<Student> students) {
        requireAllNonNull(students);
        for (int i = 0; i < students.size(); i++) {
            for (int j = i + 1; j < students.size(); j++) {
                if (students.get(i).isSameStudent(students.get(j))) {
                    throw new DuplicateStudentException();
                }
            }
        }
        internalList.setAll(students);
        sort();
    }

    public ObservableList<Student> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    private void sort() {
        FXCollections.sort(internalList, Comparator.comparing(student -> student.getName().fullName,
                String.CASE_INSENSITIVE_ORDER));
    }

    @Override
    public Iterator<Student> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UniqueStudentList otherList)) {
            return false;
        }
        return internalList.equals(otherList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }
}
