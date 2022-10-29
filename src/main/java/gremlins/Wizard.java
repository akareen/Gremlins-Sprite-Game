package gremlins;

import processing.core.PApplet;
import processing.core.PImage;

import java.util.List;

/**
 * A class representing a wizard in the game.
 */
public class Wizard extends Movable {
    /**
     * An array of the images of the wizard.
     */
    private final PImage[] spriteArray;
    /**
     * The cooldown of fireball shooting in frames.
     */
    protected int cooldownFrames;
    /**
     * A boolean value representing if the wizard is moving or not.
     */
    private boolean moving = false;
    /**
     * The value to set the wizard's moving variable to when the wizard has been evaluated.
     */
    protected boolean futureMoving = false;
    /**
     * The value to set the wizard's direction variable to when the wizard has been evaluated.
     */
    protected int futureDirection;
    /**
     * The speed of the wizard in frames per second. Can be changed by collecting a power up.
     */
    protected int speed = 2; // Can be changed by Powerup
    /**
     * The time that the wizard has spent in cooldown in frames.
     */
    protected int timeInCooldown = 0;
    /**
     * A value representing if the fireball is on or off cooldown.
     */
    protected boolean onCooldown = false;
    /**
     * Used to initiate shooting by the keyboard.
     */
    protected boolean shooting = false;


    /**
     * Constructor for the Wizard class.
     * @param y, the y coordinate of the wizard.
     * @param x, the x coordinate of the wizard.
     * @param cooldownDouble, the cooldown of the fireball in seconds.
     * @param spriteArray, the array of images of the wizard.
     */
    public Wizard(int y, int x, double cooldownDouble, PImage[] spriteArray) {
        super(y, x, 3);
        this.cooldownFrames = (int) (cooldownDouble * 60);
        this.spriteArray = spriteArray;
        futureDirection = super.direction;
    }

    // Executed every frame
    public void tick(TileGrid grid, List<FireBall> fireBalls) {
        //Updating the Cooldowns
        updateCooldowns();
        //Shooting the Fireball
        shootFireball(fireBalls);
        //Repeat based on speed
        //When the Player is On a Block
        if (super.y % 20 == 0 && super.x % 20 == 0) {
            this.moving = futureMoving;
            super.direction = futureDirection;
            //LOGIC
            if (moving && super.movingIntoWall(grid))
                moving = false;
        }
        if (moving)
            super.changePosition();
    }

    /**
     * Draws the wizard on the screen.
     * @param app, the PApplet used to draw the wizard.
     */
    public void draw(PApplet app) {
        app.image(spriteArray[this.direction], super.x, super.y);
    }


    /**
     * Sets the shooting variable to true.
     */
    public void initiateShoot() {
        this.shooting = true;
    }

    /**
     * If the wizard is on cooldown, increments its timer. If it has spent the required time in cooldown,
     * takes it off cooldown.
     */
    protected void updateCooldowns() {
        if (this.onCooldown) {
            this.timeInCooldown++;
            if (this.timeInCooldown >= cooldownFrames) {
                this.timeInCooldown = 0;
                this.onCooldown = false;
            }
        }
    }


    /**
     * If the wizard is not on cooldown and shooting has been set to true by pressing the space bar,
     * creates a new fireball and adds it to the list of fireballs. The wizard is then put on cooldown.
     * @param fireBalls, the list of fireballs.
     */
    protected void shootFireball(List<FireBall> fireBalls) {
        if (this.shooting && !this.onCooldown) {
            fireBalls.add(ObjectMaker.makeFireBall(super.y, super.x, this.direction));
            this.onCooldown = true;
            this.shooting = false;
        }
        else if (this.shooting) {
            this.shooting = false;
        }
    }

    /**
     * Returns the cooldown of the wizards shooting method
     * @return the total cooldown of the wizards shooting method in frames.
     */
    public int getCooldownFrames() {
        return this.cooldownFrames;
    }

    /**
     * Returns the time that the wizard has spent in cooldown.
     * @return the time the wizard has spent in cooldown in frames.
     */
    public int getTimeInCooldown() {
        return this.timeInCooldown;
    }

    /**
     * Returns if the wizard is on cooldown or not.
     * @return true if the wizard is on cooldown, false otherwise.
     */
    public boolean isOnCooldown() {
        return this.onCooldown;
    }

    /**
     * Sets the speed of the wizard.
     * @param speed, the speed to set the wizard to.
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * Sets the future direction of the wizard.
     * @param directionChoice, the direction to set the wizard to in evaluation time.
     */
    public void setFutureDirection(int directionChoice) {
        this.futureDirection = directionChoice;
    }

    /**
     * Sets the future moving of the wizard.
     * @param futureMoving, the moving boolean to set the wizard to in evaluation time.
     */
    public void setFutureMoving(boolean futureMoving) {
        this.futureMoving = futureMoving;
    }

}
