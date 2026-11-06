package farm.ui;

import farm.core.Game;
import farm.core.GameState;
import farm.exceptions.GameException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

public final class ConsoleUI {
    private final BufferedReader input;
    private final PrintWriter output;
    private final CommandParser parser = new CommandParser();

    public ConsoleUI() {
        this(new InputStreamReader(System.in, StandardCharsets.UTF_8),
                new OutputStreamWriter(System.out, StandardCharsets.UTF_8));
    }

    public ConsoleUI(Reader input, Writer output) {
        this.input = new BufferedReader(Objects.requireNonNull(input));
        this.output = new PrintWriter(Objects.requireNonNull(output), true);
    }

    public void run(Game game) {
        println("Welcome to Hogwarts and your farm!");
        println("Reach " + game.getTargetGold() + " gold within "
                + game.getCalendar().getMaximumDays() + " days. Type help for commands.");
        println("Water crops and feed Hogwart animals before next. Sell products, then next to win.");
        printLines(game.getStatusLines());
        while (game.getState() == GameState.RUNNING) {
            output.print("farm> ");
            output.flush();
            if (output.checkError()) {
                game.quit();
                return;
            }
            String line;
            try {
                line = input.readLine();
            } catch (IOException exception) {
                println("Input could not be read. The game has closed safely.");
                game.quit();
                return;
            }
            if (line == null) {
                println("End of input. Thanks for playing our Harry Potter Farm game!");
                game.quit();
                return;
            }
            try {
                Command command = parser.parse(line);
                if (command != null) {
                    command.execute(game, this);
                }
            } catch (GameException exception) {
                println("Cannot do that: " + exception.getMessage());
            }
        }
    }

    public void println(String message) {
        output.println(message);
    }

    public void printLines(List<String> lines) {
        for (String line : lines) {
            println(line);
        }
    }
}
