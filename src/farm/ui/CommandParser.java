package farm.ui;

import farm.exceptions.InvalidCommandException;
import java.util.Locale;

public final class CommandParser {
    private static final int MAX_INPUT_LENGTH = 256;
    private static final int DEFAULT_QUANTITY = 1;

    public Command parse(String input) throws InvalidCommandException {
        if (input == null || input.isBlank()) {
            return null;
        }
        if (input.length() > MAX_INPUT_LENGTH) {
            throw new InvalidCommandException("Command is too long. Use help for examples.");
        }

        String commandText = input.strip().toLowerCase(Locale.ROOT);
        String[] words = commandText.split("\\s+");
        String commandName = words[0];

        switch (commandName) {
            case "status":
                requireCount(words, 1, "status");
                return new ShowStatusCommand();
            case "plant":
                requireCount(words, 3, "plant <plot> <crop>");
                return new PlantCommand(positiveNumber(words[1], "Plot"), words[2]);
            case "water":
                requireCount(words, 2, "water <plot>");
                return new WaterCommand(positiveNumber(words[1], "Plot"));
            case "harvest":
                requireCount(words, 2, "harvest <plot|all>");
                if (words[1].equals("all")) {
                    return new HarvestCommand();
                }
                return new HarvestCommand(positiveNumber(words[1], "Plot"));
            case "buy":
                requireTradeCount(words, "buy <item> [quantity]");
                return new BuyCommand(words[1], quantity(words));
            case "sell":
                requireTradeCount(words, "sell <item> [quantity]");
                return new SellCommand(words[1], quantity(words));
            case "feed":
                requireCount(words, 1, "feed");
                return new FeedCommand();
            case "next":
                requireCount(words, 1, "next");
                return new NextDayCommand();
            case "help":
                requireCount(words, 1, "help");
                return new HelpCommand();
            case "quit":
                requireCount(words, 1, "quit");
                return new QuitCommand();
            default:
                throw new InvalidCommandException("Unknown command: " + commandName
                        + ". Type help for available commands.");
        }
    }

    private int quantity(String[] words) throws InvalidCommandException {
        if (words.length == 3) {
            return positiveNumber(words[2], "Quantity");
        }
        return DEFAULT_QUANTITY;
    }

    private int positiveNumber(String text, String label) throws InvalidCommandException {
        try {
            int number = Integer.parseInt(text);
            if (number > 0) {
                return number;
            }
        } catch (NumberFormatException exception) {
            throw new InvalidCommandException(label + " must be a positive whole number.");
        }
        throw new InvalidCommandException(label + " must be a positive whole number.");
    }

    private void requireCount(String[] words, int count, String usage)
            throws InvalidCommandException {
        if (words.length != count) {
            throw new InvalidCommandException("Usage: " + usage);
        }
    }

    private void requireTradeCount(String[] words, String usage) throws InvalidCommandException {
        if (words.length < 2 || words.length > 3) {
            throw new InvalidCommandException("Usage: " + usage);
        }
    }
}
