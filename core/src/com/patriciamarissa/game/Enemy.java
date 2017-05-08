package com.patriciamarissa.game;

import java.awt.Robot;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class Enemy { 
	// BUGS: ENEMIES DONT SWITCH DIRECTION, THEY GET STUCK, SOMETIMES WALK OVER PLATFORM GAPS
	// WHEN FIRST MADE THEY END UP ON THE TITLE SCREEN AND THEY ALL GENERATE ON THE FIRST PLATFORM
	// AGHHHHH LION AND GARGOYLE HAVE TO WATCH FOR HOLES
	private int x, y, hp, speed, spritecount, animatecount, movespeed, ox ;
	private Platform plat ;
	private final int type, tree, gargoyle, rock, golem, lion ;
	private Batch batch ;
	private Texture spritesheet ;
	private Texture blank ;
	private Sprite currentsprite ;
	private Sprite [] sprites ;
	private Sprite [] deathSprs ; // not sprites since knowing their widths and height isnt necessary
	private ArrayList <Laser> lasers ;
	boolean right ; // true right, false left
	boolean up ; // true up, false down
	
	public Enemy (Batch batch, int t, int x, int y, int s, Platform plat) {
		spritecount = 0 ;
		tree = 1 ;
		gargoyle = 2 ;
		golem = 3 ;
		lion = 4 ;
		rock = 5 ;
		type = t + 1 ;
		//blank = new Texture (Gdx.files.internal("sprites/blank.png")) ;
		speed = s ;
		this.batch = batch ;
		this.plat = plat ;
		ox = plat.getX () ; // original x; the platform's x gets pushed to the other side sometimes
		this.x = x ;
		this.y = y ;
		lasers = new ArrayList <Laser> () ;
		if (type == tree) { // ENEMY TYPE 1: ONLY WALKS AROUND ON A PLATFORM
			spritesheet = new Texture  (Gdx.files.internal("sprites/walking.png")) ;
			animatecount = 4 ;
			sprites = new Sprite [4] ;
			deathSprs = new Sprite [5] ;
			
			sprites [0] = new Sprite (spritesheet, 0, 80, 67, 77) ;
			sprites [1] = new Sprite (spritesheet, 70, 77, 70, 80) ;
			sprites [2] = new Sprite (spritesheet, 144, 81, 71, 77) ;
			sprites [3] = new Sprite (spritesheet, 220, 82, 78, 77) ;
			
			deathSprs [0] = new Sprite (spritesheet, 0, 162, 64, 96) ;
			deathSprs [1] = new Sprite (spritesheet, 0, 263, 66, 97) ;
			deathSprs [2] = new Sprite (spritesheet, 77, 265, 62, 95) ;
			deathSprs [3] = new Sprite (spritesheet, 150, 272, 66, 87) ;
			deathSprs [4] = new Sprite (spritesheet, 225, 289, 64, 69) ;
			
			hp = 1 ;
			right = false ;
			currentsprite = sprites [0] ;
			movespeed = 4 ;
		}
		else if (type == gargoyle) { // ENEMY TYPE 2: FLIES UP AND DOWN BETWEEN PLATS
			spritesheet = new Texture  (Gdx.files.internal("sprites/flying.png")) ;
			animatecount = 4 ;
			sprites = new Sprite [4] ;
			deathSprs = new Sprite [5] ;
			
			sprites [0] = new Sprite (spritesheet, 5, 10, 79, 114) ;
			sprites [1] = new Sprite (spritesheet, 102, 46, 81, 78) ;
			sprites [2] = new Sprite (spritesheet, 205, 45, 90, 80) ;
			sprites [3] = new Sprite (spritesheet, 311, 31, 103, 92) ;
			
			deathSprs [0] = new Sprite (spritesheet, 8, 409, 91, 100) ;
			deathSprs [1] = new Sprite (spritesheet, 116, 372, 101, 136) ;
			deathSprs [2] = new Sprite (spritesheet, 245, 364, 81, 144) ;
			deathSprs [3] = new Sprite (spritesheet, 352, 355, 81, 136) ;
			deathSprs [4] = new Sprite (spritesheet, 455, 394, 81, 144) ;
			
			hp = 1 ;
			up = false ;
			currentsprite = sprites [0] ;
			movespeed = 4 ;
		}
		else if (type == golem) { // ENEMY TYPE 3: ONLY SHOOTS STRAIGHT BEAM. STANDS STILL.
			spritesheet = new Texture  (Gdx.files.internal("sprites/fire laser.png")) ;
			animatecount = 9 ;
			sprites = new Sprite [9] ;
			deathSprs = new Sprite [7] ;

			sprites [0] = new Sprite (spritesheet, 0, 552, 181, 152) ;
			sprites [1] = new Sprite (spritesheet, 187, 552, 181, 152) ;
			sprites [2] = new Sprite (spritesheet, 379, 552, 181, 152) ;
			sprites [3] = new Sprite (spritesheet, 567, 552, 181, 152) ;
			sprites [4] = new Sprite (spritesheet, 757, 552, 181, 152) ;
			sprites [5] = new Sprite (spritesheet, 947, 552, 181, 152) ; // THE FIRING
			sprites [6] = new Sprite (spritesheet, 1133, 523, 184, 181) ;
			sprites [7] = new Sprite (spritesheet, 1323, 552, 181, 152) ;
			sprites [8] = new Sprite (spritesheet, 1518, 552, 181, 152) ;

			deathSprs [0] = new Sprite (spritesheet, 0, 727, 204, 158) ;
			deathSprs [1] = new Sprite (spritesheet, 212, 727, 237, 158) ;
			deathSprs [2] = new Sprite (spritesheet, 416, 727, 228, 158) ;
			deathSprs [3] = new Sprite (spritesheet, 712, 727, 224, 158) ;
			deathSprs [4] = new Sprite (spritesheet, 956, 727, 222, 158) ;
			deathSprs [5] = new Sprite (spritesheet, 1197, 727, 222, 158) ;
			deathSprs [6] = new Sprite (spritesheet, 1437, 727, 222, 158) ;
			
			hp = 3 ;
			currentsprite = sprites [0] ;
		}
		else if (type == lion) { // ENEMY TYPE 4: CHARGES TOWARDS PLAYER. JUST RUNS ON FLOOR-LEVEL.
			spritesheet = new Texture  (Gdx.files.internal("sprites/charging.png")) ;
			animatecount = 6 ;
			sprites = new Sprite [6] ;
			deathSprs = new Sprite [10] ;
			
			sprites [5] = new Sprite (spritesheet, 29, 577, 162, 125) ;
			sprites [4] = new Sprite (spritesheet, 207, 577, 162, 125) ;
			sprites [3] = new Sprite (spritesheet, 376, 577, 162, 125) ;
			sprites [2] = new Sprite (spritesheet, 545, 577, 162, 125) ;
			sprites [1] = new Sprite (spritesheet, 727, 577, 162, 125) ;
			sprites [0] = new Sprite (spritesheet, 917, 577, 162, 125) ;
			
			deathSprs [0] = new Sprite (spritesheet, 30, 1004, 165, 124) ;
			deathSprs [1] = new Sprite (spritesheet, 196, 1004, 165, 124) ;
			deathSprs [2] = new Sprite (spritesheet, 371, 1004, 165, 124) ;
			deathSprs [3] = new Sprite (spritesheet, 540, 1004, 165, 124) ;
			deathSprs [4] = new Sprite (spritesheet, 710, 1004, 165, 124) ;
			deathSprs [5] = new Sprite (spritesheet, 882, 1004, 165, 124) ;
			deathSprs [6] = new Sprite (spritesheet, 30, 1169, 165, 124) ;
			deathSprs [7] = new Sprite (spritesheet, 179, 1169, 159, 124) ;
			deathSprs [8] = new Sprite (spritesheet, 320, 1169, 165, 124) ;
			deathSprs [9] = new Sprite (spritesheet, 440, 1134, 155, 147) ;
			
			hp = 2 ;
			right = false ;
			currentsprite = sprites [0] ;
			movespeed = 8 ;
		}
		else if (type == rock) { // ENEMY TYPE 5: JUST STANDS THERE. HAS TO BE SHOT A LOT. BETTER TO AVOID.
			spritesheet = new Texture  (Gdx.files.internal("sprites/stand still.png")) ;
			animatecount = 0 ;
			deathSprs = new Sprite [6] ;
			
			deathSprs [0] = new Sprite (spritesheet, 6, 776, 159, 100) ;
			deathSprs [1] = new Sprite (spritesheet, 191, 776, 159, 100) ;
			deathSprs [2] = new Sprite (spritesheet, 364, 776, 159, 100) ;
			deathSprs [3] = new Sprite (spritesheet, 553, 776, 159, 100) ;
			deathSprs [4] = new Sprite (spritesheet, 746, 776, 159, 100) ;
			deathSprs [5] = new Sprite (spritesheet, 956, 776, 159, 100) ;
			
			currentsprite = new Sprite (spritesheet, 3, 10, 128, 116) ;
			hp = 5 ;
		}
	}
	
	public void draw() {
		currentsprite.setPosition(x,y); //:PP
		batch.begin();
		currentsprite.draw(batch);
		batch.end();
	}
	
	public void changeDirection() {
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
	
	public void moveWithPlat () {
		x -= speed ;
		ox -= speed ;
		//System.out.println (ox) ;
	}
	
	public void moveLeft() {
		x -= movespeed;
		if (spritecount == 0) {
			spritecount = sprites.length - 1;
			if (right) {
				changeDirection();
			}
		}
	}
	
	public void moveRight() {
		//if (x + movespeed < Gdx.graphics.getWidth()) {
			x += movespeed;
		//}
		if (spritecount == 0) {
			spritecount = sprites.length - 1;
			if (!right) {
				changeDirection();
			}
		}
	}
	
	public void moveUp () {
		y += movespeed ;
		if (spritecount == 0) {
			spritecount = sprites.length - 1 ;
			if (!up) {
				changeDirection () ;
			}
		}
	}
	
	public void moveDown () {
		y -= movespeed ;
		if (spritecount == 0) {
			spritecount = sprites.length - 1 ;
			if (up) {
				changeDirection () ;
			}
		}
	}
	
	public void move (ArrayList <Hole> holes) {
		// TYPES 2 AND 4 DONT MOVE. TYPE 2 SHOOTS A LASER THO.
		boolean tf = false ;
		if (type == lion) {
			if (right) { // going right
				for (Hole h : holes) {
					if (h.getOriginalX () > x + currentsprite.getWidth () && tf == false) {
						moveRight () ;
						tf = true ;
					}
				}
				if (tf == false) {
					moveLeft () ;
				}
			}
			else if (!right) { // going left
				for (Hole h : holes) {
					if (h.getOriginalX () + h.getWidth () < x && tf == false) {
						moveLeft () ;
						tf = true ;
					}
				}
				if (tf == false) {
					moveRight () ;
				}
			}
			//moveLeft () ;
		}
		if (type == gargoyle) {
			if (up) { // going up
				if (y < 600 - currentsprite.getHeight ()) {
					moveUp () ;
				}
				else {
					moveDown () ;
				}
			}
			else if (!up) { // going down
				if (y - movespeed > 100) {
					moveDown () ;
					tf = true ;
				}
				if (tf == false) {
					for (Hole h : holes) {
						if (h.collide(currentsprite) && tf == false && y - movespeed > 0) {
							moveDown () ;
							tf = true ;
						}
					}
				}
				if (tf == false) {
					moveUp () ;
				}
			}
		}
		if (type == tree) { // its the tree that walks based on boundaries
			if (right) { // going right
				if (x + currentsprite.getWidth () + movespeed < ox + plat.getWidth ()) {
					moveRight () ;
				}
				else {
					moveLeft () ;
				}

			 
		}
		if (type == 0) { // its the tree that walks based on boundaries
			if (right) { // going right
				if (x + currentsprite.getWidth () >= ox + plat.getWidth
						()) {

			}
			else if (!right) {
				if (x - movespeed > ox) {

					moveLeft () ;
				}
				else {
					moveRight () ;
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
				animatecount = speed;
			}
			currentsprite = sprites[spritecount];
		}
		currentsprite.setPosition (x, y) ;
		
		if (type == golem && currentsprite == sprites [5]) {
			shoot () ;
		}
		}
	}
	}
	
	public void die () {
		//dying "animation"
		batch.begin () ;
		for (Sprite i : deathSprs) {
			batch.draw(i, x, y) ;
		}
		batch.end () ;
	}
	
	public boolean collide (Player player) { // NOT IN USE YET
		Sprite playerSprite = player.getSprite();
		Rectangle rect = new Rectangle(playerSprite.getX(), playerSprite.getY(), playerSprite.getWidth(), playerSprite.getHeight());
		Rectangle logRect = new Rectangle(x, y, currentsprite.getWidth (), currentsprite.getHeight ());
		return rect.overlaps(logRect);
	}
	
	public void hurt (int damage) { // make the sprite flicker when damage is taken
		hp -= damage ;
		if (hp <= 0) {
			die () ;
		}
		else {
			batch.begin () ;
			batch.draw (blank, x, y) ;
			batch.end () ;
		}
	}
	
	public void shoot () {
		lasers.add (new Laser (1, x - 51, y + 100, speed, 1, batch)) ; // PLACEHOLDER X AND Y
	}
	
	public void removeLaser (Laser l) {
		lasers.remove (l) ;
	}
	
	public boolean checkIfDead () {
		if (hp <= 0) {
			return true ;
		}
		else {
			return false ;
		}
	}
	
	public Sprite getSprite () {
		return currentsprite ;
	}
	
	public ArrayList <Laser> getLasers () {
		return lasers ;
	}
	
	public int getHp () {
		return hp ;
	}
	
	public int getSpeed () {
		return speed ;
	}
	
	public void setSpeed (int s) {
		speed = s ;
	}
	
	public int getType () {
		return type ;
	}
	
	public int getX () {
		return x ;
	}
	
	public int getWidth () {
		return (int)currentsprite.getWidth () ;
	}
}
