import java.util.Scanner;

public class Game {

    private Player player1;
    private Player player2;

    private Scanner scanner;

    public Game() {

        scanner = new Scanner(System.in);

        player1 = new Player("Player 1");
        player2 = new Player("Player 2");
    }

    public void start() {

        menu();
    }

    private void menu() {

        while (true) {

            System.out.println("====== BATTLESHIP ======");
            System.out.println("1. Start Game");
            System.out.println("2. Instructions");
            System.out.println("3. Exit");

            System.out.print("Choose option: ");

            int choice = scanner.nextInt();

            switch (choice) {

                case 1:
                    setupGame();
                    gameLoop();
                    break;

                case 2:
                    showInstructions();
                    break;

                case 3:
                    System.out.println("Goodbye!");
                    return;

                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private void setupGame() {

        clearScreen();

        System.out.println("PLAYER 1 PLACE SHIPS");
        player1.placeShips();

        pause();
        clearScreen();

        System.out.println("PLAYER 2 PLACE SHIPS");
        player2.placeShips();

        pause();
        clearScreen();
    }

    private void gameLoop() {

        while (true) {

            playerTurn(player1, player2);

            if (player2.getBoard().allShipsSunk()) {

                System.out.println();
                System.out.println(player1.getName() + " WINS!");

                break;
            }

            pause();
            clearScreen();

            playerTurn(player2, player1);

            if (player1.getBoard().allShipsSunk()) {

                System.out.println();
                System.out.println(player2.getName() + " WINS!");

                break;
            }

            pause();
            clearScreen();
        }
    }

    private void playerTurn(Player currentPlayer, Player enemyPlayer) {

        System.out.println("================================");
        System.out.println(currentPlayer.getName() + "'S TURN");
        System.out.println("================================");

        System.out.println();
        System.out.println("YOUR BOARD:");
        currentPlayer.getBoard().printBoard(false);

        System.out.println();
        System.out.println("ENEMY BOARD:");
        enemyPlayer.getBoard().printBoard(true);

        System.out.println();

        boolean validShot = false;

        while (!validShot) {

            System.out.print("Shoot row: ");
            int row = scanner.nextInt();

            System.out.print("Shoot col: ");
            int col = scanner.nextInt();

            if (row < 0 || row >= 10 || col < 0 || col >= 10) {

                System.out.println("Invalid coordinates.");
                continue;
            }

            validShot = enemyPlayer.getBoard().shoot(row, col);

            if (!validShot) {

                char cell = enemyPlayer.getBoard().getCell(row, col);

                if (cell == 'X' || cell == 'O') {
                    System.out.println("You already shot there.");
                }
            }
        }
    }

    private void showInstructions() {

        System.out.println();
        System.out.println("========= INSTRUCTIONS =========");
        System.out.println("Each player places 5 ships.");
        System.out.println("Ships can be horizontal or vertical.");
        System.out.println("Players take turns shooting coordinates.");
        System.out.println();
        System.out.println("Symbols:");
        System.out.println("~ = water");
        System.out.println("S = ship");
        System.out.println("X = hit");
        System.out.println("O = miss");
        System.out.println();
    }

    private void clearScreen() {

        for (int i = 0; i < 30; i++) {
            System.out.println();
        }
    }

    private void pause() {

        System.out.println();
        System.out.println("Press ENTER to continue...");

        try {

            System.in.read();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}
