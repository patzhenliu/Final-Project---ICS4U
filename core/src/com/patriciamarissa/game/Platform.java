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
	private Random rand = new Random(System.currentTimeMillis());
	private int width, length;
	
	public Platform (Batch batch, int moveSpeed, int y, int prevX) {
		this.batch = batch;
		this.moveSpeed = moveSpeed;
		
		randPosition(prevX, 0);
		this.y = y;
		randLength();
		platformImg = new Texture(Gdx.files.internal("platform.png"));
		platformSprite = new Sprite(platformImg);
		width = (int)(platformSprite.getWidth() * length);
	}
	
	public void randPosition(int prevX, int row) { //does nothing
		x = prevX + rand.nextInt(200) + 200;
		//x = (prevX + rand.nextInt(20 * row) + 5) * length;
	}
	
	public void randLength() {
		length = rand.nextInt(3) + 5;
	}
	
	public void move() {
		x -= moveSpeed;
		//if(x < 0 - width) {
			//x = rand.nextInt(200) + 1000;
			//randLength () ;
			//width = (int)(platformSprite.getWidth() * length);
		//}
	}
	
	public void draw() {
		batch.begin();
		for (int i = 0; i < length; i++) {
			platformSprite.setPosition(x + platformSprite.getWidth() * i, y);
			//System.out.println(platformSprite.getWidth());
			platformSprite.draw(batch);
		}
		batch.end();
		//move();
	}
	
	public boolean collideTop(Player player){
		Sprite playerSprite = player.getSprite();
		Rectangle rect = new Rectangle(playerSprite.getX(), playerSprite.getY(), playerSprite.getWidth(), playerSprite.getHeight()/2);
		Rectangle platformRect = new Rectangle(x, platformSprite.getY() + platformSprite.getHeight(), width, platformSprite.getHeight() );
		
		return rect.overlaps(platformRect);
	}
	
	public boolean collideTop(Sprite sprite){ 
		Sprite playerSprite = sprite;
		Rectangle rect = new Rectangle(playerSprite.getX(), playerSprite.getY(), playerSprite.getWidth(), playerSprite.getHeight()/2);
		Rectangle platformRect = new Rectangle(platformSprite.getX(), platformSprite.getY() + platformSprite.getHeight(), width, platformSprite.getHeight() );
		return rect.overlaps(platformRect);
	}
	
	public boolean collideBottom(Player player){ ///hitting bottom of platform - trial and erroring stuff rn
		Sprite playerSprite = player.getSprite();
		Rectangle rect = new Rectangle(playerSprite.getX(), playerSprite.getY() + playerSprite.getHeight(), playerSprite.getWidth(), playerSprite.getHeight());
		Rectangle platformRect = new Rectangle(platformSprite.getX(), platformSprite.getY() + playerSprite.getHeight(), platformSprite.getWidth(), playerSprite.getHeight());
		return rect.overlaps(platformRect);
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return (int) platformSprite.getHeight();
	}
	
	public int getMiddle () {
		return (x + width)/2 ;
	}
	
	public void setMoveSpeed (int moveSpeed) {
		this.moveSpeed = moveSpeed ;
	}
	
	public void addSpeed (int s) {
		moveSpeed += s ;
	}
	
	public boolean offRight () {
		if (x >= 1000) {
			return true ;
		}
		else {
			return false ;
		}
	}
}
