package gremlins;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TileGrid {
    private final Tile[][] tileGrid = makeTileGrid();

    private Tile[][] makeTileGrid() {
        Tile[][] grid = new Tile[33][36];
        for (int y = 0; y < grid.length; y++)
            for (int x = 0; x < grid[y].length; x++)
                grid[y][x] = ObjectMaker.makeEmptyTile(y, x);
        return grid;
    }

    public Integer[] respawnLocation(int playerY, int playerX) {
        List<Integer[]> vacant = vacantPositionsAwayFromPlayer(playerY, playerX);
        return vacant.get(new Random().nextInt(vacant.size()));
    }

    private List<Integer[]> vacantPositionsAwayFromPlayer(int playerY, int playerX) {
        List<Integer[]> vacant = new ArrayList<>();
        for (int y = 0; y < this.tileGrid.length; y++)
            for (int x = 0; x < this.tileGrid[y].length; x++)
                if (!withinTenSpaces(y, x, playerY, playerX) && tileGrid[y][x].isEmpty())
                    vacant.add(new Integer[] {y, x});
        return vacant;
    }

    private boolean withinTenSpaces(int y, int x, int y1, int x1) {
        return (Math.abs(y - y1) + Math.abs(x - x1)) <= 10;
    }

    public boolean withinRange(int y, int x) {
        return (y >= 0 && y < 33) && (x >= 0 && x < 36);
    }

    // GETTERS AND SETTERS
    public Tile[][] getTileGrid() {
        return tileGrid;
    }

    public Tile getTile(int y, int x) {
        return this.tileGrid[y][x];
    }

    public void setTile(int y, int x, Tile obj) {
        this.tileGrid[y][x] = obj;
    }
}
