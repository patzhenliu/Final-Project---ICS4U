package com.patriciamarissa.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Button {
	Batch batch;
	private Texture hover;
	private int x, y;
	boolean clicked;
	
	private Stage stage;
    private Texture myTexture;
    private TextureRegion myTextureRegion;
    private TextureRegionDrawable myTexRegionDrawable;
    private ImageButton button;
    private boolean isHovering;

	public Button (Batch batch, Texture img, Texture hover, int x, int y) {
		this.batch = batch;
		this.hover = hover;

        myTextureRegion = new TextureRegion(img);
        myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
        button = new ImageButton(myTexRegionDrawable);
	}
	
	public void draw() {
		if (isHovering) {
        	batch.begin();
        	batch.draw(hover, 360, 237);
        	batch.end();
        }
        else {
        	stage.act(Gdx.graphics.getDeltaTime()); //Perform ui logic
            stage.draw(); //Draw the ui
        }
	}
	
	public boolean isClicked() {
		return false;
	}
}
