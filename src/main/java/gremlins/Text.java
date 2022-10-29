package gremlins;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

/**
 * An interface used to display text onto the screen.
 */
public interface Text {
    /**
     * The starting point for text displayed on the bottom bar.
     */
    final static int Y = 690;

    /**
     * Draws the amount of lives left onto the bottom of left off the screen.
     * @param app, the PApplet used to draw the text.
     * @param wizardSprite, the sprite of the wizard.
     * @param currentLives, the amount of lives left.
     */
    static void drawLives(PApplet app, PImage wizardSprite, int currentLives) {
        String output = "Lives: ";
        PFont mono;
        mono = app.createFont("Georgia", 20);
        app.textFont(mono);
        app.fill(255,255,255);
        app.text(output, 20, Y);

        int xPos = 85;
        for (int i = 0; i < currentLives; i++) {
            app.image(wizardSprite, xPos, Y - 20);
            xPos += 20;
        }

    }


    /**
     * Draws the level number on the screen with the total number of levels.
     * @param app, the PApplet used to draw the text.
     * @param currentLevel, the current level number.
     * @param totalLevels, the total number of levels.
     */
    static void drawLevel(PApplet app, int currentLevel, int totalLevels) {
        String output = String.format("Level %d/%d\n", currentLevel, totalLevels);
        PFont mono;
        mono = app.createFont("Georgia", 20);
        app.textFont(mono);
        app.fill(255,255,255);
        app.text(output, 220, Y);
    }


    /**
     * Draws the victory message onto the screen prompting the user to press ENTER to start a new game.
     * @param app, the PApplet used to draw the text.
     */
    static void drawVictoryMessage(PApplet app) {
        PFont mono;
        mono = app.createFont("Georgia", 55);
        app.textFont(mono);
        app.fill(255,255,255); // White
        app.text("Game Over", 200, 300);
        app.fill(0,255,0); // Green
        app.text("YOU WIN!", 205, 370);
        app.fill(255,255,255); // White
        app.text("Press ENTER", 183, 440);
        app.fill(255,255,255); // White
        app.text("to play again", 185, 510);
    }

    /**
     * Draws the game over message onto the screen prompting the user to press ENTER to start a new game.
     * @param app, the PApplet used to draw the text.
     */
    static void drawGameOverMessage(PApplet app) {
        PFont mono;
        mono = app.createFont("Georgia", 55);
        app.textFont(mono);
        app.fill(255,255,255); // White
        app.text("Game Over", 200, 300);
        app.fill(255, 0, 0); // Red
        app.text("YOU LOSE!", 195, 370);
        app.fill(255,255,255); // White
        app.text("Press ENTER", 183, 440);
        app.fill(255,255,255); // White
        app.text("to play again", 185, 510);
    }


    /**
     * Draws the progress of the mana recharge for the wizards shoot fireball method.
     * @param app, the PApplet used to draw the mana bar.
     * @param rechargeTime, the time it takes to recharge the mana.
     * @param currentRecharge, the current recharge time.
     */
    static void drawManaBar(PApplet app, int rechargeTime, int currentRecharge) {
        //White Bar
        app.fill(255, 255, 255);
        app.rect(570, Y - 15, 100, 10);
        //Black Bar
        int result = (int) (((float) currentRecharge / rechargeTime) * 100);
        app.fill(0,0,0);
        app.rect(570, Y - 15, result,10);
    }
}
