package com.patriciamarissa.game;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class Money {
	private Batch batch;
	private Texture spritePage;
	private Sprite currentSprite;
	private Sprite [] currentSprites ;
	private Sprite [] sprites ;
	private Sprite [] upsprs1 ;
	private Sprite [] upsprs2 ;
	private Sprite [] upsprs3 ;
	
	private int spriteCount;
	private int animationCount;
	int offsetX, y;
	
	private Random rand = new Random(System.currentTimeMillis());
	
	public Money(Batch batch, int px, int py, int range, int value) {
		this.batch = batch;
		spritePage = new Texture(Gdx.files.internal("sprites/money.png"));
		sprites = new Sprite[4];
		upsprs1 = new Sprite [4] ;
		upsprs2 = new Sprite [4] ;
		upsprs3 = new Sprite [4] ;
		
		sprites[0] = new Sprite(spritePage, 56, 6, 50, 48);
		sprites[1] = new Sprite(spritePage, 6, 6, 46, 48);
		sprites[2] = new Sprite(spritePage, 160, 6, 10, 48);
		sprites[3] = new Sprite(spritePage, 110, 6, 46, 48);
		
		upsprs1[0] = new Sprite(spritePage, 56, 58, 50, 48);
		upsprs1[1] = new Sprite(spritePage, 6, 58, 46, 48);
		upsprs1[2] = new Sprite(spritePage, 160, 58, 10, 48);
		upsprs1[3] = new Sprite(spritePage, 110, 58, 46, 48);
		
		upsprs2[0] = new Sprite(spritePage, 71, 110, 67, 65);
		upsprs2[1] = new Sprite(spritePage, 6, 110, 65, 65);
		upsprs2[2] = new Sprite(spritePage, 210, 110, 67, 65);
		upsprs2[3] = new Sprite(spritePage, 140, 110, 67, 65);
		
		upsprs3[0] = new Sprite(spritePage, 74, 176, 66, 64);
		upsprs3[1] = new Sprite(spritePage, 6, 176, 66, 64);
		upsprs3[2] = new Sprite(spritePage, 209, 176, 66, 64);
		upsprs3[3] = new Sprite(spritePage, 142, 176, 66, 64);
		
		if (value == 0) {
			currentSprites = sprites ;
			currentSprite = sprites [0] ;
		}
		if (value == 1) {
			currentSprites = upsprs1 ;
			currentSprite = upsprs1 [0] ;
		}
		if (value == 2) {
			currentSprites = upsprs2 ;
			currentSprite = upsprs2 [0] ;
		}
		if (value == 3) {
			currentSprites = upsprs3 ;
			currentSprite = upsprs3 [0] ;
		}
		
		spriteCount = 0;
		animationCount = 4;
		offsetX = rand.nextInt(range - (int)sprites[0].getWidth()) + px;
		y = py;
		System.out.println(y);
		
	}
	
	public void draw(int px) {
		currentSprite.setPosition(px + offsetX - (int)currentSprite.getWidth()/2, y);
		batch.begin();
		currentSprite.draw(batch);
		batch.end();
		spin();
		if (spriteCount == 0) {
			spriteCount = sprites.length - 1;
		}
	}
	
	public void draw(int x, int y) {
		currentSprite.setPosition(x, y);
		batch.begin();
		currentSprite.draw(batch);
		batch.end();
	}
	
	public void spin() {
		if (spriteCount > 0) {
			animationCount--;
			if (animationCount == 0) {
				spriteCount--;
				animationCount = 4;
			}
			currentSprite = currentSprites[spriteCount];
		}
	}
	
	public void setSprites (int uplevel) {
		
	}
	
	public boolean collide(Player player){
		Sprite playerSprite = player.getSprite();
		Rectangle rect = new Rectangle(playerSprite.getX(), playerSprite.getY(), playerSprite.getWidth(), playerSprite.getHeight()/2);
		Rectangle moneyRect = new Rectangle(currentSprite.getX(), currentSprite.getY(), currentSprite.getWidth(), currentSprite.getHeight() );
		
		return rect.overlaps(moneyRect);
		
	}
}
