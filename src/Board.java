import java.util.ArrayList;

public class Board {

    private char[][] displayBoard = new char[7][9];
    private ArrayList<Ship> ships = new ArrayList<>();

    public Board() {
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

    // -----------------------------
    // SHIP PLACEMENT (OBJECT BASED)
    // -----------------------------
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

    // -----------------------------
    // ATTACK LOGIC (SHIP AWARE)
    // -----------------------------
    public boolean attack(int row, int col) {

        if (!isValid(row, col)) {
            System.out.println("Invalid coordinates.");
            return false;
        }

        // Already attacked
        if (displayBoard[row][col] == '*' || displayBoard[row][col] == 'O') {
            System.out.println("Already attacked.");
            return false;
        }

        Ship hitShip = getShipAt(row, col);

        if (hitShip != null) {

            hitShip.hit();
            displayBoard[row][col] = '*';

            System.out.println("Hit!");

            if (hitShip.isSunk()) {
                System.out.println("Ship sunk!");
            }

            return true;

        } else {

            displayBoard[row][col] = 'O';
            System.out.println("Miss!");
            return true;
        }
    }

    // -----------------------------
    // SHIP RESOLUTION
    // -----------------------------
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

    // -----------------------------
    // VICTORY CHECK
    // -----------------------------
    public boolean allShipsDestroyed() {

        for (Ship ship : ships) {
            if (!ship.isSunk()) {
                return false;
            }
        }

        return true;
    }

    // -----------------------------
    // DISPLAY
    // -----------------------------
    public void printBoard(boolean hideShips) {

        System.out.print("  ");

        for (int j = 0; j < displayBoard[0].length; j++) {
            System.out.print(j + " ");
        }

        System.out.println();

        for (int i = 0; i < displayBoard.length; i++) {

            System.out.print(i + " ");

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

    // -----------------------------
    // UTIL
    // -----------------------------
    private boolean isValid(int row, int col) {
        return row >= 0 && row < displayBoard.length &&
               col >= 0 && col < displayBoard[0].length;
    }

    public char getCell(int row, int col) {
        return displayBoard[row][col];
    }
}
