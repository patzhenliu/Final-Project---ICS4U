package com.patriciamarissa.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class ControlsScreen {
	private Batch batch;
	private Texture shopImg, homeButton;
	private final int title, game, controls ;
	private int coins ;
	
	Music music;
	
	public ControlsScreen (Batch batch) { // TEMP STUFF FOR NOW
		// THREE CONTROLS PAGES
		// FIRST ONE IS PLAYER
		// SECOND ONE IS ENEMY EXPLAINATIONS
		// THIRD ONE IS PAGE EXPLAINATIONS
		this.batch = batch;
		shopImg = new Texture(Gdx.files.internal("menus/shop.png"));
		homeButton = new Texture(Gdx.files.internal("menus/returnHome.png"));
		music = Gdx.audio.newMusic(Gdx.files.internal("sounds/frogger-music.mp3"));
		title = 1 ;
		game = 2 ;
		controls = 4 ;
	}
	
	public void draw() {
		batch.begin();
	    batch.draw(shopImg, 0, 0);
	    batch.draw(homeButton, 0 ,0);
		batch.end();
	}
	
	public void update () {
		music.play();
		draw () ;
	}
	
	public int giveNextScreen () { // idk replace the keyboard commands with cursor stuff eventually
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			return title ;
		}
		else {
			return controls ;
		}
	}
}
