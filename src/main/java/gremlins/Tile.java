package gremlins;

import processing.core.PApplet;

/**
 * A class representing a 20x20 tile in the game.
 */
public class Tile extends GameObject {
    /**
     * The name of the tile.
     */
    private final String name;
    /**
     * Whether the tile is empty or not.
     */
    private boolean empty = false;

    /**
     * Constructor for the Tile class.
     * @param y, the y coordinate of the tile.
     * @param x, the x coordinate of the tile.
     * @param name, the name of the tile.
     */
    public Tile(int y, int x, String name) {
        super(y, x);
        this.name = name;
    }

    /**
     * An empty method of logic to be overwridden by subclasses.
     */
    public void tick() {}

    /**
     * Draws the tile.
     * @param app, the PApplet used to draw the tile.
     */
    public void draw(PApplet app) {}


    /**
     * Returns the name of the tile.
     * @return the name of the tile.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns whether the tile is empty or not.
     * @return true if the tile is empty, false otherwise.
     */
    public boolean isFullyDestroyed() {
        return false;
    }

    /**
     * Returns whether the tile is empty or not.
     * @return true if the tile is empty, false otherwise.
     */
    public boolean isEmpty() {
        return empty;
    }

    /**
     * Sets the empty field of the tile.
     * @param empty, the new value of the empty field.
     */
    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    /**
     * Checks if two Tiles are the same. Used for testing.
     * @param o, the object to be compared to.
     * @return true if the two objects are the same, false otherwise.
     */
    public boolean equals(Object o) {
        if (!(o instanceof Tile))
            return false;
        Tile tile = (Tile) o;
        return tile.name.equals(this.name)
                && tile.y == this.y
                && tile.x == this.x;
    }
}
