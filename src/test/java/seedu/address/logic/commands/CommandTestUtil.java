package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEVEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARENT_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARENT_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Student;
import seedu.address.testutil.EditPersonDescriptorBuilder;

/** Contains helper methods and valid values shared by command tests. */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_LEVEL_AMY = "Sec 2";
    public static final String VALID_LEVEL_BOB = "Pri 5";
    public static final String VALID_SUBJECT_MATH = "Math";
    public static final String VALID_SUBJECT_SCIENCE = "Science";
    public static final String VALID_SUBJECT_ENGLISH = "English";
    public static final String VALID_PHONE_AMY = "91111111";
    public static final String VALID_PHONE_BOB = "92222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String LEVEL_DESC_AMY = " " + PREFIX_LEVEL + VALID_LEVEL_AMY;
    public static final String LEVEL_DESC_BOB = " " + PREFIX_LEVEL + VALID_LEVEL_BOB;
    public static final String SUBJECT_DESC_MATH = " " + PREFIX_SUBJECT + VALID_SUBJECT_MATH;
    public static final String SUBJECT_DESC_SCIENCE = " " + PREFIX_SUBJECT + VALID_SUBJECT_SCIENCE;
    public static final String PARENT_PHONE_DESC_AMY = " " + PREFIX_PARENT_PHONE + VALID_PHONE_AMY;
    public static final String PARENT_PHONE_DESC_BOB = " " + PREFIX_PARENT_PHONE + VALID_PHONE_BOB;
    public static final String PARENT_EMAIL_DESC_AMY = " " + PREFIX_PARENT_EMAIL + VALID_EMAIL_AMY;
    public static final String PARENT_EMAIL_DESC_BOB = " " + PREFIX_PARENT_EMAIL + VALID_EMAIL_BOB;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&";
    public static final String INVALID_LEVEL_DESC = " " + PREFIX_LEVEL + "Secondary 7";
    public static final String INVALID_SUBJECT_DESC = " " + PREFIX_SUBJECT + "M";
    public static final String INVALID_PARENT_PHONE_DESC = " " + PREFIX_PARENT_PHONE + "911a";
    public static final String INVALID_PARENT_EMAIL_DESC = " " + PREFIX_PARENT_EMAIL + "bob!yahoo";

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withAcademicLevel(VALID_LEVEL_AMY).withSubjects(VALID_SUBJECT_MATH)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withAcademicLevel(VALID_LEVEL_BOB).withSubjects(VALID_SUBJECT_MATH, VALID_SUBJECT_SCIENCE)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).build();
    }

    /** Asserts that a command succeeds and updates the model as expected. */
    public static void assertCommandSuccess(Command command, Model actualModel,
            CommandResult expectedCommandResult, Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException exception) {
            throw new AssertionError("Execution of command should not fail.", exception);
        }
    }

    public static void assertCommandSuccess(Command command, Model actualModel,
            String expectedMessage, Model expectedModel) {
        assertCommandSuccess(command, actualModel, new CommandResult(expectedMessage), expectedModel);
    }

    /** Asserts that a command fails without changing the model. */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Student> expectedFilteredList = new ArrayList<>(actualModel.getFilteredStudentList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredStudentList());
    }

    /** Filters a model to the student at the given displayed index. */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredStudentList().size());

        Student student = model.getFilteredStudentList().get(targetIndex.getZeroBased());
        String[] splitName = student.getName().fullName.split("\\s+");
        model.updateFilteredStudentList(new NameContainsKeywordsPredicate(List.of(splitName[0])));

        assertEquals(1, model.getFilteredStudentList().size());
    }
}
