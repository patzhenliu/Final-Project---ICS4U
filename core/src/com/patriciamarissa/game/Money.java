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
	private Sprite[] sprites;
	
	private int spriteCount;
	private int animationCount;
	int offsetX, y;
	
	private Random rand = new Random(System.currentTimeMillis());
	
	public Money(Batch batch, int px, int py, int range) {
		this.batch = batch;
		spritePage = new Texture(Gdx.files.internal("sprites/money.png"));
		sprites = new Sprite[4];
		sprites[0] = new Sprite(spritePage, 56, 58, 50, 48);
		sprites[1] = new Sprite(spritePage, 6, 58, 46, 48);
		sprites[2] = new Sprite(spritePage, 160, 58, 10, 48);
		sprites[3] = new Sprite(spritePage, 110, 58, 46, 48);
		currentSprite = sprites[0];
		
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
			currentSprite = sprites[spriteCount];
		}
	}
	
	public boolean collide(Player player){
		Sprite playerSprite = player.getSprite();
		Rectangle rect = new Rectangle(playerSprite.getX(), playerSprite.getY(), playerSprite.getWidth(), playerSprite.getHeight()/2);
		Rectangle moneyRect = new Rectangle(currentSprite.getX(), currentSprite.getY(), currentSprite.getWidth(), currentSprite.getHeight() );
		
		return rect.overlaps(moneyRect);
		
	}
}
