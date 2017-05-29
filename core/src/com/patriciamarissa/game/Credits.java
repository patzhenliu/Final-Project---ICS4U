package com.patriciamarissa.game;

public class Credits {
	private Batch batch;
	private Texture shopImg;
	private final int title, credits ;
	private int coins ;
	
	public Credits (Batch batch) { // TEMP STUFF FOR NOW
		this.batch = batch;
		shopImg = new Texture(Gdx.files.internal("shop.png"));
		title = 1 ;
		credits = 5 ;
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
		if (Gdx.input.isKeyPressed(Keys.ENTER)) {
			return title ;
		}
		else {
			return credits ;
		}
	}
}
