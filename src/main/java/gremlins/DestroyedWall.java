package gremlins;

import processing.core.PApplet;
import processing.core.PImage;


/**
 * A class representing a brick wall in a destruction sequence.
 */
public class DestroyedWall extends Tile {
    /**
     * A PImage array that stores the images for the destroyed wall.
     */
    private final PImage[] sprites;
    /**
     * The current image number of the destroyed wall.
     */
    protected int spriteNumber = -1;
    /**
     * Is true once all the images in the PImage array have been displayed
     */
    protected boolean fullyDestroyed = false;

    /**
     * Creates a new DestroyedWall object.
     * @param y, the y coordinate of the destroyed wall
     * @param x, the x coordinate of the destroyed wall
     * @param name, the name of the destroyed wall
     * @param sprites, the PImage array that stores the images for the destroyed wall
     */
    public DestroyedWall(int y, int x, String name, PImage[] sprites) {
        super(y, x, name);
        this.sprites = sprites;
    }

    /**
     * Increments the spriteNumber by 1.
     * If the spriteNumber is greater than the length of the PImage array, then the fullyDestroyed boolean is set to true.
     */
    public void tick() {
        this.spriteNumber++;
        if (this.spriteNumber > 3) {
            fullyDestroyed = true;
        }
    }

    /**
     * Draws the current image of the destroyed wall.
     * @param app, The main application class
     */
    public void draw(PApplet app) {
        app.image(this.sprites[spriteNumber], super.getX(), super.getY());
    }

    /**
     * Returns the fullyDestroyed boolean.
     * @return boolean, true if the destroyed wall is fully destroyed, false otherwise
     */
    public boolean isFullyDestroyed() {
        return fullyDestroyed;
    }

}
