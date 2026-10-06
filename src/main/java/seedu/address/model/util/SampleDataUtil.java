package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.AcademicLevel;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Student;
import seedu.address.model.subject.Subject;

/** Contains utility methods for populating {@code AddressBook} with sample data. */
public class SampleDataUtil {
    public static Student[] getSampleStudents() {
        return new Student[] {
            new Student(new Name("Alex Yeoh"), new AcademicLevel("Sec 2"), getSubjectSet("Math", "Science"),
                    new Phone("87438807"), new Email("alexyeoh@example.com")),
            new Student(new Name("Bernice Yu"), new AcademicLevel("Pri 5"), getSubjectSet("English", "Math"),
                    new Phone("99272758"), new Email("berniceyu@example.com")),
            new Student(new Name("Charlotte Oliveiro"), new AcademicLevel("JC 1"), getSubjectSet("Chemistry"),
                    new Phone("93210283"), new Email("charlotte@example.com")),
            new Student(new Name("David Li"), new AcademicLevel("Sec 4"), getSubjectSet("Physics"),
                    new Phone("91031282"), new Email("lidavid@example.com")),
            new Student(new Name("Irfan Ibrahim"), new AcademicLevel("Pri 6"), getSubjectSet("English"),
                    new Phone("92492021"), new Email("irfan@example.com")),
            new Student(new Name("Roy Balakrishnan"), new AcademicLevel("Sec 3"), getSubjectSet("Biology"),
                    new Phone("92624417"), new Email("royb@example.com"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAddressBook = new AddressBook();
        for (Student sampleStudent : getSampleStudents()) {
            sampleAddressBook.addStudent(sampleStudent);
        }
        return sampleAddressBook;
    }

    public static Set<Subject> getSubjectSet(String... strings) {
        return Arrays.stream(strings)
                .map(Subject::new)
                .collect(Collectors.toSet());
    }
}
