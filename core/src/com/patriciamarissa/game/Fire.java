package com.patriciamarissa.game;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class Fire {
	private Batch batch;
	private Texture spritePage;
	private Sprite currentSprite;
	private Sprite[] sprites;
	
	private int spriteCount;
	private int animationCount;
	int offsetX, y;
	
	private Random rand = new Random(System.currentTimeMillis());
	
	public Fire(Batch batch, int px, int py, int range) {
		this.batch = batch;
		spritePage = new Texture(Gdx.files.internal("sprites/Fire.png"));
		sprites = new Sprite[6];
		sprites[0] = new Sprite(spritePage, 4, 116, 41, 50);
		sprites[1] = new Sprite(spritePage, 57, 117, 42, 50);
		sprites[2] = new Sprite(spritePage, 114, 119, 41, 48);
		sprites[3] = new Sprite(spritePage, 170, 118, 40, 48);
		sprites[4] = new Sprite(spritePage, 114, 119, 41, 48);
		sprites[5] = new Sprite(spritePage, 57, 117, 42, 50);
		currentSprite = sprites[0];
		
		spriteCount = 0;
		animationCount = 20;
		offsetX = rand.nextInt(range - (int)sprites[0].getWidth());
		y = py;
		System.out.println(y);
		
	}
	
	public void draw(int px) {
		currentSprite.setPosition(px + offsetX - (int)currentSprite.getWidth()/2, y);
		batch.begin();
		currentSprite.draw(batch);
		batch.end();
		animate();
		
	}
	
	public void animate() {
		if (spriteCount == 0) {
			spriteCount = sprites.length - 1;
		}
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
		Rectangle fireRect = new Rectangle(currentSprite.getX(), currentSprite.getY(), currentSprite.getWidth()/32, currentSprite.getHeight()/2 );
		//play with fireRect size
		return rect.overlaps(fireRect);
		
	}
}
