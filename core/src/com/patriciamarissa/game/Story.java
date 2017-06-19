package com.patriciamarissa.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Story{
	private Batch batch;
	private Texture [] pages;
	private int pageNum;
	private final int totPages = 100;
	
	Music music;
	Sound clickSound;
	
	public Story (Batch batch) {
		pages = new Texture [totPages] ;
		this.batch = batch ;
		
		music = Gdx.audio.newMusic(Gdx.files.internal("sounds/main game music.mp3"));
		clickSound = Gdx.audio.newSound(Gdx.files.internal("sounds/click.wav")); //temp
		
		//import images
		for (int i = 0; i < totPages; i++) {
			pages [i] = new Texture(Gdx.files.internal("story/story" + i +".png"));
		}
		
		pageNum = 0;
	}
	
	public void draw () {
		//draws current page on screen
		batch.begin();
	    batch.draw(pages[pageNum], 0, 0, 1000, 600);
	    batch.end();
	}
	
	public void update () {
		music.play();
		draw () ;
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			//press ESCAPE to skip the story
			pageNum = totPages;
		}
	}
	
	public int giveNextScreen () {
		//press arrow keys to change slides
		if (pageNum > totPages - 1) {
			//returns to title menu if its the end of the story
			pageNum = 0;
			return 1;
		}
		else if (Gdx.input.isKeyJustPressed(Keys.RIGHT)) {
			if (pageNum == totPages - 1) {
				clickSound.play();
				return 1;
			}
			pageNum += 1;
		}
		else if (Gdx.input.isKeyJustPressed(Keys.LEFT)) {
			if (pageNum - 1 >= 0) {
				pageNum -= 1;
				clickSound.play();
			}
			
		}
		return 8;
	}
}
