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

    private static final int MAX_DISPLAYED_LESSON_NOTES = 3;
    private static final int MAX_DISPLAYED_NOTE_TEXT_LENGTH = 60;
    private static final String MESSAGE_MORE_LESSON_NOTES = "+ %d more (history %d)";

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
        populateLessonNotes(student.getLessonNotes(), displayedIndex);
    }

    /**
     * Fills the lesson note section with the most recent notes, numbered to match the numbering
     * used by the history command. At most {@code MAX_DISPLAYED_LESSON_NOTES} notes are shown,
     * followed by a line pointing to the history command for the rest. The section is hidden and
     * unmanaged when the student has no lesson notes, so that no blank space is left on the card.
     */
    private void populateLessonNotes(List<LessonNote> notes, int displayedIndex) {
        lessonNotes.setVisible(!notes.isEmpty());
        lessonNotes.setManaged(!notes.isEmpty());

        int displayedNotes = Math.min(notes.size(), MAX_DISPLAYED_LESSON_NOTES);
        for (int i = 0; i < displayedNotes; i++) {
            lessonNotes.getChildren().add(createLessonNoteLabel(i + 1, notes.get(i)));
        }

        int hiddenNotes = notes.size() - displayedNotes;
        if (hiddenNotes > 0) {
            lessonNotes.getChildren().add(
                    createSmallLabel(String.format(MESSAGE_MORE_LESSON_NOTES, hiddenNotes, displayedIndex)));
        }
    }

    /**
     * Returns a label showing the given lesson note, prefixed with its 1-based position.
     */
    private static Label createLessonNoteLabel(int position, LessonNote note) {
        return createSmallLabel(String.format("%d. %s | %s | %s",
                position, note.getDate(), note.getSubject(), truncate(note.getNoteText().value)));
    }

    /**
     * Returns a label styled like the other detail rows on the card.
     */
    private static Label createSmallLabel(String text) {
        Label label = new Label(text);
        label.getStyleClass().add("cell_small_label");
        return label;
    }

    /**
     * Shortens {@code text} so that one long note cannot dominate the card. The full text stays
     * available through the history command.
     */
    private static String truncate(String text) {
        if (text.length() <= MAX_DISPLAYED_NOTE_TEXT_LENGTH) {
            return text;
        }
        return text.substring(0, MAX_DISPLAYED_NOTE_TEXT_LENGTH).stripTrailing() + "...";
    }
}
