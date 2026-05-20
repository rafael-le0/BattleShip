public class Board {

    char[][] board = new char[7][9];

    // Constructor
    public Board() {

        initializeBoard();
    }

    // Fill board with water
    public void initializeBoard() {

        for (int i = 0; i < board.length; i++) {

            for (int j = 0; j < board[i].length; j++) {

                board[i][j] = '~';
            }
        }
    }

    // Place ship
    public boolean placeShip(int row, int col) {

        // Bounds check
        if (row < 0 || row >= board.length ||
            col < 0 || col >= board[0].length) {

            return false;
        }

        // Prevent overlap
        if (board[row][col] != '~') {

            return false;
        }

        board[row][col] = '@';

        return true;
    }

    // Attack logic
    public boolean attack(int row, int col) {

        // Bounds check
        if (row < 0 || row >= board.length ||
            col < 0 || col >= board[0].length) {

            System.out.println("Invalid coordinates.");
            return false;
        }

        // Ship hit
        if (board[row][col] == '@') {

            board[row][col] = '*';

            System.out.println("Hit!");

            return true;
        }

        // Water hit
        else if (board[row][col] == '~') {

            board[row][col] = 'O';

            System.out.println("Miss!");

            return true;
        }

        // Already attacked
        else {

            System.out.println("Position already attacked.");

            return false;
        }
    }

    // Print board
    public void printBoard(boolean hideShips) {

        System.out.print("  ");

        for (int j = 0; j < board[0].length; j++) {

            System.out.print(j + " ");
        }

        System.out.println();

        for (int i = 0; i < board.length; i++) {

            System.out.print(i + " ");

            for (int j = 0; j < board[i].length; j++) {

                char value = board[i][j];

                // Hide enemy ships
                if (hideShips && value == '@') {

                    System.out.print("~ ");
                }

                else {

                    System.out.print(value + " ");
                }
            }

            System.out.println();
        }
    }

    // Check victory
    public boolean allShipsDestroyed() {

        for (int i = 0; i < board.length; i++) {

            for (int j = 0; j < board[i].length; j++) {

                if (board[i][j] == '@') {

                    return false;
                }
            }
        }

        return true;
    }

    // Get cell
    public char getCell(int row, int col) {

        return board[row][col];
    }
}
