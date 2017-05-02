package com.patriciamarissa.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Enemy {
	private int x, y, w, h, hp, speed, spritecount, animatecount ;
	private final int type ;
	private Batch batch ;
	private Texture spritesheet ;
	private Sprite currentsprite ;
	private Sprite [] sprites ;
	private Sprite [] deathSprs ; // not sprites since knowing their widths and height isnt necessary
	boolean leftright ; // true right, false left
	boolean updown ; // true up, false down
	
	public Enemy (Batch batch, int t, int x, int y, int s) {
		spritecount = 0 ;
		animatecount = 2 ;
		type = t ;
		this.x = x ;
		this.y = y ;
		if (type == 0) { // ENEMY TYPE 1: ONLY WALKS AROUND ON A PLATFORM
			// INCOMPLETE
			spritesheet = new Texture  (Gdx.files.internal("walking.png")) ;
			hp = 1 ;
			leftright = false ;
		}
		else if (type == 1) { // ENEMY TYPE 2: FLIES UP AND DOWN BETWEEN PLATS
			// INCOMPLETE
			spritesheet = new Texture  (Gdx.files.internal("flying.png")) ;
			hp = 1 ;
			updown = false ;
		}
		else if (type == 2) { // ENEMY TYPE 3: ONLY SHOOTS STRAIGHT BEAM
			// INCOMPLETE
			spritesheet = new Texture  (Gdx.files.internal("fire laser.png")) ;
			hp = 3 ;
			leftright = false ;
		}
		else if (type == 3) { // ENEMY TYPE 4: CHARGES TOWARDS PLAYER. JUST RUNS ON FLOOR-LEVEL.
			// INCOMPLETE
			spritesheet = new Texture  (Gdx.files.internal("charging.png")) ;
			sprites = new Sprite [6] ;
			//deathSprs = new Sprite [8] ;
			
			sprites [0] = new Sprite (spritesheet, 29, 577, 162, 125) ;
			sprites [1] = new Sprite (spritesheet, 207, 577, 162, 125) ;
			sprites [2] = new Sprite (spritesheet, 376, 577, 162, 125) ;
			sprites [3] = new Sprite (spritesheet, 545, 577, 162, 125) ;
			sprites [4] = new Sprite (spritesheet, 727, 577, 162, 125) ;
			sprites [5] = new Sprite (spritesheet, 917, 577, 162, 125) ;
			
			hp = 2 ;
			leftright = false ;
			currentsprite = sprites [0] ;
		}
		else if (type == 4) { // ENEMY TYPE 5: JUST STANDS THERE. HAS TO BE SHOT A LOT. BETTER TO AVOID.
			// COMPLETE
			spritesheet = new Texture  (Gdx.files.internal("stand still.png")) ;
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
		speed = 2 ; // MAKE IT SO THE ENEMIES SPEED UP THE LONGER YOU'VE BEEN PLAYING? FOR EACH 30 SECOND INTERVAL, INCREASE SPEED BY 1
	}
	
	public void draw() {
		batch.begin();
		currentsprite.draw(batch);
		batch.end();
	}
	
	public void changeDirection() {
		if (type != 2) {
			for (Sprite i : sprites) {
				i.flip(true,false);
			}
			leftright = !leftright ;
		}
		else {
			updown = !updown ;
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
	
	public void walk (int left, int right) { // THIS NEEDS TO TAKE THE PLATFORM BOUNDARIES AS A PARAMETER
		
	}
	
	public void fly () {
		if (y >= 600 - currentsprite.getHeight ()) {
			updown = false ;
		}
		if (y <= 100) {
			updown = true ;
		}
		if (updown == true) {
			y += speed ;
		}
		else {
			y -= speed ;
		}
	}
	
	public void shoot () {
	}
	
	public void charge () {
		
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
	
	public void loseHp (int power) {
		hp -= power ;
	}
	
	public int getSpeed () {
		return speed ;
	}
	
	public void setSpeed (int s) {
		speed = s ;
	}
}
