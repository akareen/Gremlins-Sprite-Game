package gremlins;

public class MovingBall extends Movable {
    int direction;
    final int SPEED = 4;

    public MovingBall(int y, int x, int direction) {
        super(y, x, direction);
    }

    public int getSPEED() {
        return this.SPEED;
    }

}
