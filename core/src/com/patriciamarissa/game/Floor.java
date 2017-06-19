package com.patriciamarissa.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Floor {
	private Batch batch;
	private Texture backgroundImg;
    private TextureRegion background;
    
    private int x;
    private int y;
    private int width;
    
    private int moveSpeed;
	
	public Floor(Batch batch, int x, int width, int height, int moveSpeed) {
		this.batch = batch;
		this.width = width;
		this.moveSpeed = moveSpeed;
		
		backgroundImg = new Texture(Gdx.files.internal("backgrounds/floor.png"));
		background = new TextureRegion(backgroundImg, 0, 0, width, height);
		
		this.x = x;
		int y = Gdx.graphics.getHeight();
	}
	
	public void draw() {
		//draws floor on screen
		batch.begin();
	    batch.draw(background, x, y);
		batch.end();
	}
	
	public void move() {
		//updates x coordinate so that it moves backwards on the screen
		if (x <= -1 * width) {
			x = width;
		}
		x -= moveSpeed;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setMoveSpeed(int s) {
		moveSpeed = s;
	}
}
