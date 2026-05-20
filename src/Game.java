import java.util.Scanner;

public class Game {

    private Board player1Board = new Board();
    private Board player2Board = new Board();

    private Scanner scanner = new Scanner(System.in);

    public void start() {

        System.out.println("=== BATTLESHIP GAME ===");

        setupPhase(player1Board, "Player 1");
        setupPhase(player2Board, "Player 2");

        playGame();
    }

    // -----------------------------
    // SETUP PHASE
    // -----------------------------
    private void setupPhase(Board board, String playerName) {

        System.out.println("\n" + playerName + " place your ships!");

        int shipCount = 3;

        for (int i = 0; i < shipCount; i++) {

            boolean placed = false;

            while (!placed) {

                System.out.println("\nShip " + (i + 1));

                System.out.print("Size: ");
                int size = scanner.nextInt();

                System.out.print("Row: ");
                int row = scanner.nextInt();

                System.out.print("Col: ");
                int col = scanner.nextInt();

                System.out.print("Horizontal (true/false): ");
                boolean horizontal = scanner.nextBoolean();

                Ship ship = new Ship(size, row, col, horizontal);

                if (board.placeShip(ship)) {
                    placed = true;
                    System.out.println("Ship placed!");
                } else {
                    System.out.println("Invalid placement. Try again.");
                }
            }
        }
    }

    // -----------------------------
    // GAME LOOP
    // -----------------------------
    private void playGame() {

        boolean player1Turn = true;

        while (true) {

            Board enemyBoard = player1Turn ? player2Board : player1Board;
            String currentPlayer = player1Turn ? "Player 1" : "Player 2";

            System.out.println("\n========================");
            System.out.println(currentPlayer + "'s turn");
            System.out.println("========================");

            enemyBoard.printBoard(true);

            System.out.print("Attack row: ");
            int row = scanner.nextInt();

            System.out.print("Attack col: ");
            int col = scanner.nextInt();

            enemyBoard.attack(row, col);

            // WIN CONDITION (now board-driven)
            if (enemyBoard.allShipsDestroyed()) {

                System.out.println("\n🏆 " + currentPlayer + " wins!");
                break;
            }

            player1Turn = !player1Turn;
        }
    }
}
