package com.patriciamarissa.game;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Button {
	Batch batch;
	private Texture hover;
	private int x, y;
	boolean clicked;

    private Texture img;
    private int pageNum;

	public Button (Batch batch, Texture img, Texture hover, int x, int y, int pageNum) {
		// constructor
		this.batch = batch;
		this.img = img;
		this.hover = hover;
		this.x = x;
		this.y = y;
		this.pageNum = pageNum;
	}
	
	public int getPageNum() {
		// returns what page to go to next
		return pageNum;
	}
	
	public void drawHoverImg() {
		//draws button image when selected on screen
		batch.begin();
    	batch.draw(hover, x, y);
    	batch.end();
	}
	
	public void draw() {
		//draw button image on screen
		batch.begin();
    	batch.draw(img, x, y);
    	batch.end();
	}
	
	public void setPageNum(int p) { // set where you want the button to send you to when its clicked
		pageNum = p;
	}
}
