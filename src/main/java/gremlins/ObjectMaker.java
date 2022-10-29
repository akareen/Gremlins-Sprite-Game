package gremlins;

import processing.core.PImage;

import java.util.HashMap;
import java.util.Map;

/**
 * A class used to make game objects.
 */
public class ObjectMaker {
    /**
     * A Map that stores the image of each object with their corresponding name.
     */
    public static Map<String, PImage> spritesMap = new HashMap<>();

    /**
     * Adds the image of the object to the spritesMap.
     * @param name, the name of the object.
     * @param image, the image of the object.
     */
    public static void addToImageMap(String name, PImage image) {
        spritesMap.put(name, image);
    }

    /**
     * Returns the image of the object passed in.
     * @param imageName, the name of the object.
     * @return the PImage of the object.
     */
    public static PImage getImage(String imageName) {
        return spritesMap.get(imageName);
    }

    /**
     * Creates a new Brickwall object.
     * @param y, the y coordinate of the brickwall.
     * @param x, the x coordinate of the brickwall.
     * @return the new Brickwall object made with the parameters and the image stored in the spritesMap.
     */
    public static Wall makeBrickwall(int y, int x) {
        return new Wall(y, x, "brickwall", spritesMap.get("brickwall"));
    }

    /**
     * Creates a new Stonewall object.
     * @param y, the y coordinate of the stonewall.
     * @param x, the x coordinate of the stonewall.
     * @return the new Stonewall object made with the parameters and the image stored in the spritesMap.
     */
    public static Wall makeStonewall(int y, int x) {
        return new Wall(y, x, "stonewall", spritesMap.get("stonewall"));
    }

    /**
     * Creates a new Frozenwall object.
     * @param y, the y coordinate of the frozenwall.
     * @param x, the x coordinate of the frozenwall.
     * @return the new Frozenwall object made with the parameters and the image stored in the spritesMap.
     * */
    public static Wall makeFrozenwall(int y, int x) {return new Wall(y, x, "frozenwall", spritesMap.get("frozenwall"));
    }

    /**
     * Creates a new EmptyTile object.
     * @param y, the y coordinate of the emptytile.
     * @param x, the x coordinate of the emptytile.
     * @return the new EmptyTile object made with the parameters and the image stored in the spritesMap.
     */
    public static EmptyTile makeEmptyTile(int y, int x){
        return new EmptyTile(y, x, "empty");
    }

    /**
     * Creates a new Door object.
     * @param y, the y coordinate of the door.
     * @param x, the x coordinate of the door.
     * @return the new Door object made with the parameters and the image stored in the spritesMap.
     */
    public static Wall makeDoor(int y, int x) {
        return new Wall(y, x, "door", spritesMap.get("door"));
    }

    /**
     * Creates a new DestroyedWall object.
     * @param y, the y coordinate of the destroyedwall.
     * @param x, the x coordinate of the destroyedwall.
     * @return the new DestroyedWall object made with the parameters and the image stored in the spritesMap.
     */
    public static DestroyedWall makeDestroyedWall(int y, int x) {
        return new DestroyedWall(y, x, "destroyed-wall", new PImage[] {
                spritesMap.get("brickwall_destroyed0"), spritesMap.get("brickwall_destroyed1"),
                spritesMap.get("brickwall_destroyed2"), spritesMap.get("brickwall_destroyed3")});
    }

    /**
     * Creates a new PowerUp object.
     * @param y, the y coordinate of the powerup.
     * @param x, the x coordinate of the powerup.
     * @return the new PowerUp object made with the parameters and the image stored in the spritesMap.
     */
    public static PowerUpTile makePowerUpTile(int y, int x) {
        return new PowerUpTile(y, x, "powerup", spritesMap.get("powerup"));
    }

    /**
     * Creates a new Gremlin object.
     * @param y, the y coordinate of the gremlin.
     * @param x, the x coordinate of the gremlin.
     * @param cooldownDouble, the cooldown in double of the slime shooting rate.
     * @return the new Gremlin object made with the parameters and the image stored in the spritesMap.
     */
    public static Gremlin makeGremlin(int y, int x, double cooldownDouble) {
        return new Gremlin(y, x, cooldownDouble, spritesMap.get("gremlin"));
    }

    /**
     * Creates a new Wizard object.
     * @param y, the y coordinate of the wizard.
     * @param x, the x coordinate of the wizard.
     * @param cooldownDouble, the cooldown in double of the fireball recharge time.
     * @return the new Wizard object made with the parameters and the image stored in the spritesMap.
     */
    public static Wizard makeWizard(int y, int x, double cooldownDouble) {
        return new Wizard(y, x, cooldownDouble, new PImage[] {
                spritesMap.get("wizard0"), spritesMap.get("wizard1"),
                spritesMap.get("wizard2"), spritesMap.get("wizard3")});
    }

    /**
     * Creates a new SlimeBall object.
     * @param y, the y coordinate of the slimeBall.
     * @param x, the x coordinate of the slimeBall.
     * @param direction, the direction of the slimeBall.
     * @return the new Slime object made with the parameters and the image stored in the spritesMap.
     */
    public static SlimeBall makeSlimeBall(int y, int x, int direction) {
        return new SlimeBall(y, x, direction, spritesMap.get("slime"));
    }

    /**
     * Creates a new Fireball object.
     * @param y, the y coordinate of the fireball.
     * @param x, the x coordinate of the fireball.
     * @param direction, the direction of the fireball.
     * @return the new Fireball object made with the parameters and the image stored in the spritesMap.
     */
    public static FireBall makeFireBall(int y, int x, int direction) {
        return new FireBall(y, x, direction, spritesMap.get("fireball"));
    }
}
