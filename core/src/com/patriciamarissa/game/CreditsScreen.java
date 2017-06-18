package com.patriciamarissa.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class CreditsScreen {
	private Batch batch;
	private Texture shopImg;
	private final int title, credits ;

	Music music;
	
	public CreditsScreen (Batch batch) { // TEMP STUFF FOR NOW
		this.batch = batch;
		shopImg = new Texture(Gdx.files.internal("menus/shop.png"));
		music = Gdx.audio.newMusic(Gdx.files.internal("sounds/not main game music.mp3"));
		title = 1 ;
		credits = 5 ;
	}
	
	public void draw() {
		batch.begin();
	    batch.draw(shopImg, 0, 0);
		batch.end();
	}
	
	public void update () {
		music.play () ;
		draw () ;
	}
	
	public int giveNextScreen () {
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			music.stop () ;
			return title ;
		}
		return credits ;
		
	}
}
