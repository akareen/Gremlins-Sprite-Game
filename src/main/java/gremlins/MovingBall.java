package gremlins;

public class MovingBall extends Movable {
    int direction;
    final int SPEED = 4;

    public MovingBall(int y, int x, int direction) {
        super(y, x);
        this.direction = direction;
    }

    public void changePosition() {
        super.y += super.movementModifiers[this.direction][0];
        super.x += super.movementModifiers[this.direction][1];
    }

    public int getSPEED() {
        return this.SPEED;
    }

}
