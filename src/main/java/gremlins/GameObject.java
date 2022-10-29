package gremlins;

/**
 * The head class of the hierarchy, represents all game objects.
 */
public class GameObject {
    /**
     * The y coordinate of the object.
     */
    protected int y;
    /**
     * The x coordinate of the object.
     */
    protected int x;

    /**
     * Constructor of the GameObject object.
     * @param y, the y coordinate of the object.
     * @param x, the x coordinate of the object.
     */
    public GameObject(int y, int x) {
        this.y = y;
        this.x = x;
    }


    /**
     * Retrieves the coordinates of the object.
     * @return int[] with the y and x coordinates of the object.
     */
    public int[] getCoords() {
        return new int[] {this.y, this.x};
    }

    /**
     * Retrieves the y coordinate of the object.
     * @return int y coordinate of the object.
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the y coordinate of the object.
     * @param y, the new y coordinate of the object.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Retrieves the x coordinate of the object.
     * @return int x coordinate of the object.
     */
    public int getX() {
        return x;
    }

    /**
     * Sets the x coordinate of the object.
     * @param x, the new x coordinate of the object.
     */
    public void setX(int x) {
        this.x = x;
    }
}
