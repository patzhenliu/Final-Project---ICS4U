package com.patriciamarissa.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Background {
	private Batch batch;
	private Texture backgroundImg;
    
    private int x, y;
    private int width;
    
    private int moveSpeed;
	
	public Background(Batch batch, int x, int width, int height, int moveSpeed) {
		this.batch = batch;
		this.width = width;
		this.moveSpeed = moveSpeed;
		
		backgroundImg = new Texture(Gdx.files.internal("backgrounds/background.png"));
		
		this.x = x;
		y = 100;
	}
	
	public void draw() {
		//draws background on screen
		batch.begin();
	    batch.draw(backgroundImg, x, y);
		batch.end();
	}
	
	public void move() {
		//updates x coordinate so background moves backwards on screen
		if (x <= -1 * width) {
			x = width;
		}
		x -= moveSpeed;
	}
	
	public void setX(int x) {
		this.x = x;
	}
}
