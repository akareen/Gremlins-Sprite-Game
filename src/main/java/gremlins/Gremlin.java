package gremlins;

import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Extends the Movable class to add the functionality of a Gremlin.
 */
public class Gremlin extends Movable {
    /**
     * The cooldown of the shooting the slimeball in frames.
     */
    private final int cooldownFrames;
    /**
     * The image of the gremlin.
     */
    private final PImage sprite;
    /**
     * The amount of frames that has passed in total.
     */
    private int frames = 0;
    /**
     * A boolean value representing if the gremlin is moving or not.
     */
    protected boolean moving = true;
    /**
     * The speed of the gremlin.
     */
    protected int speed = 2;

    /**
     * The constructor for the Gremlin class.
     * @param y, the y coordinate of the gremlin.
     * @param x, the x coordinate of the gremlin.
     * @param cooldownDouble, the cooldown of the shooting the slimeball.
     * @param sprite, the image of the gremlin.
     */
    public Gremlin(int y, int x, double cooldownDouble, PImage sprite) {
        super(y, x, new Random().nextInt(4));
        this.cooldownFrames = (int) (cooldownDouble * 60.0);
        this.sprite = sprite;
    }

    /**
     * If the gremlin is moving and the shooting of slimeballs is off cooldown, it will shoot a slimeball.
     * If the gremlin has hit a fireball it will respawn the gremlin at least 10 tiles away from the wizard.
     * If the gremlin is moving and on a whole tile it will evaluate if it is moving into a wall and
     * adjust accordingly otherwise it will simply move at the movement speed of 1 frames per second.
     * @param grid, the grid of the game.
     * @param slimeBalls, the list of slimeballs.
     * @param fireBalls, the list of fireballs.
     * @param wizard, the wizard object.
     */
    public void tick(TileGrid grid, List<SlimeBall> slimeBalls, List<FireBall> fireBalls, Wizard wizard) {
        if (moving) {
            if (frames % cooldownFrames == 0)
                slimeBalls.add(ObjectMaker.makeSlimeBall(super.y, super.x, this.direction));
            frames++;
        }
        if (contactFireballs(fireBalls)) {
            respawn(grid, wizard);
        }
        if (moving) {
            if (super.y % 20 == 0 && super.x % 20 == 0) {
                modifyMovement(grid);
            }
            super.changePosition();
        }
    }

    /**
     * Draws the gremlin object.
     * @param app, the main application.
     */
    public void draw(PApplet app) {
        app.image(this.sprite, super.x, super.y);
    }

    /**
     * Changes the direction of the gremlin if it is moving into a wall.
     * @param grid, the grid of tiles.
     */
    private void modifyMovement(TileGrid grid) {
        if (super.movingIntoWall(grid)) {
            super.direction = newDirection(grid);
        }
    }

    /**
     * Finds a new empty direction for the gremlin to move into, does not come
     * back the same way unless it's the only valid.
     * @param grid, the grid of tiles.
     * @return the new direction for the gremlin to move into.
     */
    private int newDirection(TileGrid grid) {
        int gridY = super.y / 20;
        int gridX = super.x / 20;
        List<Integer> validMoves = new ArrayList<>();
        for (int i = 0; i < movementModifiers.length; i++) {
            if (grid.withinRange(gridY + movementModifiers[i][0], gridX + movementModifiers[i][1])
                && grid.getTile(gridY + movementModifiers[i][0], gridX + movementModifiers[i][1]).isEmpty())
                    validMoves.add(i);
        }
        if (validMoves.size() > 1) {
            validMoves.remove(Integer.valueOf(oppositeDirection()));
            return validMoves.get(new Random().nextInt(validMoves.size()));
        }
        return validMoves.get(0);
    }

    /**
     * Checks if the gremlin has been hit by a fireball. Removes the fireball from the fireballs list if it has
     * hit the gremlin.
     * @param fireBalls, the list of fireballs.
     * @return true if the gremlin has been hit by a fireball, false otherwise.
     */
    private boolean contactFireballs(List<FireBall> fireBalls) {
        for (FireBall fireBall : fireBalls) {
            if (GameLogic.hitboxOverlap(super.getCoords(), fireBall.getCoords())) {
                fireBalls.remove(fireBall);
                return true;
            }
        }
        return false;
    }

    /**
     * Respawns at a random location at least 10 tiles away from the wizard.
      */
    private void respawn(TileGrid grid, Wizard wizard) {
        Integer[] newCoordinates = grid.respawnLocation(wizard.getY() / 20, wizard.getX() / 20);
        this.y = newCoordinates[0] * 20;
        this.x = newCoordinates[1] * 20;
    }

    /**
     * Sets the moving boolean to the value passed into the function.
     * @param moving, the boolean value to set the moving boolean to.
     */
    public void setMoving(boolean moving) {
        this.moving = moving;
    }


    /**
     * Returns the opposite direction of the direction the gremlin is currently facing.
     * @return int, the opposite direction of the gremlin.
     */
    protected int oppositeDirection() {
        int[] dir = {0, 1, 2, 3};
        int[] opp = {1, 0, 3, 2};
        for (int i = 0; i < dir.length; i++) {
            if (dir[i] == super.direction) {
                return opp[i];
            }
        }
        return 0;
    }
}
