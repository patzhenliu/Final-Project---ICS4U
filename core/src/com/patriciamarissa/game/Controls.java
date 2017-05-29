package com.patriciamarissa.game;

public class Controls {
	private Batch batch;
	private Texture shopImg;
	private final int title, game, controls ;
	private int coins ;
	
	public Controls (Batch batch) { // TEMP STUFF FOR NOW
		// THREE CONTROLS PAGES
		// FIRST ONE IS PLAYER
		// SECOND ONE IS ENEMY EXPLAINATIONS
		// THIRD ONE IS PAGE EXPLAINATIONS
		this.batch = batch;
		shopImg = new Texture(Gdx.files.internal("shop.png"));
		title = 1 ;
		game = 2 ;
		controls = 4 ;
	}
	
	public void draw() {
		batch.begin();
	    batch.draw(shopImg, 0, 0);
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
		if (Gdx.input.isKeyPressed(Keys.G)) {
			return game ;
		}
		else if (Gdx.input.isKeyPressed(Keys.T)) {
			return title ;
		}
		else {
			return controls ;
		}
	}
}
