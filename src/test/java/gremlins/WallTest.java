package gremlins;

import processing.core.PApplet;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WallTest {
    Wall wall = new Wall(0, 10, "test", null);

    @Test
    public void constructorTest() {
        assertNotNull(wall);
    }

    @Test
    public void tickTest() {
        wall.tick();
    }

    public void drawTest() {

    }
}
