package com.patriciamarissa.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class Laser {
	private Batch batch ;
	private int x, y, speed, power, type ;
	private final int GOOD, BAD ;
	private Texture img ;
	private Sprite laser ;
	
	public Laser (int t, int x, int y, int s, int p, Batch b) {
		batch = b ;
		GOOD = 0 ;
		BAD = 1 ;
		type = t ;
		if (type == BAD) {
			img = new Texture ("enemy laser.png") ;
		}
		else {
			img = new Texture ("user laser.png") ;
		}
		laser = new Sprite (img) ;
		this.x = x ;
		this.y = y ;
		speed = s ;
		power = p ;
	}
	
	public void move () {
		if (type == GOOD) { // go right towards the enemy
			x += speed * 3 ;
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
}
