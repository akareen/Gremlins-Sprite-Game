package gremlins;

import processing.core.PApplet;
import processing.core.PImage;

import java.util.List;
import java.util.Stack;

public class Wizard extends Movable {
    //Static
    private final PImage[] spriteArray;
    private final int cooldownFrames;
    //Dynamic
    private boolean moving = false;
    private boolean futureMoving = false;
    private Stack<Integer> directionStack = new Stack<>();
    private int speed = 2; // Can be changed by Powerup
    //Fireball Related
    private int timeInCooldown = 0;
    private boolean onCooldown = false;
    private boolean shooting = false;


    public Wizard(int y, int x, double cooldownDouble, PImage[] spriteArray) {
        super(y, x);
        super.direction = 3;
        this.cooldownFrames = (int) (cooldownDouble * 60);
        this.spriteArray = spriteArray;
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
                super.direction = popDirection();
                //LOGIC
                if (moving && super.movingIntoWall(this.direction, grid))
                    moving = false;
            }
            changePosition();
        }
    }

    public void draw(PApplet app) {
        app.image(spriteArray[this.direction], super.x, super.y);
    }

    // MOVEMENT METHODS
    // Moves the Wizard by one pixel if the movement is active
    protected void changePosition() {
        if (moving) {
            super.y += super.movementModifiers[super.direction][0];
            super.x += super.movementModifiers[super.direction][1];
        }
    }

    // SHOOTING METHODS
    public void initiateShoot() {
        this.shooting = true;
    }

    // If the Wizard is on cooldown increments its timer, if it has spent
    // required time in cooldown takes it off cooldown
    private void updateCooldowns() {
        if (this.onCooldown)
            this.timeInCooldown++;
        if (this.timeInCooldown >= cooldownFrames) {
            this.timeInCooldown = 0;
            this.onCooldown = false;
        }
    }

    // If able to shoots then it shoots a fireball from the wizard by
    // adding to the fireBalls list
    private void shootFireball(List<FireBall> fireBalls) {
        if (this.shooting && !this.onCooldown) {
            fireBalls.add(
                    ObjectMaker.makeFireBall(super.y, super.x, this.direction));
            this.onCooldown = true;
        }
        this.shooting = false;
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

    public void pushDirection(int directionChoice) {
        this.directionStack.push(directionChoice);
    }

    private int popDirection() {
        if (!this.directionStack.isEmpty())
            return this.directionStack.pop();
        return super.direction;
    }

    public void setFutureMoving(boolean futureMoving) {
        this.futureMoving = futureMoving;
    }

//    public void setFutureDirection(int futureDirection) {
//        this.futureDirection = futureDirection;
//    }
}
