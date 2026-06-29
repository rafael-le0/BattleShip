
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Player {

    private String name;
    private Board board;
    private int hitStreak = 0;

    public Player(String name, int rows, int cols) {

        this.name = name;
        this.board = new Board(rows, cols);
    }

    // Getters
    public String getName() {

        return name;
    }

    public Board getBoard() {

        return board;
    }

    public int getHitStreak() {

        return hitStreak;
    }

    public void increaseHitStreak() {

        hitStreak++;
    }

    public void resetHitStreak() {

        hitStreak = 0;
    }

    public ArrayList<Ship> getShips() {

        return board.getShips();
    }
    
    private boolean empDisabled = false;

    private Weapon[] weapons = {
        Weapon.normalShot(),
        Weapon.radar(),
        Weapon.artillery(),
        Weapon.airstrike(),
        Weapon.emp()
    };

    public Weapon[] getWeapons() {

        return weapons;
    }

    public boolean isEmpDisabled() {

        return empDisabled;
    }

    public void setEmpDisabled(boolean empDisabled) {

        this.empDisabled = empDisabled;
    }

    // Load profile from file
    public boolean loadProfile(String fileName, String profileName) {

        try {

            Scanner file = new Scanner(new File(fileName));

            boolean foundProfile = false;

            while (file.hasNextLine()) {

                String line = file.nextLine().trim();

                if (line.equalsIgnoreCase("NAME: " + profileName)) {

                    foundProfile = true;
                    break;
                }
            }

            if (!foundProfile) {

                file.close();
                System.out.println("Perfil nao encontrado.");
                return false;
            }

            while (file.hasNextLine()) {

                String line = file.nextLine().trim();

                if (line.equals("END_PROFILE")) {
                    break;
                }

                if (line.isEmpty()) {
                    continue;
                }

                String[] parts = line.split("\\s+");

                if (parts.length != 4) {
                    continue;
                }

                int size = Integer.parseInt(parts[0]);
                int row = Integer.parseInt(parts[1]) - 1;
                int col = Integer.parseInt(parts[2]) - 1;
                boolean horizontal = Boolean.parseBoolean(parts[3]);

                Ship ship = new Ship(size, row, col, horizontal);

                if (!board.placeShip(ship)) {

                    file.close();
                    System.out.println("Perfil invalido.");
                    return false;
                }
            }

            file.close();

            System.out.println("Perfil carregado com sucesso!");
            board.printBoard(false);

            return true;

        } catch (FileNotFoundException e) {

            System.out.println("Ficheiro nao encontrado.");
            return false;
        }
    }

    public boolean saveProfile(String fileName, String profileName) {
        try {

            Scanner file = new Scanner(new File(fileName));

            StringBuilder text = new StringBuilder();

            boolean insideProfile = false;
            boolean replaced = false;

            while (file.hasNextLine()) {

                String line = file.nextLine();

                if (line.trim().equalsIgnoreCase("NAME: " + profileName)) {

                    insideProfile = true;
                    replaced = true;

                    text.append(line).append("\n\n");

                    for (Ship ship : board.getShips()) {

                        text.append(ship.getSize()).append(" ")
                            .append(ship.getRow() + 1).append(" ")
                            .append(ship.getCol() + 1).append(" ")
                            .append(ship.isHorizontal()).append("\n");
                    }

                    text.append("\nEND_PROFILE\n");

                    continue;
                }

                if (insideProfile) {

                    if (line.trim().equals("END_PROFILE")) {

                        insideProfile = false;
                    }

                    continue;
                }

                text.append(line).append("\n");
            }

            file.close();

            if (!replaced) {

                System.out.println("Perfil nao encontrado.");
                return false;
            }

            FileWriter writer = new FileWriter(fileName);

            writer.write(text.toString());

            writer.close();

            System.out.println("Perfil guardado com sucesso!");

            return true;

        }

        catch (IOException e) {

            System.out.println("Erro ao guardar perfil.");

            return false;
        }
    }

    // Ship placement
    public void placeShips(Scanner input) {

        System.out.println("\n" + name + " posicione os seus navios!");

        int[] shipSizes;

        if (board.getRows() == 7) {

            shipSizes = new int[]{5, 4, 3, 3, 2};

        } else if (board.getRows() == 10) {

            shipSizes = new int[]{5, 4, 4, 3, 3, 2, 2};

        } else {

            shipSizes = new int[]{5, 5, 4, 4, 3, 3, 3, 2, 2, 2};
        }

        for (int i = 0; i < shipSizes.length; i++) {

            boolean placed = false;

            while (!placed) {

                board.printBoard(false);

                int size = shipSizes[i];

                System.out.println("\nPosicione o navio de tamanho " + size);

                int row;

                while (true) {

                    System.out.print("Linha: ");

                    if (input.hasNextInt()) {

                        row = input.nextInt() - 1;

                        if (row >= 0 && row < board.getRows()) {
                            break;
                        }

                        System.out.println("Linha invalida.");

                    } else {

                        System.out.println("Linha invalida. Digite um numero.");
                        input.next();
                    }
                }

                int col;

                while (true) {

                    System.out.print("Coluna: ");

                    if (input.hasNextInt()) {

                        col = input.nextInt() - 1;

                        if (col >= 0 && col < board.getCols()) {
                            break;
                        }

                        System.out.println("Coluna invalida.");

                    } else {

                        System.out.println("Coluna invalida. Digite um numero.");
                        input.next();
                    }
                }

                boolean horizontal;

                while (true) {

                    System.out.print("Horizontal (true/false): ");
                    String directionInput = input.next();

                    if (directionInput.equalsIgnoreCase("true")) {

                        horizontal = true;
                        break;

                    } else if (directionInput.equalsIgnoreCase("false")) {

                        horizontal = false;
                        break;

                    } else {

                        System.out.println("Entrada invalida. Digite true ou false.");
                    }
                }

                Ship ship = new Ship(size, row, col, horizontal);

                if (board.placeShip(ship)) {

                    System.out.println("Navio posicionado!");
                    placed = true;

                } else {

                    System.out.println("Posicao invalida. Tente novamente.");
                }
            }
        }
    }

    // Attacking
    public boolean attack(Player enemy, Scanner input, Weapon weapon) {

        System.out.println("\nTurno de " + name);

       

        switch (weapon.getType()) {

            case NORMAL:
                return useNormalShot(enemy, input);

            case RADAR:
                return useRadar(enemy, input);

            case ARTILLERY:
                return useArtillery(enemy, input);

            case AIRSTRIKE:
                return useAirstrike(enemy, input);

            case EMP:
                enemy.setEmpDisabled(true);

                System.out.println("\nEMP ativado!");
                System.out.println(enemy.getName() +
                        " nao podera utilizar armas especiais no proximo turno.");

                return false;
        }

        return false;
    }

    private boolean useRadar(Player enemy, Scanner input) {
        enemy.getBoard().printBoard(true);

        System.out.print("Linha do radar: ");
        int row = input.nextInt() - 1;

        System.out.print("Coluna do radar: ");
        int col = input.nextInt() - 1;

        return enemy.getBoard().radarScan(row, col);
    }

    private boolean useArtillery(Player enemy, Scanner input) {
        enemy.getBoard().printBoard(true);

        System.out.print("Linha inicial: ");
        int row = input.nextInt() - 1;

        System.out.print("Coluna inicial: ");
        int col = input.nextInt() - 1;

        return enemy.getBoard().artillery(row, col);
    }

    private boolean useAirstrike(Player enemy, Scanner input) {
        enemy.getBoard().printBoard(true);

        System.out.println("1 - Linha");
        System.out.println("2 - Coluna");

        int option = input.nextInt();

        if (option == 1) {

            System.out.print("Linha: ");
            int row = input.nextInt() - 1;

            return enemy.getBoard().airstrikeRow(row);

        } else {

            System.out.print("Coluna: ");
            int col = input.nextInt() - 1;

            return enemy.getBoard().airstrikeColumn(col);
        }
    }  
    
    private boolean useNormalShot(Player enemy, Scanner input) {

        enemy.getBoard().printBoard(true);

        int row;
        int col;

        while (true) {

            System.out.print("Linha: ");

            if (input.hasNextInt()) {

                row = input.nextInt() - 1;

                if (row >= 0 && row < enemy.getBoard().getRows()) {
                    break;
                }

                System.out.println("Linha invalida.");

            } else {

                System.out.println("Linha invalida.");
                input.next();
            }
        }

        while (true) {

            System.out.print("Coluna: ");

            if (input.hasNextInt()) {

                col = input.nextInt() - 1;

                if (col >= 0 && col < enemy.getBoard().getCols()) {
                    break;
                }

                System.out.println("Coluna invalida.");

            } else {

                System.out.println("Coluna invalida.");
                input.next();
            }
        }

        return enemy.getBoard().attack(row, col);
    }
}