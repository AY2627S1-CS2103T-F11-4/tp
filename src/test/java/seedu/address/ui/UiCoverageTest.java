package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Student;

public class UiCoverageTest {

    @BeforeAll
    public static void initializeJavaFxToolkit() {
        try {
            Platform.startup(() -> { });
        } catch (IllegalStateException exception) {
            // The toolkit has already been initialized by another test.
        }
    }

    @Test
    public void personCard_constructor_populatesStudentDetails() throws Exception {
        runOnFxThread(() -> {
            PersonCard personCard = new PersonCard(ALICE, 2);

            assertEquals(ALICE, personCard.student);
            assertNotNull(personCard.getRoot());
        });
    }

    @Test
    @SuppressWarnings("unchecked")
    public void personListPanel_constructorAndCell_updateStudentGraphic() throws Exception {
        ObservableList<Student> students = FXCollections.observableArrayList(ALICE);

        runOnFxThread(() -> {
            PersonListPanel panel = new PersonListPanel(students);
            VBox root = (VBox) panel.getRoot();
            ListView<Student> listView = (ListView<Student>) root.getChildren().get(0);
            ListCell<Student> cell = listView.getCellFactory().call(listView);
            Method updateItem = cell.getClass().getDeclaredMethod("updateItem", Student.class, boolean.class);
            updateItem.setAccessible(true);

            updateItem.invoke(cell, new Object[] {null, true});
            assertTrue(cell.getGraphic() == null);
            updateItem.invoke(cell, new Object[] {null, false});
            assertTrue(cell.getGraphic() == null);
            updateItem.invoke(cell, ALICE, false);
            assertNotNull(cell.getGraphic());
        });
    }

    @Test
    public void mainWindow_fillInnerParts_populatesPlaceholders() throws Exception {
        runOnFxThread(() -> {
            Stage defaultStage = new Stage();
            MainWindow defaultWindow = new MainWindow(defaultStage, new StubLogic(),
                    Path.of("data", "addressbook.json"));
            assertNotNull(defaultWindow.getPrimaryStage());
            defaultStage.close();

            Stage stage = new Stage();
            MainWindow mainWindow = new MainWindow(stage,
                    new StubLogic(new GuiSettings(800, 600, 10, 20)), Path.of("data", "addressbook.json"));

            mainWindow.fillInnerParts();

            assertNotNull(mainWindow.getPersonListPanel());
            stage.close();
        });
    }

    @Test
    public void mainWindow_executeFailure_displaysError() throws Exception {
        runOnFxThread(() -> {
            String errorMessage = "Invalid student input.";
            Stage stage = new Stage();
            MainWindow mainWindow = new MainWindow(stage, new StubLogic(new ParseException(errorMessage)),
                    Path.of("data", "addressbook.json"));
            mainWindow.fillInnerParts();

            Method executeCommand = MainWindow.class.getDeclaredMethod("executeCommand", String.class);
            executeCommand.setAccessible(true);
            InvocationTargetException thrown;
            try {
                executeCommand.invoke(mainWindow, "addstudent invalid");
                throw new AssertionError("Expected executeCommand to fail");
            } catch (InvocationTargetException exception) {
                thrown = exception;
            }
            assertEquals(errorMessage, thrown.getCause().getMessage());

            Field resultDisplayField = MainWindow.class.getDeclaredField("resultDisplay");
            resultDisplayField.setAccessible(true);
            ResultDisplay resultDisplay = (ResultDisplay) resultDisplayField.get(mainWindow);

            Field textAreaField = ResultDisplay.class.getDeclaredField("resultDisplay");
            textAreaField.setAccessible(true);
            TextArea textArea = (TextArea) textAreaField.get(resultDisplay);
            assertEquals(errorMessage, textArea.getText());
            stage.close();
        });
    }

    private static void runOnFxThread(FxAction action) throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<Throwable> failure = new AtomicReference<>();
        Platform.runLater(() -> {
            try {
                action.run();
            } catch (Throwable throwable) {
                failure.set(throwable);
            } finally {
                latch.countDown();
            }
        });

        assertTrue(latch.await(10, TimeUnit.SECONDS));
        if (failure.get() != null) {
            throw new AssertionError("JavaFX test action failed", failure.get());
        }
    }

    @FunctionalInterface
    private interface FxAction {
        void run() throws Exception;
    }

    private static class StubLogic implements Logic {
        private final GuiSettings guiSettings;
        private final Exception executionFailure;

        StubLogic() {
            this(new GuiSettings(), null);
        }

        StubLogic(GuiSettings guiSettings) {
            this(guiSettings, null);
        }

        StubLogic(Exception executionFailure) {
            this(new GuiSettings(), executionFailure);
        }

        StubLogic(GuiSettings guiSettings, Exception executionFailure) {
            this.guiSettings = guiSettings;
            this.executionFailure = executionFailure;
        }

        @Override
        public CommandResult execute(String commandText) throws CommandException, ParseException {
            if (executionFailure instanceof CommandException commandException) {
                throw commandException;
            }
            if (executionFailure instanceof ParseException parseException) {
                throw parseException;
            }
            return new CommandResult(commandText);
        }

        @Override
        public ObservableList<Student> getFilteredStudentList() {
            return FXCollections.observableArrayList(ALICE);
        }

        @Override
        public GuiSettings getGuiSettings() {
            return guiSettings;
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            // No-op for the UI coverage test.
        }
    }
}
