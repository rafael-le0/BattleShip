import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

public class App {

    public static void main(String[] args) throws UnsupportedEncodingException {

        // Possibilita a impressão de caráteres acentuados
        System.setOut(new PrintStream(System.out, true, "UTF-8"));

        Game game = new Game();

        game.start();
    }
}