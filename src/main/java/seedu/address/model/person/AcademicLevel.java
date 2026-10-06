package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Set;

/** Represents a student's academic level. */
public class AcademicLevel {

    public static final String MESSAGE_CONSTRAINTS =
            "Level must be one of: Pri 1-6, Sec 1-5, JC 1-2 (e.g. Sec 2).";

    private static final Set<String> VALID_LEVELS = Set.of(
            "Pri 1", "Pri 2", "Pri 3", "Pri 4", "Pri 5", "Pri 6",
            "Sec 1", "Sec 2", "Sec 3", "Sec 4", "Sec 5", "JC 1", "JC 2");

    public final String value;

    /**
     * Creates an academic level.
     *
     * @param level a valid academic level.
     */
    public AcademicLevel(String level) {
        requireNonNull(level);
        String trimmedLevel = level.trim();
        checkArgument(isValidAcademicLevel(trimmedLevel), MESSAGE_CONSTRAINTS);
        value = trimmedLevel;
    }

    public static boolean isValidAcademicLevel(String level) {
        return level != null && VALID_LEVELS.contains(level.trim());
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof AcademicLevel otherLevel)) {
            return false;
        }
        return value.equals(otherLevel.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
