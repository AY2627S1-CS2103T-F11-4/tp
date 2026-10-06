package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AcademicLevelTest {

    @Test
    public void constructor_invalidLevel_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new AcademicLevel("Secondary 2"));
    }

    @Test
    public void equals() {
        AcademicLevel level = new AcademicLevel("Sec 2");

        assertTrue(level.equals(level));
        assertTrue(level.equals(new AcademicLevel("sec 2")));
        assertFalse(level.equals(null));
        assertFalse(level.equals("Sec 2"));
        assertEquals(level.value.hashCode(), level.hashCode());
    }
}
