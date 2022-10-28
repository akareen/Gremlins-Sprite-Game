package gremlins;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AppTest {
    App app = new App();

    @Test
    public void setupTest() {
        app.setup();
    }
}
