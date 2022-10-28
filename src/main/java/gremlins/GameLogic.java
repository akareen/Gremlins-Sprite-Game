package gremlins;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public interface GameLogic {
    static void wizardLogic(App app) {
        app.wizard.tick(app.grid, app.fireBalls);
    }

    static void gremlinLogic(App app) {
        if (app.currentTimeFrozen >= app.totalTimeFrozen) {
            unFreezeGremlins(app);
            System.out.println("YO");
        }

        for (Gremlin gremlin : app.gremlins) {
            gremlin.tick(app.grid, app.slimeBalls, app.fireBalls, app.wizard);
            if (Helper.collisionDetector(gremlin.getCoords(), app.wizard.getCoords())) {
                app.lives--;
                SetupGame.loadObjects(app);
                break;
            }
        }
        if (app.currentTimeFrozen >= 1)
            app.currentTimeFrozen++;
    }

    static boolean fireSlimeCollision(SlimeBall slime, App app) {
        boolean collision = false;
        List<FireBall> toRemove = new ArrayList<>();
        for (FireBall fireBall : app.fireBalls)
            if (Helper.collisionDetector(fireBall.getCoords(), slime.getCoords())) {
                toRemove.add(fireBall);
                collision = true;
            }
        for (FireBall fireBall : toRemove)
            app.fireBalls.remove(fireBall);
        return collision;
    }


    static void slimeBallLogic(App app) {
        List<SlimeBall> toRemove = new ArrayList<>();
        for (SlimeBall slimeBall : app.slimeBalls) {
            for (int i = 0; i < slimeBall.getSPEED(); i++) {
                if (fireSlimeCollision(slimeBall, app))
                    toRemove.add(slimeBall);
                if (slimeBall.tick(app.grid)) {
                    toRemove.add(slimeBall);
                    break;
                }
                if (Helper.collisionDetector(slimeBall.getCoords(), app.wizard.getCoords())) {
                    app.lives--;
                    SetupGame.loadObjects(app);
                    return;
                }
            }
        }
        for (SlimeBall item : toRemove)
            app.slimeBalls.remove(item);
    }

    static void fireBallLogic(App app) {
        List<FireBall> toRemove = new ArrayList<>();
        for (FireBall fireBall : app.fireBalls) {
            for (int i = 0; i < fireBall.getSPEED(); i++) {
                int tickVal = fireBall.tick(app.grid);
                if (tickVal == 2)
                    freezeGremlins(app);
                if (tickVal != 0) {
                    toRemove.add(fireBall);
                    break;
                }
            }
        }
        for (FireBall item : toRemove)
            app.fireBalls.remove(item);
    }

    static void unFreezeGremlins(App app) {
        app.currentTimeFrozen = 0;
        for (Gremlin gremlin : app.gremlins)
            gremlin.setMoving(true);
    }
    static void freezeGremlins(App app) {
        app.currentTimeFrozen = 1;
        for (Gremlin gremlin : app.gremlins)
            gremlin.setMoving(false);
    }

    static void evaluatePowerups(App app) {
        for (PowerUpTile powerup : app.powerupTiles) {
            if (!powerup.isEmpty() && Helper.collisionDetector(
                    powerup.getCoords(), app.wizard.getCoords())) {
                powerup.activatePowerup(app.wizard);
            }
        }
    }

    static boolean checkWin(App app) {
        return Helper.collisionDetector(
                app.wizard.getCoords(),
                app.grid.getTile(app.exitLocation[0], app.exitLocation[1]).getCoords());
    }

    static void gameExecution(App app) {
        evaluatePowerups(app);
        wizardLogic(app);
        gremlinLogic(app);
        slimeBallLogic(app);
        fireBallLogic(app);
    }

}
