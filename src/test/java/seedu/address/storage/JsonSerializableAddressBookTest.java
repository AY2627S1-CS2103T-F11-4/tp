package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;

public class JsonSerializableAddressBookTest {

    @Test
    public void toModelType_typicalStudents_success() throws Exception {
        JsonSerializableAddressBook data = new JsonSerializableAddressBook(getTypicalAddressBook());
        AddressBook addressBook = data.toModelType();
        assertEquals(getTypicalAddressBook(), addressBook);
    }

    @Test
    public void constructor_nullStudents_createsEmptyAddressBook() throws Exception {
        JsonSerializableAddressBook data = new JsonSerializableAddressBook((List<JsonAdaptedStudent>) null);

        assertEquals(new AddressBook(), data.toModelType());
    }

    @Test
    public void toModelType_invalidStudent_throwsIllegalValueException() {
        JsonAdaptedStudent invalidStudent = new JsonAdaptedStudent(
                "Alice", "Sec 2", List.of("M"), "91234567", "alice@example.com");
        JsonSerializableAddressBook data = new JsonSerializableAddressBook(List.of(invalidStudent));

        assertThrows(IllegalValueException.class, data::toModelType);
    }

    @Test
    public void toModelType_duplicateStudents_throwsIllegalValueException() {
        JsonAdaptedStudent first = new JsonAdaptedStudent(
                "Alice", "Sec 2", List.of("Math"), "91234567", "alice@example.com");
        JsonAdaptedStudent second = new JsonAdaptedStudent(
                "Alice", "Pri 1", List.of("Science"), "92345678", "alice2@example.com");
        JsonSerializableAddressBook data = new JsonSerializableAddressBook(List.of(first, second));

        assertThrows(IllegalValueException.class, JsonSerializableAddressBook.MESSAGE_DUPLICATE_STUDENT,
                data::toModelType);
    }
}
