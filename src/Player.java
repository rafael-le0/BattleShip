import java.util.Scanner;

public class Player {

    private String name;
    private Board board;

    public Player(String name) {

        this.name = name;
        this.board = new Board();
    }

    
    // Getters
    public String getName() {

        return name;
    }

    public Board getBoard() {

        return board;
    }

   //Ship placement
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

                    System.out.print("Linha: ");

                    if (input.hasNextInt()) {

                        row = input.nextInt() - 1;

                        // Coordinate bounds validation
                        if (row >= 0 && row < 7) {
                            break;
                        }

                        System.out.println("Linha inválida. Digite um valor de 1 a 7.");

                    } else {

                        System.out.println("Linha inválida. Digite um número.");
                        input.next();
                    }
                }

                //Column validation
                int col;

                while (true) {

                    System.out.print("Coluna: ");

                    if (input.hasNextInt()) {

                        col = input.nextInt() - 1;

                        // Coordinate bounds validation
                        if (col >= 0 && col < 9) {
                            break;
                        }

                        System.out.println("Coluna inválida. Digite um valor de 1 a 9.");

                    } else {

                        System.out.println("Coluna inválida. Digite um número.");
                        input.next();
                    }
                }

                //Horizontal Validation
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

                        System.out.println("Entrada inválida. Digite true ou false.");
                    }
                }

                Ship ship = new Ship(size, row, col, horizontal);

                if (board.placeShip(ship)) {

                    System.out.println("Navio posicionado!");
                    placed = true;

                } else {

                    System.out.println("Posição inválida. Tente novamente.");
                }
            }
        }
    }

   //Attacking
    public boolean attack(Player enemy, Scanner input) {

        System.out.println("\nTurno de " + name);

        while (true) {

            enemy.getBoard().printBoard(true);

            int row;
            int col;

            //Row vallidations
            while (true) {

                System.out.print("Linha do ataque: ");

                if (input.hasNextInt()) {

                    row = input.nextInt() - 1;

                    if (row >= 0 && row < 7) {
                        break;
                    }

                    System.out.println("Linha inválida. Digite um valor de 1 a 7.");

                } else {

                    System.out.println("Linha inválida. Digite um número.");
                    input.next();
                }
            }

            //Column validation
            while (true) {

                System.out.print("Coluna do ataque: ");

                if (input.hasNextInt()) {

                    col = input.nextInt() - 1;

                    if (col >= 0 && col < 9) {
                        break;
                    }

                    System.out.println("Coluna inválida. Digite um valor de 1 a 9.");

                } else {

                    System.out.println("Coluna inválida. Digite um número.");
                    input.next();
                }
            }

            // Prevent attacking same cell
            char cell = enemy.getBoard().getCell(row, col);

            if (cell == '*' || cell == 'O' || cell == '=') {

                System.out.println("Posição já atacada. Tente novamente.");
                continue;
            }

            return enemy.getBoard().attack(row, col);
        }
    }
}