package com.patriciamarissa.game;

import java.util.Arrays;

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
	private int [] pricelist ;
	private int [] boughtlist ;

	private Texture [] ugpics ;
	private Upgrade [] upgrades ;
	
	private Texture las, lif, jum, mon, tim, nuk, fir, hol ;
	
	/* lasers (upgradable to 3) (10, 20, 40)
	 * more life (upgradable to 6, but starts at 3) (10, 20, 40)
	 * higher jump (upgradable twice, or to whatever point hits top of screen) (10, 20, 40)
	 * increase money (upgradable) (20, 40, 60)
	 * slow down time (one time use) (10)
	 * kill all enemies? (one time use, timed, 10 seconds) (20)
	 * remove fire (one time use) (10)
	 * remove holes (one time use) (10)
	 * GOING TO HAVE TO CHANGE LION TO ACCOMODATE THE HOLE REMOVAL. STILL NEED TO FIX LION AND GARGOYLE MOVEMENT ANYWAYS.
	 */
	
	public Shop(Batch batch) {
		this.batch = batch;
		shopImg = new Texture(Gdx.files.internal("menus/shop.png"));
		title = 1 ;
		game = 2 ;
		shop = 3 ;
		coins = 0 ;
		upgrades = new Upgrade [8] ;
		ugpics = new Texture [8] ;
		
		spritePage = new Texture(Gdx.files.internal("sprites/shopGhost.png"));
		speech = new Texture(Gdx.files.internal("sprites/shopSpeech.png"));

		sprites = new Sprite[5];
		sprites[0] = new Sprite(spritePage, 190, 278, 130, 120);
		sprites[1] = new Sprite(spritePage, 354, 278, 134, 120);
		sprites[2] = new Sprite(spritePage, 520, 278, 140, 120);
		sprites[3] = new Sprite(spritePage, 354, 278, 134, 120);
		sprites[4] = new Sprite(spritePage, 190, 278, 130, 120);
		
		ugpics [0] = new Texture (Gdx.files.internal("sprites/player laser small.png")) ;
		ugpics [1] = new Texture (Gdx.files.internal("sprites/lifeImg bigger.png")) ;
		ugpics [2] = new Texture (Gdx.files.internal("sprites/lifeImg.png")) ; // PLAYER JUMP
		ugpics [3] = new Texture (Gdx.files.internal("sprites/lifeImg.png")) ; // money
		ugpics [4] = new Texture (Gdx.files.internal("sprites/lifeImg.png")) ; // time
		ugpics [5] = new Texture (Gdx.files.internal("sprites/kaboomsmall.png")) ; // nuke enemies
		ugpics [6] = new Texture (Gdx.files.internal("sprites/lifeImg.png")) ; // remove fire
		ugpics [7] = new Texture (Gdx.files.internal("sprites/lifeImg.png")) ; // remove holes
		
		ghostSprite = sprites[0];
		
		spriteCount = 0;
		animationCount = 5;
		pricelist = new int [10] ;
		Arrays.fill (pricelist, 10) ;
		pricelist [3] = 20 ; // increase money
		pricelist [7] = 20 ; // nuke the enemies
		boughtlist = new int [10] ;
		Arrays.fill(boughtlist, 0) ;
	}
	
	public void buy (int index, int [] powers) {
		// LIVES, LASERS, HIGH JUMP, INCREASE MONEY, DOUBLE MONEY, DOUBLE SCORE, SLOW TIME, NUKE, KILL FIRE, KILL HOLES
		boughtlist [index] += 1 ; // INDEXES 0 TO 3 ARE UPGRADABLE
		powers [index] += 1 ;
		deduct (pricelist [index]) ;
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
	
	public void updatePage (Player player) {
		// use mouse coordinates to figure out which img from the list to use
		animateGhost();
		// if something is already bought, grey out the image and make it unclickable
		// else make it so that you can click to buy
		// and maybe play a cha ching sound?
	}
	
	public void update (Player player) { // needs the player to give an upgrade in case an upgrade is purchased
		updatePage (player) ;
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
