package gremlins;

import java.util.ArrayList;
import java.util.List;

/**
 * An interface used to control the logic of the game.
 */
public interface GameLogic {
    /**
     * A parameter used to calculate the four corners of a 20x20 image from the top left corner.
     */
    final static int BLOCK_MODIFIER = 20 - 1;

    /**
     * A function that determines whether the hitboxes of two objects overlap.
     * Uses the helper function _hitboxOverlap to test if coordinate1 overlaps with coordinate2 or vice versa.
     * @param coordinate1, the y and x coordinates of the first object.
     * @param coordinate2, the y and x coordinates of the second object.
     * @return true if the two objects overlap, false otherwise.
     */
    static boolean hitboxOverlap(int[] coordinate1, int[] coordinate2) {
        return _hitboxOverlap(coordinate1, coordinate2) || _hitboxOverlap(coordinate2, coordinate1);
    }

    /**
     * A helper function for hitboxOverlap.
     * @param coordinate1, the y and x coordinates of the first object.
     * @param coordinate2, the y and x coordinates of the second object.
     * @return true if coordinate 2 overlaps with coordinate 1, false otherwise.
     */
    static boolean _hitboxOverlap(int[] coordinate1, int[] coordinate2) {
        int y1 = coordinate1[0]; int x1 = coordinate1[1];
        int y2 = coordinate2[0]; int x2 = coordinate2[1];
        return  (y2 >= y1 && y2 <= (y1 + BLOCK_MODIFIER)) &&
                (x2 >= x1 && x2 <= (x1 + BLOCK_MODIFIER));
    }

    /**
     * Calls the wizard's tick method to update the wizard's position and to create fireballs.
     * @param app, the main application.
     */
    static void wizardLogic(App app) {
        app.wizard.tick(app.grid, app.fireBalls);
    }

    /**
     * Calls the gremlin's tick method to update the gremlin's position and to create slimeballs.
     * Unfreezes the gremlin's if it has been frozen for the required amount of time. If the gremlin contacts
     * the wizard the level restarts with one less life.
     * @param app, the main application.
     */
    static void gremlinLogic(App app) {
        if (app.currentTimeFrozen >= app.totalTimeFrozen)
            unFreezeGremlins(app);

        for (Gremlin gremlin : app.gremlins) {
            gremlin.tick(app.grid, app.slimeBalls, app.fireBalls, app.wizard);
            if (hitboxOverlap(gremlin.getCoords(), app.wizard.getCoords())) {
                app.lives--;
                SetupGame.loadObjects(app);
                break;
            }
        }
        if (app.currentTimeFrozen >= 1)
            app.currentTimeFrozen++;
    }

    /**
     * Evaluates if a slimeball has collided with any fireball if it has then it
     * deletes both the slimeball and the fireball from the map.
     * @param slime, the slimeball.
     * @param app, the main application.
     * @return true, if the slimeball has collided with a fireball, false otherwise.
     */
    static boolean fireSlimeCollision(SlimeBall slime, App app) {
        boolean collision = false;
        List<FireBall> toRemove = new ArrayList<>();
        for (FireBall fireBall : app.fireBalls)
            if (hitboxOverlap(fireBall.getCoords(), slime.getCoords())) {
                toRemove.add(fireBall);
                collision = true;
            }
        for (FireBall fireBall : toRemove)
            app.fireBalls.remove(fireBall);
        return collision;
    }

    /**
     * Peforms the logic for each slimeball. If the fireSlimeCollision method returns true then the slimeball will be removed.
     * If the slimeballs tick method returns true it will also be removed. If there is a collision with the slimeball
     * and the wizard then the game will restart the level will once less life.
     * @param app, the main application.
     */
    static void slimeBallLogic(App app) {
        List<SlimeBall> toRemove = new ArrayList<>();
        for (SlimeBall slimeBall : app.slimeBalls) {
            if (fireSlimeCollision(slimeBall, app))
                toRemove.add(slimeBall);
            if (slimeBall.tick(app.grid)) {
                toRemove.add(slimeBall);
                break;
            }
            if (hitboxOverlap(slimeBall.getCoords(), app.wizard.getCoords())) {
                app.lives--;
                SetupGame.loadObjects(app);
                return;
            }
        }
        for (SlimeBall item : toRemove)
            app.slimeBalls.remove(item);
    }

    /**
     * Performs the logic for each fireball. If the fireball's tick method does not return 0 then the fireball will be removed.
     * If the tick method returns 2 then all the gremlins will be frozen.
     * @param app, the main application.
     */
    static void fireBallLogic(App app) {
        List<FireBall> toRemove = new ArrayList<>();
        for (FireBall fireBall : app.fireBalls) {
            int tickVal = fireBall.tick(app.grid);
            if (tickVal == 2)
                freezeGremlins(app);
            if (tickVal != 0) {
                toRemove.add(fireBall);
                break;
            }
        }
        for (FireBall item : toRemove)
            app.fireBalls.remove(item);
    }

    /**
     * Unfreezes all the gremlins by setting their moving boolean to true. It also resets the frozen time timer.
     * @param app, the main application.
     */
    static void unFreezeGremlins(App app) {
        app.currentTimeFrozen = 0;
        for (Gremlin gremlin : app.gremlins)
            gremlin.setMoving(true);
    }

    /**
     * Freezes all the gremlins by setting their moving boolean to false. It sets the frozen time timer to 1.
     * @param app, the main application.
     */
    static void freezeGremlins(App app) {
        app.currentTimeFrozen = 1;
        for (Gremlin gremlin : app.gremlins)
            gremlin.setMoving(false);
    }

    /**
     * Checks if the wizard has made contact with the powerup if it has the activatePowerup method
     * of the wizard is called, thus increasing the wizards speed.
     * @param app, the main application.
     */
    static void evaluatePowerups(App app) {
        for (PowerUpTile powerup : app.powerupTiles) {
            if (!powerup.isEmpty() && hitboxOverlap(powerup.getCoords(), app.wizard.getCoords())) {
                powerup.activatePowerup(app.wizard);
            }
        }
    }

    /**
     * Checks if the wizard has made contact with the exit tile. If it has then the level is completed.
     * @param app, the main application.
     */
    static boolean checkWin(App app) {
        return hitboxOverlap(app.wizard.getCoords(),
                app.grid.getTile(app.exitLocation[0], app.exitLocation[1]).getCoords());
    }
}
