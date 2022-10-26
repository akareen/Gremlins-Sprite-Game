package gremlins;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TileGridTest {
    TileGrid grid = new TileGrid();

    @Test
    public void constructorTest() {
        assertNotNull(grid);
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
