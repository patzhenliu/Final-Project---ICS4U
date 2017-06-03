package com.patriciamarissa.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Upgrade {
	private Batch batch;
	//private final int totUpgrades = 10;
	private int cost, type, x, y ;
	private final int ONEUSE, UPGRADABLE ;
	private Texture img, b1, b2, desc, dbg1, dbg2 ;
	private Sprite sprite ;
	private Sprite buybase, buyhover ; // button unclicked, button hoverered
	
	private boolean isClicked ;
	private boolean isBought ;
	
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
	
	public Upgrade(Batch batch, int c, int t, Texture i, int x, int y) {
		this.batch = batch ;
		cost = c ;
		type = t ;
		ONEUSE = 1 ;
		UPGRADABLE = 2 ;
		img = i ;
		sprite = new Sprite (img) ;
	}
	
	public void update () {
		batch.begin () ;
		batch.draw(img, x, y);
		batch.end () ;
	}
	
	public void openMenu () {
		
	}
}
