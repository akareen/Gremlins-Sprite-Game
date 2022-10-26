package gremlins;

import processing.core.PApplet;
import processing.core.PImage;

public class SlimeBall extends MovingBall {
    private final PImage sprite;

    public SlimeBall(int y, int x, int direction, PImage sprite) {
        super(y, x, direction);
        this.sprite = sprite;
    }


    public boolean tick(TileGrid grid) {
        if (super.y % 20 == 0 && super.x % 20 == 0)
            if (super.movingIntoWall(grid))
                return true;
        changePosition();
        return false;
    }

    public void draw(PApplet app) {
        app.image(this.sprite, super.x, super.y);
    }

}
