package gremlins;

import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Gremlin extends Movable {
    private final int cooldownFrames;
    private final PImage sprite;
    private int frames = 0;

    public Gremlin(int y, int x, double cooldownDouble, PImage sprite) {
        super(y, x);
        this.cooldownFrames = (int) (cooldownDouble * 60.0);
        this.sprite = sprite;
        super.direction = new Random().nextInt(4);
    }


    public void tick(TileGrid grid, List<SlimeBall> slimeBalls,
                     List<FireBall> fireBalls, Wizard wizard) {
        if (frames % cooldownFrames == 0)
            slimeBalls.add(
                    ObjectMaker.makeSlimeBall(super.y, super.x, this.direction));
        frames++;
        if (contactFireballs(fireBalls))
            respawn(grid, wizard);
        if (super.y % 20 == 0 && super.x % 20 == 0) {
            modifyMovement(grid);
        }
        super.changePosition();
    }


    public void draw(PApplet app) {
        app.image(this.sprite, super.x, super.y);
    }

    //Changes the movement of the gremlin if it is moving into a wall
    private void modifyMovement(TileGrid grid) {
        if (super.movingIntoWall(this.direction, grid))
            this.direction = newDirection(grid);
    }

    // Finds a new empty direction for the gremlin to move into, does not come
    // back same way unless it's the only valid
    private int newDirection(TileGrid grid) {
        int gridY = super.y / 20;
        int gridX = super.x / 20;
        List<Integer> validMoves = new ArrayList<>();
        for (int i = 0; i < super.movementModifiers.length; i++) {
            if (grid.withinRange(gridY + super.movementModifiers[i][0],
                    gridX + super.movementModifiers[i][1]))
                if (grid.getTile(gridY + super.movementModifiers[i][0],
                        gridX + super.movementModifiers[i][1]).isEmpty())
                    validMoves.add(i);
        }
        if (validMoves.size() == 1)
            return validMoves.get(0);
        if (validMoves.size() > 1) {
            validMoves.remove(Integer.valueOf(oppositeDirection(direction)));
            Random random = new Random();
            return validMoves.get(random.nextInt(validMoves.size()));
        }
        return this.direction;
    }

    // Turns North to South, East to West etc. Used to see same direction it came
    private int oppositeDirection(int direction) {
        int[] dir = {0, 1, 2, 3};
        int[] opp = {1, 0, 3, 2};
        for (int i = 0; i < dir.length; i++)
            if (dir[i] == direction)
                return opp[i];
        return 0;
    }

    // If the Gremlin hits a fireball remove the fireball from the array and
    // return true so that the gremlin can be deleted
    private boolean contactFireballs(List<FireBall> fireBalls) {
        for (FireBall fireBall : fireBalls)
            if (Helper.collisionDetector(super.getCoords(), fireBall.getCoords())) {
                fireBalls.remove(fireBall);
                return true;
            }
        return false;
    }

    // Respawns at a random location at least 10 tiles away from the wizard
    private void respawn(TileGrid grid, Wizard wizard) {
        int[] pos = wizard.getCoords();
        int yPos = pos[0] / 20; int xPos = pos[1] / 20;
        Integer[] newCoordinates = grid.respawnLocation(yPos, xPos);
        this.y = newCoordinates[0] * 20;
        this.x = newCoordinates[1] * 20;
    }
}
