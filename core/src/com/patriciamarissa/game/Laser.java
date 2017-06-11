package com.patriciamarissa.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class Laser {
	private Batch batch ;
	private int x, y, speed, power ;
	boolean isGood ;
	private Texture img ;
	private Sprite laser ;
	
	public Laser (boolean ig, int x, int y, int s, int p, Batch b) {
		batch = b ;
		isGood = ig ;
		this.x = x ;
		this.y = y ;
		speed = s ;
		power = p ;
		if (!isGood) {
			img = new Texture (Gdx.files.internal("sprites/enemy laser.png")) ;
		}
		else {
			img = new Texture (Gdx.files.internal("sprites/player laser.png")) ; // default in case smth goes wrong with if statements
			if (power == 1) {
				img = new Texture (Gdx.files.internal("sprites/player laser.png")) ;
			}
			if (power == 2) {
				img = new Texture (Gdx.files.internal("sprites/player laser up.png")) ;
			}
			if (power == 3) {
				img = new Texture (Gdx.files.internal("sprites/player laser up 2.png")) ;
			}
		}
		laser = new Sprite (img) ;
	}
	
	public void move () {
		if (isGood) { // go right towards the enemy
			x += speed * 2 ;
		}
		else { // go left towards the user
			x -= speed * 3 ;
		}
		laser.setPosition (x, y) ;
	}
	
	public void draw () {
		batch.begin () ;
		batch.draw (laser, x, y) ;
		batch.end () ;
	}
	
	public boolean collide (Player player) {
		Sprite playerSprite = player.getSprite () ;
		Rectangle rect = new Rectangle (playerSprite.getX (), playerSprite.getY (), playerSprite.getWidth ()/2, playerSprite.getHeight ()) ;
		Rectangle logRect = new Rectangle (laser.getX (), laser.getY (), laser.getWidth (), laser.getHeight ()) ;
		return rect.overlaps(logRect);
	}
	
	public boolean collide (Enemy enemy) {
		Sprite enemySprite = enemy.getSprite () ;
		Rectangle rect = new Rectangle (enemySprite.getX (), enemySprite.getY (), enemySprite.getWidth (), enemySprite.getHeight ()) ;
		Rectangle logRect = new Rectangle(laser.getX(), laser.getY(), laser.getWidth (), laser.getHeight() );
		return rect.overlaps(logRect);
	}
	
	public void doDamage (Player player) { // enemies always do the same amount of damage, 1 life lost each time
		player.die () ;
	}
	
	public void doDamage (Enemy enemy) {
		enemy.loseHp (power) ;
	}
	
	public int getX () {
		return x ;
	}
}
