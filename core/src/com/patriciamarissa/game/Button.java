package com.patriciamarissa.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Button {
	Batch batch;
	private Texture hover;
	private int x, y;
	boolean clicked;
	
	private Stage stage;
    private TextureRegion myTextureRegion;
    private TextureRegionDrawable myTexRegionDrawable;
    private ImageButton button;
    private boolean isHovering;
    private boolean isClicked;
    private Texture img;
    private int pageNum;

	public Button (Batch batch, Texture img, Texture hover, int x, int y, int pageNum) {
		this.batch = batch;
		this.img = img;
		this.hover = hover;
		this.x = x;
		this.y = y;
		this.pageNum = pageNum;

        myTextureRegion = new TextureRegion(img);
        myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
        button = new ImageButton(myTexRegionDrawable);
        
	}
	
	public int getPageNum() {
		return pageNum;
	}
	
	public void drawHoverImg() {
		batch.begin();
    	batch.draw(hover, x, y);
    	batch.end();
	}
	
	public void draw() {
		batch.begin();
    	batch.draw(img, x, y);
    	batch.end();
	}
}
