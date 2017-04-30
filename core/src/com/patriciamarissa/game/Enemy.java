package com.patriciamarissa.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Enemy {
	private int x, y, w, h, hp, speed ;
	private final int type ;
	private Batch batch ;
	private Texture spritesheet ;
	private Sprite currentsprite ;
	private Sprite [] sprites ;
	boolean leftright ; // true right, false left
	boolean updown ; // true up, false down
	
	public Enemy (Batch batch, int t, int x, int y, int s) {
		type = t ;
		this.x = x ;
		this.y = y ;
		// MAKE OWN SPRITES, FIND WIDTHS AND HEIGHTS, THEN MAKE ENEMY RECTS
		if (type == 0) { // ENEMY TYPE 1: ONLY WALKS AROUND ON A PLATFORM, MAKE IT CUTE
			hp = 1 ;
			leftright = false ;
		}
		else if (type == 1) { // ENEMY TYPE 2: A GHOST, FLIES UP AND DOWN BETWEEN PLATS
			hp = 1 ;
			updown = false ;
		}
		else if (type == 2) { // ENEMY TYPE 3: ONLY SHOOTS STRAIGHT BEAM, CHANGES DIRECTION IF PLAYER IS IN FRONT OR BEHIND
			hp = 2 ;
			leftright = false ;
		}
		else if (type == 3) { // ENEMY TYPE 4: CHARGES TOWARDS PLAYER, MAYBE A RHINO
			hp = 3 ;
			leftright = false ;
		}
		else if (type == 4) { // ENEMY TYPE 5: SPIKY. JUST GOTTA AVOID UNTIL YOU CAN SHOOT. JUST GRAB A MARIO SHELL OR SMTH.
			hp = 3 ;
		}
		speed = 2 ; // MAKE IT SO THE ENEMIES SPEED UP THE LONGER YOU'VE BEEN PLAYING? FOR EACH 30 SECOND INTERVAL, INCREASE SPEED BY 1
	}
	
	public void walk (int left, int right) { // THIS NEEDS TO TAKE THE PLATFORM BOUNDARIES AS A PARAMETER
		
	}
	
	public void fly () {
		if (y >= 600 - currentsprite.getHeight ()) {
			updown = false ;
		}
		if (y <= 100) {
			updown = true ;
		}
		if (updown == true) {
			y += speed ;
		}
		else {
			y -= speed ;
		}
	}
	
	public void shoot () {
	}
	
	public void charge (int px) { // rhino charges towards player if player is on the same platform
		
	}
	
	public boolean checkIfDead () {
		if (hp <= 0) {
			return true ;
		}
		else {
			return false ;
		}
	}
	
	public Sprite getSprite () {
		return currentsprite ;
	}
	
	public void loseHp (int power) {
		hp -= power ;
	}
	
	public void addSpeed (int s) {
		speed += s ;
	}
}
