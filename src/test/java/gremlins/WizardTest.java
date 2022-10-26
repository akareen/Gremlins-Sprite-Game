package gremlins;

import processing.core.PApplet;
import org.junit.jupiter.api.Test;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class WizardTest {
    Wizard wizard = new Wizard(0, 0, 0.0, null);

    @Test
    public void constructorTest() {
        assertNotNull(wizard);
    }


    @Test
    public void tickTest() {
        updateCooldownsTest();
        shootFireballTest();
    }

    @Test
    public void drawTest() {

    }


    @Test
    public void initiateShootTest() {
        wizard.shooting = false;
        wizard.initiateShoot();
        assertTrue(wizard.shooting);
    }

    @Test
    public void updateCooldownsTest() {
        wizard.onCooldown = true;
        wizard.timeInCooldown = 0;
        wizard.cooldownFrames = 2;
        wizard.updateCooldowns();
        assertEquals(wizard.timeInCooldown, 1);

        wizard.updateCooldowns();
        assertEquals(0, wizard.timeInCooldown);
        assertFalse(wizard.onCooldown);

        wizard.updateCooldowns();
        assertEquals(wizard.timeInCooldown, 0);
        assertFalse(wizard.onCooldown);

    }

    @Test
    public void shootFireballTest() {
        boolean[][] branches = {{true, false}, {true, true}, {false, false}, {false, true}};
        List<FireBall> fireBalls = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            wizard.shooting = branches[i][0];
            wizard.onCooldown = branches[i][1];
            if (i == 0) {
                wizard.shootFireball(fireBalls);
                assertEquals(1, fireBalls.size());
                assertEquals(new FireBall(wizard.y, wizard.x, wizard.direction, null), fireBalls.get(0));
                assertTrue(wizard.onCooldown);
                assertFalse(wizard.shooting);
            }
            else {
                wizard.shootFireball(fireBalls);
                assertEquals(1, fireBalls.size());
                assertEquals(branches[i][0], wizard.shooting);
                assertEquals(branches[i][1], wizard.onCooldown);
            }
        }
    }

    //Getter and setter testing
    @Test
    public void getCooldownFramesTest() {
        wizard.cooldownFrames = 10;
        assertEquals(wizard.getCooldownFrames(), 10);
    }

    @Test
    public void getTimeInCooldownTest() {
        wizard.timeInCooldown = 20;
        assertEquals(wizard.getTimeInCooldown(), 20);
    }

    @Test
    public void isOnCooldownTest() {
        wizard.onCooldown = false;
        assertFalse(wizard.isOnCooldown());
    }

    @Test
    public void setSpeedTest() {
        wizard.setSpeed(4);
        assertEquals(wizard.speed, 4);
    }

    @Test
    public void setFutureDirectionTest() {
        wizard.setFutureDirection(2);
        assertEquals(wizard.futureDirection, 2);
    }

    @Test
    public void setFutureMovingTest() {
        wizard.setFutureMoving(false);
        assertFalse(wizard.futureMoving);
    }
}
