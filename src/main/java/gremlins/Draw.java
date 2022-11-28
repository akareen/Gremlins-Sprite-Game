package gremlins;

/**
 * The draw interface used by the main application.
 * The methods take in the main application as a parameter and perform draw operations.
 */
public interface Draw {
    /**
     * Draws the victory message for the game.
     * @param app, the main application
     */
    static void drawVictory(App app) {
        app.fill(0, 0, 0);
        app.rect(-1, -1, App.WIDTH + 2, App.HEIGHT + 2);
        Text.drawVictoryMessage(app);
    }

    /**
     * Draws the game over message for the game.
     * @param app, the main application
     */
    static void drawGameOver(App app) {
        app.fill(0, 0, 0);
        app.rect(-1, -1, App.WIDTH + 2, App.HEIGHT + 2);
        Text.drawGameOverMessage(app);
    }

    /**
     * Draws the brown background of the game.
     * @param app, the main application
     */
    static void drawBackground(App app) {
        app.fill(191, 153, 114);
        app.rect(-1, -1, App.WIDTH + 2, App.HEIGHT + 2);
    }

    /**
     * Iterates through the TileGrid of the game and draws each non-empty tile.
     * It has the logic of updating a destroyed wall to an empty wall
     * once the destruction sequence is complete.
     * @param app, the main application.
     */
    static void drawTiles(App app) {
        for (int y = 0; y < app.grid.getYLength(); y++) {
            for (int x = 0; x < app.grid.getXLength(); x++) {
                Tile tile = app.grid.getTile(y, x);
                tile.tick();
                if (!(tile.isEmpty())
                        && tile.getName().equals("destroyed-wall")) {
                    if (tile.isFullyDestroyed()) {
                        app.grid.setTile(y, x, new EmptyTile(y, x, "empty"));
                    }
                    else {
                        tile.draw(app);
                    }
                }
                else if (!tile.isEmpty()) {
                    tile.draw(app);
                }
            }
        }
    }

    /**
     * Draws the wizard object.
     * @param app, the main application
     */
    static void drawWizard(App app) {
        app.wizard.draw(app);
    }

    /**
     * Draws the gremlin objects.
     * @param app, the main application
     */
    static void drawGremlins(App app) {
        for (Gremlin gremlin : app.gremlins) {
            gremlin.draw(app);
        }
    }

    /**
     * Draws the slimeball objects.
     * @param app, the main application
     */
    static void drawSlimeBalls(App app) {
        for (SlimeBall slimeBall : app.slimeBalls) {
            slimeBall.draw(app);
        }
    }

    /**
     * Draws the fireball objects.
     * @param app, the main application
     */
    static void drawFireBalls(App app) {
        for (FireBall fireBall : app.fireBalls) {
            fireBall.draw(app);
        }
    }

    /**
     * Draws the number of lives as shown by a number of wizard sprites.
     * @param app, the main application
     */
    static void drawTextField(App app) {
        Text.drawLives(app, ObjectMaker.getImage("wizard1"), app.lives);
        Text.drawLevel(app, app.levelNum, app.totalNumLevels);
    }

    /**
     * Draws the progress of the wizard cooldown.
     * @param app, the main application
     */
    static void drawManaBar(App app) {
        if (app.wizard.isOnCooldown()) {
            Text.drawManaBar(app, app.wizard.getCooldownFrames(), 
            app.wizard.getTimeInCooldown());
        }
    }
}
