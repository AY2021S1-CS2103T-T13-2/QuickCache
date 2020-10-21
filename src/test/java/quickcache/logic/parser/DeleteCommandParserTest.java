package quickcache.logic.parser;

import static quickcache.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static quickcache.logic.parser.CommandParserTestUtil.assertParseFailure;
import static quickcache.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static quickcache.testutil.TypicalIndexes.INDEX_FIRST_FLASHCARD;

import org.junit.jupiter.api.Test;

import quickcache.commons.core.Messages;
import quickcache.logic.commands.DeleteCommand;
import quickcache.model.flashcard.Flashcard;
import quickcache.model.flashcard.FlashcardContainsTagPredicate;
import quickcache.model.flashcard.FlashcardPredicate;
import quickcache.model.flashcard.Tag;
import quickcache.testutil.TypicalTags;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteCommandParserTest {

    private final DeleteCommandParser parser = new DeleteCommandParser();

    @Test
    public void parse_validIndex_returnsDeleteCommand() {
        assertParseSuccess(parser, "1", DeleteCommand.withIndex(INDEX_FIRST_FLASHCARD));
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validTag_returnsDeleteCommand() {
        Set<Tag> tagsToMatch = new HashSet<>();
        tagsToMatch.add(TypicalTags.testTag);
        FlashcardPredicate flashcardPredicate = prepareFlashcardPredicate(tagsToMatch);
        assertParseSuccess(parser, " t/test", DeleteCommand.withPredicate(flashcardPredicate, tagsToMatch));
    }

    @Test
    public void parse_emptyTag_throwsParseException() {
        assertParseFailure(parser, " t/ t/test", Tag.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_validIndexAndTag_throwsParseException() {
        assertParseFailure(parser, "1 t/test", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    private FlashcardPredicate prepareFlashcardPredicate(Set<Tag> tagsToMatch) {
        ArrayList<Predicate<Flashcard>> predicates = new ArrayList<>();
        predicates.add(new FlashcardContainsTagPredicate(tagsToMatch));
        FlashcardPredicate flashcardPredicate = new FlashcardPredicate(predicates);
        return flashcardPredicate;
    }
}
