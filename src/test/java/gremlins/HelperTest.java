package gremlins;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HelperTest {

    @Test
    public void collisionDetectorTest() {
        int[][][] cases = {
                {{10, 10}, {9, 10}},
                {{10, 10}, {31, 10}},
                {{10, 10}, {10, 9}},
                {{10, 10}, {11, 31}},
                {{10, 10}, {18, 18}}
        };
        for (int i = 0; i < cases.length; i++) {
            if (i == 4 || i == 3)
                assertTrue(Helper.collisionDetector(cases[i][0], cases[i][1]));
            else
                assertFalse(Helper.collisionDetector(cases[i][0], cases[i][1]));
        }
    }

    @Test
    public void oppositeDirectionTest() {
        assertEquals(Helper.oppositeDirection(2), 3);
        assertEquals(Helper.oppositeDirection(10), 0);
    }
}
