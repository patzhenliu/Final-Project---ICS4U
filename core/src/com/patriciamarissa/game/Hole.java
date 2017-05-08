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
		private int x, originalx;
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
			
			x = prevX + rand.nextInt(200) + 100;
			originalx = x ;
			y = 0;
			holeImg = new Texture(Gdx.files.internal("hole.png"));
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
		
		public void randPosition() { //does nothing
			return;
		}
		
		public void move() {
			x -= moveSpeed;
			originalx -= moveSpeed ;
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
			Rectangle holeRect = new Rectangle(holeSprite.getX(), holeSprite.getY(), holeSprite.getWidth(), 120);
			return rect.overlaps(holeRect);
		}
		
		public boolean collide(Sprite enemySprite){ ///should only be feet colliding not anything
			Rectangle rect = new Rectangle(enemySprite.getX(), enemySprite.getY(), enemySprite.getWidth(), enemySprite.getHeight());
			Rectangle holeRect = new Rectangle(holeSprite.getX(), holeSprite.getY(), holeSprite.getWidth(), 120);
			return rect.overlaps(holeRect);
		}
		
		public int getX() {
			return x;
		}
		
		public int getY() {
			return y;
		}
		
		public int getOriginalX () {
			return originalx ;
		}
		
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


