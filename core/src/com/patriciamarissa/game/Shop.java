package com.patriciamarissa.game;

import java.util.Arrays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Shop {
	private Batch batch;
	private Texture shopImg;
	private final int title, game, shop ;
	private int coins ;
	private int [] pricelist ;
	private int [] boughtlist ;
	
	/* lasers (upgradable to 3) (10, 20, 40)
	 * more life (upgradable to 6, but starts at 3) (10, 20, 40)
	 * higher jump (upgradable twice, or to whatever point hits top of screen) (10, 20, 40)
	 * increase money (upgradable) (20, 40, 60)
	 * double money (one time use) (10)
	 * double score (one time use) (10)
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
	}
	
	public void updatePage (Player player) {
		// use mouse coordinates to figure out which img from the list to use
		// if something is already bought, grey out the image and make it unclickable
		// else make it so that you can click to buy
		// and maybe play a cha ching sound?
	}
	
	public void update (Player player) { // needs the player to give an upgrade in case an upgrade is purchased
		updatePage (player) ;
		draw () ;
	}
	
	public int giveNextScreen () { // idk replace the keyboard commands with cursor stuff eventually
		if (Gdx.input.isKeyPressed(Keys.G)) {
			return game ;
		}
		else if (Gdx.input.isKeyPressed(Keys.T)) {
			return title ;
		}
		else {
			return shop ;
		}
	}
}
