package com.patriciamarissa.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Money {
	private Batch batch;
	private Texture spritePage;
	private Sprite currentSprite;
	private Sprite[] sprites;
	
	public Money() {
		sprites = new Sprite[4];
		sprites[0] = new Sprite(spritePage, 28, 29, 25, 24);
		sprites[1] = new Sprite(spritePage, 3, 29, 23, 24);
		sprites[2] = new Sprite(spritePage, 80, 29, 5, 24);
		sprites[3] = new Sprite(spritePage, 55, 29, 23, 24);
	}
}
