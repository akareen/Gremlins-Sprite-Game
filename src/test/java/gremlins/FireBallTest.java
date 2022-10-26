package gremlins;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FireBallTest {
    FireBall fireBall = ObjectMaker.makeFireBall(100, 100, 3);
    TileGrid grid = new TileGrid();

    @Test
    public void isMovingIntoFrozenWallTest() {

    }

    @Test
    public void getYPos() {
        fireBall.y = 100;
        fireBall.x = 100;
        fireBall.direction = 3;
        assertEquals(fireBall.getYPos(), (100 / 20) + 1);
    }

    @Test
    public void getXPos() {
        fireBall.y = 100;
        fireBall.x = 100;
        fireBall.direction = 1;
        assertEquals(fireBall.getXPos(), (100 / 20) + 1);
    }
}
