package gremlins;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * Extends the MovingBall class to add the functionality of a fireball.
 */
public class FireBall extends MovingBall {
    /**
     * The image of the fireball
     */
    private final PImage sprite;

    /**
     * The constructor for the FireBall class.
     * @param y, the y coordinate of the fireball.
     * @param x, the x coordinate of the fireball.
     * @param direction, the direction the fireball is travelling.
     * @param sprite, the image of the fireball.
     */
    public FireBall(int y, int x, int direction, PImage sprite) {
        super(y, x, direction);
        this.direction = direction;
        this.sprite = sprite;
    }

    /**
     * Moves the fireball, when the fireball is on a whole tile it will evaluate
     * if it is moving into a wall and will return a value depending on what wall it is moving into.
     * @param grid, the grid of the game.
     * @return 0 if the fireball is moving into an empty tile or a tile in a destruction sequence,
     * 1 if the fireball will hit a brick or stone wall,
     * 2 if the fireball will hit a frozen wall.
     */
    public int tick(TileGrid grid) {
        if (super.y % 20 == 0 && super.x % 20 == 0) {
            int tickVal = evaluateMovement(grid);
            if (tickVal != 0) {
                return tickVal;
            }
        }
        changePosition();
        return 0;
    }

    /**
     * Draws the fireball object.
     * @param app, the main application.
     */
    public void draw(PApplet app) {
        app.image(this.sprite, super.x, super.y);
    }

    /**
     * Evaluates the movement of the fireball.
     * @param grid, the grid of tiles.
     * @return 0 if the fireball is still moving,
     * 1 if the fireball has hit a stone wall or brick wall,
     * 2 if the fireball has hit a frozen wall.
     */
    protected int evaluateMovement(TileGrid grid) {
        if (isMovingIntoFrozenWall(grid)) {
            return 2;
        }
        if (isMovingIntoBrickWall(grid) || isMovingIntoStoneWall(grid)) {
            return 1;
        }
        return 0;
    }

    /**
     * Checks if the fireball is moving into a brick wall. If it is sets the brick wall it is moving into
     * to a destroyed brick wall, thus starting the destruction sequence.
     * @param grid, the grid of tiles.
     * @return true if the fireball is moving into a brick wall,
     * false otherwise.
     */
    protected boolean isMovingIntoBrickWall(TileGrid grid) {
        if (grid.getTile(getYPos(), getXPos()).getName().equals("brickwall")) {
            grid.setTile(getYPos(), getXPos(), ObjectMaker.makeDestroyedWall(getYPos() * 20, getXPos() * 20));
            return true;
        }
        return false;
    }

    /**
     * Checks if the fireball is moving into a stone wall.
     * @param grid, the grid of tiles.
     * @return true if the fireball is moving into a stone wall,
     * false otherwise.
     */
    protected boolean isMovingIntoStoneWall(TileGrid grid) {
        return grid.getTile(getYPos(), getXPos()).getName().equals("stonewall");
    }

    /**
     * Checks if the fireball is moving into a frozen wall.
     * @param grid, the grid of tiles.
     * @return true if the fireball is moving into a frozen wall,
     * false otherwise.
     */
    protected boolean isMovingIntoFrozenWall(TileGrid grid) {
        if (grid.getTile(getYPos(), getXPos()).getName().equals("frozenwall")) {
            grid.setTile(getYPos(), getXPos(), ObjectMaker.makeDestroyedWall(getYPos() * 20, getXPos() * 20));
            return true;
        }
        return false;
    }

    /**
     * Returns the y coordinate of the fireball in grid tiles by dividing its location by 20.
     * @return an integer representing the y coordinate of the fireball in grid tiles.
     */
    protected int getYPos() {
        return (super.y / 20) + movementModifiers[super.direction][0];
    }

    /**
     * Returns the x coordinate of the fireball in grid tiles by dividing its location by 20.
     * @return an integer representing the x coordinate of the fireball in grid tiles.
     */
    protected int getXPos() {
        return (super.x / 20) + movementModifiers[super.direction][1];
    }
}
