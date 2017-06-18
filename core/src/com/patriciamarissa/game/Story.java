package com.patriciamarissa.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Story implements InputProcessor{
	private Batch batch;
	private Texture [] pages;
	private int pageNum;
	private final int totPages = 100;
	private Texture waitImg;
	
	Music music;
	Sound clickSound;
	
	public Story (Batch batch) {
		pages = new Texture [totPages] ;
		this.batch = batch ;
		
		waitImg = new Texture(Gdx.files.internal("menus/wait.png"));
		music = Gdx.audio.newMusic(Gdx.files.internal("sounds/frogger-music.mp3"));
		clickSound = Gdx.audio.newSound(Gdx.files.internal("sounds/sound-frogger-time.wav")); //temp
		
		
		for (int i = 0; i < totPages; i++) {
			drawWaitImg(); //this doesn't work FOR SOME REASON >:(
			pages [i] = new Texture(Gdx.files.internal("story/story" + i +".png"));
		}
		
		pageNum = 0;
	}
	
	public void drawWaitImg() {
		batch.begin();
		batch.draw(waitImg, 0, 0);
		batch.end();
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
		music.play();
		draw () ;
		if (Gdx.input.isKeyJustPressed(Keys.S)) {
			pageNum = totPages;
		}
	}
	
	public int giveNextScreen () { // idk replace the keyboard commands with cursor stuff eventually
		if (pageNum > totPages - 1) {
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

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}
