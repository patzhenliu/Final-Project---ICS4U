package com.patriciamarissa.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Shop {
	
	private Batch batch;
	private Texture shopImg;
	private final int title, game, shop ;
	private int coins ;

	private Texture spritePage;
	private Sprite ghostSprite;
	private Sprite[] sprites;
	private int spriteCount;
	private int animationCount;
	
	private Texture speech;
	
	public Shop(Batch batch) {
		this.batch = batch;
		shopImg = new Texture(Gdx.files.internal("menus/shop.png"));
		title = 1 ;
		game = 2 ;
		shop = 3 ;
		coins = 0 ;
		
		spritePage = new Texture(Gdx.files.internal("sprites/shopGhost.png"));
		speech = new Texture(Gdx.files.internal("sprites/shopSpeech.png"));

		sprites = new Sprite[5];
		sprites[0] = new Sprite(spritePage, 190, 278, 130, 120);
		sprites[1] = new Sprite(spritePage, 354, 278, 134, 120);
		sprites[2] = new Sprite(spritePage, 520, 278, 140, 120);
		sprites[3] = new Sprite(spritePage, 354, 278, 134, 120);
		sprites[4] = new Sprite(spritePage, 190, 278, 130, 120);
		
		ghostSprite = sprites[0];
		
		spriteCount = 0;
		animationCount = 5;
	}
	
	public void add (int c) {
		coins += c ;
	}
	
	public void deduct (int d) {
		coins -= d ;
	}
	
	public void draw() {
		batch.begin();
	    batch.draw(shopImg, 0, 0);
		batch.end();
		drawGhost();
	}
	
	public void updatePage () {
		// use mouse coordinates to figure out which img from the list to use
		animateGhost();
	}
	
	public void update () {
		updatePage () ;
		draw () ;
	}
	
	public void drawGhost() {
		batch.begin();
		batch.draw(ghostSprite, 800, 350);
		batch.draw(speech, 450, 450);
		batch.end();
	}
	
	public void animateGhost() {
		if (spriteCount == 0) {
			spriteCount = sprites.length - 1;
		}
		if (spriteCount > 0) {
			animationCount--;
			if (animationCount == 0) {
				spriteCount--;
				animationCount = 5;
			}
			ghostSprite = sprites[spriteCount];
		}
	}
	
	public int giveNextScreen () { // idk replace the keyboard commands with cursor stuff eventually
		if (Gdx.input.isKeyJustPressed(Keys.G)) {
			spriteCount = 0;
			return game ;
		}
		else if (Gdx.input.isKeyJustPressed(Keys.T)) {
			spriteCount = 0;
			return title ;
		}
		else {
			return shop ;
		}
	}
}
