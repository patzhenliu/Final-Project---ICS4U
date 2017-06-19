package com.patriciamarissa.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class Enemy {
	private int x, y, hp, speed, spritecount, animatecount, movespeed, deathcount ;
	private Platform plat ;
	private final int type, tree, gargoyle, golem, lion ;
	private Batch batch ;
	private Texture spritesheet ;
	private Texture gl8, gl7, gl6, gl5, gl4, gl3, gl2, gl1, gl0 ; // golem pics seperate bc they each got resized
	private Sprite currentsprite ;
	private Sprite [] sprites ;
	private Sprite [] deathSprs ; // not sprites since knowing their widths and height isnt necessary
	private ArrayList <Laser> lasers ;
	private ArrayList <Hole> holes ;
	boolean right ; // true right, false left
	boolean up ; // true up, false down
	boolean dying ;
	private int minX, maxX, minY, maxY;
	private boolean areHolesDead ;
	
	public Enemy (Batch batch, int t, int x, int y, int s, Platform plat, ArrayList <Hole> h, boolean areHolesDead) {
		// constructor
		dying = false ;
		deathcount = 0 ;
		spritecount = 0 ;
		animatecount = 3 ;
		tree = 1 ;
		gargoyle = 2 ;
		golem = 3 ;
		lion = 4 ;
		type = t + 1 ;
		speed = s ;
		this.batch = batch ;
		this.plat = plat ;
		this.y = y ;
		this.areHolesDead = areHolesDead ;
		holes = h ;
		lasers = new ArrayList <Laser> () ;
		if (type == tree) { // ENEMY TYPE 1: WALKS AROUND ON PLATFORM
			spritesheet = new Texture  (Gdx.files.internal("sprites/walking.png")) ;
			sprites = new Sprite [4] ;
			deathSprs = new Sprite [5] ;
			
			sprites [0] = new Sprite (spritesheet, 0, 80, 70, 77) ;
			sprites [1] = new Sprite (spritesheet, 70, 77, 70, 80) ;
			sprites [2] = new Sprite (spritesheet, 144, 81, 70, 77) ;
			sprites [3] = new Sprite (spritesheet, 212, 82, 70, 77) ;
			
			deathSprs [4] = new Sprite (spritesheet, 0, 162, 64, 96) ;
			deathSprs [3] = new Sprite (spritesheet, 0, 263, 66, 97) ;
			deathSprs [2] = new Sprite (spritesheet, 77, 265, 62, 95) ;
			deathSprs [1] = new Sprite (spritesheet, 150, 272, 66, 87) ;
			deathSprs [0] = new Sprite (spritesheet, 225, 289, 64, 69) ;
			
			hp = 1 ;
			right = false ;
			currentsprite = sprites [0] ;
			movespeed = 4 ;
		}
		else if (type == gargoyle) { // ENEMY TYPE 2: FLIES UP AND DOWN BETWEEN PLATS
			spritesheet = new Texture  (Gdx.files.internal("sprites/flying.png")) ;
			sprites = new Sprite [4] ;
			deathSprs = new Sprite [5] ;
			
			sprites [3] = new Sprite (spritesheet, 5, 10, 79, 114) ;
			sprites [2] = new Sprite (spritesheet, 102, 46, 81, 78) ;
			sprites [1] = new Sprite (spritesheet, 205, 45, 90, 80) ;
			sprites [0] = new Sprite (spritesheet, 311, 31, 103, 92) ;
			
			deathSprs [4] = new Sprite (spritesheet, 8, 409, 91, 100) ;
			deathSprs [3] = new Sprite (spritesheet, 116, 372, 101, 136) ;
			deathSprs [2] = new Sprite (spritesheet, 245, 364, 81, 144) ;
			deathSprs [1] = new Sprite (spritesheet, 352, 355, 81, 136) ;
			deathSprs [0] = new Sprite (spritesheet, 455, 394, 81, 144) ;
			
			hp = 1 ;
			up = false ;
			currentsprite = sprites [0] ;
			movespeed = 4 ;
		}
		else if (type == golem) { // ENEMY TYPE 3: ONLY SHOOTS STRAIGHT BEAM. STANDS STILL.
			spritesheet = new Texture  (Gdx.files.internal("sprites/fire laser.png")) ;
			gl8 = new Texture  (Gdx.files.internal("sprites/fl0.png")) ;
			gl7 = new Texture  (Gdx.files.internal("sprites/fl1.png")) ;
			gl6 = new Texture  (Gdx.files.internal("sprites/fl2.png")) ;
			gl5 = new Texture  (Gdx.files.internal("sprites/fl3.png")) ;
			gl4 = new Texture  (Gdx.files.internal("sprites/fl4.png")) ;
			gl3 = new Texture  (Gdx.files.internal("sprites/fl5.png")) ;
			gl2 = new Texture  (Gdx.files.internal("sprites/fl6.png")) ;
			gl1 = new Texture  (Gdx.files.internal("sprites/fl7.png")) ;
			gl0 = new Texture  (Gdx.files.internal("sprites/fl8.png")) ;
			sprites = new Sprite [9] ;
			deathSprs = new Sprite [7] ;
			
			sprites [8] = new Sprite (gl8) ;
			sprites [7] = new Sprite (gl7) ;
			sprites [6] = new Sprite (gl6) ;
			sprites [5] = new Sprite (gl5) ;
			sprites [4] = new Sprite (gl4) ;
			sprites [3] = new Sprite (gl3) ;
			sprites [2] = new Sprite (gl2) ;
			sprites [1] = new Sprite (gl1) ;
			sprites [0] = new Sprite (gl0) ;

			deathSprs [6] = new Sprite (spritesheet, 0, 727, 204, 158) ;
			deathSprs [5] = new Sprite (spritesheet, 212, 727, 237, 158) ;
			deathSprs [4] = new Sprite (spritesheet, 416, 727, 228, 158) ;
			deathSprs [3] = new Sprite (spritesheet, 712, 727, 224, 158) ;
			deathSprs [2] = new Sprite (spritesheet, 956, 727, 222, 158) ;
			deathSprs [1] = new Sprite (spritesheet, 1197, 727, 222, 158) ;
			deathSprs [0] = new Sprite (spritesheet, 1437, 727, 222, 158) ;
			
			hp = 3 ;
			animatecount = 5 ;
			currentsprite = sprites [0] ;
		}
		else if (type == lion) { // ENEMY TYPE 4: CHARGES TOWARDS PLAYER. JUST RUNS ON FLOOR-LEVEL.
			spritesheet = new Texture  (Gdx.files.internal("sprites/charging.png")) ;
			sprites = new Sprite [6] ;
			deathSprs = new Sprite [10] ;
			
			sprites [5] = new Sprite (spritesheet, 29, 577, 162, 125) ;
			sprites [4] = new Sprite (spritesheet, 207, 577, 162, 125) ;
			sprites [3] = new Sprite (spritesheet, 376, 577, 162, 125) ;
			sprites [2] = new Sprite (spritesheet, 545, 577, 162, 125) ;
			sprites [1] = new Sprite (spritesheet, 727, 577, 162, 125) ;
			sprites [0] = new Sprite (spritesheet, 917, 577, 162, 125) ;
			
			deathSprs [9] = new Sprite (spritesheet, 30, 1004, 165, 124) ;
			deathSprs [8] = new Sprite (spritesheet, 196, 1004, 165, 124) ;
			deathSprs [7] = new Sprite (spritesheet, 371, 1004, 165, 124) ;
			deathSprs [6] = new Sprite (spritesheet, 540, 1004, 165, 124) ;
			deathSprs [5] = new Sprite (spritesheet, 710, 1004, 165, 124) ;
			deathSprs [4] = new Sprite (spritesheet, 882, 1004, 165, 124) ;
			deathSprs [3] = new Sprite (spritesheet, 30, 1169, 165, 124) ;
			deathSprs [2] = new Sprite (spritesheet, 179, 1169, 159, 124) ;
			deathSprs [1] = new Sprite (spritesheet, 320, 1169, 165, 124) ;
			deathSprs [0] = new Sprite (spritesheet, 440, 1134, 155, 147) ;
			
			hp = 2 ;
			right = false ;
			currentsprite = sprites [0] ;
			movespeed = 8 ;
		}
		
		if (type == gargoyle || type == lion) {
			this.x = x ;
		}
		if (type == golem) { // pushing their sprite over to fully fit on the plat
			this.x = x - 200 ;
		}
		if (type == tree) { // pushing their sprite over to fully fit on the plat
			this.x = x - 100 ;
		}
		
		if (type != lion) { // lion adheres to the floor, but with all else it doesnt matter
			minX = plat.getX () ;
			maxX = plat.getX () + plat.getWidth () ;
		}
		else { // it's a lion.
			if (areHolesDead) { // no holes, no restrictions on how far it can go on the screen
				minX = -1000 ;
				maxX = 1000 ;
			}
			else {
				boolean onleft = false ;
				boolean onright = false ;
				for (int i = 0 ; i < holes.size () ; i++) {
					if (holes.get(i).getX () >= this.x + currentsprite.getWidth () && (onright == false || maxX > holes.get(i).getX ())) {
						// if there's a hole on the right, register its x as the right boundary.
						// if there's another hole on the right but one's already been registered, check the registered one.
						// use the closer hole as the boundary.
						maxX = holes.get(i).getX () ;
						onright = true ;
					}
					if (holes.get(i).getX () + holes.get(i).getWidth () <= this.x && (onleft == false || minX < holes.get(i).getX () + holes.get(i).getWidth ())) {
						// if there's a hole on the left, register its x as the right boundary.
						// if there's another hole on the left but one's already been registered, check the registered one.
						// use the closer hole as the boundary.
						minX = holes.get(i).getX () + holes.get(i).getWidth () ;
						onleft = true ;
					}
				}
				if (onright == false) { // no holes on the right.
					maxX = 1000 ;
				}
				if (onleft == false) { // no holes on the left.
					minX = 0 ;
				}
			}
		}
		if (type != gargoyle) { // the y vals of any enemy besides the gargoyles do not matter.
			minY = 100 ;
			maxY = 100 ;
		}
		else { // it's a gargoyle.
			minY = 100 ;
			maxY = 600 - (int) currentsprite.getHeight () ;
		}
	}
	
	public void draw() { // as long as the enemy is living, draw it
		currentsprite.setPosition(x,y);
		batch.begin();
		if (!isDead ()) {
			currentsprite.draw(batch);
		}
		batch.end();
	}
	
	public void changeDirection() { // flip which way the enemy is going, and their sprites if not a gargoyle
		if (type != gargoyle) {
			for (Sprite i : sprites) {
				i.flip(true,false);
			}
			right = !right ;
		}
		else {
			up = !up ;
		}
	}
	
	public void moveWithPlat () { // moves with rest of screen
		x -= speed ;
		updateBoundaries () ;
	}
	
	public void updateBoundaries () { // boundaries must change as platform moves
		minX -= speed ;
		if (type == tree) {
			maxX = minX + plat.getWidth () - (int) currentsprite.getWidth () ;
		}
		if (type == lion && maxX == 1000 && areHolesDead == false) { // if there wasn't a hole on the right, need to check if a new hole has appeared.
			boolean onright = false ;
			for (int i = 0 ; i < holes.size () ; i++) {
				if (holes.get(i).getX () >= this.x + currentsprite.getWidth () && (onright == false || maxX > holes.get(i).getX ())) {
					maxX = holes.get(i).getX () ;
					onright = true ;
				}
			}
		}
		else {
			maxX -= speed ;
		}
		maxY = 600 - (int) currentsprite.getHeight () ;
	}
	
	public void moveLeft() {
		x -= movespeed;
		if (spritecount == 0) {
			spritecount = sprites.length - 1;
			if (right) { // if moving left but boolean right is still true, change it to move left
				changeDirection();
			}
		}
	}
	
	public void moveRight() {
		x += movespeed;
		if (spritecount == 0) {
			spritecount = sprites.length - 1;
			if (!right) { // if moving right but boolean right is false, change it so it'll keep moving right
				changeDirection();
			}
		}
	}
	
	public void moveUp () {
		y += movespeed ;
		if (spritecount == 0) {
			spritecount = sprites.length - 1 ;
			if (!up) { // if moving up but up is false, change it to keep moving up
				changeDirection () ;
			}
		}
	}
	
	public void moveDown () {
		y -= movespeed ;
		if (spritecount == 0) {
			spritecount = sprites.length - 1 ;
			if (up) { // if moving down but boolean up is true, change it so it stays moving down
				changeDirection () ;
			}
		}
	}
	
	public void move () { // enemy's movement independent of the screen movement. golem doesn't move, but its lasers do.
		if (type == lion) {
			if (right) { // going right
				if (currentsprite.getX () + currentsprite.getWidth () < maxX) { // hasn't hit max right yet
					moveRight () ;
				}
				else { // hit max right, go left
					moveLeft () ;
				}
			}
			else if (!right) { // going left
				if (x - movespeed > minX) { // hasnt hit min left yet
					moveLeft () ;
				}
				else { // hit min left, go right
					moveRight () ;
				}
			}
		}
		if (type == tree) {
			if (right) { // going right
				if (currentsprite.getX () < maxX) {
					moveRight () ;
				}
				else {
					x -= movespeed * 2 ;
					moveLeft () ;
				}
			}
			else if (!right) { // going left
				if (x - movespeed > minX) {
					moveLeft () ;
				}
				else {
					x += movespeed * 2 ;
					moveRight () ;
				}
			}
		}
		if (type == gargoyle) {
			if (up) { // going up
				if (y < maxY) {
					moveUp () ;
				}
				else { // hit the roof, go down
					moveDown () ;
				}
			}
			else if (!up) { // going down
				if (y - movespeed > minY) {
					moveDown ()  ;
				}	
				else { // hit the floor, go up
					moveUp () ;
				}
			}
		}
		if (type == golem) {
			if (spritecount == 0) {
				spritecount = sprites.length - 1 ;
			}
		}
		if (spritecount > 0) {
			animatecount--;
			if (animatecount == 0) {
				spritecount--;
				if (type != golem) {
					animatecount = 3;
				}
				else {
					animatecount = 5 ;
				}
			}
			currentsprite = sprites[spritecount];
		}
		currentsprite.setPosition (x, y) ;
		
		if (type == golem && currentsprite == sprites [5]) { // make the golem shoot when it shines the brightest!
			shoot () ;
		}
	}
	
	public void die () {
		// start dying animation, but not fully dead yet, so animation is still visible
		dying = true ;
		spritecount = deathSprs.length - 1 ;
		animatecount = 3 ;
		currentsprite = deathSprs [0] ;
	}
	
	public void animateDeath () { // uses a different sprite list than the regular animate, therefore seperate method
		if (spritecount > 0) {
			animatecount--;
			if (animatecount == 0) {
				spritecount--;
				deathcount ++ ;
				animatecount = 3;
			}
			currentsprite = deathSprs[spritecount];
		}
		currentsprite.setPosition (x, y) ;
	}
	
	public boolean isDead () { // once it finishes the death animation cycle, officially pronounce it as dead
		// truly dead and it'll be removed from enemies/won't be drawn
		if (deathcount == deathSprs.length - 1) {
			return true ;
		}
		else {
			return false ;
		}
	}
	
	public boolean collide (Player player) { // return whether or not it collided with the player
		Sprite playerSprite = player.getSprite();
		Rectangle rect = new Rectangle(playerSprite.getX(), playerSprite.getY(), playerSprite.getWidth(), playerSprite.getHeight());
		Rectangle logRect = new Rectangle(x, y, currentsprite.getWidth (), currentsprite.getHeight ());
		return rect.overlaps(logRect);
	}
	
	public void loseHp (int damage) { // get damaged
		hp -= damage ;
		if (hp <= 0) { // killed
			die () ;
		}
	}
	
	public void shoot () { // golem shoots
		lasers.add (new Laser (false, x, y + 50, speed, 1, batch)) ;
	}
	
	public void removeLaser (Laser l) { // removes laser from golem's laser list
		lasers.remove (l) ;
	}
	
	public Sprite getSprite () { // returns current sprite
		return currentsprite ;
	}
	
	public ArrayList <Laser> getLasers () { // returns golem's lasers
		return lasers ;
	}
	
	public int getHp () { // returns hp
		return hp ;
	}
	
	public int getSpeed () { // returns the movespeed that goes with the platforms, not the enemy's independent speed
		return speed ;
	}
	
	public void setSpeed (int s) { // sets the moving-with-platform speed. used whenever there's a speedup.
		speed = s ;
	}
	
	public int getType () { // returns what kind of enemy this enemy is
		return type ;
	}
	
	public int getX () { // returns x
		return x ;
	}
	
	public int getWidth () { // returns current width
		return (int)currentsprite.getWidth () ;
	}
}
