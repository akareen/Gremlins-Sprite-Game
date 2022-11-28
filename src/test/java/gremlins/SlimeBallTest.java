package gremlins;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SlimeBallTest {
    SlimeBall slimeBall = ObjectMaker.makeSlimeBall(9,10,3);
    TileGrid grid = new TileGrid();

    @Test
    public void constructorTest() {
        assertNotNull(slimeBall);
    }

    @Test
    public void tickTest() {
        int[][] coordCases = {{9,10}, {20, 10}, {10, 20}, {0, 0}};
        for (int i = 0; i < coordCases.length; i++) {
            slimeBall.setY(coordCases[i][0]);
            slimeBall.setX(coordCases[i][1]);
            switch (i) {
                case 0:
                    assertFalse(slimeBall.tick(grid));
                    assertEquals(slimeBall.getY(), 10);
                    assertEquals(slimeBall.getX(), 10);
                    break;
                case 1:
                    assertFalse(slimeBall.tick(grid));
                    assertEquals(slimeBall.getY(), 21);
                    assertEquals(slimeBall.getX(), 10);
                    break;
                case 2:
                    assertFalse(slimeBall.movingIntoWall(grid));
                    assertFalse(slimeBall.tick(grid));
                    assertEquals(slimeBall.getY(), 11);
                    assertEquals(slimeBall.getX(), 20);
                    break;
                case 3:
                    grid.setTile(1, 0, ObjectMaker.makeStonewall(1,0));
                    assertTrue(slimeBall.tick(grid));
                    assertEquals(slimeBall.getY(), 0);
                    assertEquals(slimeBall.getX(), 0);
                    break;
            }
        }
    }

    @Test
    public void getYTest() {
        slimeBall.setY(110);
        assertEquals(110, slimeBall.getY());
    }

    @Test
    public void getXTest() {
        slimeBall.setX(210);
        assertEquals(210, slimeBall.getX());
    }

    @Test
    public void getCoordsTest() {
        slimeBall.setY(110);
        slimeBall.setX(210);
        int[] coords = slimeBall.getCoords();
        assertEquals(110, coords[0]);
        assertEquals(210, coords[1]);
    }

    @Test
    public void movingIntoWallTest() {
        slimeBall.setY(0);
        slimeBall.setX(0);
        slimeBall.setDirection(3);

        grid.setTile(1,0, ObjectMaker.makeBrickwall(1,0));
        assertTrue(slimeBall.movingIntoWall(grid));

        grid.setTile(1,0, ObjectMaker.makeStonewall(1,0));
        assertTrue(slimeBall.movingIntoWall(grid));

        grid.setTile(1,0, ObjectMaker.makeEmptyTile(1,0));
        assertFalse(slimeBall.movingIntoWall(grid));
    }

}
