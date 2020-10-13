package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CHOICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUESTION;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.flashcard.Flashcard;
import seedu.address.flashcard.Question;
import seedu.address.flashcard.Statistics;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.QuickCache;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.testutil.EditFlashcardDescriptorBuilder;


/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_QUESTION_ALICE = "Alice";
    public static final String VALID_QUESTION_AMY = "Amy Bee";
    public static final String VALID_QUESTION_BOB = "Bob Choo";
    public static final String VALID_QUESTION_KEV = "KEv Chee";
    public static final String VALID_ANSWER_ALICE = "2";
    public static final String VALID_ANSWER_AMY = "11111111";
    public static final String VALID_ANSWER_BOB = "22222222";
    public static final String VALID_ANSWER_KEV = "11112222";
    public static final String VALID_OPTION_ALICE = "1";
    public static final String VALID_OPTION_AMY = "23";
    public static final String VALID_OPTION_BOB = "555";

    public static final String CHOICE_DESC = " " + PREFIX_CHOICE + "First" + " "
            + PREFIX_CHOICE + "Second" + " " + PREFIX_CHOICE + "Third" + " "
            + PREFIX_CHOICE + "Fourth";
    public static final String QUESTION_DESC_ALICE = " " + PREFIX_QUESTION + VALID_QUESTION_ALICE;
    public static final String QUESTION_DESC_AMY = " " + PREFIX_QUESTION + VALID_QUESTION_AMY;
    public static final String QUESTION_DESC_BOB = " " + PREFIX_QUESTION + VALID_QUESTION_BOB;
    public static final String ANSWER_DESC_ALICE = " " + PREFIX_ANSWER + VALID_ANSWER_ALICE;
    public static final String ANSWER_DESC_AMY = " " + PREFIX_ANSWER + VALID_ANSWER_AMY;
    public static final String ANSWER_DESC_BOB = " " + PREFIX_ANSWER + VALID_ANSWER_BOB;
    public static final String OPTION_DESC_ALICE = " " + PREFIX_OPTION + VALID_OPTION_ALICE;
    public static final String OPTION_DESC_AMY = " " + PREFIX_OPTION + VALID_OPTION_AMY;
    public static final String OPTION_DESC_BOB = " " + PREFIX_OPTION + VALID_OPTION_BOB;

    // empty string not allowed for addresses
    public static final String INVALID_QUESTION_DESC = " " + PREFIX_QUESTION + " ";
    public static final String INVALID_ANSWER_DESC = " " + PREFIX_ANSWER + " ";
    public static final String INVALID_OPTION_DESC = " " + PREFIX_OPTION + " ";
    public static final String INVALID_OPTION_NON_ALPHANUMERIC_DESC = " " + PREFIX_OPTION + "abc";

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditFlashcardDescriptor DESC_AMY;
    public static final EditCommand.EditFlashcardDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditFlashcardDescriptorBuilder().withQuestion(VALID_QUESTION_AMY)
                .withAnswer(VALID_ANSWER_AMY).build();
        DESC_BOB = new EditFlashcardDescriptorBuilder().withQuestion(VALID_QUESTION_BOB)
                .withAnswer(VALID_ANSWER_BOB).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
                                            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
                                            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
                                            Model expectedModel, Question expectedQuestion,
                                            Boolean expectedIsCorrect, boolean isChangeWindow) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, expectedQuestion,
                expectedIsCorrect, isChangeWindow);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
                                            Model expectedModel, Question expectedQuestion,
                                            Statistics expectedStatistics) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, expectedQuestion,
                expectedStatistics);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered person list and selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        QuickCache expectedQuickCache = new QuickCache(actualModel.getQuickCache());
        List<Flashcard> expectedFilteredList = new ArrayList<>(actualModel.getFilteredFlashcardList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedQuickCache, actualModel.getQuickCache());
        assertEquals(expectedFilteredList, actualModel.getFilteredFlashcardList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the flashcard at the given {@code targetIndex} in the
     * {@code model}'s QuickCache.
     */
    public static void showFlashcardAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredFlashcardList().size());

        Flashcard flashcard = model.getFilteredFlashcardList().get(targetIndex.getZeroBased());
        final String[] splitName = flashcard.getQuestion().toString().split("\\s+");
        model.updateFilteredFlashcardList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[1])));

        assertEquals(1, model.getFilteredFlashcardList().size());
    }


}
