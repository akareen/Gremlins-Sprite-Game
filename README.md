# **USYD INFO1113 Gremlins Maze Game**

<img title="Title" src="docs/images/gremlins_gif.gif?raw=true" alt="Alt text" width="325" data-align="center">

<u>*received perfect marks (although the code is not perfect due to time constraints)*</u>

Requires Java 8 and Gradle to run

Build instructions:

```
gradle build
```

Run instructions:

```
gradle run
```



### Game Information

The map is a 33x36 grid of tiles (all tiles are 20x20 pixels).

The bottom bar is used to display the number of lives, spell cooldown, current level number and timer remaining on powerup.

The level files contain a grid of text characters representing what should be in the cell.

- X = Stone walls

- B = Brick walls

- P = Powerup

- F = Frozen wall powerup

- G = where the gremlin is placed

- W = where the player starts from

- Spaces are empty space

- The config file is used to load the game and set cooldowns 



Images of the game objects in <u>src\main\resources\gremlins</u>

#### 

#### Wizard

The player is controlled with the arrow keys. (UP, DOWN, LEFT, RIGHT)

The wizard shoots fireballs by pressing the space bar, they travel in the direction that the wizard is currently facing until they hit an object.

When they hit a brickwall the wall is destroyed, hitting a frozen wall activates the freeze powerup.

When the fireball hits a gremlin it respawns in an empty area in the map at least 10 tiles away from the player.



#### Gremlins

Each gremlin shoots slime projectiles in the direction of their current movement.

If the slime hits the player, the player respawns with a lost life.

If the slime hits a fireball both the slimeball and the fireball are vapourised.



#### Powerup

The powerup symbolised by the lightning causes the players movement to speed up for a fixed portion of time.

The frozen wall once destroyed by a fireball causes all gremlins to freeze in place for a fixed portion of time.



#### Collision

Collision is handled with simple code below (BLOCK_MODIFIER = 19)

```Java
static boolean _hitboxOverlap(int[] coordinate1, int[] coordinate2) {  
    int y1 = coordinate1[0]; int x1 = coordinate1[1];  
    int y2 = coordinate2[0]; int x2 = coordinate2[1];  
    return  (y2 >= y1 && y2 <= (y1 + BLOCK_MODIFIER)) &&  
            (x2 >= x1 && x2 <= (x1 + BLOCK_MODIFIER));  
}
```



#### Win and Lose conditions

The current level is completed when the player reaches the exit. A new level will then begin.

If there are no more levels the player wins.

If the player loses all their lives the player has lost.



#### UML Visualisation

![Alt text](docs/images/Movable.png?raw=true "Title")

*The Movable object class hierachy*



![Alt text](docs/images/Tile.png?raw=true "Title")

*The Tile object class hierachy*



##### Acknowledgements:

The images used in this game were created by the INFO1113 staff at the University of Sydney. The unit coordinator for that unit is Dr. Polash. All the code, logic and gameplay is my own.
