package gremlins;

import processing.core.PApplet;

public class Tile {
    private int y; private int x;
    private final String name;
    private boolean empty = false;

    public Tile(int y, int x, String name) {
        this.y = y;
        this.x = x;
        this.name = name;
    }

    public void tick() {}

    public void draw(PApplet app) {}

    // Getters and Setters
    public int[] getCoords() {
        return new int[] {y, x};
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public String getName() {
        return name;
    }

    public boolean isFullyDestroyed() {
        return false;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }
}
