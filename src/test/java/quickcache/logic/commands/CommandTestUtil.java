package quickcache.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import quickcache.commons.core.index.Index;
import quickcache.logic.commands.exceptions.CommandException;
import quickcache.logic.parser.CliSyntax;
import quickcache.model.Model;
import quickcache.model.QuickCache;
import quickcache.model.flashcard.Flashcard;
import quickcache.model.flashcard.Question;
import quickcache.model.flashcard.QuestionContainsKeywordsPredicate;
import quickcache.model.flashcard.Statistics;
import quickcache.testutil.Assert;
import quickcache.testutil.EditFlashcardDescriptorBuilder;


/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_QUESTION_ONE = "Question One";
    public static final String VALID_QUESTION_TWO = "Question Two";
    public static final String VALID_QUESTION_THREE = "Question Three";
    public static final String VALID_ANSWER_ONE = "1";
    public static final String VALID_ANSWER_TWO = "2";
    public static final String VALID_ANSWER_THREE = "3";
    public static final String VALID_OPTION_ONE = "1";
    public static final String VALID_OPTION_TWO = "2";
    public static final String VALID_OPTION_THREE = "3";
    public static final String VALID_TAG_LSM1301 = "LSM1301";
    public static final String VALID_TAG_MODULE = "Module";

    public static final String CHOICE_DESC = " " + CliSyntax.PREFIX_CHOICE + "First" + " "
        + CliSyntax.PREFIX_CHOICE + "Second" + " " + CliSyntax.PREFIX_CHOICE + "Third" + " "
        + CliSyntax.PREFIX_CHOICE + "Fourth";
    public static final String QUESTION_DESC_ONE = " " + CliSyntax.PREFIX_QUESTION + VALID_QUESTION_ONE;
    public static final String QUESTION_DESC_TWO = " " + CliSyntax.PREFIX_QUESTION + VALID_QUESTION_TWO;
    public static final String QUESTION_DESC_THREE = " " + CliSyntax.PREFIX_QUESTION + VALID_QUESTION_THREE;
    public static final String ANSWER_DESC_ONE = " " + CliSyntax.PREFIX_ANSWER + VALID_ANSWER_ONE;
    public static final String ANSWER_DESC_TWO = " " + CliSyntax.PREFIX_ANSWER + VALID_ANSWER_TWO;
    public static final String ANSWER_DESC_THREE = " " + CliSyntax.PREFIX_ANSWER + VALID_ANSWER_THREE;
    public static final String OPTION_DESC_ONE = " " + CliSyntax.PREFIX_OPTION + VALID_OPTION_ONE;
    public static final String OPTION_DESC_TWO = " " + CliSyntax.PREFIX_OPTION + VALID_OPTION_TWO;
    public static final String OPTION_DESC_THREE = " " + CliSyntax.PREFIX_OPTION + VALID_OPTION_THREE;

    // empty string not allowed for addresses
    public static final String INVALID_QUESTION_DESC = " " + CliSyntax.PREFIX_QUESTION + " ";
    public static final String INVALID_ANSWER_DESC = " " + CliSyntax.PREFIX_ANSWER + " ";
    public static final String INVALID_OPTION_DESC = " " + CliSyntax.PREFIX_OPTION + " ";
    public static final String INVALID_OPTION_NON_ALPHANUMERIC_DESC = " " + CliSyntax.PREFIX_OPTION + "abc";

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditFlashcardDescriptor DESC_TWO;
    public static final EditCommand.EditFlashcardDescriptor DESC_THREE;

    static {
        DESC_TWO = new EditFlashcardDescriptorBuilder().withQuestion(VALID_QUESTION_TWO)
            .withAnswer(VALID_ANSWER_TWO).build();
        DESC_THREE = new EditFlashcardDescriptorBuilder().withQuestion(VALID_QUESTION_THREE)
            .withAnswer(VALID_ANSWER_THREE).build();
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

        Assert.assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
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
        model.updateFilteredFlashcardList(new QuestionContainsKeywordsPredicate(Arrays.asList(splitName[1])));

        assertEquals(1, model.getFilteredFlashcardList().size());
    }


}
