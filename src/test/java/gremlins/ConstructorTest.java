package gremlins;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class ConstructorTest {
    @Test
    public void constructorTest() {
        assertNotNull(new App());
        assertNotNull(new DestroyedWall(0,0,null,null));
        assertNotNull(new EmptyTile(9, 0, null));
        assertNotNull(new FireBall(0,0,0,null));
        assertNotNull(new Gremlin(0, 0, 0, null));
        assertNotNull(new PowerUpTile(0, 0, null, null));
        assertNotNull(new SlimeBall(0, 0, 0, null));
        assertNotNull(new Wizard(0, 0,0, null));
    }
}
