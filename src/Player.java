
import java.util.Scanner;

public class Player {

    private String name;
    private Board board;

    public Player(String name) {

        this.name = name;
        this.board = new Board();
    }

    public String getName() {

        return name;
    }

    public Board getBoard() {

        return board;
    }

    // Player attacks another player
    public void attack(Player enemy, Scanner input) {

        System.out.println("\n" + name + "'s turn");

        System.out.print("Row: ");
        int row = input.nextInt();

        System.out.print("Column: ");
        int col = input.nextInt();

        enemy.getBoard().attack(row, col);
    }

    // Alternates turns automatically
    public static void nextTurn(Player current, Player enemy, Scanner input) {

        current.attack(enemy, input);
    }
}
```

