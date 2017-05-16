package com.patriciamarissa.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Floor {
	private Batch batch ;
	private int movespeed, prevX ;
	private Sprite floor ;
	private Texture img ;
	
	public Floor (Batch b, int ms, int px) {
		batch = b ;
		movespeed = ms ;
		prevX = px ;
		img = new Texture (Gdx.files.internal ("floor.png")) ;
		floor = new Sprite (img) ;
	}
	
	public void draw () {
		
	}
}
