package com.patriciamarissa.game;

import java.awt.Image;
import java.util.Random;

import javax.swing.ImageIcon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class Platform {
	private Batch batch;
	private Texture platformImg;
	private Sprite platformSprite;
	private int moveSpeed;
	private int x;
	private int y;
	private int length;
	private Random rand = new Random(System.currentTimeMillis() );
	
	/*private Rectangle platrect ;
	private final int y, w, h ;
	private int x ;
	private final Texture platimg ;
	private final Sprite plat ;*/
	
	public Platform (Batch batch, int moveSpeed, int y, int prevX) {
		this.batch = batch;
		this.moveSpeed = moveSpeed;
		
		x = prevX + rand.nextInt(200) + 100;
		this.y = y;
		length = rand.nextInt(200) + 150;
		platformImg = new Texture(Gdx.files.internal("platform.png"));
		platformSprite = new Sprite(platformImg);
		platformSprite.setSize(length, platformSprite.getHeight());
		/*this.x = x ;
		this.y = y ;
		this.w = w ;
		h = 30 ;
		platrect = new Rectangle (x, y, w, h) ;
		platimg = new Texture ("platform.png") ;
		plat = new Sprite (platimg) ;*/
	}
	
	public void randPosition() { //does nothing
		return;
	}
	
	public void move() {
		x -= moveSpeed;
		if(x < 0 - platformSprite.getWidth()) {
			x = rand.nextInt(200) + 1000;
		}
	}
	
	public void draw() {
		platformSprite.setPosition(x, y);
		batch.begin();
		platformSprite.draw(batch);
		batch.end();
		//move();
	}
	
	public boolean collideTop(Player player){ ///should only be feet colliding not anything
		Sprite playerSprite = player.getSprite();
		Rectangle rect = new Rectangle(playerSprite.getX(), playerSprite.getY() + playerSprite.getHeight()/2, playerSprite.getWidth(), playerSprite.getHeight()/2);
		Rectangle logRect = new Rectangle(platformSprite.getX(), platformSprite.getY() + playerSprite.getHeight(), platformSprite.getWidth(), playerSprite.getHeight() );
		return rect.overlaps(logRect);
	}
	
	public boolean collideBottom(Player player){ ///hitting bottom of platform - trial and erroring stuff rn
		Sprite playerSprite = player.getSprite();
		Rectangle rect = new Rectangle(playerSprite.getX(), playerSprite.getY() + playerSprite.getHeight(), playerSprite.getWidth(), playerSprite.getHeight());
		Rectangle logRect = new Rectangle(platformSprite.getX(), platformSprite.getY() + playerSprite.getHeight(), platformSprite.getWidth(), playerSprite.getHeight());
		return rect.overlaps(logRect);
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getLength() {
		return length;
	}
	
	public int getHeight() {
		return (int) platformSprite.getHeight();
	}
	
	public void setMoveSpeed (int moveSpeed) {
		this.moveSpeed = moveSpeed ;
	}
	
	public void addSpeed (int s) {
		moveSpeed += s ;
	}
}
