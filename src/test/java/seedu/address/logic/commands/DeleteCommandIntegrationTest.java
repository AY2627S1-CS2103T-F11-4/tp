package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.logic.LogicManager;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
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

/**
 * Tests student deletion through command parsing, model updates, saving and reloading.
 */
public class DeleteCommandIntegrationTest {

    @TempDir
    public Path testFolder;

    @Test
    public void execute_studentWithNotes_deletionPersistsAfterReload() throws Exception {
        LessonNote alexNote = new LessonNote(new LessonDate("2026-09-18"),
                new Subject("Math"), new NoteText("Covered algebra"));
        LessonNote bettyNote = new LessonNote(new LessonDate("2026-09-19"),
                new Subject("Science"), new NoteText("Covered cells"));
        Student alex = new PersonBuilder().withName("Alex Yeoh").withLessonNotes(alexNote).build();
        Student betty = new PersonBuilder().withName("Betty Lim").withLessonNotes(bettyNote).build();

        AddressBook initialData = new AddressBook();
        initialData.addStudent(alex);
        initialData.addStudent(betty);
        Model model = new ModelManager(initialData, new UserPrefs());
        Path dataFile = testFolder.resolve("students.json");
        StorageManager storage = new StorageManager(new JsonAddressBookStorage(dataFile),
                new JsonUserPrefsStorage(testFolder.resolve("preferences.json")));
        storage.saveAddressBook(initialData);
        LogicManager logic = new LogicManager(model, storage);

        // Execute through the normal command path, which must save automatically.
        logic.execute("deletestudent 1");
        assertEquals(List.of(betty), model.getAddressBook().getStudentList());

        // Reload from disk without manually saving after deletion.
        ReadOnlyAddressBook savedData = new JsonAddressBookStorage(dataFile).readAddressBook().orElseThrow();
        Model reloadedModel = new ModelManager(savedData, new UserPrefs());
        assertEquals(List.of(betty), reloadedModel.getAddressBook().getStudentList());
    }
}
