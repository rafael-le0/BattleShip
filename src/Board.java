import java.util.ArrayList;

public class Board {

    private char[][] displayBoard;
    private ArrayList<Ship> ships = new ArrayList<>();

    public Board(int rows, int cols) {

        displayBoard = new char[rows][cols];
        initializeBoard();
    }

    // Fill with water
    public void initializeBoard() {

        for (int i = 0; i < displayBoard.length; i++) {

            for (int j = 0; j < displayBoard[i].length; j++) {

                displayBoard[i][j] = '~';
            }
        }
    }

    public boolean placeShip(Ship ship) {

        int r = ship.getRow();
        int c = ship.getCol();

        // Check all tiles first
        for (int i = 0; i < ship.getSize(); i++) {

            int row = r + (ship.isHorizontal() ? 0 : i);
            int col = c + (ship.isHorizontal() ? i : 0);

            if (!isValid(row, col)) {
                return false;
            }

            if (displayBoard[row][col] != '~') {
                return false;
            }
        }

        // Place ship
        ships.add(ship);

        for (int i = 0; i < ship.getSize(); i++) {

            int row = r + (ship.isHorizontal() ? 0 : i);
            int col = c + (ship.isHorizontal() ? i : 0);

            displayBoard[row][col] = '@';
        }

        return true;
    }

    public boolean attack(int row, int col) {

        if (!isValid(row, col)) {

            System.out.println("Coordenadas invalidas.");
            return false;
        }

        // Already attacked
        if (displayBoard[row][col] == '*' ||
            displayBoard[row][col] == 'O' ||
            displayBoard[row][col] == '=') {

            System.out.println("Celula ja atacada.");
            return false;
        }

        Ship hitShip = getShipAt(row, col);

        if (hitShip != null) {

            hitShip.hit();

            displayBoard[row][col] = '*';

            System.out.println("Acerto!");

            // Ship sunk
            if (hitShip.isSunk()) {

                markSunkShip(hitShip);

                System.out.println("Navio naufragado!");
            }

            return true;

        } else {

            displayBoard[row][col] = 'O';

            System.out.println("Falhou!");

            return false;
        }
    }

    public boolean radarScan(int centerRow, int centerCol) {

        System.out.println("\nRadar:");

        for (int r = centerRow - 1; r <= centerRow + 1; r++) {

            for (int c = centerCol - 1; c <= centerCol + 1; c++) {

                if (!isValid(r, c)) {
                    continue;
                }

                if (getShipAt(r, c) != null) {

                    System.out.println("(" + (r + 1) + ", " + (c + 1) + ") -> NAVIO");

                } else {

                    System.out.println("(" + (r + 1) + ", " + (c + 1) + ") -> Agua");
                }
            }
        }

        return false;
    } 
    
    public boolean artillery(int row, int col) {

        boolean hit = false;

        for (int r = row; r < row + 2; r++) {

            for (int c = col; c < col + 2; c++) {

                if (isValid(r, c)) {

                    if (attack(r, c)) {

                        hit = true;
                    }
                }
            }
        }

        return hit;
    }

    public boolean airstrikeRow(int row) {

        boolean hit = false;

        for (int c = 0; c < getCols(); c++) {

            if (attack(row, c)) {

                hit = true;
            }
        }

        return hit;
    }

    public boolean airstrikeColumn(int col) {

        boolean hit = false;

        for (int r = 0; r < getRows(); r++) {

            if (attack(r, col)) {

                hit = true;
            }
        }

        return hit;
    }

    // Mark entire sunk ship with =
    private void markSunkShip(Ship ship) {

        int r = ship.getRow();
        int c = ship.getCol();

        for (int i = 0; i < ship.getSize(); i++) {

            int row = r + (ship.isHorizontal() ? 0 : i);
            int col = c + (ship.isHorizontal() ? i : 0);

            displayBoard[row][col] = '=';
        }
    }

    private Ship getShipAt(int row, int col) {

        for (Ship ship : ships) {

            int r = ship.getRow();
            int c = ship.getCol();

            for (int i = 0; i < ship.getSize(); i++) {

                int sr = r + (ship.isHorizontal() ? 0 : i);
                int sc = c + (ship.isHorizontal() ? i : 0);

                if (sr == row && sc == col) {
                    return ship;
                }
            }
        }

        return null;
    }

    public boolean allShipsDestroyed() {

        for (Ship ship : ships) {

            if (!ship.isSunk()) {
                return false;
            }
        }

        return true;
    }

    public void printBoard(boolean hideShips) {

        System.out.print("  ");

        for (int j = 0; j < displayBoard[0].length; j++) {

            System.out.print((j + 1) + " ");
        }

        System.out.println();

        for (int i = 0; i < displayBoard.length; i++) {

            System.out.print((i + 1) + " ");

            for (int j = 0; j < displayBoard[i].length; j++) {

                char value = displayBoard[i][j];

                if (hideShips && value == '@') {

                    System.out.print("~ ");

                } else {

                    System.out.print(value + " ");
                }
            }

            System.out.println();
        }
    }

    private boolean isValid(int row, int col) {

        return row >= 0 &&
               row < displayBoard.length &&
               col >= 0 &&
               col < displayBoard[0].length;
    }

    public char getCell(int row, int col) {

        return displayBoard[row][col];
    }

    // Return all ships on the board
    public ArrayList<Ship> getShips() {

        return ships;
    }

    // Board dimensions
    public int getRows() {

        return displayBoard.length;
    }

    public int getCols() {

        return displayBoard[0].length;
    }
}