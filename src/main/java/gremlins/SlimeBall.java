package gremlins;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * Extends the MovingBall class to add the functionality of a slimeball.
 */
public class SlimeBall extends MovingBall {
    /**
     * The image of the slimeball.
     */
    private final PImage sprite;

    /**
     * Constructor of the SlimeBall object.
     * @param y, the y coordinate of the slimeball.
     * @param x, the x coordinate of the slimeball.
     * @param direction, the direction of the slimeball.
     * @param sprite, the image of the slimeball.
     */
    public SlimeBall(int y, int x, int direction, PImage sprite) {
        super(y, x, direction);
        this.sprite = sprite;
    }

    /**
     * The main logic for the Slimeball, if it is on a whole tile defined as when both the x and y
     * modulo 20 are 0, it will check if it is moving into a wall if it is it will return true.
     * Otherwise, it will change its position and return false.
     * @param grid, the grid of the game.
     * @return true if the slimeball is moving into a wall, false otherwise.
     */
    public boolean tick(TileGrid grid) {
        if (super.y % 20 == 0 && super.x % 20 == 0 && super.movingIntoWall(grid)) {
            return true;
        }
        changePosition();
        return false;
    }

    /**
     * Draws the slimeball.
     * @param app, the PApplet used to draw the slimeball.
     */
    public void draw(PApplet app) {
        app.image(this.sprite, super.x, super.y);
    }
}
