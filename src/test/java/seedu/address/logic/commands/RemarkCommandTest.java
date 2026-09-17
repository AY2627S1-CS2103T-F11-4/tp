package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Remark;
import seedu.address.testutil.PersonBuilder;

/** Contains integration tests and unit tests for {@link RemarkCommand}. */
public class RemarkCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addRemark_success() {
        Person original = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person replacement = new PersonBuilder(original).withRemark("Some remark").build();
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(original, replacement);
        expectedModel.updateFilteredPersonList(person -> true);

        String expectedMessage = String.format(RemarkCommand.MESSAGE_ADD_REMARK_SUCCESS,
                Messages.format(replacement));
        assertCommandSuccess(new RemarkCommand(INDEX_FIRST_PERSON, new Remark("Some remark")), model,
                expectedMessage, expectedModel);
    }

    @Test
    public void execute_clearRemark_success() {
        Person original = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person replacement = new PersonBuilder(original).withRemark("").build();
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(original, replacement);
        expectedModel.updateFilteredPersonList(person -> true);

        String expectedMessage = String.format(RemarkCommand.MESSAGE_DELETE_REMARK_SUCCESS,
                Messages.format(replacement));
        assertCommandSuccess(new RemarkCommand(INDEX_FIRST_PERSON, new Remark("")), model,
                expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index invalidIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        assertCommandFailure(new RemarkCommand(invalidIndex, new Remark("Some remark")), model,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        RemarkCommand standardCommand = new RemarkCommand(INDEX_FIRST_PERSON, new Remark("Like skiing."));

        assertTrue(standardCommand.equals(standardCommand));
        assertTrue(standardCommand.equals(
                new RemarkCommand(INDEX_FIRST_PERSON, new Remark("Like skiing."))));
        assertFalse(standardCommand.equals(null));
        assertFalse(standardCommand.equals(new ClearCommand()));
        assertFalse(standardCommand.equals(
                new RemarkCommand(INDEX_SECOND_PERSON, new Remark("Like skiing."))));
        assertFalse(standardCommand.equals(
                new RemarkCommand(INDEX_FIRST_PERSON, new Remark("Favorite pastime: Eating"))));
    }

    @Test
    public void toStringMethod() {
        RemarkCommand command = new RemarkCommand(INDEX_FIRST_PERSON, new Remark("Like skiing."));
        String expected = RemarkCommand.class.getCanonicalName()
                + "{index=" + INDEX_FIRST_PERSON + ", remark=Like skiing.}";
        assertEquals(expected, command.toString());
    }
}
