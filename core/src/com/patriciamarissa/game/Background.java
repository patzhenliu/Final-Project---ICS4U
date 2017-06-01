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
		
		backgroundImg = new Texture(Gdx.files.internal("backgrounds/background.png"));
		//background = new TextureRegion(backgroundImg, 0, 100, width, height);
		
		this.x = x;
		int y = 100;
		moving = true;
	}
	
	public void draw() {
		if (x <= -1 * width) {
			x = width;
		}
		batch.begin();
		//batch.draw(background, 0, 0);
	    batch.draw(backgroundImg, x, 100);
		batch.end();
	}
	
	public void move() {
		x -= moveSpeed;
	}
	
	public void setX(int x) {
		this.x = x;
	}
}
