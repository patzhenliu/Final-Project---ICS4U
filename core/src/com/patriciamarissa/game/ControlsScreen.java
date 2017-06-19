package com.patriciamarissa.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class ControlsScreen {
	private Batch batch;
	private Texture shopImg;
	private final int title, controls ;
	
	Music music;
	
	public ControlsScreen (Batch batch) {
		// constructor
		this.batch = batch;
		shopImg = new Texture(Gdx.files.internal("menus/controlscreen.png"));
		music = Gdx.audio.newMusic(Gdx.files.internal("sounds/not main game music.mp3"));
		title = 1 ;
		controls = 4 ;
	}
	
	public void draw() {
		//draws image on screen
		batch.begin();
	    batch.draw(shopImg, 0, 0);
		batch.end();
	}
	
	public void update () {
		// updates the page
		music.play();
		draw () ;
	}
	
	public int giveNextScreen () {
		//press buttons to go to different page
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			//ESC to return to start menu
			music.stop () ;
			return title ;
		}
		else {
			return controls ;
		}
	}
}
