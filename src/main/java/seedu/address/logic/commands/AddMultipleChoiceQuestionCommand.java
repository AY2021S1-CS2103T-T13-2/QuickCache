package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CHOICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUESTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.flashcard.Flashcard;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;


/**
 * Adds a person to the address book.
 */
public class AddMultipleChoiceQuestionCommand extends Command {

    public static final String COMMAND_WORD = "addmcq";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a flashcard to QuickCache. "
            + "Parameters: "
            + PREFIX_QUESTION + "Question "
            + PREFIX_ANSWER + "Answer "
            + "" + PREFIX_CHOICE + "Choices...\n"
            + "[" + PREFIX_TAG + "TAG]...\n";

    public static final String MESSAGE_SUCCESS = "New flashcard added: %1$s";
    public static final String MESSAGE_DUPLICATE_FLASHCARD = "This flashcard already exists in QuickCache";

    private final Flashcard toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddMultipleChoiceQuestionCommand(Flashcard flashcard) {
        requireNonNull(flashcard);
        toAdd = flashcard;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasFlashcard(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_FLASHCARD);
        }

        model.addFlashcard(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddMultipleChoiceQuestionCommand // instanceof handles nulls
                && toAdd.equals(((AddMultipleChoiceQuestionCommand) other).toAdd));
    }
}
