package gremlins;

import processing.core.PImage;

import java.util.HashMap;
import java.util.Map;

public class ObjectMaker {
    public static Map<String, PImage> spritesMap = new HashMap<>();

    /* Image Map Methods */
    public static void addToImageMap(String name, PImage image) {
        spritesMap.put(name, image);
    }

    public static PImage getImage(String imageName) {
        return spritesMap.get(imageName);
    }

    /* Making Tiles */
    public static Wall makeBrickwall(int y, int x) {
        return new Wall(y, x, "brickwall", spritesMap.get("brickwall"));
    }

    public static Wall makeStonewall(int y, int x) {
        return new Wall(y, x, "stonewall", spritesMap.get("stonewall"));
    }

    public static Wall makeFrozenwall(int y, int x) {
        return new Wall(y, x, "frozenwall", spritesMap.get("frozenwall"));
    }

    public static EmptyTile makeEmptyTile(int y, int x){
        return new EmptyTile(y, x, "empty");
    }

    public static Wall makeDoor(int y, int x) {
        return new Wall(y, x, "door", spritesMap.get("door"));
    }

    public static DestroyedWall makeDestroyedWall(int y, int x) {
        return new DestroyedWall(y, x, "destroyed-wall", new PImage[] {
                spritesMap.get("brickwall_destroyed0"),
                spritesMap.get("brickwall_destroyed1"),
                spritesMap.get("brickwall_destroyed2"),
                spritesMap.get("brickwall_destroyed3")});
    }

    public static PowerUpTile makePowerUpTile(int y, int x) {
        return new PowerUpTile(y, x, "powerup", spritesMap.get("powerup"));
    }


    /* Making Movables */
    public static Gremlin makeGremlin(int y, int x, double cooldownDouble) {
        return new Gremlin(y, x, cooldownDouble, spritesMap.get("gremlin"));
    }

    public static Wizard makeWizard(int y, int x, double cooldownDouble) {
        return new Wizard(y, x, cooldownDouble, new PImage[] {
                spritesMap.get("wizard0"), spritesMap.get("wizard1"),
                spritesMap.get("wizard2"), spritesMap.get("wizard3")});
    }

    public static SlimeBall makeSlimeBall(int y, int x, int direction) {
        return new SlimeBall(y, x, direction, spritesMap.get("slime"));
    }

    public static FireBall makeFireBall(int y, int x, int direction) {
        return new FireBall(y, x, direction, spritesMap.get("fireball"));
    }
}
