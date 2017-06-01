package com.patriciamarissa.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Upgrade {
	private Batch batch;
	
	private Texture square;
	int x, y;
	int price;
	private boolean owned;
	private Texture icon;
	private String description;
	
	public Upgrade(Batch batch, int x, int y, Texture image) {
		this.batch = batch;
		square = new Texture(Gdx.files.internal("sprites/upgrade.png"));
		this.x = x;
		this.y = y;
		icon = image;
	}
	
	public void draw() {
		batch.begin();
		//System.out.println(x + "," + y);
		batch.draw(square, x,  y);
		batch.draw(icon, x,  y);
		batch.end();
	}
	
}
