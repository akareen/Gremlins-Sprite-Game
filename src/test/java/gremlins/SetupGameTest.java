package gremlins;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import processing.core.PApplet;

import static org.junit.jupiter.api.Assertions.*;


public class SetupGameTest {
    public static App app;

    @BeforeAll
    public static void setupApp() {
        app = new App();
        PApplet.runSketch(new String[] {"App"}, app);
        app.setup();
        app.loop();
        app.delay(1000);
    }

    @Test
    public void loadLevelTest() {
        //Test setting values from the first level map
        assertEquals("level1.txt", app.layoutFile);
        assertEquals(0.3333, app.wizardCooldown);
        assertEquals(3.0, app.enemyCooldown);
        assertEquals(33, app.tileLetterConfiguration.length);
        assertEquals(36, app.tileLetterConfiguration[0].length);
        //3 frozen, 1 powerup, 4 gremlin, 1 wizard, 1 exit
        assertEquals(3, app.assetLocations.get("Frozenwall").size());
        assertEquals(1, app.assetLocations.get("Powerup").size());
        assertEquals(4, app.assetLocations.get("Gremlin").size());
        assertEquals(1, app.assetLocations.get("Wizard").size());
        assertEquals(1, app.assetLocations.get("Door").size());
        //check that the objects are loaded properly
        assertNotNull(app.wizard);
        assertEquals(4, app.gremlins.size());
        assertEquals(1, app.powerupTiles.size());
        assertEquals(0, app.fireBalls.size());
    }
}
