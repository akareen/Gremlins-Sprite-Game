package gremlins;

/**
 * An abstract class that represents a movable object.
 */
public abstract class Movable extends GameObject {

    /**
     * The direction of the object. 0 = West, 1 = East, 2, = North, 3 = South
     */
    protected int direction;
    /**
     * Modifiers for the objects y and x coordinate depending on the direction it is facing.
     */
    static final protected int[][] movementModifiers = {
            {0, -1}, // West
            {0,  1}, // East
            {-1, 0}, // North
            {1,  0}  // South
    };

    /**
     * Constructor of the Movable object.
     * @param y, the y coordinate of the object.
     * @param x, the x coordinate of the object.
     * @param direction, the direction the object is facing.
     */
    public Movable(int y, int x, int direction) {
        super(y, x);
        this.direction = direction;
    }

    // Get and return the tile that the movable object is moving into with the current direction

    /**
     * Returns the tile that the movable object is moving into with the current direction.
     * @param grid, the grid of the game.
     * @return the tile that the movable object is moving into.
     */
    public Tile getFutureTile(TileGrid grid) {
        int yPos = (y / 20) + movementModifiers[this.direction][0];
        int xPos = (x / 20) + movementModifiers[this.direction][1];
        return grid.getTile(yPos, xPos);
    }

    /**
     * Modifies the position of y and x by the corresponding change in the movementModifiers array. For example
     * increasing the y coordinate by 1 when the direction is south (3).
     */
    protected void changePosition() {
        this.y += movementModifiers[this.direction][0];
        this.x += movementModifiers[this.direction][1];
    }


    /**
     * Evaluates if the movable object is moving into a wall.
     * @param grid, the grid of the game.
     * @return true if the object is moving into a brick, stone or frozen wall, false otherwise.
     */
    public boolean movingIntoWall(TileGrid grid) {
        Tile futureTile = this.getFutureTile(grid);
        return futureTile.getName().equals("brickwall")
                || futureTile.getName().equals("stonewall")
                || futureTile.getName().equals("frozenwall");
    }


    /**
     * Sets the direction of the movable object.
     * @param direction, the new direction of the object.
     */
    public void setDirection(int direction) {
        this.direction = direction;
    }
}
