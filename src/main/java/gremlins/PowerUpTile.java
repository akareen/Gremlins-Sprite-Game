package gremlins;

import processing.core.PApplet;
import processing.core.PImage;

public class PowerUpTile extends Tile {
    // Static
    private final PImage image;
    private final int RESPAWN_TIME_FRAMES = 7 * 60;
    private final int POWERUP_LENGTH = 7 * 60;
    // Dynamic
    private int timeToActivation = 0;
    private int timeToDeactivation = 0;
    private boolean powerupActive = false;
    private Wizard wizRef;


    public PowerUpTile(int y, int x, String name, PImage image) {
        super(y, x, name);
        this.image = image;
        super.setEmpty(true);
    }

    public void tick() {
        if (super.isEmpty())
            notSpawnedMethod();
        if (this.powerupActive)
            activeMethod();
    }

    public void draw(PApplet app) {
        if (!super.isEmpty())
            app.image(image, super.getX(), super.getY());
    }

    private void notSpawnedMethod() {
        this.timeToActivation++;
        if (this.timeToActivation >= this.RESPAWN_TIME_FRAMES) {
            super.setEmpty(false);
            this.timeToActivation = 0;
        }
    }

    private void activeMethod() {
        this.timeToDeactivation++;
        if (this.timeToDeactivation >= this.POWERUP_LENGTH) {
            deactivatePowerup();
            this.powerupActive = false;
            this.timeToDeactivation = 0;
        }
    }

    public void activatePowerup(Wizard wizard) {
        this.wizRef = wizard;
        wizRef.setSpeed(4);
        this.powerupActive = true;
        super.setEmpty(true);
    }

    public void deactivatePowerup() {
        wizRef.setSpeed(2);
    }
}
