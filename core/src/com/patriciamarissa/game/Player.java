package com.patriciamarissa.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Player {
	private Batch batch;
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
	private int groundLvl;
	private int jumpHeight;
	
	
	boolean isJumpingUp;
	boolean facingForwards; //true-right false-left
	
	public Player(Batch batch, int moveSpeed) {
		this.batch = batch;
		this.moveSpeed = moveSpeed;
		
		spritePage = new Texture(Gdx.files.internal("SpriteSheet.png"));
		deathImg = new Texture(Gdx.files.internal("death.png"));

		sprites = new Sprite[4];
		sprites[0] = new Sprite(spritePage, 38, 50, 31, 50);
		sprites[1] = new Sprite(spritePage, 3, 50, 30, 50);
		sprites[2] = new Sprite(spritePage, 38, 50, 31, 50);
		sprites[3] = new Sprite(spritePage, 80, 50, 31, 50);
		
		currentSprite = sprites[0];
		
		spriteCount = 0;
		animationCount = 2;
		
		
		groundLvl = 100;
		jumpHeight = groundLvl + 50;
		facingForwards = true;
		reset();
		
		//deathImg = new Texture(Gdx.files.internal("sprites/death.png"));
		
		
	}
	
	public void reset() {
		
		if (!facingForwards) {
			changeDirection();
		}
		x = 100;
		y = 100;
		speed = 10;
		
		isJumpingUp = false;
		facingForwards = true;
		isDead = false;
		dyingSpeed = 20;
	}
	
	public void moveLeft() {
		//if (x - speed > 0) {
			x -= speed;
		//}
		if (spriteCount == 0) {
			
			spriteCount = sprites.length - 1;
			if (facingForwards) {
				changeDirection();
			}
		}
	}
	
	public void moveRight() {
		if (x + speed < Gdx.graphics.getWidth()) {
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
		//sprites[spriteCount].flip(true,false);
		currentSprite.draw(batch);
		batch.end();
	}
	
	public void move() {
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
		
		if (y >= groundLvl + jumpHeight) {
			isJumpingUp = false;
			currentSprite = sprites[3];
		}
		if (y > groundLvl && !isJumpingUp) {
			y -= speed;
			currentSprite = sprites[3];
		}
		
		currentSprite.setPosition(x, y);
	}
	
	public void die(){
		isDead = true;
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
		//System.out.println(jumpHeight);
	}
	
	public boolean isJumping() {
		return isJumpingUp;
	}
	
	public int getHeight() {
		return (int) currentSprite.getHeight();
	}
	
	public int getJumpHeight() { //not in use
		return jumpHeight;
	}
	
	public int getX() {
		return x;
	}

}
