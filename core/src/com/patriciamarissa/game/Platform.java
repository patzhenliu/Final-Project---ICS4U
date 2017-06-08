package com.patriciamarissa.game;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class Platform {
	private Batch batch;
	private Texture platformImg;
	private Sprite platformSprite;
	private int moveSpeed;
	private int x;
	private int mval ;
	private int y;
	
	private Random rand = new Random(System.currentTimeMillis());
	private int width, length;
	private ArrayList<Money> moneyList;
	private ArrayList<Fire> fireList;
	private boolean deactiveFire ;
	
	public Platform (Batch batch, int moveSpeed, int y, int prevX, int moneyval, boolean df) {
		this.batch = batch;
		this.moveSpeed = moveSpeed;
		mval = moneyval ;
		deactiveFire = df ;
		randLength();
		//randPosition(prevX, 0);
		this.y = y;
		platformImg = new Texture(Gdx.files.internal("backgrounds/platform.png"));
		platformSprite = new Sprite(platformImg);
		width = (int)(platformSprite.getWidth() * length);
		
		moneyList = new ArrayList<Money>();
		createMoney(rand.nextInt(5));
		fireList = new ArrayList<Fire>();
		createFire(rand.nextInt(2));
		
		//System.out.println(width);
		//platformSprite.setSize(rand.nextInt(200) + 150, platformSprite.getHeight());
		/*this.x = x ;
		this.y = y ;
		this.w = w ;
		h = 30 ;
		platrect = new Rectangle (x, y, w, h) ;
		platimg = new Texture ("platform.png") ;
		plat = new Sprite (platimg) ;*/
	}
	
	public void createMoney(int num) {
		
		moneyList.clear();
		for (int i = 0; i < num; i++) {
			moneyList.add(new Money(batch, x, y + (int)platformSprite.getHeight(), width, mval));
		}
	}
	
	public void createFire(int num) {
		
		fireList.clear();
		for (int i = 0; i < num; i++) {
			fireList.add(new Fire(batch, x, y + (int)platformSprite.getHeight(), width));
		}
	}
	
	public void randPosition(int prevX, int row) { //does nothing
		//x = prevX + rand.nextInt(200) + 200;
		x = (prevX + rand.nextInt(20 * row) + 5) * length;
		//createMoney(rand.nextInt(5));
		//createFire(rand.nextInt(5));
	}
	
	public void randLength() {
		length = rand.nextInt(10) + 5;
	}
	
	public void move() {
		x -= moveSpeed;
		if(x < 0 - width) {
			x = rand.nextInt(200) + 1000;
			//createMoney(rand.nextInt(5));
		}
		//System.out.println (x) ;
		//if(x < 0 - width) {
			//x = rand.nextInt(200) + 1000;
			//randLength () ;
			//width = (int)(platformSprite.getWidth() * length);
		//}
	}
	
	public void draw() {
		batch.begin();
		for (int i = 0; i < length; i++) {
			platformSprite.setPosition(x + platformSprite.getWidth() * i, y);
			//System.out.println(platformSprite.getWidth());
			platformSprite.draw(batch);
		}
		
		batch.end();
		drawMoney();
		if (deactiveFire == false) {
			drawFire();
		}
		//move();
	}
	
	public void drawMoney() {
		for (int i = 0; i < moneyList.size(); i++) {
			moneyList.get(i).draw(x);
		}
	}
	
	public void drawFire() {
		for (int i = 0; i < fireList.size(); i++) {
			fireList.get(i).draw(x);
		}
	}
	
	public boolean moneyCollision(Player player) { ///NEEDS WORK
		for (int i = 0; i < moneyList.size(); i++) {
			if (moneyList.get(i).collide(player)) {
				moneyList.remove(i);
				System.out.println("MONEY");
				return true;
			}
		}
		return false;
	}
	
	public boolean fireCollision(Player player) { ///NEEDS WORK
		for (int i = 0; i < fireList.size(); i++) {
			if (fireList.get(i).collide(player)) {
				fireList.remove(i);
				return true;
			}
		}
		return false;
	}
	
	public boolean collideTop(Player player){
		Sprite playerSprite = player.getSprite();
		Rectangle rect = new Rectangle(playerSprite.getX(), playerSprite.getY(), playerSprite.getWidth(), playerSprite.getHeight()/2);
		Rectangle platformRect = new Rectangle(x, platformSprite.getY() + platformSprite.getHeight(), width, platformSprite.getHeight() );
		
		return rect.overlaps(platformRect);
	}
	
	public boolean collideTop(Sprite sprite){ 
		Sprite playerSprite = sprite;
		Rectangle rect = new Rectangle(playerSprite.getX(), playerSprite.getY(), playerSprite.getWidth(), playerSprite.getHeight()/2);
		Rectangle platformRect = new Rectangle(platformSprite.getX(), platformSprite.getY() + platformSprite.getHeight(), width, platformSprite.getHeight() );
		return rect.overlaps(platformRect);
	}
	
	public boolean collideBottom(Player player){ ///hitting bottom of platform - trial and erroring stuff rn
		Sprite playerSprite = player.getSprite();
		Rectangle rect = new Rectangle(playerSprite.getX(), playerSprite.getY() + playerSprite.getHeight(), playerSprite.getWidth(), playerSprite.getHeight());
		Rectangle platformRect = new Rectangle(platformSprite.getX(), platformSprite.getY() + playerSprite.getHeight(), platformSprite.getWidth(), playerSprite.getHeight());
		return rect.overlaps(platformRect);
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return (int) platformSprite.getHeight();
	}
	
	public int getMiddle () {
		return (x + width)/2 ;
	}
	
	public void setMoveSpeed (int moveSpeed) {
		this.moveSpeed = moveSpeed ;
	}
	
	public void addSpeed (int s) {
		moveSpeed += s ;
	}
	
	public void setFireStatus (boolean f) {
		deactiveFire = f ;
	}
	
	public boolean offRight () {
		if (x >= 1000) {
			return true ;
		}
		else {
			return false ;
		}
	}
}
