import java.util.Scanner;

public class Player {

    private String name;
    private Board board;

    public Player(String name) {

        this.name = name;
        this.board = new Board();
    }

    // -----------------------------
    // GETTERS
    // -----------------------------
    public String getName() {

        return name;
    }

    public Board getBoard() {

        return board;
    }

    // -----------------------------
    // SHIP PLACEMENT
    // -----------------------------
    public void placeShips(Scanner input) {

        System.out.println("\n" + name + " posicione os seus navios!");

        // Fixed Battleship ship sizes
        int[] shipSizes = {5, 4, 3, 3, 2};

        for (int i = 0; i < shipSizes.length; i++) {

            boolean placed = false;

            while (!placed) {

                // Show player's board while placing ships
                board.printBoard(false);

                int size = shipSizes[i];

                System.out.println("\nPosicione o navio de tamanho " + size);

                // -----------------------------
                // ROW VALIDATION
                // -----------------------------
                int row;

                while (true) {

                    System.out.print("Fila: ");

                    if (input.hasNextInt()) {

                        row = input.nextInt() - 1;

                        // Coordinate bounds validation
                        if (row >= 0 && row < 7) {
                            break;
                        }

                        System.out.println("Fila inválida. Use um valor de 1 à 7.");

                    } else {

                        System.out.println("Fila inválida. Ponha um número.");
                        input.next();
                    }
                }

                // -----------------------------
                // COLUMN VALIDATION
                // -----------------------------
                int col;

                while (true) {

                    System.out.print("Coluna: ");

                    if (input.hasNextInt()) {

                        col = input.nextInt() - 1;

                        // Coordinate bounds validation
                        if (col >= 0 && col < 9) {
                            break;
                        }

                        System.out.println("Coluna inválida. Use um valor de 1 à 9.");

                    } else {

                        System.out.println("Coluna inválida. Ponha um número.");
                        input.next();
                    }
                }

                // -----------------------------
                // HORIZONTAL VALIDATION
                // -----------------------------
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

                        System.out.println("Entrada inválida. Digite 'true' ou 'false'.");
                    }
                }

                Ship ship = new Ship(size, row, col, horizontal);

                if (board.placeShip(ship)) {

                    System.out.println("Navio posicionado!");
                    placed = true;

                } else {

                    System.out.println("Opção inválida. Tente novamente.");
                }
            }
        }
    }

    // -----------------------------
    // ATTACK
    // -----------------------------
    public boolean attack(Player enemy, Scanner input) {

        System.out.println("\n" + "Vez de" + name);

        enemy.getBoard().printBoard(true);

        int row;
        int col;

        // Keep asking until BOTH coordinates are valid
        while (true) {

            // -----------------------------
            // ROW VALIDATION
            // -----------------------------
            while (true) {

                System.out.print("Atacar fila: ");

                if (input.hasNextInt()) {

                    row = input.nextInt() - 1;

                    if (row >= 0 && row < 7) {
                        break;
                    }

                    System.out.println("Fila inválida. Use um valor de 1 à 7.");

                } else {

                    System.out.println("Fila inválida. Ponha um número.");
                    input.next();
                }
            }

            // -----------------------------
            // COLUMN VALIDATION
            // -----------------------------
            while (true) {

                System.out.print("Atacar coluna: ");

                if (input.hasNextInt()) {

                    col = input.nextInt() - 1;

                    if (col >= 0 && col < 9) {
                        break;
                    }

                    System.out.println("Coluna inválida. Use um valor de 1 à 9.");

                } else {

                    System.out.println("Coluna inválida. Ponha um número.");
                    input.next();
                }
            }

            // Attempt attack
            boolean result = enemy.getBoard().attack(row, col);

            // If position was already attacked,
            // ask again instead of losing turn
            char cell = enemy.getBoard().getCell(row, col);

            if (cell == '*' || cell == 'O' || cell == '=') {

                return result;
            }

            return result;
        }
    }
}