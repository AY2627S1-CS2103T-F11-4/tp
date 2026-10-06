package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LEVEL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_SCIENCE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.testutil.EditPersonDescriptorBuilder;

public class EditPersonDescriptorTest {

    @Test
    public void equals() {
        EditPersonDescriptor descriptorWithSameValues = new EditPersonDescriptor(DESC_AMY);
        assertTrue(DESC_AMY.equals(descriptorWithSameValues));
        assertTrue(DESC_AMY.equals(DESC_AMY));
        assertFalse(DESC_AMY.equals(null));
        assertFalse(DESC_AMY.equals(5));
        assertFalse(DESC_AMY.equals(DESC_BOB));

        EditPersonDescriptor editedAmy = new EditPersonDescriptorBuilder(DESC_AMY)
                .withName(VALID_NAME_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withAcademicLevel(VALID_LEVEL_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withSubjects(VALID_SUBJECT_SCIENCE).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withPhone(VALID_PHONE_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));
    }

    @Test
    public void toStringMethod() {
        EditPersonDescriptor descriptor = new EditPersonDescriptor();
        String expected = EditPersonDescriptor.class.getCanonicalName() + "{name="
                + descriptor.getName().orElse(null) + ", academicLevel="
                + descriptor.getAcademicLevel().orElse(null) + ", subjects="
                + descriptor.getSubjects().orElse(null) + ", parentPhone="
                + descriptor.getParentPhone().orElse(null) + ", parentEmail="
                + descriptor.getParentEmail().orElse(null) + "}";
        assertEquals(expected, descriptor.toString());
    }
}
