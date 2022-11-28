package gremlins;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import processing.core.PApplet;

import static org.junit.jupiter.api.Assertions.*;


public class AppTest {
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
    public void testGameover() {
        app.lives = 0;
        assertTrue(app.gameOver());
        app.delay(1000);
        app.levelNum = 3;
        assertTrue(app.gameOver());
        app.lives = 3;
        app.levelNum = 3;
        assertTrue(app.gameOver());
        app.levelNum = 1;
    }

    @Test
    public void keyPressedTest() {
        //check restart of game
        app.keyCode = 32;
        app.keyPressed();
        assertTrue(app.wizard.shooting);

        int[][] cases = {{37, 0}, {39, 1}, {38, 2}, {40, 3}};
        for (int i = 0; i < cases.length; i++) {
            app.keyCode = cases[i][0];
            app.keyPressed();
            assertEquals(cases[i][1], app.wizard.futureDirection);
            assertTrue(app.wizard.futureMoving);
        }
    }

    @Test
    public void keyReleasedTest() {
        int[] cases = {37, 39, 38, 40};
        for (int i = 0; i < cases.length; i++) {
            app.keyCode = cases[i];
            app.keyReleased();
            assertFalse(app.wizard.futureMoving);
        }
    }
}
