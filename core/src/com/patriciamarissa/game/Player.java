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
	private boolean isDead;
	private Texture deathImg;
	
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
	
	boolean isJumpingUp;
	boolean facingForwards; //true-right false-left
	boolean deactivateHoles ;
	boolean deactivateFire ;
	
	Hole hole;
	Sound jumpSound;
	Sound dieSound;
	
	public Player(Batch batch, int speed, int moveSpeed) {
		this.batch = batch;
		startSpeed = speed;
		this.moveSpeed = moveSpeed;
		deathImg = new Texture(Gdx.files.internal("sprites/death.png")) ;
		importSprite();
		currentSprite = sprites[0];
		spriteCount = 0;
		animationCount = 2;
		powerups = new int [6] ; // lasers, life, jump, money, fire, holes
		groundLvl = 100;
		jumpHeight = 150;
		facingForwards = true;
		lives = 3;
		lasers = new ArrayList <Laser> () ;
		reset();
		hole = null;
		deactivateHoles = false ;
		deactivateFire = false ;
		
		jumpSound = Gdx.audio.newSound(Gdx.files.internal("sounds/jump1.wav")); //temp
		dieSound = Gdx.audio.newSound(Gdx.files.internal("sounds/die.wav")); //temp
	}
	
	public void importSprite() {
		spritePage = new Texture(Gdx.files.internal("sprites/SpriteSheet.png"));
		sprites = new Sprite[4];
		sprites[0] = new Sprite(spritePage, 38, 50, 31, 50);
		sprites[1] = new Sprite(spritePage, 3, 50, 30, 50);
		sprites[2] = new Sprite(spritePage, 38, 50, 31, 50);
		sprites[3] = new Sprite(spritePage, 80, 50, 31, 50);
	}
	
	public void reset() {
		
		if (!facingForwards) {
			changeDirection();
		}
		resetPos();
		lasers.clear();
		hole = null;
		minX = -100;
		maxX = Gdx.graphics.getWidth();
		if (lives <= 0) {
			resetLives () ;
		}
		groundLvl = 100;
		jumpHeight = 150 + (125 * (powerups [2])) ;
		speed = startSpeed;
		isJumpingUp = false;
		facingForwards = true;
		isDead = false;
		dyingSpeed = 20;
		
		if (powerups [4] == 1) {
			deactivateFire = true ;
		}
		if (powerups [5] == 1) {
			deactivateHoles = true ;
		}
	}
	
	public void resetPos() {
		x = 100;
		y = 100;
		for (int i = 0; i < sprites.length; i++) {
			sprites[i].setPosition(x, y);
		}
	}
	
	public void moveLeft() {
		
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
		x -= moveSpeed;
	}
	
	public void jump() {
		if (y <= groundLvl) {
			jumpSound.play();
			isJumpingUp = true;
		}
	}
	
	public void changeDirection() {
		
		for (int i = 0; i < sprites.length; i++) {
			sprites[i].flip(true,false);
		}
		facingForwards = !facingForwards;
		
	}
	
	public void draw() {
		batch.begin();
		currentSprite.draw(batch);
		batch.end();
	}
	
	public void move() {
		if (hole != null) {
			//System.out.println("BOUNDARIES RESET!");
			setBoundaries(hole.getX(), hole.getX() + hole.getWidth()- getWidth());
		}
			//System.out.println(facingForwards);
		moveBack();
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
		//System.out.println(x+","+y);
		currentSprite.setPosition(x, y);
	}
	
	public void shoot() {
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
	
	public void removeLaser (Laser l) {
		lasers.remove (l) ;
	}
	
	public ArrayList <Laser> getLasers () {
		return lasers ;
	}
	
	public void resetOneTimeUps () {
		deactivateFire = false ;
		deactivateHoles = false ;
		powerups [4] = 0 ;
		powerups [5] = 0 ;
	}
	
	public void die(){
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
				batch.draw(deathImg, x, y);
				batch.end();
			}
			else {
				reset();
			}
			return true;
			
		}
		return false;
	}
	
	public boolean getIsDead() {
		return isDead;
	}
	
	public void stopJump() {
		isJumpingUp = false;
	}
	
	public void endSpriteCycle() {
		spriteCount = 0;
		currentSprite = sprites[0];
	}
	
	public Sprite getSprite() {
		return currentSprite;
	}
	
	public void setGroundLvl(int gl) {
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
		minX = min;
		maxX = max;
	}
	
	public void setInHole(Hole h) {
		hole = h;
	}
	
	public void losePower (int index) {
		powerups [index] = 0 ;
	}
	
	public int getJumpHeight() { //not in use
		return jumpHeight;
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
	
	public void setMoveSpeed(int moveSpeed) {
		this.moveSpeed = moveSpeed;
	}
	
	public void resetLives() {
		lives = 3;
		lives += powerups [1] ;
	}
	
	public int [] getPowers () {
		return powerups ;
	}
	
	public int getMoneyMult () {
		return powerups [3] ;
	}
	
	public int getLaserStrength () {
		return powerups [0] ;
	}
	
	public int getDyingSpeed() {
		return dyingSpeed;
	}
	
	public boolean getFacingForwards() {
		return facingForwards;
	}
}
