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
        for (int y = 0; y < app.grid.getTileGrid().length; y++) {
            for (int x = 0; x < app.grid.getTileGrid()[y].length; x++) {
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
        app.wizard.tick(app.grid, app.fireBalls);
        app.wizard.draw(app);
    }

    static void drawGremlins(App app) {
        if (app.currentTimeFrozen >= app.totalTimeFrozen) {
            app.unFreezeGremlins();
            System.out.println("YO");
        }

        for (Gremlin gremlin : app.gremlins) {
            gremlin.tick(app.grid, app.slimeBalls, app.fireBalls, app.wizard);
            gremlin.draw(app);
            if (Helper.collisionDetector(
                    gremlin.getCoords(), app.wizard.getCoords())) {
                app.lives--;
                app.loadObjects();
                break;
            }
        }
        if (app.currentTimeFrozen >= 1)
            app.currentTimeFrozen++;
    }

    static void drawSlimeBalls(App app) {
        List<SlimeBall> toRemove = new ArrayList<>();
        for (SlimeBall slimeBall : app.slimeBalls) {
            for (int i = 0; i < slimeBall.getSPEED(); i++) {
                if (app.fireSlimeCollision(slimeBall))
                    toRemove.add(slimeBall);
                if (slimeBall.tick(app.grid)) {
                    toRemove.add(slimeBall);
                    break;
                }
                slimeBall.draw(app);
                if (Helper.collisionDetector(slimeBall.getCoords(), app.wizard.getCoords())) {
                    app.lives--;
                    app.loadObjects();
                    return;
                }
            }
        }
        for (SlimeBall item : toRemove)
            app.slimeBalls.remove(item);
    }

    static void drawFireBalls(App app) {
        List<FireBall> toRemove = new ArrayList<>();
        for (FireBall fireBall : app.fireBalls) {
            for (int i = 0; i < fireBall.getSPEED(); i++) {
                int tickVal = fireBall.tick(app.grid);
                if (tickVal == 2)
                    app.freezeGremlins();
                if (tickVal != 0) {
                    toRemove.add(fireBall);
                    break;
                }
                fireBall.draw(app);
            }
        }
        for (FireBall item : toRemove)
            app.fireBalls.remove(item);
    }

    static void drawTextField(App app) {
        Text.drawLives(app, ObjectMaker.getImage("wizard1"), app.lives);
        Text.drawLevel(app, app.levelNum, app.totalNumLevels);
    }

    static void drawManaBar(App app) {
        if (app.wizard.isOnCooldown())
            Text.drawManaBar(app, app.wizard.getCooldownFrames(), app.wizard.getTimeInCooldown());
    }

}
