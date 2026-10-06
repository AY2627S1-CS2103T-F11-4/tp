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
        assertThrows(IllegalArgumentException.class, () -> new AcademicLevel("sec 2"));
        assertThrows(IllegalArgumentException.class, () -> new AcademicLevel("Sec  2"));
        assertFalse(AcademicLevel.isValidAcademicLevel("Secondary 2"));
        assertFalse(AcademicLevel.isValidAcademicLevel("sec 2"));
        assertFalse(AcademicLevel.isValidAcademicLevel("Sec  2"));
        assertFalse(AcademicLevel.isValidAcademicLevel(null));
    }

    @Test
    public void isValidAcademicLevel_allSupportedLevels_returnsTrue() {
        for (String level : new String[] {"Pri 1", "Pri 2", "Pri 3", "Pri 4", "Pri 5", "Pri 6",
            "Sec 1", "Sec 2", "Sec 3", "Sec 4", "Sec 5", "JC 1", "JC 2"}) {
            assertTrue(AcademicLevel.isValidAcademicLevel(level));
        }
    }

    @Test
    public void equals() {
        AcademicLevel level = new AcademicLevel("Sec 2");

        assertTrue(level.equals(level));
        assertTrue(level.equals(new AcademicLevel("Sec 2")));
        assertFalse(level.equals(null));
        assertFalse(level.equals("Sec 2"));
        assertEquals(level.value.hashCode(), level.hashCode());
    }
}
