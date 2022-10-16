package gremlins;

public class EmptyTile extends Tile {
    public EmptyTile(int y, int x, String name) {
        super(y, x, name);
        super.setEmpty(true);
    }
}
