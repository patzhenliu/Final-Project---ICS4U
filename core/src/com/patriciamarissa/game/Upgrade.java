package com.patriciamarissa.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Upgrade {
	private Batch batch;
	private Texture square, cross;
	int x, y, num, price, level;
	private boolean owned, owned2, owned3;
	private Texture icon;
	private String description;
	
	/* lasers (upgradable to 3) (10, 20, 40)
	 * more life (upgradable to 6, but starts at 3) (10, 20, 40)
	 * higher jump (upgradable twice, or to whatever point hits top of screen) (10, 20, 40)
	 * increase money (upgradable) (20, 40, 80)
	 * slow down time (one time use) (10)
	 * kill all enemies? (one time use, timed, 10 seconds) (20)
	 * remove fire (one time use) (10)
	 * remove holes (one time use) (10)
	 * GOING TO HAVE TO CHANGE LION TO ACCOMODATE THE HOLE REMOVAL. STILL NEED TO FIX LION AND GARGOYLE MOVEMENT ANYWAYS.
	 */
	
	public Upgrade(Batch batch, int x, int y, int n, Texture image) {
		this.batch = batch;
		square = new Texture(Gdx.files.internal("sprites/upgrade.png"));
		cross = new Texture(Gdx.files.internal("upgrades/crossout.png"));
		this.x = x;
		this.y = y;
		num = n ;
		owned = false ;
		owned2 = false ;
		owned3 = false ;
		icon = image;
		level = 3 ;
		if (num == 3 || num == 5) {
			price = 20 ;
		}
		else {
			price = 10 ;
		}
	}
	
	public void draw() { // EW IF I HAVE TO DRAW BORDERS I SWEAR TO GOD,
		batch.begin();
		//System.out.println(x + "," + y);
		batch.draw(square, x,  y);
		if (num > 3) { // the non-upgradables
			batch.draw(icon, x+40, y+40);
		}
		else {
			for (int i = 0 ; i < level ; i ++) {
				batch.draw (icon, x + 40 + (i * 5), y + 40 + (i * 5)) ;
			}
		}
		if (num == 6 || num == 7) {
			batch.draw (cross, x+40, y+40) ;
		}
		batch.end();
	}
	
	public void levelUp () {
		level += 1 ;
		updatePrice () ;
	}
	
	public void updatePrice () {
		price = price * 2 ;
	}
	
	public void setOwnedFalse () {
		owned = false ;
	}
	
	public boolean isOwned () {
		return owned ;
	}
	
	public boolean isOwnedTwo () {
		return owned2 ;
	}
	
	public boolean isOwnedThree () {
		return owned3 ;
	}
}
