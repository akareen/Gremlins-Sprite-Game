package gremlins;

/**
 * Extends the Tile class and represents an empty tile.
 */
public class EmptyTile extends Tile {
    /**
     * Creates a new EmptyTile object.
     * @param y, the y coordinate of the empty tile.
     * @param x, the x coordinate of the empty tile.
     * @param name, the name of the empty tile.
     */
    public EmptyTile(int y, int x, String name) {
        super(y, x, name);
        super.setEmpty(true);
    }
}
