import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Game {

    private Player player1;
    private Player player2;

    private Scanner input = new Scanner(System.in);

    private int rows;
    private int cols;
    private String profileFile;

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
            System.out.println("Yolokeni Mbambi\n");
            System.out.println("1. Jogar");
            System.out.println("2. Sair");
            System.out.println();

            int choice;

            while (true) {

                System.out.print("Escolha uma opcao: ");

                if (input.hasNextInt()) {

                    choice = input.nextInt();
                    input.nextLine();

                    if (choice == 1 || choice == 2) {
                        break;
                    }

                } else {

                    input.next();
                }

                System.out.println("Opcao invalida.");
            }

            if (choice == 1) {

                setupGame();
                gameLoop();

            } else {

                System.out.println("Saindo do jogo...");
                return;
            }
        }
    }

    // Game setup
    private void setupGame() {

        System.out.println("\n=== CONFIGURACAO DOS JOGADORES ===");

        chooseBoardSize();

        System.out.print("Nome do Jogador 1: ");
        String name1 = input.nextLine();

        System.out.print("Nome do Jogador 2: ");
        String name2 = input.nextLine();

        player1 = new Player(name1, rows, cols);
        player2 = new Player(name2, rows, cols);

        setupPlayer(player1);
        setupPlayer(player2);
    }

    // Board size selection
    private void chooseBoardSize() {

        System.out.println("\nEscolha o tamanho do tabuleiro:");
        System.out.println("1 - Pequeno (7x9)");
        System.out.println("2 - Medio (10x10)");
        System.out.println("3 - Grande (15x15)");

        int option;

        while (true) {

            System.out.print("Opcao: ");

            if (input.hasNextInt()) {

                option = input.nextInt();
                input.nextLine();

                if (option >= 1 && option <= 3) {
                    break;
                }

            } else {

                input.next();
            }

            System.out.println("Opcao invalida.");
        }

        switch (option) {

            case 1:
                rows = 7;
                cols = 9;
                profileFile = "profiles_7x9.txt";
                break;

            case 2:
                rows = 10;
                cols = 10;
                profileFile = "profiles_10x10.txt";
                break;

            default:
                rows = 15;
                cols = 15;
                profileFile = "profiles_15x15.txt";
                break;
        }
    }

    // Display only profiles that contain at least one ship
    private void showProfiles() {

        System.out.println("\nPerfis disponiveis:");

        try {

            Scanner file = new Scanner(new File(profileFile));

            String currentProfile = null;
            boolean hasShips = false;

            while (file.hasNextLine()) {

                String line = file.nextLine().trim();

                if (line.startsWith("NAME:")) {

                    currentProfile = line.substring(5).trim();
                    hasShips = false;
                }

                else if (line.equals("END_PROFILE")) {

                    if (currentProfile != null && hasShips) {

                        System.out.println(currentProfile);
                    }

                    currentProfile = null;
                }

                else if (!line.isEmpty()) {

                    String[] parts = line.split("\\s+");

                    if (parts.length == 4) {

                        try {

                            Integer.parseInt(parts[0]);
                            Integer.parseInt(parts[1]);
                            Integer.parseInt(parts[2]);

                            if (parts[3].equalsIgnoreCase("true") ||
                                parts[3].equalsIgnoreCase("false")) {

                                hasShips = true;
                            }

                        } catch (NumberFormatException ignored) {
                        }
                    }
                }
            }

            file.close();

        } catch (FileNotFoundException e) {

            System.out.println("Nao foi possivel abrir o ficheiro de perfis.");
        }
    }

    private void saveProfile(Player player) {
        System.out.println("\nPerfis CUSTOM disponiveis:");

        try {

            Scanner file = new Scanner(new File(profileFile));

            while (file.hasNextLine()) {

                String line = file.nextLine().trim();

                if (line.startsWith("NAME: Custom")) {

                    System.out.println(line.substring(5).trim());
                }
            }

            file.close();

        } catch (FileNotFoundException e) {

                System.out.println("Erro ao abrir o ficheiro.");
                return;
            }

            System.out.print("\nEscolha o perfil (Custom1...Custom5): ");
            String profileName = input.nextLine();

            writeProfile(profileName, player);
    }

    private void writeProfile(String profileName, Player player) {
        try {

            Scanner file = new Scanner(new File(profileFile));

            StringBuilder content = new StringBuilder();

            boolean replacing = false;

            while (file.hasNextLine()) {

                String line = file.nextLine();

                if (line.trim().equalsIgnoreCase("NAME: " + profileName)) {

                    content.append(line).append("\n");

                    for (Ship ship : player.getShips()) {

                        content.append(ship.getSize()).append(" ")
                            .append(ship.getRow() + 1).append(" ")
                            .append(ship.getCol() + 1).append(" ")
                            .append(ship.isHorizontal()).append("\n");
                    }

                    replacing = true;

                    while (file.hasNextLine()) {

                        line = file.nextLine();

                        if (line.trim().equals("END_PROFILE")) {

                            content.append("END_PROFILE\n");
                            break;
                        }
                    }

                    continue;
                }

                content.append(line).append("\n");
            }

            file.close();

            if (!replacing) {

                System.out.println("Perfil nao encontrado.");
                return;
            }

            PrintWriter out = new PrintWriter(new FileWriter(profileFile));

            out.print(content.toString());

            out.close();

            System.out.println("Perfil guardado com sucesso!");

        } catch (IOException e) {

            System.out.println("Erro ao guardar perfil.");
        }
    }

    // Player setup
    private void setupPlayer(Player player) {

        System.out.println("\nConfiguracao de " + player.getName());

        while (true) {

            System.out.println("1 - Posicionar navios manualmente");
            System.out.println("2 - Carregar perfil");

            System.out.print("Opcao: ");

            if (!input.hasNextInt()) {

                System.out.println("Opcao invalida.");
                input.next();
                continue;
            }

            int option = input.nextInt();
            input.nextLine();

            if (option == 1) {

                player.placeShips(input);

                System.out.print("\nDeseja guardar esta disposicao como perfil? (s/n): ");
                String answer = input.nextLine();

                if (answer.equalsIgnoreCase("s")) {

                    saveProfile(player);
                }

                break;
            }

            if (option == 2) {

                showProfiles();

                System.out.print("\nNome do perfil: ");
                String profileName = input.nextLine();

                if (player.loadProfile(profileFile, profileName)) {
                    break;
                }

                continue;
            }

            System.out.println("Opcao invalida.");
        }
    }

    // Main game loop
    private void gameLoop() {

        Player currentPlayer = player1;
        Player enemyPlayer = player2;

        while (true) {

            System.out.println("\n========================");
            System.out.println("Vez de " + currentPlayer.getName());
            System.out.println("Streak atual: " + currentPlayer.getHitStreak());
            System.out.println("========================");

            Weapon chosenWeapon = Weapon.normalShot();

            if (currentPlayer.isEmpDisabled()) {

                System.out.println("Os teus sistemas foram desativados por EMP!");
                currentPlayer.setEmpDisabled(false);

            } else {

                System.out.println("\nArmas disponiveis:");

                Weapon[] weapons = currentPlayer.getWeapons();

                int option = 1;

                for (Weapon weapon : weapons) {

                    if (currentPlayer.getHitStreak() >= weapon.getUnlockStreak()) {

                        System.out.println(option + " - " + weapon);
                        option++;
                    }
                }

                int choice;

                while (true) {

                    System.out.print("Escolha uma arma: ");

                    if (input.hasNextInt()) {

                        choice = input.nextInt();
                        input.nextLine();

                        if (choice >= 1 && choice < option) {
                            break;
                        }
                    }
                    else {

                        input.next();
                    }

                    System.out.println("Opcao invalida.");
                }

                int index = 1;

                for (Weapon weapon : weapons) {

                    if (currentPlayer.getHitStreak() >= weapon.getUnlockStreak()) {

                        if (index == choice) {

                            chosenWeapon = weapon;
                            break;
                        }

                        index++;
                    }
                }
            }

            System.out.println("\nArma escolhida: " + chosenWeapon.getName());

            boolean hit = currentPlayer.attack(enemyPlayer, input, chosenWeapon);

    if (chosenWeapon.getType() != Weapon.Type.RADAR) {

        if (hit) {

            currentPlayer.increaseHitStreak();
            System.out.println("Streak: " + currentPlayer.getHitStreak());

        } else {

            currentPlayer.resetHitStreak();
            System.out.println("Streak perdida!");
        }
    }

            if (enemyPlayer.getBoard().allShipsDestroyed()) {

                System.out.println("\n" + currentPlayer.getName() + " GANHOU!");
                break;
            }

            if (chosenWeapon.getType() != Weapon.Type.RADAR && !hit) {

                Player temp = currentPlayer;
                currentPlayer = enemyPlayer;
                enemyPlayer = temp;
            }
        }
    }
}