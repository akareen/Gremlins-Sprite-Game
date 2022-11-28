package gremlins;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * A class extending Tile that represents a power up tile.
 */
public class PowerUpTile extends Tile {
    /**
     * The image of the power up tile.
     */
    private final PImage sprite;
    /**
     * How long it take for the power up to respawn in frames.
     */
    protected final int RESPAWN_TIME_FRAMES = 7 * 60;
    /**
     * How long the power up lasts in frames.
     */
    protected final int POWERUP_LENGTH = 7 * 60;
    /**
     * The amount of frames that has before activation.
     */
    protected int timeToActivation = 0;
    /**
     * The amount of time left until deactivation.
     */
    protected int timeToDeactivation = 0;
    /**
     * Boolean value representing if the power up is active or not.
     */
    protected boolean powerupActive = false;
    /**
     * A reference to the wizard.
     */
    protected Wizard wizRef;

    /**
     * The constructor for the power up tile.
     * @param y, the y coordinate of the power up tile.
     * @param x, the x coordinate of the power up tile.
     * @param name, the name of the power up tile.
     * @param sprite, the image of the power up tile.
     */
    public PowerUpTile(int y, int x, String name, PImage sprite) {
        super(y, x, name);
        this.sprite = sprite;
        super.setEmpty(true);
    }

    /**
     * If the power up is active once the active timer has passed it will deactivate the power up.
     * If the power up is not active and the activation timer has passed it will make the power up ready to be collected
     * once there is a collision between the wizard and the power up.
     */
    public void tick() {
        if (super.isEmpty()) {
            notSpawnedMethod();
        }
        if (this.powerupActive) {
            activeMethod();
        }
    }

    /**
     * Draws the power up tile if the power up is active.
     * @param app, the main application class.
     */
    public void draw(PApplet app) {
        if (!super.isEmpty()) {
            app.image(sprite, super.getX(), super.getY());
        }
    }

    /**
     * Increases the activation timer if it is greater than or equal to the respawn time then the
     * powerup is respawned.
     */
    private void notSpawnedMethod() {
        this.timeToActivation++;
        if (this.timeToActivation >= this.RESPAWN_TIME_FRAMES) {
            super.setEmpty(false);
            this.timeToActivation = 0;
        }
    }

    /**
     * Increases the deactivation timer if it is greater than or equal to the power up length then the
     * powerup is deactivated.
     */
    private void activeMethod() {
        this.timeToDeactivation++;
        if (this.timeToDeactivation >= this.POWERUP_LENGTH) {
            deactivatePowerup();
            this.powerupActive = false;
            this.timeToDeactivation = 0;
        }
    }

    /**
     * The method is called when the wizard makes contact with the power up.
     * The wizards speed is increased to 4 and the powerup is set to empty waiting for the respawn timer.
     * @param wizard
     */
    public void activatePowerup(Wizard wizard) {
        this.wizRef = wizard;
        wizRef.setSpeed(4);
        this.powerupActive = true;
        super.setEmpty(true);
    }

    /** Sets the wizards speed back to 2. */
    public void deactivatePowerup() {
        wizRef.setSpeed(2);
    }
}
