package com.patriciamarissa.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Upgrade {
	private Batch batch;
	private Texture square, hoversquare, currentsquare, cross, greycross;
	int x, y, num, price, level;
	private final int laser, life, jump, money, killf, killh ;
	private boolean owned, owned3, affordable;
	private Texture icon, greyver;
	private Texture upmon2, upmon3 ; // upgraded nomey 2, upgraded money 3
	private Texture uplife2, uplife3 ;
	private Texture uplas2, uplas3 ;
	private Texture upjump2, upjump3 ;
	private Texture mongrey2, mongrey3 ; // grey money 2, grey money 3; money sprites look different with each upgrade
	private Texture cicon, ccross ; // current icon, current cross
	
	/* AVAILABLE ITEMS IN THE SHOP:
	 * lasers (upgradable to 3) (10, 20, 40)
	 * more life (upgradable to 6, but starts at 3) (20, 40, 80)
	 * higher jump (upgradable twice, or to whatever point hits top of screen) (10, 20, 40)
	 * increase money (upgradable) (20, 40, 80)
	 * remove fire (one time use) (10)
	 * remove holes (one time use) (10)
	 */
	
	public Upgrade(Batch batch, int x, int y, int num, int price, Texture image, Texture grey) {
		// constructor
		this.batch = batch;
		square = new Texture(Gdx.files.internal("sprites/upgrade.png"));
		hoversquare = new Texture(Gdx.files.internal("sprites/upgradeHover.png"));
		currentsquare = square ;
		cross = new Texture(Gdx.files.internal("upgrades/icons/crossout.png"));
		greycross = new Texture(Gdx.files.internal("upgrades/icons/crossout grey.png"));
		mongrey2 = new Texture(Gdx.files.internal("upgrades/icons/money 2 grey.png"));
		mongrey3 = new Texture(Gdx.files.internal("upgrades/icons/money 3 grey.png"));
		
		upmon2 = new Texture(Gdx.files.internal("upgrades/icons/money 2.png"));
		upmon3 = new Texture(Gdx.files.internal("upgrades/icons/money 3.png"));
		uplife2 = new Texture(Gdx.files.internal("upgrades/icons/heart 2.png"));
		uplife3 = new Texture(Gdx.files.internal("upgrades/icons/heart 3.png"));
		uplas2 = new Texture(Gdx.files.internal("upgrades/icons/laser 2.png"));
		uplas3 = new Texture(Gdx.files.internal("upgrades/icons/laser 3.png"));
		upjump2 = new Texture(Gdx.files.internal("upgrades/icons/jump 2.png"));
		upjump3 = new Texture(Gdx.files.internal("upgrades/icons/jump 3.png"));
		
		laser = 0 ;
		life = 1 ;
		jump = 2 ;
		money = 3 ;
		killf = 4 ;
		killh = 5 ;
		
		this.x = x;
		this.y = y;
		this.num = num ;
		this.price = price ;
		owned = false ;
		owned3 = false ;
		affordable = false ;
		icon = image;
		greyver = grey ;
		cicon = grey ;
		ccross = greycross ;
		level = 1 ;
	}
	
	public void draw() { // draws the box and the current sprite, whether its grey or colored
		batch.begin();
		batch.draw(currentsquare, x,  y);
		batch.draw(cicon, x+40, y+40);
		if (num == killf || num == killh) {
			batch.draw (ccross, x+40, y+40) ;
		}
		batch.end();
	}
	
	public void updateSquare (boolean selected) { // determines which box sprite to use depending on if its selected
		if (selected) {
			currentsquare = hoversquare ;
		}
		else {
			currentsquare = square ;
		}
	}
	
	public void updateIcon (int playermon) { 
		// updates icon color and whether the upgrade can be bought based on player's money
		if (playermon >= price) { // player can buy it, make it a colored icon, if upgradable, corresponding to level
			cicon = icon ;
			if (level == 2) {
				if (num == money) {
					cicon = upmon2 ;
				}
				if (num == laser) {
					cicon = uplas2 ;
				}
				if (num == life) {
					cicon = uplife2 ;
				}
				if (num == jump) {
					cicon = upjump2 ;
				}
			}
			if (level == 3) {
				if (num == money) {
					cicon = upmon3 ;
				}
				if (num == laser) {
					cicon = uplas3 ;
				}
				if (num == life) {
					cicon = uplife3 ;
				}
				if (num == jump) {
					cicon = upjump3 ;
				}
			}
			ccross = cross ;
			affordable = true ;
		}
		if (playermon < price || (owned == true && level == 1 && num > 3) || (level == 3 && owned3 == true)) {
			// if the player is too broke or the item's been bought out, grey it out and make it unbuyable
			cicon = greyver ;
			if (num == money) {
				if (level == 2) {
					cicon = mongrey2 ;
				}
				if (level == 3) {
					cicon = mongrey3 ;
				}
			}
			ccross = greycross ;
			affordable = false ;
		}
	}
	
	public boolean isBecausePlayerBroke (int playermon) {
		// returns whether reasoning for not being able to buy is because the player is broke.
		// this affects the ghost's text in the shop.
		if (playermon < price ) {
			return true ;
		}
		else {
			return false ;
		}
	}
	
	public void buy () { 
		// increases level if upgradable, checks off that it's been bought at the current level.
		// there isn't an owned2 because knowing if you've bought a midpoint upgrade can just be determined by level.
		if (level == 1) {
			owned = true ;
		}
		if (level == 3) {
			owned3 = true ;
		}
		if (num < 4 && level != 3) {
			level += 1 ;
		}
	}
	
	public void updatePrice () { // doubles price
		price = price * 2 ;
	}
	
	public void setOwnedFalse () { // for the one-time upgrades, makes them not owned when exiting main game
		owned = false ;
	}
	
	public boolean isBuyable () { // shop needs to know if upgrade can be bought when registering player hitting enter
		return affordable ;
	}
	
	public int getType () { // if it returns 4 or higher, its a one-time upgrade; else, its an upgradable upgrade
		return num ;
	}
}
