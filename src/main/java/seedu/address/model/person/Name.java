package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Locale;

/**
 * Represents a Person's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name {

    /** Error message shown when a student name fails validation. */
    public static final String MESSAGE_CONSTRAINTS =
            "Names should only contain alphanumeric characters, spaces, - and /, "
                    + "and must contain at least one letter or digit.";

    public static final int MAX_LENGTH = 60;

    /*
     * The lookahead requires at least one letter or digit anywhere in the name.
     * It rejects punctuation-only names while allowing hyphens and slashes.
     * The remaining pattern restricts allowed characters and rejects leading spaces.
     * Numeric-only names remain valid under the letter-or-digit requirement.
     * Examples: "Anne-Marie" and "123" are accepted; "---" and "/" are rejected.
     */
    public static final String VALIDATION_REGEX =
            "(?=.*\\p{Alnum})[\\p{Alnum}/\\-][\\p{Alnum}/\\- ]*";

    public final String fullName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Name(String name) {
        requireNonNull(name);
        String normalisedName = name.trim().replaceAll("\\s+", " ");
        checkArgument(isValidName(normalisedName), MESSAGE_CONSTRAINTS);
        fullName = normalisedName;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return test.length() <= MAX_LENGTH && test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Name otherName)) {
            return false;
        }

        return fullName.equalsIgnoreCase(otherName.fullName);
    }

    @Override
    public int hashCode() {
        return fullName.toLowerCase(Locale.ROOT).hashCode();
    }

}
