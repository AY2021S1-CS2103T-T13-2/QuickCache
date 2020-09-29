package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ANSWER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUESTION_BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditCommand.EditFlashcardDescriptor;
import seedu.address.testutil.EditFlashcardDescriptorBuilder;


public class EditFlashcardDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditFlashcardDescriptor descriptorWithSameValues = new EditFlashcardDescriptor(DESC_AMY);
        assertTrue(DESC_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_AMY.equals(DESC_AMY));

        // null -> returns false
        assertFalse(DESC_AMY.equals(null));

        // different types -> returns false
        assertFalse(DESC_AMY.equals(5));

        // different values -> returns false
        assertFalse(DESC_AMY.equals(DESC_BOB));

        // different question -> returns false
        EditFlashcardDescriptor editedAmy = new EditFlashcardDescriptorBuilder(DESC_AMY)
                .withQuestion(VALID_QUESTION_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different answer -> returns false
        editedAmy = new EditFlashcardDescriptorBuilder(DESC_AMY).withAnswer(VALID_ANSWER_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));


    }
}