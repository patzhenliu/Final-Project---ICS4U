package com.patriciamarissa.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class TitleScreen {
	
	private final int title, game, shop, controls, credits ;
	private Batch batch;
	private Texture page ;
	private Texture [] pages;
	
	public TitleScreen (Batch batch) {
		title = 1 ;
		game = 2 ;
		shop = 3 ;
		controls = 4 ;
		credits = 5 ;
		pages = new Texture [5] ;
		this.batch = batch ;
		
		pages [0] = new Texture(Gdx.files.internal("menus/Title0.png"));
		pages [1] = new Texture(Gdx.files.internal("menus/Title1.png"));
		pages [2] = new Texture(Gdx.files.internal("menus/Title2.png"));
		pages [3] = new Texture(Gdx.files.internal("menus/Title3.png"));
		pages [4] = new Texture(Gdx.files.internal("menus/Title4.png"));
		
		page = pages [0] ;
	}
	
	public void draw () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		batch.begin();
	    batch.draw(page, 0, 0);
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
		if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			return game ;
		}
		else if (Gdx.input.isKeyJustPressed(Keys.S)) {
			return shop ;
		}
		else if (Gdx.input.isKeyJustPressed(Keys.C)) {
			return controls ;
		}
		else if (Gdx.input.isKeyJustPressed(Keys.TAB)) {
			return credits ;
		}
		else {
			return title ;
		}
	}
}
