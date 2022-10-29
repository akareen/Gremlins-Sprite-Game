package gremlins;

import java.io.File;
import java.util.*;

/**
 * An interface used to setup the game.
 */
public interface SetupGame {

    /**
     * Loads the level specified by the levelNum by reading the level file by character, creating asset locations
     * from this data and then finally once the data is organised it is used to create the game objects.
     * @param app, the main application class.
     * @param levelNum, the level number.
     */
    static void loadLevel(App app, int levelNum) {
        setValuesFromMap(app, levelNum);
        readLevel(app);
        createAssetLocations(app);
        loadObjects(app);
    }

    /**
     * Sets the values of the layoutFile, gremlinCooldown and wizardCooldown from the levelsMap.
     * The layoutFile is the path to the level text file.
     * @param app, the main application class.
     * @param levelNum, the level number.
     */
    static void setValuesFromMap(App app, int levelNum) {
        app.layoutFile = app.levelsMap.get(levelNum).get("layout");
        app.wizardCooldown = Double.parseDouble(app.levelsMap.get(levelNum).get("wizard_cooldown"));
        app.enemyCooldown = Double.parseDouble(app.levelsMap.get(levelNum).get("enemy_cooldown"));
    }

    /**
     * Reads the level text file and stores the data in the tileLetterConfiguration.
     * tileLetterConfiguration is a 33x36 string array that stores the layout of the current level.
     * Each character in the level text file is stored in the tileLetterConfiguration.
     * @param app, the main application class.
     */
    static void readLevel(App app) {
        File fileName = new File(app.layoutFile);
        try {
            try (Scanner scanner = new Scanner(fileName)) {
                String[][] configArray = new String[33][36];
                int y = 0;
                while (scanner.hasNextLine()) {
                    configArray[y] = scanner.nextLine().split("");
                    y += 1;
                }
                app.tileLetterConfiguration = configArray;
            }
        } catch (Exception e) {
            System.out.println("Incorrect config file");
        }
    }

    /**
     * Creates the asset locations from the tileLetterConfiguration.
     * The asset locations are stored in the assetLocations map.
     * The key is the name of the asset.
     * The value is an ArrayList of all the locations of the asset.
     * @param app, the main application class.
     */
    static void createAssetLocations(App app) {
        app.assetLocations = makeDefaultAssetLocations();
        String[] ls = {"Wizard", "Brickwall", "Stonewall", "Gremlin", "Door", "Powerup", "Frozenwall"};
        String[] codes = {"W", "B", "X", "G", "E", "P", "F"};
        app.grid = new TileGrid();
        for (int y = 0; y < app.tileLetterConfiguration.length; y++)
            for (int x = 0; x < app.tileLetterConfiguration[y].length; x++) {
                for (int i = 0; i < codes.length; i++)
                    if (app.tileLetterConfiguration[y][x].equals(codes[i])) {
                        if (app.tileLetterConfiguration[y][x].equals("E"))
                            app.exitLocation = new int[] {y, x};
                        app.assetLocations.get(ls[i]).add(new Integer[] {y, x});
                        break;
                    }
            }
    }

    /**
     * Creates the default asset locations.
     * The asset locations are stored in the assetLocations map.
     * The key is the name of the asset.
     * The value is an empty ArrayList of all the locations of the asset.
     * @return the default asset locations.
     */
    static Map<String, List<Integer[]>> makeDefaultAssetLocations() {
        String[] ls = {"Wizard", "Brickwall", "Stonewall", "Gremlin", "Door", "Empty", "Powerup", "Frozenwall"};
        Map<String, List<Integer[]>> map = new HashMap<>();
        for (String s : ls)
            map.put(s, new ArrayList<>());
        return map;
    }

    /**
     * Loads the game objects from the asset locations.
     * All the game objects are loaded into the main application class.
     * @param app, the main application class.
     */
    static void loadObjects(App app) {
        app.slimeBalls = new ArrayList<>();
        app.fireBalls = new ArrayList<>();
        Integer[] wizardLocation = app.assetLocations.get("Wizard").get(0);
        //Make Wizard
        app.wizard = ObjectMaker.makeWizard(wizardLocation[0] * 20, wizardLocation[1] * 20, app.wizardCooldown);
        //Make Gremlins
        app.gremlins = new ArrayList<>();
        for (Integer[] gremlinLocation : app.assetLocations.get("Gremlin"))
            app.gremlins.add(ObjectMaker.makeGremlin(
                    gremlinLocation[0] * 20, gremlinLocation[1] * 20, app.enemyCooldown));
        // Make Brickwalls
        for (Integer[] brickwallLocation : app.assetLocations.get("Brickwall"))
            app.grid.setTile(brickwallLocation[0], brickwallLocation[1],
                    ObjectMaker.makeBrickwall(brickwallLocation[0] * 20, brickwallLocation[1] * 20));
        // Make Stonewalls
        for (Integer[] stonewallLocation : app.assetLocations.get("Stonewall"))
            app.grid.setTile(stonewallLocation[0], stonewallLocation[1],
                    ObjectMaker.makeStonewall(stonewallLocation[0] * 20, stonewallLocation[1] * 20));
        // Make Frozenwalls
        for (Integer[] frozenwallLocation : app.assetLocations.get("Frozenwall"))
            app.grid.setTile(frozenwallLocation[0], frozenwallLocation[1],
                    ObjectMaker.makeFrozenwall(frozenwallLocation[0] * 20, frozenwallLocation[1] * 20));
        // Make Door
        Integer[] doorLocation = app.assetLocations.get("Door").get(0);
        app.grid.setTile(doorLocation[0], doorLocation[1],
                ObjectMaker.makeDoor(doorLocation[0] * 20, doorLocation[1] * 20));
        // Make Powerups
        app.powerupTiles = new ArrayList<>();
        for (Integer[] powerupLocation : app.assetLocations.get("Powerup")) {
            PowerUpTile powerupTile = ObjectMaker.makePowerUpTile(powerupLocation[0] * 20, powerupLocation[1] * 20);
            app.grid.setTile(powerupLocation[0], powerupLocation[1], powerupTile);
            app.powerupTiles.add(powerupTile);
        }
    }

}