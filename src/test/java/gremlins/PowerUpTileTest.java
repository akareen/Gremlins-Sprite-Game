package gremlins;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PowerUpTileTest {
    PowerUpTile power = ObjectMaker.makePowerUpTile(10, 10);
    Wizard wizref = ObjectMaker.makeWizard(10, 10, 0);

    @Test
    public void tickTest() {
        power.timeToActivation = power.RESPAWN_TIME_FRAMES - 2;
        power.tick();
        assertEquals(power.timeToActivation, power.RESPAWN_TIME_FRAMES - 1);

        power.tick();
        assertEquals(power.timeToActivation, 0);
        assertFalse(power.isEmpty());

        power.activatePowerup(wizref);
        power.timeToDeactivation = power.POWERUP_LENGTH - 2;
        power.tick();
        assertEquals(power.timeToDeactivation, power.POWERUP_LENGTH - 1);

        power.tick();
        assertFalse(power.powerupActive);
        assertEquals(power.timeToDeactivation, 0);
    }

    @Test
    public void activatePowerupTest() {
        power.activatePowerup(wizref);
        assertEquals(wizref.speed, 4);
        assertTrue(power.powerupActive);
    }

    @Test
    public void deactivatePowerupTest() {
        power.wizRef = wizref;
        power.deactivatePowerup();
        assertEquals(power.wizRef.speed, 2);
    }
}
