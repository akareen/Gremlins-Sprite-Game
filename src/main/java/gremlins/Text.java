package gremlins;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

public class Text {
    private final static int Y = 690;

    public static void drawLives(PApplet app, PImage wizardSprite, int currentLives) {
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

    public static void drawLevel(PApplet app, int currentLevel, int totalLevels) {
        String output = String.format("Level %d/%d\n", currentLevel, totalLevels);
        PFont mono;
        mono = app.createFont("Georgia", 20);
        app.textFont(mono);
        app.fill(255,255,255);
        app.text(output, 220, Y);
    }


    public static void drawVictoryMessage(PApplet app) {
        PFont mono;
        mono = app.createFont("Georgia", 55);
        app.textFont(mono);
        app.fill(255,255,255); // White
        app.text("Game Over", 200, 300);
        app.fill(0,255,0); // Green
        app.text("YOU WIN!", 205, 370);
    }


    public static void drawGameOverMessage(PApplet app) {
        PFont mono;
        mono = app.createFont("Georgia", 55);
        app.textFont(mono);
        app.fill(255,255,255); // White
        app.text("Game Over", 200, 300);
        app.fill(255, 0, 0); // Red
        app.text("YOU LOSE!", 195, 370);
    }


    public static void drawManaBar(PApplet app, int rechargeTime, int currentRecharge) {
        //White Bar
        app.fill(255, 255, 255);
        app.rect(570, Y - 15, 100, 10);
        //Black Bar
        int result = (int) (((float) currentRecharge / rechargeTime) * 100);
        app.fill(0,0,0);
        app.rect(570, Y - 15, result,10);
    }
}
