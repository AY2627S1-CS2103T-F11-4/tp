package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Locale;
import java.util.Map;

/** Represents a student's academic level. */
public class AcademicLevel {

    public static final String MESSAGE_CONSTRAINTS =
            "Academic level must be Pri 1-6, Sec 1-5, or JC 1-2.";

    private static final Map<String, String> CANONICAL_LEVELS = Map.ofEntries(
            Map.entry("pri 1", "Pri 1"), Map.entry("pri 2", "Pri 2"),
            Map.entry("pri 3", "Pri 3"), Map.entry("pri 4", "Pri 4"),
            Map.entry("pri 5", "Pri 5"), Map.entry("pri 6", "Pri 6"),
            Map.entry("sec 1", "Sec 1"), Map.entry("sec 2", "Sec 2"),
            Map.entry("sec 3", "Sec 3"), Map.entry("sec 4", "Sec 4"),
            Map.entry("sec 5", "Sec 5"), Map.entry("jc 1", "JC 1"),
            Map.entry("jc 2", "JC 2"));

    public final String value;

    public AcademicLevel(String level) {
        requireNonNull(level);
        String normalisedLevel = normalise(level);
        checkArgument(isValidAcademicLevel(normalisedLevel), MESSAGE_CONSTRAINTS);
        value = CANONICAL_LEVELS.get(normalisedLevel.toLowerCase(Locale.ROOT));
    }

    public static boolean isValidAcademicLevel(String level) {
        return level != null && CANONICAL_LEVELS.containsKey(normalise(level).toLowerCase(Locale.ROOT));
    }

    private static String normalise(String level) {
        return level.trim().replaceAll("\\s+", " ");
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
