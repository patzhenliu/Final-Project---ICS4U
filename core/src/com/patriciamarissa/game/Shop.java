package com.patriciamarissa.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Shop {
	private Batch batch;
	private Texture shopImg;
	private final int title, game, shop ;
	private boolean las1, las2, las3 ;
	private boolean life1, life2, life3 ;
	private boolean jump1, jump2 ;
	private boolean mon1, mon2, mon3 ;
	private boolean dubmon, dubscore, halftime, nuke, killfire, killholes ;
	private int coins ;
	private int [] pricelist ;
	
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
		shopImg = new Texture(Gdx.files.internal("shop.png"));
		title = 1 ;
		game = 2 ;
		shop = 3 ;
		coins = 0 ;
		
		las1 = false ;
		las2 = false ;
		las3 = false ;
		life1 = false ;
		life2 = false ;
		life3 = false ;
		jump1 = false ;
		jump2 = false ;
		mon1 = false ;
		mon2 = false ;
		mon3 = false ;
		dubmon = false ;
		dubscore = false ;
		halftime = false ;
		nuke = false ;
		killfire = false ;
		killholes = false ;
		
		pricelist = new int [10] ;
		Arrays.fill (pricelist, 10) ;
		pricelist [3] = 20 ; // increase money
		pricelist [7] = 20 ; // nuke the enemies
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
	
	public void updatePage () {
		// use mouse coordinates to figure out which img from the list to use
	}
	
	public void update () {
		updatePage () ;
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
