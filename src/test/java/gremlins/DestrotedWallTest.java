package gremlins;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DestrotedWallTest {
    DestroyedWall wall = ObjectMaker.makeDestroyedWall(10,10);

    @Test
    public void constructorTest() {
        assertNotNull(wall);
    }

    @Test
    public void tickTest() {
        wall.spriteNumber = -1;
        wall.tick();
        assertEquals(wall.spriteNumber, 0);
        assertFalse(wall.fullyDestroyed);

        wall.spriteNumber = 3;
        wall.tick();
        assertEquals(wall.spriteNumber, 4);
        assertTrue(wall.fullyDestroyed);
    }

    @Test
    public void isFullyDestroyedTest() {
        wall.fullyDestroyed = false;
        assertFalse(wall.isFullyDestroyed());
    }
}
