package gremlins;

public interface Helper {
    int BLOCK_MODIFIER = 20 - 1;

    static boolean collisionDetector(int[] coordinate1, int[] coordinate2) {
        return _collisionDetector(coordinate1, coordinate2) || _collisionDetector(coordinate2, coordinate1);
    }

    static boolean _collisionDetector(int[] coordinate1, int[] coordinate2) {
        int y1 = coordinate1[0]; int x1 = coordinate1[1];
        int y2 = coordinate2[0]; int x2 = coordinate2[1];

        return  (y2 >= y1 && y2 <= (y1 + BLOCK_MODIFIER)) &&
                (x2 >= x1 && x2 <= (x1 + BLOCK_MODIFIER));
    }

    // Turns North to South, East to West etc. Used to see same direction it came
    static int oppositeDirection(int direction) {
        int[] dir = {0, 1, 2, 3};
        int[] opp = {1, 0, 3, 2};
        for (int i = 0; i < dir.length; i++)
            if (dir[i] == direction)
                return opp[i];
        return 0;
    }
}
