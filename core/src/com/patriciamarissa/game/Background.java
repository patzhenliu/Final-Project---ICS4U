package com.patriciamarissa.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Background {
	private Batch batch;
	private Texture backgroundImg;
    private TextureRegion background;
    
    private int x;
    private int y;
    private int width;
    
    private int moveSpeed;
    private boolean moving;
	
	public Background(Batch batch, int x, int width, int height, int moveSpeed) {
		this.batch = batch;
		this.width = width;
		this.moveSpeed = moveSpeed;
		
		backgroundImg = new Texture(Gdx.files.internal("background.png"));
		background = new TextureRegion(backgroundImg, 0, 0, width, height);
		
		this.x = x;
		int y = Gdx.graphics.getHeight();
		moving = true;
	}
	
	public void draw() {
		if (moving) {
			x -= moveSpeed;
		}
		if (x <= -1 * width) {
			x = width;
		}
		batch.begin();
		//batch.draw(background, 0, 0);
	    batch.draw(background, x, y);
		batch.end();
	}
	
	public void stop() {
		moving = false;
	}
	
	public void start() {
		moving = true;
	}
	
	public void setX(int x) {
		this.x = x;
	}
}
