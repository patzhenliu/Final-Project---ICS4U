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
        
        button.setPosition(x, y);
        button.addListener( new ClickListener(Buttons.LEFT) {              
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	System.out.println("CLICKED");
                isClicked = true;
            };
        });
        
        button.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
            	System.out.println("ON");
            	isHovering = true;
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
            	System.out.println("OFF");
            	isHovering = false;
            }
         });
        
        stage = new Stage(new ScreenViewport()); //Set up a stage for the ui
        stage.addActor(button); //Add the button to the stage to perform rendering and take input.
        Gdx.input.setInputProcessor(stage); //Start taking input from the ui
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
