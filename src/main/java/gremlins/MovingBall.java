package gremlins;

/**
 * Extends the Movable class to include the functionality of a moving ball.
 */
public abstract class MovingBall extends Movable {
    /**
     * The speed of the ball in frames per second.
     */
    final int SPEED = 4;

    /**
     * Constructor of the MovingBall object.
     * @param y, the y coordinate of the ball.
     * @param x, the x coordinate of the ball.
     * @param direction, the direction of the ball.
     */
    public MovingBall(int y, int x, int direction) {
        super(y, x, direction);
    }

    /**
     * Returns the speed of the ball.
     * @return the speed of the ball.
     */
    public int getSPEED() {
        return this.SPEED;
    }

    /**
     * An abstract method that checks if two MovingBalls are the same. Used for testing.
     * @param o, the object to be compared to.
     * @return true if the two objects are the same, false otherwise.
     */
    public abstract boolean equals(MovingBall o);
}
