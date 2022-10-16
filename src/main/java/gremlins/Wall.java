package gremlins;

import processing.core.PApplet;
import processing.core.PImage;

public class Wall extends Tile {
    private final PImage sprite;

    public Wall(int y, int x, String name, PImage sprite) {
        super(y, x, name);
        this.sprite = sprite;
    }

    public void tick() {}

    public void draw(PApplet app) {
        app.image(this.sprite, super.getX(), super.getY());
    }
}