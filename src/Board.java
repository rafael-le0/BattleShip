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
    public void placeShip(int row, int col) {

        board[row][col] = '@';
    }

    // Attack logic
    public void attack(int row, int col) {

        // Ship hit
        if (board[row][col] == '@') {

            board[row][col] = '*';
            System.out.println("Hit!");
        }

        // Water hit
        else if (board[row][col] == '~') {

            board[row][col] = 'O';
            System.out.println("Miss!");
        }

        // Already attacked
        else {

            System.out.println("Position already attacked.");
        }
    }

    // Print board
    public void printBoard() {

        for (int i = 0; i < board.length; i++) {

            for (int j = 0; j < board[i].length; j++) {

                System.out.print(board[i][j] + " ");
            }

            System.out.println();
        }
    }
}
