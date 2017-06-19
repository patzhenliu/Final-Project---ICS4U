package com.patriciamarissa.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Player {
	private Batch batch;
	private ArrayList <Laser> lasers ;
	private Texture spritePage;
	private Sprite currentSprite;
	private Sprite[] sprites;
	
	private int dyingSpeed; //how long death lasts for
	private int spriteCount;
	private int speed;
	private int animationCount;
	private int moveSpeed; //speed of background
	
	private int x;
	private int y;
	private int minX, maxX; //player boundaries
	private int groundLvl;
	private int jumpHeight;
	private int lives;
	private int [] powerups;
	private int startSpeed;
	
	private boolean isDead;
	boolean isJumpingUp;
	boolean facingForwards; //true-right false-left
	boolean deactivateHoles ;
	boolean deactivateFire ;
	
	Hole hole;
	Sound jumpSound;
	Sound dieSound;
	
	public Player(Batch batch, int speed, int moveSpeed) {
		// constructor
		this.batch = batch;

		jumpSound = Gdx.audio.newSound(Gdx.files.internal("sounds/jump1.wav")); //temp
		dieSound = Gdx.audio.newSound(Gdx.files.internal("sounds/die.wav")); //temp
		importSprite();
		currentSprite = sprites[0];
		
		startSpeed = speed;
		this.moveSpeed = moveSpeed;
		groundLvl = 100;
		jumpHeight = 150;
		lives = 3;
		
		spriteCount = 0;
		animationCount = 2;
		dyingSpeed = 20;
		
		powerups = new int [6] ; // lasers, life, jump, money, fire, holes
		lasers = new ArrayList <Laser> () ;
		
		facingForwards = true;
		deactivateHoles = false ;
		deactivateFire = false ;
		
		reset(true);
	}
	
	public void importSprite() {
		//import player sprites
		spritePage = new Texture(Gdx.files.internal("sprites/SpriteSheet.png"));
		sprites = new Sprite[4];
		sprites[0] = new Sprite(spritePage, 38, 50, 31, 50);
		sprites[1] = new Sprite(spritePage, 3, 50, 30, 50);
		sprites[2] = new Sprite(spritePage, 38, 50, 31, 50);
		sprites[3] = new Sprite(spritePage, 80, 50, 31, 50);
	}
	
	public void reset(boolean resetLives) {
		//sets variables to their original values
		if (!facingForwards) {
			changeDirection();
		}
		if (resetLives) {
			resetLives () ;
		}
		
		resetPos();
		lasers.clear();
		hole = null;
		minX = -100;
		maxX = Gdx.graphics.getWidth();
		
		groundLvl = 100;
		jumpHeight = 150 + (125 * (powerups [2])) ;
		speed = startSpeed;

		isJumpingUp = false;
		facingForwards = true;
		isDead = false;
				
		if (powerups [4] == 1) {
			deactivateFire = true ;
		}
		if (powerups [5] == 1) {
			deactivateHoles = true ;
		}
	}
	
	public void resetPos() {
		//sets player to original position
		x = 100;
		y = 100;
		for (int i = 0; i < sprites.length; i++) {
			sprites[i].setPosition(x, y);
		}
	}
	
	public void moveLeft() {
		//checks if player can move left
		//direction facing left if he moves
		if (x - speed > minX) {
			x -= speed;
		}
		if (spriteCount == 0) {
			
			spriteCount = sprites.length - 1;
			if (facingForwards) {
				changeDirection();
			}
		}
	}
	
	public void moveRight() {
		//checks if player can move right
		//direction facing right if he moves
		if (x + speed < maxX) {	
			x += speed;
		}
		if (spriteCount == 0) {
			
			spriteCount = sprites.length - 1;
			if (!facingForwards) {
				changeDirection();
			}
		}
	}
	
	public void moveBack() {
		//player moves backwards with the environment
		x -= moveSpeed;
	}
	
	public void jump() {
		if (y <= groundLvl) {
			jumpSound.play();
			isJumpingUp = true;
		}
	}
	
	public void changeDirection() {
		//changes sprites to face to opposite direction
		for (int i = 0; i < sprites.length; i++) {
			sprites[i].flip(true,false);
		}
		facingForwards = !facingForwards;
		
	}
	
	public void draw() {
		//draws player on screen
		batch.begin();
		currentSprite.draw(batch);
		batch.end();
	}
	
	public void move() {
		if (hole != null) {
			//player get stuck in a hole
			setBoundaries(hole.getX(), hole.getX() + hole.getWidth()- getWidth());
		}
			
		moveBack();
		
		//cycles through sprites to animate player
		if (spriteCount > 0) {
			animationCount--;
			if (animationCount == 0) {
				spriteCount--;
				animationCount = speed;
			}
			currentSprite = sprites[spriteCount];
			
		}
		
		if (isJumpingUp) {
			y += speed;
			currentSprite = sprites[3];
		}
		
		if (y >= groundLvl + jumpHeight || Gdx.input.isKeyPressed(Keys.DOWN)) {
			isJumpingUp = false;
			currentSprite = sprites[3];
		}
		if (y > groundLvl && !isJumpingUp) {
			y -= speed;
			currentSprite = sprites[3];
		}
		currentSprite.setPosition(x, y);
	}
	
	public void shoot() {
		//create laser object
		//speed is dependent on the direction player is facing
		int laserSpeed = speed;
		if (facingForwards) {
			if (laserSpeed < 0) {
				laserSpeed = -laserSpeed;
			}
		}
		else {
			if (laserSpeed > 0) {
				laserSpeed = -laserSpeed;
			}
		}
		lasers.add (new Laser (true, x + 10, y + 20, laserSpeed, powerups [0], batch)) ; // PLACEHOLDER X AND Y
	}
	
	public void removeLaser (Laser l) { // removes the laser from player's laser list
		lasers.remove (l) ;
	}
	
	public ArrayList <Laser> getLasers () { // returns player's lasers, used for their movement and enemy collision
		return lasers ;
	}
	
	public void resetOneTimeUps () {
		//upgrades with one time use are reset to not bought
		deactivateFire = false ;
		deactivateHoles = false ;
		powerups [4] = 0 ;
		powerups [5] = 0 ;
	}
	
	public void die(){
		//player loses a life when he dies
		dieSound.play();
		isDead = true;
		lives -= 1 ;
		if (x < 0) {
			x = 0;
		}
	}
	
	public boolean dying() {
		//dying "animation"
		if(isDead){
			if (dyingSpeed > 0) {
				dyingSpeed--;
				batch.begin();
				batch.end();
			}
			else {
				reset(false);
			}
			return true;
			
		}
		return false;
	}
	
	public boolean getIsDead() {
		return isDead;
	}
	
	public void stopJump() { // used when max height is reached or when player wants to drop from their jump
		isJumpingUp = false;
	}
	
	public void endSpriteCycle() {
		spriteCount = 0;
		currentSprite = sprites[0];
	}
	
	public Sprite getSprite() {
		return currentSprite;
	}
	
	public void setGroundLvl(int gl) { // used whenever ground level changes, like when landing on a platform
		groundLvl = gl;
	}
	
	public boolean isJumping() {
		return isJumpingUp;
	}
	
	public int getHeight() {
		return (int) currentSprite.getHeight();
	}
	
	public int getWidth () {
		return (int) currentSprite.getWidth () ;
	}
	
	public void setBoundaries(int min, int max) {
		// used when a player falls into a hole, to prevent them from clipping from the hole into the floor
		minX = min;
		maxX = max;
	}
	
	public void setInHole(Hole h) { // fell in a hole? thats the playa's hole now
		hole = h;
	}
	
	public void losePower (int index) { // used when removing the one-time powerups
		powerups [index] = 0 ;
	}
	
	public int getX() {
		return x;
	}
	public int getLives() {
		return lives ;
	}
	
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public int getSpeed() {
		return speed;
	}
	
	public int getY () {
		return y ;
	}
	
	public void setMoveSpeed(int moveSpeed) { // sets speed player moves with environment
		this.moveSpeed = moveSpeed;
	}
	
	public void resetLives() { // called after a gameover, fully restores player's lives
		lives = 3;
		lives += powerups [1] ;
	}
	
	public int [] getPowers () { // needed to edit the powerup list in shop
		return powerups ;
	}
	
	public int getMoneyMult () {
		// returns what the money val is multiplied by so when u collect in main, coins increases by the multiplier
		return powerups [3] ;
	}
	
	public int getLaserStrength () { // returns how hard the enemy'll get hit
		return powerups [0] ;
	}
	
	public int getDyingSpeed() { // gives the dying speed.
		return dyingSpeed;
	}
	
	public boolean getFacingForwards() {
		// does things like determine which sprites to use, which way to shoot the player lasers
		return facingForwards;
	}
}
