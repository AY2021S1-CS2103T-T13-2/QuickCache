package quickcache.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import quickcache.model.Model;
import quickcache.model.ModelManager;
import quickcache.model.QuickCache;
import quickcache.model.UserPrefs;
import quickcache.model.flashcard.Flashcard;
import quickcache.testutil.EditFlashcardDescriptorBuilder;
import quickcache.testutil.FlashcardBuilder;
import quickcache.testutil.TypicalFlashcards;
import quickcache.testutil.TypicalIndexes;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand)
 * and unit tests for EditCommand.
 */
public class EditCommandTest {

    private final Model model = new ModelManager(TypicalFlashcards.getTypicalQuickCache(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedForOpenEndedUnfilteredList_success() {
        Flashcard editedFlashcard = new FlashcardBuilder().build();
        EditCommand.EditFlashcardDescriptor descriptor = new EditFlashcardDescriptorBuilder(editedFlashcard).build();
        EditCommand editCommand = new EditCommand(TypicalIndexes.INDEX_FIRST_FLASHCARD, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_FLASHCARD_SUCCESS, editedFlashcard);

        Model expectedModel = new ModelManager(new QuickCache(model.getQuickCache()), new UserPrefs());
        expectedModel.setFlashcard(model.getFilteredFlashcardList().get(0), editedFlashcard);

        CommandTestUtil.assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_allFieldsSpecifiedForMultipleChoiceUnfilteredList_success() {
        String[] choices = {"First", "Second", "Third", "Fourth"};
        Flashcard editedFlashcard = new FlashcardBuilder()
            .withMultipleChoiceQuestion("Multiple Choice Question", choices).withAnswer("2").build();
        EditCommand.EditFlashcardDescriptor descriptor = new EditFlashcardDescriptorBuilder(editedFlashcard).build();
        Flashcard editedMultipleChoice = new FlashcardBuilder()
            .withMultipleChoiceQuestion("Multiple Choice Question", choices).withAnswer(choices[1]).build();
        EditCommand editCommand = new EditCommand(TypicalIndexes.INDEX_SEVENTH_FLASHCARD, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_FLASHCARD_SUCCESS, editedMultipleChoice);

        Model expectedModel = new ModelManager(new QuickCache(model.getQuickCache()), new UserPrefs());
        expectedModel.setFlashcard(model.getFilteredFlashcardList().get(6), editedMultipleChoice);

        CommandTestUtil.assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {

        Flashcard flashcardInFilteredList =
            model.getFilteredFlashcardList().get(TypicalIndexes.INDEX_FIRST_FLASHCARD.getZeroBased());
        Flashcard editedFlashcard =
            new FlashcardBuilder(flashcardInFilteredList).withQuestion(CommandTestUtil.VALID_QUESTION_BOB)
                .build();
        EditCommand editCommand = new EditCommand(TypicalIndexes.INDEX_FIRST_FLASHCARD,
            new EditFlashcardDescriptorBuilder().withQuestion(CommandTestUtil.VALID_QUESTION_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_FLASHCARD_SUCCESS, editedFlashcard);

        Model expectedModel = new ModelManager(new QuickCache(model.getQuickCache()), new UserPrefs());
        expectedModel.setFlashcard(model.getFilteredFlashcardList().get(0), editedFlashcard);

        CommandTestUtil.assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(TypicalIndexes.INDEX_FIRST_FLASHCARD,
            CommandTestUtil.DESC_AMY);

        // same values -> returns true
        EditCommand.EditFlashcardDescriptor copyDescriptor =
            new EditCommand.EditFlashcardDescriptor(CommandTestUtil.DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(TypicalIndexes.INDEX_FIRST_FLASHCARD, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(TypicalIndexes.INDEX_SECOND_FLASHCARD,
            CommandTestUtil.DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(TypicalIndexes.INDEX_FIRST_FLASHCARD,
            CommandTestUtil.DESC_BOB)));
    }

}
