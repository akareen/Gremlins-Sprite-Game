package gremlins;

import processing.core.PApplet;
import processing.core.PImage;

public class DestroyedWall extends Tile {
    private final PImage[] sprites;
    int spriteNumber = -1;
    private boolean fullyDestroyed = false;

    public DestroyedWall(int y, int x, String name, PImage[] sprites) {
        super(y, x, name);
        this.sprites = sprites;
    }

    public void tick() {
        this.spriteNumber++;
        if (this.spriteNumber > 3)
            fullyDestroyed = true;
    }

    public void draw(PApplet app) {
        app.image(this.sprites[spriteNumber], super.getX(), super.getY());
    }

    public boolean isFullyDestroyed() {
        return fullyDestroyed;
    }

}
