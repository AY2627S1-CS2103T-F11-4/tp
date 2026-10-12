package seedu.address.ui;

import java.util.Comparator;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.lesson.LessonNote;
import seedu.address.model.person.Student;

/**
 * A UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Student student;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label parentPhone;
    @FXML
    private Label level;
    @FXML
    private Label parentEmail;
    @FXML
    private FlowPane subjects;
    @FXML
    private VBox lessonNotes;

    /**
     * Creates a {@code PersonCard} with the given {@code Person} and index to display.
     */
    public PersonCard(Student student, int displayedIndex) {
        super(FXML);
        this.student = student;
        id.setText(displayedIndex + ". ");
        name.setText(student.getName().fullName);
        parentPhone.setText("Parent: " + student.getParentPhone().value);
        level.setText("Level: " + student.getAcademicLevel().value);
        parentEmail.setText("Email: " + student.getParentEmail().value);
        student.getSubjects().stream()
                .sorted(Comparator.comparing(subject -> subject.subjectName))
                .forEach(subject -> subjects.getChildren().add(new Label(subject.subjectName)));
        populateLessonNotes(student.getLessonNotes());
    }

    /**
     * Fills the lesson note section with the lines produced by {@link LessonNoteFormatter}.
     * The section is hidden and unmanaged when there are no lines, so that no blank space is
     * left on the card.
     */
    private void populateLessonNotes(List<LessonNote> notes) {
        List<String> lines = LessonNoteFormatter.format(notes);

        lessonNotes.setVisible(!lines.isEmpty());
        lessonNotes.setManaged(!lines.isEmpty());

        for (String line : lines) {
            Label label = new Label(line);
            label.getStyleClass().add("cell_small_label");
            lessonNotes.getChildren().add(label);
        }
    }
}
