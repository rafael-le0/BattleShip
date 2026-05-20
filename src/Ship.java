public class Ship {

    private int size;
    private int health;

    private int row;
    private int col;

    private boolean horizontal;

    public Ship(int size, int row, int col, boolean horizontal) {

        this.size = size;
        this.health = size;

        this.row = row;
        this.col = col;

        this.horizontal = horizontal;
    }

    public void hit() {
        health--;
    }

    public boolean isSunk() {
        return health <= 0;
    }

    public int getSize() {
        return size;
    }

    public int getHealth() {
        return health;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isHorizontal() {
        return horizontal;
    }
}
