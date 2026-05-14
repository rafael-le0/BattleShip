import java.util.Scanner;

public class Game {

    private Scanner scanner;

    public Game() {
        scanner = new Scanner(System.in);
    }

    public void menu() {

        while (true) {

            System.out.println("=== BATTLESHIP ===");
            System.out.println("1. Start Game");
            System.out.println("2. Instructions");
            System.out.println("3. Exit");

            int choice = scanner.nextInt();

            switch (choice) {

                case 1:
                    startGame();
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

    private void startGame() {
        System.out.println("Starting game...");
    }

    private void showInstructions() {
        System.out.println("Destroy all enemy ships.");
    }
}