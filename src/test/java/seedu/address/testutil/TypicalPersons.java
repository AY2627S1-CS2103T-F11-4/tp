package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LEVEL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LEVEL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Student;

/** A utility class containing typical students used in tests. */
public class TypicalPersons {

    public static final Student ALICE = new PersonBuilder().withName("Alice Pauline")
            .withAcademicLevel("Sec 1").withEmail("alice@example.com")
            .withPhone("94351253").withSubjects("Math").build();
    public static final Student BENSON = new PersonBuilder().withName("Benson Meier")
            .withAcademicLevel("Pri 5").withEmail("johnd@example.com")
            .withPhone("98765432").withSubjects("English", "Math").build();
    public static final Student CARL = new PersonBuilder().withName("Carl Kurz")
            .withAcademicLevel("Sec 3").withPhone("95352563")
            .withEmail("heinz@example.com").withSubjects("Science").build();
    public static final Student DANIEL = new PersonBuilder().withName("Daniel Meier")
            .withAcademicLevel("Sec 4").withPhone("87652533")
            .withEmail("cornelia@example.com").withSubjects("English").build();
    public static final Student ELLE = new PersonBuilder().withName("Elle Meyer")
            .withAcademicLevel("Pri 4").withPhone("94822247")
            .withEmail("werner@example.com").withSubjects("Math").build();
    public static final Student FIONA = new PersonBuilder().withName("Fiona Kunz")
            .withAcademicLevel("Pri 6").withPhone("94824271")
            .withEmail("lydia@example.com").withSubjects("Science").build();
    public static final Student GEORGE = new PersonBuilder().withName("George Best")
            .withAcademicLevel("JC 1").withPhone("94824421")
            .withEmail("anna@example.com").withSubjects("Physics").build();

    public static final Student AMY = new PersonBuilder().withName(VALID_NAME_AMY)
            .withAcademicLevel(VALID_LEVEL_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withSubjects("Math").build();
    public static final Student BOB = new PersonBuilder().withName(VALID_NAME_BOB)
            .withAcademicLevel(VALID_LEVEL_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withSubjects("Math", "Science").build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier";

    private TypicalPersons() {}

    public static AddressBook getTypicalAddressBook() {
        AddressBook addressBook = new AddressBook();
        getTypicalPersons().forEach(addressBook::addStudent);
        return addressBook;
    }

    public static List<Student> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
