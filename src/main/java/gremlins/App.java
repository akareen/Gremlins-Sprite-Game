package gremlins;

import processing.core.PApplet;
import processing.data.JSONObject;
import processing.data.JSONArray;

import java.util.*;
import java.io.*;

public class App extends PApplet {
    //Final Size Values
    protected static final int WIDTH = 720;
    protected static final int HEIGHT = 720;
    private static final int FPS = 60;

    //Loaded data from the config file
    private final String configPath;
    Map<Integer, Map<String, String>> levelsMap;

    /* FOR ALL LEVELS */
    protected int totalNumLevels;
    protected int levelNum = 1;

    /* FOR THE CURRENT LEVEL */
    //Static
    protected String layoutFile;
    protected String[][] tileLetterConfiguration;
    protected Map<String, List<Integer[]>> assetLocations;
    protected int[] exitLocation;
    protected final int totalTimeFrozen = 6 * 60;

    //Dynamic
    protected int lives;
    protected int TOTALLIVES;
    protected Wizard wizard;
    protected List<Gremlin> gremlins;
    protected List<SlimeBall> slimeBalls;
    protected List<FireBall> fireBalls;
    protected List<PowerUpTile> powerupTiles;
    protected int currentTimeFrozen = 0;

    protected double wizardCooldown, enemyCooldown;
    protected TileGrid grid;


    public App() {
        this.configPath = "config.json";
    }

    /**
     * Initialise the setting of the window size.
     */
    public void settings() {
        size(WIDTH, HEIGHT);
    }

    /**
     * Load all resources such as images. Initialise the elements such as the player, enemies and map elements.
     */
    public void setup() {
        //Setup 60 frames a second
        frameRate(FPS);
        loadImages();
        readJSON();
        SetupGame.loadLevel(this, levelNum);
    }

    private void loadImages() {
        String[] imageNames = {"brickwall",  "brickwall_destroyed0", "brickwall_destroyed1", "brickwall_destroyed2",
                "brickwall_destroyed3", "door", "fireball", "frozenwall", "gremlin", "powerup", "slime", "stonewall", 
                "wizard0", "wizard1", "wizard2", "wizard3"
        };
        for (String name : imageNames)
            ObjectMaker.addToImageMap(name, loadImage(Objects.requireNonNull(
                    this.getClass().getResource(name + ".png"))
                    .getPath().replace("%20", " ")));
    }

    private void readJSON() {
        JSONObject config = loadJSONObject(new File(this.configPath));
        this.TOTALLIVES = (int) config.get("lives");
        this.lives = TOTALLIVES;

        JSONArray levels = (JSONArray) config.get("levels");
        Map<Integer, Map<String, String>> levelsMap = new HashMap<>();

        for (int i = 0; i < levels.size(); i++) {
            JSONObject level = (JSONObject) levels.get(i);
            Map<String, String> map = new HashMap<>();
            
            map.put("layout", (String) level.get("layout"));
            map.put("wizard_cooldown", "" + level.get("wizard_cooldown"));
            map.put("enemy_cooldown", "" + level.get("enemy_cooldown"));
            
            levelsMap.put(i + 1, map);
        }
        this.levelsMap = levelsMap;
        this.totalNumLevels = levelsMap.size();
    }

    /**
     * Receive key pressed signal from the keyboard.
     */
    public void keyPressed() {
        if (gameOver() && key == ENTER) { //Enter pressed
            this.lives = TOTALLIVES;
            this.levelNum = 1;
            SetupGame.loadLevel(this, levelNum);
        }
        int[] codes = {LEFT, RIGHT, UP, DOWN};
        int[] directions = {0, 1, 2, 3};
        for (int i = 0; i < codes.length; i++)
            if (keyCode == codes[i]) {
                wizard.setFutureMoving(true);
                wizard.setFutureDirection(directions[i]);
                break;
            }
        if (keyCode == 32) //SPACE BAR
            wizard.initiateShoot();
    }

    /**
     * Receive key released signal from the keyboard.
     */
    public void keyReleased(){
        int[] codes = {LEFT, RIGHT, UP, DOWN};
        for (int code : codes)
            if (keyCode == code) {
                wizard.setFutureMoving(false);
                break;
            }
    }

    private boolean gameOver() {
        return this.lives <= 0 || this.levelNum > this.totalNumLevels;
    }


    /**
     * Draw all elements in the game by current frame.
     */
    public void draw() {
        if (this.levelNum > totalNumLevels)
            Draw.drawVictory(this);
        else if (this.lives <= 0)
            Draw.drawGameOver(this);
        else {
            GameLogic.gameExecution(this);
            Draw.drawGame(this);
            if (GameLogic.checkWin(this)) {
                this.levelNum++;
                if (this.levelNum <= totalNumLevels)
                    SetupGame.loadLevel(this, levelNum);
            }
        }
    }


    public static void main(String[] args) {
        PApplet.main("gremlins.App");
    }
}
