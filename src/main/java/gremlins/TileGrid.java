package gremlins;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A 33x36 grid of tiles used to represent the game map.
 */
public class TileGrid {
    /**
     * The grid of tiles.
     */
    private final Tile[][] tileGrid = makeTileGrid();

    /**
     * Makes a 33x36 grid of tiles filled with empty tiles.
     * @return the grid of tiles.
     */
    private Tile[][] makeTileGrid() {
        Tile[][] grid = new Tile[33][36];
        for (int y = 0; y < grid.length; y++)
            for (int x = 0; x < grid[y].length; x++)
                grid[y][x] = ObjectMaker.makeEmptyTile(y, x);
        return grid;
    }

    /**
     * Returns an empty tile that is at least 10 tiles away from the wizard.
     * @param playerY, the y coordinate of the wizard.
     * @param playerX, the x coordinate of the wizard.
     * @return an empty tile at least 10 tile radius away from the wizard.
     */
    public Integer[] respawnLocation(int playerY, int playerX) {
        List<Integer[]> vacant = vacantPositionsAwayFromPlayer(playerY, playerX);
        return vacant.get(new Random().nextInt(vacant.size()));
    }

    /**
     * Returns a list of empty tiles that are at least 10 tiles away from the wizard.
     * @param playerY, the y coordinate of the wizard.
     * @param playerX, the x coordinate of the wizard.
     * @return a list of empty tiles at least 10 tile radius away from the wizard.
     */
    protected List<Integer[]> vacantPositionsAwayFromPlayer(int playerY, int playerX) {
        List<Integer[]> vacant = new ArrayList<>();
        for (int y = 0; y < this.tileGrid.length; y++)
            for (int x = 0; x < this.tileGrid[y].length; x++)
                if (!withinTenSpaces(y, x, playerY, playerX) && tileGrid[y][x].isEmpty())
                    vacant.add(new Integer[] {y, x});
        return vacant;
    }

/**
     * Returns a boolean value representing if the tile is at least 10 tiles away from the wizard.
     * @param y, the y coordinate of the tile.
     * @param x, the x coordinate of the tile.
     * @param playerY, the y coordinate of the wizard.
     * @param playerX, the x coordinate of the wizard.
     * @return a boolean value representing if the tile is at least 10 tiles away from the wizard.
     */
    protected boolean withinTenSpaces(int y, int x, int playerY, int playerX) {
        return (Math.abs(y - playerY) + Math.abs(x - playerX)) <= 10;
    }

    /**
     * Checks if the given y and x coordinates are within the grid.
     * @param y, the y coordinate.
     * @param x, the x coordinate.
     * @return true if the y value is greater than or equal to 0 and less than the length of the grid and
     * the x value is greater than or equal to 0 and less than the width of the grid.
     * Otherwise, it returns false.
     */
    public boolean withinRange(int y, int x) {
        return (y >= 0 && y < 33) && (x >= 0 && x < 36);
    }

    /**
     * Returns the length of the grid.
     * @return the length of the grid.
     */
    public int getYLength() {
        return this.tileGrid.length;
    }

    /**
     * Returns the width of the grid.
     * @return the width of the grid.
     */
    public int getXLength() {
        return this.tileGrid[0].length;
    }

    /**
     * Returns the tile at the given y and x coordinates.
     * @param y, the y coordinate.
     * @param x, the x coordinate.
     * @return the tile at the given y and x coordinates.
     */
    public Tile getTile(int y, int x) {
        return this.tileGrid[y][x];
    }

    /**
     * Sets the tile at the given y and x coordinates to the given tile.
     * @param y, the y coordinate.
     * @param x, the x coordinate.
     * @param tile, the tile to be set.
     */
    public void setTile(int y, int x, Tile tile) {
        this.tileGrid[y][x] = tile;
    }

}
