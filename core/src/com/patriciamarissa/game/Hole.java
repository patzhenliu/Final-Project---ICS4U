package com.patriciamarissa.game;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class Hole {
	private Batch batch;
	private Texture holeImg;
	private Sprite holeSprite;
	private int moveSpeed;
	private int x ;
	private int y;
	private Random rand = new Random(System.currentTimeMillis() );
	
	public Hole (Batch batch, int moveSpeed, int prevX) {
		// constructor
		this.batch = batch;
		this.moveSpeed = moveSpeed;
		randPosition(prevX);
		y = 0;
		holeImg = new Texture(Gdx.files.internal("backgrounds/hole.png"));
		holeSprite = new Sprite(holeImg);
		holeSprite.setSize(rand.nextInt(100) + 100, holeSprite.getHeight());
	}
	
	public void randPosition(int prevX) {
		// pushes the x-val further to the right by a varying amount
		x = prevX + rand.nextInt(200) + 150;
	}
	
	public void move() {
		// moves with the environment
		x -= moveSpeed;
		if(x < 0 - holeSprite.getWidth()) {
			x = rand.nextInt(200) + 1000;
		}
	}
	
	public void draw() {
		// draws the hole
		holeSprite.setPosition(x, y);
		batch.begin();
		holeSprite.draw(batch);
		batch.end();
	}
	
	public boolean collide(Player player){
		//checks collision with a player
		Sprite playerSprite = player.getSprite();
		Rectangle rect = new Rectangle(playerSprite.getX(), playerSprite.getY(), playerSprite.getWidth(), playerSprite.getHeight());
		Rectangle holeRect = new Rectangle(holeSprite.getX() + playerSprite.getWidth(), holeSprite.getY(), holeSprite.getWidth() - playerSprite.getWidth() * 2, 120);
		return rect.overlaps(holeRect);
	}
	
	public boolean collide(int x, int y, int w, int h){
		//checks collision given coordinate and dimensions
		Rectangle rect = new Rectangle(x, y, w, h);
		Rectangle holeRect = new Rectangle(holeSprite.getX() + w, holeSprite.getY(), holeSprite.getWidth() - w * 2, 120);
		return rect.overlaps(holeRect);
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getWidth() {
		return (int) holeSprite.getWidth () ;
	}
	
	public int getHeight() {
		return (int) holeSprite.getHeight();
	}
	
	public void setMoveSpeed (int moveSpeed) { // used when all the speeds get reset to their originals
		this.moveSpeed = moveSpeed ;
	}
	
	public void addSpeed (int s) { // used when things speed up
		moveSpeed += s ;
	}
}


