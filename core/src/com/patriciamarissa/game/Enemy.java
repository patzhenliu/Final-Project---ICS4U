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
			spritesheet = new Texture  (Gdx.files.internal("walking.png")) ;
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
			leftright = false ;
			currentsprite = sprites [0] ;
		}
		else if (type == 1) { // ENEMY TYPE 2: FLIES UP AND DOWN BETWEEN PLATS
			spritesheet = new Texture  (Gdx.files.internal("flying.png")) ;
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
			updown = false ;
			currentsprite = sprites [0] ;
		}
		else if (type == 2) { // ENEMY TYPE 3: ONLY SHOOTS STRAIGHT BEAM. STANDS STILL.
			spritesheet = new Texture  (Gdx.files.internal("fire laser.png")) ;
			sprites = new Sprite [9] ;
			deathSprs = new Sprite [7] ;

			sprites [0] = new Sprite (spritesheet, 0, 552, 181, 152) ;
			sprites [1] = new Sprite (spritesheet, 187, 552, 181, 152) ;
			sprites [2] = new Sprite (spritesheet, 379, 552, 181, 152) ;
			sprites [3] = new Sprite (spritesheet, 567, 552, 181, 152) ;
			sprites [4] = new Sprite (spritesheet, 757, 552, 181, 152) ;
			sprites [5] = new Sprite (spritesheet, 947, 552, 181, 152) ;
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
		else if (type == 3) { // ENEMY TYPE 4: CHARGES TOWARDS PLAYER. JUST RUNS ON FLOOR-LEVEL.
			spritesheet = new Texture  (Gdx.files.internal("charging.png")) ;
			sprites = new Sprite [6] ;
			deathSprs = new Sprite [10] ;
			
			sprites [0] = new Sprite (spritesheet, 29, 577, 162, 125) ;
			sprites [1] = new Sprite (spritesheet, 207, 577, 162, 125) ;
			sprites [2] = new Sprite (spritesheet, 376, 577, 162, 125) ;
			sprites [3] = new Sprite (spritesheet, 545, 577, 162, 125) ;
			sprites [4] = new Sprite (spritesheet, 727, 577, 162, 125) ;
			sprites [5] = new Sprite (spritesheet, 917, 577, 162, 125) ;
			
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
			leftright = false ;
			currentsprite = sprites [0] ;
		}
		else if (type == 4) { // ENEMY TYPE 5: JUST STANDS THERE. HAS TO BE SHOT A LOT. BETTER TO AVOID.
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
	
	public void hurt () { // make the sprite flicker when damage is taken
		
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
}
