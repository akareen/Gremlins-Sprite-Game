package gremlins;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;


public class GameLogicTest {
    public static App app;

    @BeforeAll
    public static void setupApp() {
        app = new App();
    }


    @Test
    public void hitboxOverlapTest() {
       int[][][] branches = {
               //{{y1, x1}, {y2, x2}}
               {{10, 10}, {20, 20}}, //True
               //All below are false cases
               {{10, 15}, {15, 40}}, {{8, 12}, {18, 8}}, {{20, 2147483646}, {30, 40}},
               {{40, 10}, {200, 20}}, {{50, 10}, {300, 50}}, {{70, 20}, {400, 10}}, {{40, 2147483646}, {550, 10}},
               {{110, 30}, {10, 40}}, {{45, 20}, {20, 100}}, {{68, 100}, {30, 10}}, {{98, 2147483646}, {45, 200}},
               {{2147483646, 10}, {100, 20}}, {{2147483645, 10}, {90,100}}, {{2147483645,12}, {300, 8}}, {{2147483645, 2147483635}, {50, 100}},
       };
       for (int i = 0; i < branches.length; i++) {
           if (i == 0)
               assertTrue(GameLogic.hitboxOverlap(branches[i][0], branches[i][1]));
           else
               assertFalse(GameLogic.hitboxOverlap(branches[i][0], branches[i][1]));
       }
       for (int i = 0; i < branches.length; i++) {
           if (i == 0)
               assertTrue(GameLogic.hitboxOverlap(branches[i][1], branches[i][0]));
           else
               assertFalse(GameLogic.hitboxOverlap(branches[i][1], branches[i][0]));
       }
   }

    @Test
    public void wizardLogicTest() {
        app.wizard = ObjectMaker.makeWizard(20, 20, 1);
        app.grid = new TileGrid();
        app.fireBalls = new ArrayList<>();
        app.wizard.initiateShoot();
        app.wizard.setFutureMoving(true);
        app.wizard.setFutureDirection(3);

        GameLogic.wizardLogic(app);
        assertEquals(20, app.wizard.getX());
        assertEquals(21, app.wizard.getY());
        assertEquals(1, app.fireBalls.size());
        assertTrue(app.wizard.onCooldown);
        assertFalse(app.wizard.shooting);

        app.wizard.initiateShoot();
        GameLogic.wizardLogic(app);
        assertEquals(20, app.wizard.getX());
        assertEquals(22, app.wizard.getY());
        assertEquals(1, app.wizard.timeInCooldown);
        assertEquals(1, app.fireBalls.size());
        assertTrue(app.wizard.onCooldown);
        assertFalse(app.wizard.shooting);

        app.wizard.timeInCooldown = app.wizard.cooldownFrames;
        GameLogic.wizardLogic(app);
        assertEquals(20, app.wizard.getX());
        assertEquals(23, app.wizard.getY());
        assertEquals(0, app.wizard.timeInCooldown);
        assertFalse(app.wizard.onCooldown);

        app.wizard.x = 40;
        app.wizard.direction = 1;
        GameLogic.wizardLogic(app);
        assertEquals(41, app.wizard.getX());
        assertEquals(23, app.wizard.getY());

        GameLogic.wizardLogic(app);
        assertEquals(42, app.wizard.getX());
        assertEquals(23, app.wizard.getY());

        app.wizard.moving = false;
        GameLogic.wizardLogic(app);
        assertEquals(42, app.wizard.getX());
        assertEquals(23, app.wizard.getY());

        app.wizard.x = 0; app.wizard.y = 0;
        app.wizard.direction = 3;
        app.wizard.moving = true;
        app.grid.setTile(1, 0, ObjectMaker.makeStonewall(20, 20));
        GameLogic.wizardLogic(app);
        assertEquals(0, app.wizard.getX());
        assertEquals(0, app.wizard.getY());
        assertFalse(app.wizard.moving);

        app.wizard.moving = true;
        app.grid.setTile(1, 0, ObjectMaker.makeEmptyTile(20, 20));
        GameLogic.wizardLogic(app);
        assertEquals(0, app.wizard.getX());
        assertEquals(1, app.wizard.getY());
        assertTrue(app.wizard.moving);
   }

   @Test
   public void gremlinLogicTest() {
        app.gremlins = new ArrayList<>();
        app.gremlins.add(ObjectMaker.makeGremlin(20, 20, 1));
        app.grid = new TileGrid();
        app.fireBalls = new ArrayList<>();
		app.slimeBalls = new ArrayList<>();
        app.wizard = ObjectMaker.makeWizard(200, 200, 1);
        app.delay(1000);

		GameLogic.freezeGremlins(app);
		GameLogic.gremlinLogic(app);

        app.currentTimeFrozen = app.totalTimeFrozen;
        app.gremlins.get(0).direction = 3;
        GameLogic.gremlinLogic(app);
        assertTrue(app.gremlins.get(0).moving);
        assertEquals(21, app.gremlins.get(0).getY());
        assertEquals(20, app.gremlins.get(0).getX());
		assertEquals(1, app.slimeBalls.size());

		app.gremlins.get(0).direction = 1;
		GameLogic.gremlinLogic(app);
		assertTrue(app.gremlins.get(0).moving);
		assertEquals(21, app.gremlins.get(0).getY());
		assertEquals(21, app.gremlins.get(0).getX());
		assertEquals(1, app.slimeBalls.size());

		app.fireBalls.add(ObjectMaker.makeFireBall(25, 25, 1));
		GameLogic.gremlinLogic(app);
		assertNotEquals(21, app.gremlins.get(0).getY());
		assertNotEquals(22, app.gremlins.get(0).getX());

		app.gremlins.get(0).direction = 2;
		app.gremlins.get(0).y = 200;
		app.gremlins.get(0).x = 200;
		app.wizard = ObjectMaker.makeWizard(20, 20, 1);

		app.grid.setTile(180/20, 200/20, ObjectMaker.makeBrickwall(180, 200));
		app.grid.setTile(200/20, 220/20, ObjectMaker.makeBrickwall(200, 220));
		GameLogic.gremlinLogic(app);
		assertEquals(0, app.gremlins.get(0).direction);

		app.gremlins.get(0).direction = 2;
		app.gremlins.get(0).y = 200;
		app.gremlins.get(0).x = 200;
		app.grid.setTile(200/20, 180/20, ObjectMaker.makeStonewall(200, 180));
		GameLogic.gremlinLogic(app);
		assertEquals(3, app.gremlins.get(0).direction);
   }



	@Test
	public void slimeBallLogicTest() {
		app.slimeBalls = new ArrayList<>();
		app.fireBalls = new ArrayList<>();
		app.grid = new TileGrid();
        app.gremlins = new ArrayList<>();
		app.wizard = ObjectMaker.makeWizard(200, 20, 1);
        app.delay(1000);
		
		//Fire slime collision test
		app.slimeBalls.add(ObjectMaker.makeSlimeBall(20, 20, 2));
		app.fireBalls.add(ObjectMaker.makeFireBall(30, 20, 2));
		GameLogic.slimeBallLogic(app);
		assertEquals(0, app.slimeBalls.size());
		assertEquals(0, app.fireBalls.size());

		app.slimeBalls.add(ObjectMaker.makeSlimeBall(20, 20, 2));
		GameLogic.slimeBallLogic(app);
		assertEquals(1, app.slimeBalls.size());

		//slime hit wall test
		app.slimeBalls.add(ObjectMaker.makeSlimeBall(0, 20, 3));
		app.grid.setTile(1, 1, ObjectMaker.makeBrickwall(20, 20));
		GameLogic.slimeBallLogic(app);
		assertEquals(1, app.slimeBalls.size());
	}

	@Test
	public void fireBallLogicTest() {
        app.fireBalls = new ArrayList<>();
        app.gremlins = new ArrayList<>();
        app.grid = new TileGrid();
        app.delay(1000);

        //Test fireball hit frozen wall
        app.gremlins.add(ObjectMaker.makeGremlin(0, 0, 1));
        app.fireBalls.add(ObjectMaker.makeFireBall(0, 0, 3));
        app.grid.setTile(1, 0, ObjectMaker.makeFrozenwall(20, 20));
        GameLogic.fireBallLogic(app);
        assertEquals(0, app.fireBalls.size());
        assertFalse(app.gremlins.get(0).moving);

        app.fireBalls.add(ObjectMaker.makeFireBall(100, 100, 3));
        GameLogic.fireBallLogic(app);
        assertEquals(1, app.fireBalls.size());
   }

	@Test
	public void evaluatePowerupsTest() {
        app.powerupTiles = new ArrayList<>();
        //powerup empty no overlap
        app.powerupTiles.add(ObjectMaker.makePowerUpTile(0, 0));
        app.wizard = ObjectMaker.makeWizard(100, 100, 1);
        GameLogic.evaluatePowerups(app);
        assertEquals(2, app.wizard.speed);

        //powerup empty overlap
        app.powerupTiles.add(ObjectMaker.makePowerUpTile(90, 100));
        GameLogic.evaluatePowerups(app);
        assertEquals(2, app.wizard.speed);


        //powerup not empty no overlap
        app.powerupTiles.get(0).empty = false;
        GameLogic.evaluatePowerups(app);
        assertEquals(2, app.wizard.speed);

        //powerup not empty overlap
        app.powerupTiles.get(1).empty = false;
        GameLogic.evaluatePowerups(app);
        assertEquals(4, app.wizard.speed);
   }

   @Test
   public void checkWinTest() {
		app.exitLocation = new int[] {20, 20};
		app.wizard = ObjectMaker.makeWizard(20, 25, 1);
		assertTrue(GameLogic.checkWin(app));
   }
}

