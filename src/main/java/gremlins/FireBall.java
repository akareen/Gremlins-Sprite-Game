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

    public boolean tick(TileGrid grid) {
        if (super.y % 20 == 0 && super.x % 20 == 0)
            if (isMovingIntoStoneWall(this.direction, grid))
                return true;
        changePosition();
        return false;
    }

    public void draw(PApplet app) {
        app.image(this.sprite, super.x, super.y);
    }

    // Returns true if the fireball is moving into a stone wall
    private boolean isMovingIntoStoneWall(int direction, TileGrid grid) {
        int yPos = (y / 20) + super.movementModifiers[direction][0];
        int xPos = (x / 20) + super.movementModifiers[direction][1];
        Tile futureTile = grid.getTile(yPos, xPos);
        if (!futureTile.isEmpty() && futureTile.getName().equals("brickwall")) {
            grid.setTile(yPos, xPos,
                    ObjectMaker.makeDestroyedWall(yPos * 20, xPos * 20));
            return true;
        }
        return futureTile.getName().equals("stonewall");
    }
}
