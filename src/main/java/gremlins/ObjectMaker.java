package gremlins;

import processing.core.PImage;

import java.util.HashMap;
import java.util.Map;

public class ObjectMaker {
    public static Map<String, PImage> imageMap = new HashMap<>();

    /* Image Map Methods */
    public static void addToImageMap(String name, PImage image) {
        imageMap.put(name, image);
    }

    public static PImage getImage(String imageName) {
        return imageMap.get(imageName);
    }

    /* Making Tiles */
    public static Wall makeBrickwall(int y, int x) {
        return new Wall(y, x, "brickwall", imageMap.get("brickwall"));
    }

    public static Wall makeStonewall(int y, int x) {
        return new Wall(y, x, "stonewall", imageMap.get("stonewall"));
    }

    public static EmptyTile makeEmptyTile(int y, int x){
        return new EmptyTile(y, x, "empty");
    }

    public static Wall makeDoor(int y, int x) {
        return new Wall(y, x, "door", imageMap.get("door"));
    }

    public static DestroyedWall makeDestroyedWall(int y, int x) {
        return new DestroyedWall(y, x, "destroyed-wall", new PImage[] {
                imageMap.get("brickwall_destroyed0"),
                imageMap.get("brickwall_destroyed1"),
                imageMap.get("brickwall_destroyed2"),
                imageMap.get("brickwall_destroyed3")
        });
    }

    public static PowerUpTile makePowerUpTile(int y, int x) {
        return new PowerUpTile(y, x, "powerup", imageMap.get("powerup"));
    }

    /* Making Movables */
    public static Gremlin makeGremlin(int y, int x, double cooldownDouble) {
        return new Gremlin(y, x, cooldownDouble, imageMap.get("gremlin"));
    }

    public static Wizard makeWizard(int y, int x, double cooldownDouble) {
        return new Wizard(y, x, cooldownDouble, new PImage[] {
                imageMap.get("wizard0"), imageMap.get("wizard1"),
                imageMap.get("wizard2"), imageMap.get("wizard3")});
    }

    public static SlimeBall makeSlimeBall(int y, int x, int direction) {
        return new SlimeBall(y, x, direction, imageMap.get("slime"));
    }

    public static FireBall makeFireBall(int y, int x, int direction) {
        return new FireBall(y, x, direction, imageMap.get("fireball"));
    }
}
