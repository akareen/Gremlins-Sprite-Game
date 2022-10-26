package gremlins;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TileGridTest {
    TileGrid grid = new TileGrid();

    @Test
    public void constructorTest() {
        assertNotNull(grid);
    }

    @Test
    public void respawnLocationTest() {
        for (int y = 0; y < 33; y++)
            for (int x = 0; x < 36; x++)
                grid.setTile(y, x, ObjectMaker.makeStonewall(y, x));
        grid.setTile(20, 20, ObjectMaker.makeEmptyTile(20, 20));
        Integer[] location = grid.respawnLocation(10, 10);
        assertEquals(location[0], 20);
        assertEquals(location[1], 20);
    }

    @Test
    public void vacantPositionsAwayFromPlayerTest() {
        int withinTen = 21;
        for (int i = 0; i < 2; i++) {
            int num = 19;
            while (num >= 1) {
                withinTen += num;
                num -= 2;
            }
        }

        grid.setTile(10, 12, ObjectMaker.makeBrickwall(10, 12));
        grid.setTile(20, 20, ObjectMaker.makeStonewall(20, 20));
        List<Integer[]> ls = grid.vacantPositionsAwayFromPlayer(10, 10);
        assertEquals(ls.size(), 33 * 36 - (withinTen + 1));
    }

    @Test
    public void withinTenSpacesTest() {
        int[][] ls = {{10, 10, 11, 11}, {10, 10, 19, 19}};
        for (int i = 0; i < ls.length; i++) {
            if (i == 0)
                assertTrue(grid.withinTenSpaces(ls[i][0], ls[i][1], ls[i][2], ls[i][3]));
            else
                assertFalse(grid.withinTenSpaces(ls[i][0], ls[i][1], ls[i][2], ls[i][3]));
        }
    }

    @Test
    public void withinRangeTest() {
        int[][] branches = {{-1, -1}, {-1, 0}, {33, 0}, {0, -1}, {0, 36}, {-1, -1}, {0, 0}};
        for (int i = 0; i < branches.length; i++) {
            if (i != 6)
                assertFalse(grid.withinRange(branches[i][0], branches[i][1]));
            else
                assertTrue(grid.withinRange(branches[i][0], branches[i][1]));
        }
    }

    @Test
    public void getAndSetTest() {
        Tile frozen = ObjectMaker.makeFrozenwall(10, 10);
        grid.setTile(10, 10, frozen);
        assertEquals(grid.getTile(10, 10), frozen);
    }

    @Test
    public void getYLengthTest() {
        assertEquals(33, grid.getYLength());
    }

    @Test
    public void getXLengthTest() {
        assertEquals(36, grid.getXLength());
    }
}
