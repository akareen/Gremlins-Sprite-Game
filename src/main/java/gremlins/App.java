package gremlins;

import processing.core.PApplet;
import processing.data.JSONObject;
import processing.data.JSONArray;

import java.util.*;
import java.io.*;

public class App extends PApplet {
    //Final Size Values
    public static final int WIDTH = 720;
    public static final int HEIGHT = 720;
    public static final int FPS = 60;

    //Loaded data from the config file
    public String configPath;
    Map<Integer, Map<String, String>> levelsMap;

    /* FOR ALL LEVELS */
    private int totalNumLevels;
    private int levelNum = 1;

    /* FOR THE CURRENT LEVEL */
    //Static
    private String layoutFile;
    private String[][] tileLetterConfiguration;
    private Map<String, List<Integer[]>> assetLocations;
    private int[] exitLocation;
    //Dynamic
    public int lives;
    public Wizard wizard;
    public List<Gremlin> gremlins;
    public List<SlimeBall> slimeBalls;
    public List<FireBall> fireBalls;
    public List<PowerUpTile> powerupTiles;

    private double wizardCooldown, enemyCooldown;
    TileGrid grid;


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
                "brickwall_destroyed3", "door", "fireball", "gremlin", "powerup", "slime", "stonewall", "wizard0",
                "wizard1", "wizard2", "wizard3"
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
            System.out.println(map);
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
        this.wizardCooldown = Double.parseDouble(
                this.levelsMap.get(levelNum).get("wizard_cooldown"));
        this.enemyCooldown = Double.parseDouble(
                this.levelsMap.get(levelNum).get("enemy_cooldown"));
    }

    private void readLevelFile(String levelName) {
        File fileName = new File(levelName);
        try {
            Scanner scanner = new Scanner(fileName);
            String[][] configArray = new String[33][36];
            int y = 0;
            while (scanner.hasNextLine()) {
                configArray[y] = scanner.nextLine().split("");
                y += 1;
            }
            this.tileLetterConfiguration = configArray;
        } catch (Exception e) {
            System.out.println("Incorrect config file");
        }
    }

    private void createAssetLocations(String[][] configFile) {
        this.assetLocations = makeDefaultAssetLocations();
        String[] ls = {"Wizard", "Brickwall", "Stonewall", "Gremlin", "Door", "Powerup"};
        String[] codes = {"W", "B", "X", "G", "E", "P"};
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

    private void loadObjects() {
        //Make Slimeballs
        this.slimeBalls = new ArrayList<>();
        this.fireBalls   = new ArrayList<>();
        //Make Wizard
        Integer[] wizardCoords = assetLocations.get("Wizard").get(0);
        this.wizard = ObjectMaker.makeWizard(
                wizardCoords[0] * 20, wizardCoords[1] * 20, wizardCooldown);
        //Make Gremlins
        this.gremlins = new ArrayList<>();
        for (Integer[] ls : assetLocations.get("Gremlin"))
            this.gremlins.add(ObjectMaker.makeGremlin(
                    ls[0] * 20, ls[1] * 20, enemyCooldown));
        //Make BrickWalls
        for (Integer[] ls : assetLocations.get("Brickwall"))
            this.grid.setTile(ls[0], ls[1], ObjectMaker.makeBrickwall(
                    ls[0] * 20, ls[1] * 20));
        //Make StoneWalls
        for (Integer[] ls : assetLocations.get("Stonewall"))
            this.grid.setTile(ls[0], ls[1], ObjectMaker.makeStonewall(
                    ls[0] * 20, ls[1] * 20));
        // Make Powerup
        this.powerupTiles = new ArrayList<>();
        for (Integer[] ls : assetLocations.get("Powerup")) {
            PowerUpTile powerup = ObjectMaker.makePowerUpTile(
                    ls[0] * 20, ls[1] * 20);
            this.powerupTiles.add(powerup);
            this.grid.setTile(ls[0], ls[1], powerup);
        }
        //Make Door
        Integer[] ls = assetLocations.get("Door").get(0);
        this.grid.setTile(ls[0], ls[1], ObjectMaker.makeDoor(
                ls[0] * 20, ls[1] * 20));
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
                wizard.pushDirection(directions[i]);
                break;
            }
        if (keyCode == 32) //SPACE BAR
            wizard.initiateShoot();
    }

    /**
     * Receive key released signal from the keyboard.
     */
    public void keyReleased(){
        wizard.setFutureMoving(false);
    }


    /**
     * Draw all elements in the game by current frame.
     */
    public void draw() {
        if (this.levelNum > totalNumLevels)
            drawVictory();
        else if (this.lives <= 0)
            drawGameOver();
        else {
            drawBackground();
            evaluatePowerups();
            drawTiles();
            drawWizard();
            drawGremlins();
            drawSlimeBalls();
            drawFireBalls();
            drawTextField();
            drawManaBar();
            if (checkWin()) {
                this.levelNum++;
                if (this.levelNum <= totalNumLevels)
                    loadLevel(levelNum);
            }
        }
    }

    /* Helper Methods for Draw */
    private void drawBackground() {
        fill(191, 153, 114);
        this.rect(-1, -1, WIDTH + 2, HEIGHT + 2);
    }

    private void evaluatePowerups() {
        for (PowerUpTile powerup : this.powerupTiles) {
            if (!powerup.isEmpty() && Helper.collisionDetector(
                    powerup.getCoords(), this.wizard.getCoords())) {
                powerup.activatePowerup(this.wizard);
            }
        }
    }

    private void drawTiles() {
        for (int y = 0; y < this.grid.getTileGrid().length; y++) {
            for (int x = 0; x < this.grid.getTileGrid()[y].length; x++) {
                Tile tile = this.grid.getTile(y, x);
                tile.tick();
                if (!(tile.isEmpty())
                        && tile.getName().equals("destroyed-wall")) {
                    if (tile.isFullyDestroyed())
                        this.grid.setTile(y, x, new EmptyTile(y, x, "empty"));
                    else
                        tile.draw(this);
                }
                else if (!tile.isEmpty())
                    tile.draw(this);
            }
        }
    }

    private void drawWizard() {
        wizard.tick(this.grid, fireBalls);
        wizard.draw(this);
    }

    private void drawGremlins() {
        for (Gremlin gremlin : this.gremlins) {
            gremlin.tick(this.grid, slimeBalls, fireBalls, wizard);
            gremlin.draw(this);
            if (Helper.collisionDetector(
                    gremlin.getCoords(), wizard.getCoords())) {
                this.lives--;
                loadObjects();
                break;
            }
        }
    }

    private void drawSlimeBalls() {
        List<SlimeBall> toRemove = new ArrayList<>();
        for (SlimeBall slimeBall : this.slimeBalls) {
            for (int i = 0; i < slimeBall.getSPEED(); i++) {
                if (fireSlimeCollision(slimeBall))
                    toRemove.add(slimeBall);
                if (slimeBall.tick(this.grid)) {
                    toRemove.add(slimeBall);
                    break;
                }
                slimeBall.draw(this);
                if (Helper.collisionDetector(slimeBall.getCoords(), wizard.getCoords())) {
                    this.lives--;
                    loadObjects();
                    return;
                }
            }
        }
        for (SlimeBall item : toRemove)
            this.slimeBalls.remove(item);
    }

    private boolean fireSlimeCollision(SlimeBall slime) {
        boolean collision = false;
        List<FireBall> toRemove = new ArrayList<>();
        for (FireBall fireBall : this.fireBalls)
            if (Helper.collisionDetector(
                    fireBall.getCoords(), slime.getCoords())) {
                toRemove.add(fireBall);
                collision = true;
            }
        for (FireBall fireBall : toRemove)
            this.fireBalls.remove(fireBall);
        return collision;
    }

    private void drawFireBalls() {
        List<FireBall> toRemove = new ArrayList<>();
        for (FireBall fireBall : this.fireBalls) {
            for (int i = 0; i < fireBall.getSPEED(); i++) {
                if (fireBall.tick(this.grid)) {
                    toRemove.add(fireBall);
                    break;
                }
                fireBall.draw(this);
            }
        }
        for (FireBall item : toRemove)
            this.fireBalls.remove(item);

    }

    private void drawTextField() {
        Text.drawLives(this, ObjectMaker.getImage("wizard1"), this.lives);
        Text.drawLevel(this, levelNum, totalNumLevels);
    }

    private void drawManaBar() {
        if (wizard.isOnCooldown())
            Text.drawManaBar(this, wizard.getCooldownFrames(), wizard.getTimeInCooldown());
    }

    //FIX UP
    private void drawVictory() {
        fill(0, 0, 0);
        this.rect(-1, -1, WIDTH + 2, HEIGHT + 2);
        Text.drawVictoryMessage(this);
    }

    private void drawGameOver() {
        fill(0, 0, 0);
        this.rect(-1, -1, WIDTH + 2, HEIGHT + 2);
        Text.drawGameOverMessage(this);
    }


    private boolean checkWin() {
        return Helper.collisionDetector(wizard.getCoords(),
                grid.getTile(exitLocation[0], exitLocation[1]).getCoords());
    }


    private static Map<String, List<Integer[]>> makeDefaultAssetLocations() {
        String[] ls = {"Wizard", "Brickwall", "Stonewall",
                "Gremlin", "Door", "Empty", "Powerup"
        };
        Map<String, List<Integer[]>> map = new HashMap<>();
        for (String s : ls)
            map.put(s, new ArrayList<>());
        return map;
    }


    public static void main(String[] args) {
        PApplet.main("gremlins.App");
    }
}
