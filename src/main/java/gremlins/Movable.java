package gremlins;

public class Movable {
    protected int y; protected int x;
    protected int direction = 1;  //0 = West, 1 = East, 2, = North, 3 = South
    protected int[][] movementModifiers = {
            {0, -1}, // West
            {0,  1}, // East
            {-1, 0}, // North
            {1,  0}  // South
    };

    public Movable(int y, int x) {
        this.y = y;
        this.x = x;
    }

    // Get and return the tile that the movable object is moving into with the current direction
    public Tile getFutureTile(int direction, TileGrid grid) {
        int yPos = (y / 20) + this.movementModifiers[direction][0];
        int xPos = (x / 20) + this.movementModifiers[direction][1];
        return grid.getTile(yPos, xPos);
    }

    // Modify the position of y and x by the corresponding change in the movementModifiers array
    protected void changePosition() {
        this.y += this.movementModifiers[this.direction][0];
        this.x += this.movementModifiers[this.direction][1];
    }

    // Returns true if moving into a brick or stonewall
    public boolean movingIntoWall(int direction, TileGrid grid) {
        Tile futureTile = this.getFutureTile(direction, grid);
        return futureTile.getName().equals("brickwall")
                || futureTile.getName().equals("stonewall");
    }

    public int[] getCoords() {
        return new int[] {this.y, this.x};
    }

    public int getY() {
        return this.y;
    }

    public int getX() {
        return this.x;
    }
}
