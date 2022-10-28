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
    private Map<Integer, Map<String, String>> levelsMap;

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
        loadLevel(levelNum);
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
        this.lives = (int) config.get("lives");

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


    // Loads
    private void loadLevel(int levelNum) {
        setValuesFromMap(levelNum);
        readLevelFile(this.layoutFile);
        createAssetLocations(this.tileLetterConfiguration);
        loadObjects();
    }

    private void setValuesFromMap(int levelNum) {
        this.layoutFile = this.levelsMap.get(levelNum).get("layout");
        this.wizardCooldown = Double.parseDouble(this.levelsMap.get(levelNum).get("wizard_cooldown"));
        this.enemyCooldown = Double.parseDouble(this.levelsMap.get(levelNum).get("enemy_cooldown"));
    }

    private void readLevelFile(String levelName) {
        File fileName = new File(levelName);
        try {
            try (Scanner scanner = new Scanner(fileName)) {
                String[][] configArray = new String[33][36];
                int y = 0;
                while (scanner.hasNextLine()) {
                    configArray[y] = scanner.nextLine().split("");
                    y += 1;
                }
                this.tileLetterConfiguration = configArray;
            }
        } catch (Exception e) {
            System.out.println("Incorrect config file");
        }
    }

    private void createAssetLocations(String[][] configFile) {
        this.assetLocations = makeDefaultAssetLocations();
        String[] ls = {"Wizard", "Brickwall", "Stonewall", "Gremlin", "Door", "Powerup", "Frozenwall"};
        String[] codes = {"W", "B", "X", "G", "E", "P", "F"};
        this.grid = new TileGrid();
        for (int y = 0; y < configFile.length; y++)
            for (int x = 0; x < configFile[y].length; x++) {
                for (int i = 0; i < codes.length; i++)
                    if (configFile[y][x].equals(codes[i])) {
                        if (configFile[y][x].equals("E"))
                            exitLocation = new int[] {y, x};
                        assetLocations.get(ls[i]).add(new Integer[] {y, x});
                        break;
                    }
                }
    }

    protected void loadObjects() {
        //Make Slimeballs
        this.slimeBalls = new ArrayList<>();
        this.fireBalls   = new ArrayList<>();
        //Make Wizard
        Integer[] wizardCoords = assetLocations.get("Wizard").get(0);
        this.wizard = ObjectMaker.makeWizard(wizardCoords[0] * 20, wizardCoords[1] * 20, wizardCooldown);
        //Make Gremlins
        this.gremlins = new ArrayList<>();
        for (Integer[] ls : assetLocations.get("Gremlin"))
            this.gremlins.add(ObjectMaker.makeGremlin(ls[0] * 20, ls[1] * 20, enemyCooldown));
        //Make BrickWalls
        for (Integer[] ls : assetLocations.get("Brickwall"))
            this.grid.setTile(ls[0], ls[1], ObjectMaker.makeBrickwall(ls[0] * 20, ls[1] * 20));
        //Make StoneWalls
        for (Integer[] ls : assetLocations.get("Stonewall"))
            this.grid.setTile(ls[0], ls[1], ObjectMaker.makeStonewall(ls[0] * 20, ls[1] * 20));
        //Make Frozenwalls
        for (Integer[] ls : assetLocations.get("Frozenwall"))
            this.grid.setTile(ls[0], ls[1], ObjectMaker.makeFrozenwall(ls[0] * 20, ls[1] * 20));

        // Make Powerup
        this.powerupTiles = new ArrayList<>();
        for (Integer[] ls : assetLocations.get("Powerup")) {
            PowerUpTile powerup = ObjectMaker.makePowerUpTile(ls[0] * 20, ls[1] * 20);
            this.powerupTiles.add(powerup);
            this.grid.setTile(ls[0], ls[1], powerup);
        }
        //Make Door
        Integer[] ls = assetLocations.get("Door").get(0);
        this.grid.setTile(ls[0], ls[1], ObjectMaker.makeDoor(ls[0] * 20, ls[1] * 20));
    }


    /**
     * Receive key pressed signal from the keyboard.
     */
    public void keyPressed() {
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


    /**
     * Draw all elements in the game by current frame.
     */
    public void draw() {
        if (this.levelNum > totalNumLevels)
            Draw.drawVictory(this);
        else if (this.lives <= 0)
            Draw.drawGameOver(this);
        else {
            Draw.drawBackground(this);
            evaluatePowerups();
            Draw.drawTiles(this);
            Draw.drawWizard(this);
            Draw.drawGremlins(this);
            Draw.drawSlimeBalls(this);
            Draw.drawFireBalls(this);
            Draw.drawTextField(this);
            Draw.drawManaBar(this);
            if (checkWin()) {
                this.levelNum++;
                if (this.levelNum <= totalNumLevels)
                    loadLevel(levelNum);
            }
        }
    }


    private void evaluatePowerups() {
        for (PowerUpTile powerup : this.powerupTiles) {
            if (!powerup.isEmpty() && Helper.collisionDetector(
                    powerup.getCoords(), this.wizard.getCoords())) {
                powerup.activatePowerup(this.wizard);
            }
        }
    }



    boolean fireSlimeCollision(SlimeBall slime) {
        boolean collision = false;
        List<FireBall> toRemove = new ArrayList<>();
        for (FireBall fireBall : this.fireBalls)
            if (Helper.collisionDetector(fireBall.getCoords(), slime.getCoords())) {
                toRemove.add(fireBall);
                collision = true;
            }
        for (FireBall fireBall : toRemove)
            this.fireBalls.remove(fireBall);
        return collision;
    }


    private boolean checkWin() {
        return Helper.collisionDetector(wizard.getCoords(), grid.getTile(exitLocation[0], exitLocation[1]).getCoords());
    }


    private static Map<String, List<Integer[]>> makeDefaultAssetLocations() {
        String[] ls = {"Wizard", "Brickwall", "Stonewall", "Gremlin", "Door", "Empty", "Powerup", "Frozenwall"};
        Map<String, List<Integer[]>> map = new HashMap<>();
        for (String s : ls)
            map.put(s, new ArrayList<>());
        return map;
    }

    void unFreezeGremlins() {
        this.currentTimeFrozen = 0;
        for (Gremlin gremlin : this.gremlins)
            gremlin.setMoving(true);
    }

    void freezeGremlins() {
        this.currentTimeFrozen = 1;
        for (Gremlin gremlin : this.gremlins)
            gremlin.setMoving(false);
    }


    public static void main(String[] args) {
        PApplet.main("gremlins.App");
    }
}
