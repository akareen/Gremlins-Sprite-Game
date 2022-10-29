package gremlins;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * Extends the Tile class to represent a wall.
 */
public class Wall extends Tile {
    /**
     * The PImage of the wall.
     */
    private final PImage sprite;

    /**
     * Constructor for the Wall class.
     * @param y, the y coordinate of the wall.
     * @param x, the x coordinate of the wall.
     * @param sprite, the PImage of the wall.
     */
    public Wall(int y, int x, String name, PImage sprite) {
        super(y, x, name);
        this.sprite = sprite;
    }

    /**
     * Empty method of logic to be overwridden by subclasses.
     */
    public void tick() {}

    /**
     * Draws the wall.
     * @param app, the PApplet used to draw the wall.
     */
    public void draw(PApplet app) {
        app.image(this.sprite, super.getX(), super.getY());
    }
}