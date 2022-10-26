package gremlins;

import processing.core.PApplet;
import processing.core.PImage;

import java.util.List;

public class Wizard extends Movable {
    //Static
    private final PImage[] spriteArray;
    protected int cooldownFrames;
    //Dynamic
    private boolean moving = false;
    protected boolean futureMoving = false;
    protected int futureDirection;
    protected int speed = 2; // Can be changed by Powerup
    //Fireball Related
    protected int timeInCooldown = 0;
    protected boolean onCooldown = false;
    protected boolean shooting = false;


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
        for (int i = 0; i < this.speed; i++) {
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
    }

    public void draw(PApplet app) {
        app.image(spriteArray[this.direction], super.x, super.y);
    }


    // SHOOTING METHODS
    public void initiateShoot() {
        this.shooting = true;
    }

    // If the Wizard is on cooldown increments its timer, if it has spent
    // required time in cooldown takes it off cooldown
    protected void updateCooldowns() {
        if (this.onCooldown) {
            this.timeInCooldown++;
            if (this.timeInCooldown >= cooldownFrames) {
                this.timeInCooldown = 0;
                this.onCooldown = false;
            }
        }
    }

    // If able to shoots then it shoots a fireball from the wizard by
    // adding to the fireBalls list
    protected void shootFireball(List<FireBall> fireBalls) {
        if (this.shooting && !this.onCooldown) {
            fireBalls.add(ObjectMaker.makeFireBall(super.y, super.x, this.direction));
            this.onCooldown = true;
            this.shooting = false;
        }
    }

    // GETTERS AND SETTERS
    public int getCooldownFrames() {
        return this.cooldownFrames;
    }

    public int getTimeInCooldown() {
        return this.timeInCooldown;
    }

    public boolean isOnCooldown() {
        return this.onCooldown;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setFutureDirection(int directionChoice) {
        this.futureDirection = directionChoice;
    }

    public void setFutureMoving(boolean futureMoving) {
        this.futureMoving = futureMoving;
    }

}
