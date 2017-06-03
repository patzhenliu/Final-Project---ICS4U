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
	private final int totPages = 100;
	
	public Story (Batch batch) {
		pages = new Texture [totPages] ;
		this.batch = batch ;
		
		for (int i = 0; i < totPages; i++) {
			pages [i] = new Texture(Gdx.files.internal("story/story" + i +".png"));
		}
		
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
		if (Gdx.input.isKeyJustPressed(Keys.S)) {
			pageNum = totPages;
		}
	}
	
	public int giveNextScreen () { // idk replace the keyboard commands with cursor stuff eventually
		if (pageNum > totPages - 1) {
			return 1;
		}
		else if (Gdx.input.isKeyJustPressed(Keys.ENTER)) {
			if (pageNum == totPages - 1) {
				return 1;
			}
			pageNum += 1;
		}
		return 8;
	}
}
