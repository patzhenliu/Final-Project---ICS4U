package com.patriciamarissa.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class CreditsScreen {
	private Batch batch;
	private Texture img;
	private final int title, credits ;
	int y;

	Music music, winnerMusic;
	
	public CreditsScreen (Batch batch) { // TEMP STUFF FOR NOW
		this.batch = batch;
		img = new Texture(Gdx.files.internal("menus/credits.png"));
		music = Gdx.audio.newMusic(Gdx.files.internal("sounds/not main game music.mp3"));
		winnerMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/medalceremony.mp3"));
		title = 1 ;
		credits = 5 ;
		y = 0 - img.getHeight();
	}
	
	public void draw() {
		//draws image on screen		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
	    batch.draw(img, 0, y);
		batch.end();
	}
	
	public void update () {
		if (y >= -1400) {
			//music changed at a certain point in the credits
			music.dispose();
			winnerMusic.play () ;
		}
		else {
			music.play();
		}
		draw () ;
		//y coordinate is updated so credits scroll upward
		y += 3;
	}
	
	public int giveNextScreen () {
		//press buttons to go to different pages
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE) || y >= 0) {
			//ESC to return to start menu
			music.dispose() ;
			winnerMusic.dispose();
			y = 0 - img.getHeight();
			return title ;
		}
		return credits ;
		
	}
}
