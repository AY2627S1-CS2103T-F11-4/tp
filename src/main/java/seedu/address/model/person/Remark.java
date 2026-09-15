package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Stores an optional, immutable note about a person. Empty remarks are allowed.
 */
public class Remark {

    public final String value;

    /**
     * Creates a remark from a non-null value.
     */
    public Remark(String value) {
        this.value = requireNonNull(value);
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
        if (!(other instanceof Remark otherRemark)) {
            return false;
        }
        return value.equals(otherRemark.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
