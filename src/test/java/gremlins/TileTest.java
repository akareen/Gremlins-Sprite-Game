package gremlins;

import org.junit.jupiter.api.Test;
import processing.core.PApplet;

import static org.junit.jupiter.api.Assertions.*;

public class TileTest {
    Tile tile = ObjectMaker.makeEmptyTile(10,10);

    @Test
    public void constructorTest() {
        assertNotNull(tile);
    }

    @Test
    public void tickTest() {
        tile.tick();
    }


    @Test
    public void getCoordsTest() {
        tile.setY(100);
        tile.setX(110);
        int[] coords = tile.getCoords();
        assertEquals(coords[0], tile.getY());
        assertEquals(coords[1], tile.getX());
    }


    @Test
    public void getNameTest() {
        assertEquals("empty", tile.getName());
    }

    @Test
    public void isFullyDestroyedTest() {
        assertFalse(tile.isFullyDestroyed());
    }

    @Test
    public void isEmptyTest() {
        assertTrue(tile.isEmpty());
    }

    @Test
    public void setEmptyTest() {
        tile.setEmpty(false);
        assertFalse(tile.isEmpty());
    }
}
