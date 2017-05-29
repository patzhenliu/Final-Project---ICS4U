package com.patriciamarissa.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Story {
	private Batch batch;
	private Texture [] pages;
	private int pageNum;
	
	public Story (Batch batch) {
		pages = new Texture [6] ;
		this.batch = batch ;
		
		pages [0] = new Texture(Gdx.files.internal("story/story0.png"));
		pages [1] = new Texture(Gdx.files.internal("story/story1.png"));
		pages [2] = new Texture(Gdx.files.internal("story/story2.png"));
		pages [3] = new Texture(Gdx.files.internal("story/story3.png"));
		pages [4] = new Texture(Gdx.files.internal("story/story4.png"));
		pages [5] = new Texture(Gdx.files.internal("story/story5.png"));
		
		pageNum = 0;
	}
	
	public void draw () {
		TextureRegion page = new TextureRegion(pages[pageNum], 0, 0, 900, 600);
		batch.begin();
	    batch.draw(pages[pageNum], 0, 0, 1000, 600);
	    batch.end();
	}
	
	public void updatePage () {
		// use mouse coordinates to figure out which img from the list to use
	}
	
	public void update () {
		updatePage () ;
		draw () ;
	}
	
	public int giveNextScreen () { // idk replace the keyboard commands with cursor stuff eventually
		if (pageNum == 5) {
			return 1;
		}
		else if (Gdx.input.isKeyJustPressed(Keys.ENTER)) {
			pageNum += 1;
		}
		return 8;
	}
}
