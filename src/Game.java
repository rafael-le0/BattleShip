import java.util.Scanner;

public class Game {

    private Player player1;
    private Player player2;

    private Scanner input = new Scanner(System.in);

    
    // Start game
    public void start() {

        showMainMenu();
    }

    // Main menu
    private void showMainMenu() {

        while (true) {

            System.out.println();
            System.out.println("=======================================");
            System.out.println("        B A T A L H A  N A V A L");
            System.out.println("=======================================");
            System.out.println();
            System.out.println("              |    |");
            System.out.println("             )_)  )_)");
            System.out.println("            )___))___)");
            System.out.println("           )____)_____)");
            System.out.println("         _____|____|____");
            System.out.println("---------\\              /---------");
            System.out.println();
            System.out.println("Por Rafael Alberto");
            System.out.println("Tchanek dos Santos");
            System.out.println("Yolokeni Mbambi\n\n");
            System.out.println("1. Jogar");
            System.out.println("2. Sair");
            System.out.println();

            int choice;

            while (true) {

                System.out.print("Escolha uma opção: ");

                if (input.hasNextInt()) {

                    choice = input.nextInt();
                    input.nextLine();

                    if (choice == 1 || choice == 2) {
                        break;
                    }
                } else {

                    input.next();
                }

                System.out.println("Opção inválida.");
            }

            // Start game
            if (choice == 1) {

                setupGame();
                gameLoop();
            }

            // Exit
            else {

                System.out.println("Saindo do jogo...");
                return;
            }
        }
    }

   
    // Game setup
    private void setupGame() {

        System.out.println("\n=== PLAYER SETUP ===");

        // Create players
        System.out.print("Nome do Jogador 1: ");
        String name1 = input.nextLine();

        System.out.print("Nome do Jogador 2: ");
        String name2 = input.nextLine();

        player1 = new Player(name1);
        player2 = new Player(name2);

        // Setup phase
        player1.placeShips(input);
        player2.placeShips(input);
    }

    
    // Main game loop
    private void gameLoop() {

        Player currentPlayer = player1;
        Player enemyPlayer = player2;

        while (true) {

            System.out.println("\n========================");
            System.out.println("Vez de "+currentPlayer.getName());
            System.out.println("========================");

            boolean hit = currentPlayer.attack(enemyPlayer, input);

            // Victory check
            if (enemyPlayer.getBoard().allShipsDestroyed()) {

                System.out.println("\n" + currentPlayer.getName() + " GANHOU!");
                break;
            }

            // Swap turns ONLY if player missed
            if (!hit) {

                Player temp = currentPlayer;
                currentPlayer = enemyPlayer;
                enemyPlayer = temp;
            }
        }
    }
}