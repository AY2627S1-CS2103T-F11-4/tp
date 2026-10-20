package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.logic.LogicManager;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.lesson.LessonDate;
import seedu.address.model.lesson.LessonNote;
import seedu.address.model.lesson.NoteText;
import seedu.address.model.person.Student;
import seedu.address.model.subject.Subject;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.StorageManager;
import seedu.address.testutil.PersonBuilder;

/** Tests editing through parsing, model updates, automatic saving, and reloading. */
public class EditCommandIntegrationTest {

    @TempDir
    public Path testFolder;

    @Test
    public void execute_editAndInvalidRequests_preservesSavedState() throws Exception {
        LessonNote note = new LessonNote(new LessonDate("2024-09-18"),
                new Subject("Math"), new NoteText("Covered algebra"));
        Student alex = new PersonBuilder().withName("Alex Yeoh").withLessonNotes(note).build();
        Student betty = new PersonBuilder().withName("Betty Lim").build();
        AddressBook initialData = new AddressBook();
        initialData.addStudent(alex);
        initialData.addStudent(betty);
        Model model = new ModelManager(initialData, new UserPrefs());
        Path dataFile = testFolder.resolve("students.json");
        StorageManager storage = new StorageManager(new JsonAddressBookStorage(dataFile),
                new JsonUserPrefsStorage(testFolder.resolve("preferences.json")));
        storage.saveAddressBook(initialData);
        LogicManager logic = new LogicManager(model, storage);

        logic.execute("edit 1 lvl/Sec 3 s/Math s/Science pn/98765432");
        Student editedAlex = new PersonBuilder(alex).withAcademicLevel("Sec 3")
                .withSubjects("Math", "Science").withPhone("98765432").build();
        List<Student> expectedStudents = List.of(editedAlex, betty);
        assertEquals(expectedStudents, model.getAddressBook().getStudentList());
        assertEquals(expectedStudents,
                new JsonAddressBookStorage(dataFile).readAddressBook().orElseThrow().getStudentList());

        String savedContents = Files.readString(dataFile);
        assertThrows(ParseException.class, () -> logic.execute("edit 1 n/Changed Name pn/abc"));
        assertThrows(ParseException.class, () -> logic.execute("edit 1 n/Changed Name s/"));
        assertEquals(expectedStudents, model.getAddressBook().getStudentList());
        assertEquals(savedContents, Files.readString(dataFile));
        Model reloadedModel = new ModelManager(
                new JsonAddressBookStorage(dataFile).readAddressBook().orElseThrow(), new UserPrefs());
        assertEquals(expectedStudents, reloadedModel.getAddressBook().getStudentList());
    }
}
