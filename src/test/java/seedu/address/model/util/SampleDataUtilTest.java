package seedu.address.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;

public class SampleDataUtilTest {

    @Test
    public void getSamplePersons_allPersonsHaveEmptyRemark() {
        Person[] samplePersons = SampleDataUtil.getSamplePersons();

        assertTrue(samplePersons.length > 0);
        for (Person person : samplePersons) {
            assertEquals("", person.getRemark().value);
        }
    }

    @Test
    public void getSampleAddressBook_containsAllSamplePersons() {
        ReadOnlyAddressBook sampleAddressBook = SampleDataUtil.getSampleAddressBook();

        assertEquals(SampleDataUtil.getSamplePersons().length, sampleAddressBook.getPersonList().size());
    }

    @Test
    public void getTagSet_returnsTagForEachString() {
        assertEquals(2, SampleDataUtil.getTagSet("friends", "colleagues").size());
        assertEquals(0, SampleDataUtil.getTagSet().size());
    }
}
