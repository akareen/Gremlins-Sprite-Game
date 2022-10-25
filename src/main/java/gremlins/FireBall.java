package gremlins;

import processing.core.PApplet;
import processing.core.PImage;

public class FireBall extends MovingBall {
    private final PImage sprite;

    public FireBall(int y, int x, int direction, PImage sprite) {
        super(y, x, direction);
        this.direction = direction;
        this.sprite = sprite;
    }

    public int tick(TileGrid grid) {
        if (super.y % 20 == 0 && super.x % 20 == 0) {
            int tickVal = evaluateMovement(grid);
            if (tickVal != 0)
                return  tickVal;
        }
        changePosition();
        return 0;
    }

    public void draw(PApplet app) {
        app.image(this.sprite, super.x, super.y);
    }

    // Returns true if the fireball is moving into a stone wall
    private int evaluateMovement(TileGrid grid) {
        if (isMovingIntoFrozenWall(grid))
            return 2;
        if (isMovingIntoBrickWall(grid) || isMovingIntoStoneWall(grid))
            return 1;
        return 0;
    }

    private boolean isMovingIntoBrickWall(TileGrid grid) {
        if (grid.getTile(getYPos(), getXPos()).getName().equals("brickwall")) {
            grid.setTile(getYPos(), getXPos(),
                    ObjectMaker.makeDestroyedWall(getYPos() * 20, getXPos() * 20));
            return true;
        }
        return false;
    }

    private boolean isMovingIntoStoneWall(TileGrid grid) {
        return grid.getTile(getYPos(), getXPos()).getName().equals("stonewall");
    }

    private boolean isMovingIntoFrozenWall(TileGrid grid) {
        if (grid.getTile(getYPos(), getXPos()).getName().equals("frozenwall")) {
            grid.setTile(getYPos(), getXPos(),
                    ObjectMaker.makeDestroyedWall(getYPos() * 20, getXPos() * 20));
            return true;
        }
        return false;
    }

    private int getYPos() {
        return (super.y / 20) + super.movementModifiers[super.direction][0];
    }

    private int getXPos() {
        return (super.x / 20) + super.movementModifiers[super.direction][1];
    }
}
