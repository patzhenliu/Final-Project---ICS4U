package com.patriciamarissa.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class LoseScreen {
	
	private final int title, game, shop, lose ;
	private Batch batch;
	private Texture background ;
	private Texture page ;
	private Texture highscore ;
	private Texture [] pages;
	
	public LoseScreen (Batch batch) {
		title = 1 ;
		game = 2 ;
		shop = 3 ;
		lose = 7 ;
		pages = new Texture [4] ;
		this.batch = batch ;
		
		pages [0] = new Texture(Gdx.files.internal("menus/lose0.png"));
		pages [1] = new Texture(Gdx.files.internal("menus/lose1.png"));
		pages [2] = new Texture(Gdx.files.internal("menus/lose2.png"));
		pages [3] = new Texture(Gdx.files.internal("menus/lose3.png"));
		
		page = pages [0] ;
		background = new Texture(Gdx.files.internal("backgrounds/losebg.png"));
		highscore = new Texture(Gdx.files.internal("menus/highscore and cc.png"));
	}
	
	public void draw () {
		batch.begin();
		batch.draw (background, 0, 0) ;
	    batch.draw(page, 300, 50);
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
			return game ;
		}
		else if (Gdx.input.isKeyPressed(Keys.S)) {
			return shop ;
		}
		else if (Gdx.input.isKeyPressed(Keys.H)) {
			return title ;
		}
		else {
			return lose ;
		}
	}
}
