package gremlins;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FireBallTest {
    FireBall fireBall = ObjectMaker.makeFireBall(100, 100, 3);
    TileGrid grid = new TileGrid();

    @Test
    public void tickTest() {
        int[][] falseBranches = {{1, 20}, {20, 1}, {1, 1}};
        for (int i = 0; i < falseBranches.length; i++) {
            fireBall.y = falseBranches[i][0];
            fireBall.x = falseBranches[i][1];
            assertEquals(fireBall.tick(grid), 0);
            assertEquals(fireBall.y, falseBranches[i][0] + 1);
        }
    }

    @Test
    public void isMovingIntoBrickWallTest() {
        fireBall.y = 220;
        fireBall.x = 220;
        fireBall.direction = 3;
        assertFalse(fireBall.isMovingIntoBrickWall(grid));

        grid.setTile(12,11, ObjectMaker.makeBrickwall(12,11));
        assertTrue(fireBall.isMovingIntoBrickWall(grid));
        assertEquals("destroyed-wall", grid.getTile(12, 11).getName());
    }

    @Test
    public void isMovingIntoFrozenWallTest() {
        fireBall.y = 220;
        fireBall.x = 220;
        fireBall.direction = 3;
        assertFalse(fireBall.isMovingIntoFrozenWall(grid));

        grid.setTile(12,11, ObjectMaker.makeFrozenwall(12,11));
        assertTrue(fireBall.isMovingIntoFrozenWall(grid));
        assertEquals("destroyed-wall", grid.getTile(12, 11).getName());
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
