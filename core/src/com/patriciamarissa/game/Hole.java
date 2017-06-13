package com.patriciamarissa.game;

import java.awt.Image;
import java.util.Random;

import javax.swing.ImageIcon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class Hole {
	//death
		private Batch batch;
		private Texture holeImg;
		private Sprite holeSprite;
		private int moveSpeed;
		private int x ;
		private int y;
		private Random rand = new Random(System.currentTimeMillis() );
		
		/*private Rectangle platrect ;
		private final int y, w, h ;
		private int x ;
		private final Texture platimg ;
		private final Sprite plat ;*/
		
		public Hole (Batch batch, int moveSpeed, int prevX) {
			this.batch = batch;
			this.moveSpeed = moveSpeed;
			
			randPosition(prevX);
			// originalx = x ;
			y = 0;
			holeImg = new Texture(Gdx.files.internal("backgrounds/hole.png"));
			holeSprite = new Sprite(holeImg);
			holeSprite.setSize(rand.nextInt(100) + 100, holeSprite.getHeight());
			/*this.x = x ;
			this.y = y ;
			this.w = w ;
			h = 30 ;
			platrect = new Rectangle (x, y, w, h) ;
			platimg = new Texture ("platform.png") ;
			plat = new Sprite (platimg) ;*/
		}
		
		public void randPosition(int prevX) { //does nothing
			x = prevX + rand.nextInt(200) + 150;
		}
		
		public void move() {
			x -= moveSpeed;
			// originalx -= moveSpeed ;
			if(x < 0 - holeSprite.getWidth()) {
				x = rand.nextInt(200) + 1000;
			}
		}
		
		public void draw() {
			holeSprite.setPosition(x, y);
			batch.begin();
			holeSprite.draw(batch);
			batch.end();
			//move();
		}
		
		public boolean collide(Player player){ ///should only be feet colliding not anything
			Sprite playerSprite = player.getSprite();
			Rectangle rect = new Rectangle(playerSprite.getX(), playerSprite.getY(), playerSprite.getWidth(), playerSprite.getHeight());
			Rectangle holeRect = new Rectangle(holeSprite.getX() + playerSprite.getWidth(), holeSprite.getY(), holeSprite.getWidth() - playerSprite.getWidth() * 2, 120);
			return rect.overlaps(holeRect);
		}
		
		public boolean collide(int x, int y, int w, int h){ ///should only be feet colliding not anything
			Rectangle rect = new Rectangle(x, y, w, h);
			Rectangle holeRect = new Rectangle(holeSprite.getX() + w, holeSprite.getY(), holeSprite.getWidth() - w * 2, 120);
			return rect.overlaps(holeRect);
		}
		
		public boolean collide(Sprite enemySprite, float ey){ ///should only be feet colliding not anything
			Rectangle rect = new Rectangle(enemySprite.getX(), ey, enemySprite.getWidth(), enemySprite.getHeight());
			Rectangle holeRect = new Rectangle(holeSprite.getX(), holeSprite.getY(), holeSprite.getWidth(), 120);
			return rect.overlaps(holeRect);
			// enemy y isn't enemy.getY () because the lion's y is adjusted for this collide.
		}
		
		public boolean isAligned (Sprite enemySprite, float ex) {
			// checking to see if the sprite fits completely in the hole
			if (x <= (int) ex && x + getWidth () >= (int) ex + (int) enemySprite.getWidth ()) {
				return true ;
			}
			else {
				return false ;
			}
		}
		
		public int partialAlign (Sprite enemySprite, float ex) {
			if (ex > x && ex < x + getWidth ()) { // the left side is hanging into the hole
				return (int) ((int) (x + getWidth ()) - ex) + 5 ;
			}
			else if (ex + enemySprite.getWidth () > x) { // the right side is hanging into the hole
				return (int) ((int) (ex + enemySprite.getWidth ()) - x) + getWidth () + 5 ;
			}
			else { // not over the hole
				return 0 ;
			}
		}
		
		public int getX() {
			return x;
		}
		
		public int getY() {
			return y;
		}
		
		/*public int getOriginalX () {
			return originalx ;
		}*/
		
		public int getWidth() {
			return (int) holeSprite.getWidth () ;
		}
		
		public int getHeight() {
			return (int) holeSprite.getHeight();
		}
		
		public void setMoveSpeed (int moveSpeed) {
			this.moveSpeed = moveSpeed ;
		}
		
		public void addSpeed (int s) {
			moveSpeed += s ;
		}
	}


