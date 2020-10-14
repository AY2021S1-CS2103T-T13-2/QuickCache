package quickcache.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import quickcache.logic.commands.EditCommand;
import quickcache.model.flashcard.Answer;
import quickcache.model.flashcard.Choice;
import quickcache.model.flashcard.Flashcard;
import quickcache.model.flashcard.MultipleChoiceQuestion;
import quickcache.model.flashcard.OpenEndedQuestion;
import quickcache.model.flashcard.Tag;

/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class EditFlashcardDescriptorBuilder {

    private final EditCommand.EditFlashcardDescriptor descriptor;

    public EditFlashcardDescriptorBuilder() {
        descriptor = new EditCommand.EditFlashcardDescriptor(false);
    }

    public EditFlashcardDescriptorBuilder(EditCommand.EditFlashcardDescriptor descriptor) {
        this.descriptor = new EditCommand.EditFlashcardDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code person}'s details
     */
    public EditFlashcardDescriptorBuilder(Flashcard flashcard) {
        descriptor = new EditCommand.EditFlashcardDescriptor(false);
        descriptor.setQuestion(flashcard.getQuestion());
        descriptor.setTags(flashcard.getTags());
        descriptor.setAnswer(flashcard.getAnswer());
        boolean isMcq = flashcard.getQuestion() instanceof MultipleChoiceQuestion;
        descriptor.setIsMcq(isMcq);
        if (isMcq) {
            descriptor.setChoices(flashcard.getQuestion().getChoices().get());
        } else {
            Choice[] emptyArray = new Choice[0];
            descriptor.setChoices(emptyArray);
        }
    }

    /**
     * Sets the {@code question} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditFlashcardDescriptorBuilder withQuestion(String question) {
        descriptor.setQuestion(new OpenEndedQuestion(question));
        return this;
    }

    /**
     * Sets the {@code answer} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditFlashcardDescriptorBuilder withAnswer(String answer) {
        descriptor.setAnswer(new Answer(answer));
        return this;
    }

    /**
     * Sets the {@code choices} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditFlashcardDescriptorBuilder withChoices(Choice[] choices) {
        descriptor.setChoices(choices);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditFlashcardDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }


    public EditCommand.EditFlashcardDescriptor build() {
        return descriptor;
    }
}
