package gremlins;

import processing.core.PApplet;
import processing.data.JSONObject;
import processing.data.JSONArray;

import java.util.*;
import java.io.*;

public class App extends PApplet {
    /**
     * The width of the window
     */
    protected static final int WIDTH = 720;
    /**
     * The height of the window
     */
    protected static final int HEIGHT = 720;
    /**
     * Frames per second
     */
    private static final int FPS = 60;


    /**
     * Path to the config.json file
     */
    private final String configPath;
    /**
     * For each level stores the level.txt file, the gremlin cooldown and the wizard cooldown
     */
    Map<Integer, Map<String, String>> levelsMap;


    /**
     * Total number of levels for the game
     */
    protected int totalNumLevels;
    /**
     * Current level of the game
     */
    protected int levelNum = 1;
    /**
     * The current level text file
     */
    protected String layoutFile;
    /**
     * A multi-dimensional string array that stores the layout of the current level.
     * Every row represents a line read from the level text file.
     * Every column represents a character in the line.
     */
    protected String[][] tileLetterConfiguration;
    /**
     * Stores the locations for all game objects.
     * The String is the name of the object.
     * The ArrayList is a list of all the locations of the object.
     */
    protected Map<String, List<Integer[]>> assetLocations;
    /**
     * The location of the exit door.
     */
    protected int[] exitLocation;
    /**
     * The total time in frames for the Gremlins to be frozen when the frozen tile powerup is activated.
     */
    protected final int totalTimeFrozen = 6 * 60;
    /**
     * The total number of lives in the game
     */
    protected int TOTALLIVES;


    /**
     * The current number of lives in the game
     */
    protected int lives;
    /**
     * The Wizard object
     */
    protected Wizard wizard;
    /**
     * The list of Gremlin objects
     */
    protected List<Gremlin> gremlins;
    /**
     * The list of SlimeBall objects
     */
    protected List<SlimeBall> slimeBalls;
    /**
     * The list of FireBall objects
     */
    protected List<FireBall> fireBalls;
    /**
     * The list of Powerup objects
     */
    protected List<PowerUpTile> powerupTiles;
    /**
     * The total amount of frames that the Gremlins have been frozen for
     */
    protected int currentTimeFrozen = 0;

    /**
     * The fireball cooldown for the Wizard
      */
    protected double wizardCooldown;
    /** The slimeball cooldown for all the Gremlins
     */
    protected double gremlinCooldown;
    /**
     * A 33x36 Tile array that stores tile objects for every 20x20 pixel tile in the game.
     */
    protected TileGrid grid;

    /**
     * The Constructor of the app class, which initializes the configPath.
     */
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

    /**
     * Loads all the images from the resources folder into the ObjectMaker class.
     */
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
