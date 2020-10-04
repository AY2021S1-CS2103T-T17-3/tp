package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeletePersonCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern COMPLEX_COMMAND_FORMAT = Pattern.compile("(?<firstCommandWord>\\S+) (?<secondCommandWord>\\S+)(?<arguments>.*)");
    private static final Pattern SIMPLE_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {

        final Matcher simpleMatcher = SIMPLE_COMMAND_FORMAT.matcher(userInput.trim());
        final Matcher complexMatcher = COMPLEX_COMMAND_FORMAT.matcher(userInput.trim());
        if (simpleMatcher.matches()) {
            return parseSimpleCommand(simpleMatcher);
        } else if (complexMatcher.matches()) {
            return parseComplexCommand(complexMatcher);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }
    }

    private Command parseComplexCommand(Matcher complexMatcher) throws ParseException {

        final String firstCommandWord = complexMatcher.group("firstCommandWord");
        final String secondCommandWord = complexMatcher.group("secondCommandWord");
        final String fullCommandWord = firstCommandWord + " " + secondCommandWord;
        final String arguments = complexMatcher.group("arguments");
        switch (fullCommandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeletePersonCommand.COMMAND_WORD:
            return new DeletePersonCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    private Command parseSimpleCommand(Matcher simpleMatcher) throws ParseException {
        final String commandWord = simpleMatcher.group("commandWord");
        switch (commandWord) {

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
