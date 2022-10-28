package gremlins;

import java.util.ArrayList;
import java.util.List;

public interface Draw {
    // Ending Messages
    static void drawVictory(App app) {
        app.fill(0, 0, 0);
        app.rect(-1, -1, App.WIDTH + 2, App.HEIGHT + 2);
        Text.drawVictoryMessage(app);
    }

    static void drawGameOver(App app) {
        app.fill(0, 0, 0);
        app.rect(-1, -1, App.WIDTH + 2, App.HEIGHT + 2);
        Text.drawGameOverMessage(app);
    }


    static void drawBackground(App app) {
        app.fill(191, 153, 114);
        app.rect(-1, -1, App.WIDTH + 2, App.HEIGHT + 2);
    }


    static void drawTiles(App app) {
        for (int y = 0; y < app.grid.getYLength(); y++) {
            for (int x = 0; x < app.grid.getXLength(); x++) {
                Tile tile = app.grid.getTile(y, x);
                tile.tick();
                if (!(tile.isEmpty())
                        && tile.getName().equals("destroyed-wall")) {
                    if (tile.isFullyDestroyed())
                        app.grid.setTile(y, x, new EmptyTile(y, x, "empty"));
                    else
                        tile.draw(app);
                }
                else if (!tile.isEmpty())
                    tile.draw(app);
            }
        }
    }

    static void drawWizard(App app) {
        app.wizard.draw(app);
    }

    static void drawGremlins(App app) {
        for (Gremlin gremlin : app.gremlins)
            gremlin.draw(app);
    }

    static void drawSlimeBalls(App app) {
        for (SlimeBall slimeBall : app.slimeBalls)
            slimeBall.draw(app);
    }

    static void drawFireBalls(App app) {
        for (FireBall fireBall : app.fireBalls)
            fireBall.draw(app);
    }


    static void drawTextField(App app) {
        Text.drawLives(app, ObjectMaker.getImage("wizard1"), app.lives);
        Text.drawLevel(app, app.levelNum, app.totalNumLevels);
    }

    static void drawManaBar(App app) {
        if (app.wizard.isOnCooldown())
            Text.drawManaBar(app, app.wizard.getCooldownFrames(), app.wizard.getTimeInCooldown());
    }

    static void drawGame(App app) {
        drawBackground(app);
        drawTiles(app);
        drawWizard(app);
        drawGremlins(app);
        drawSlimeBalls(app);
        drawFireBalls(app);
        drawTextField(app);
        drawManaBar(app);
    }

}
