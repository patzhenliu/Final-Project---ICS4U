package com.patriciamarissa.game;

import java.util.EventListener;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class TitleScreen {
	
	private final int title, game, shop, controls, credits ;
	private Batch batch;
	private int page ;
	private Texture clickedPage;
	private Texture background;
	
	Button playButton;
	Actor play;
	
	private Stage stage;
    private Texture myTexture;
    private TextureRegion myTextureRegion;
    private TextureRegionDrawable myTexRegionDrawable;
    private ImageButton button;
    private boolean hover;

	
	public TitleScreen (Batch batch) {
		title = 1 ;
		game = 2 ;
		shop = 3 ;
		controls = 4 ;
		credits = 5 ;
		this.batch = batch ;
		page = title;
		
		clickedPage = new Texture(Gdx.files.internal("menus/playHover.png"));
		
		myTexture = new Texture(Gdx.files.internal("menus/playButton.png"));
        myTextureRegion = new TextureRegion(myTexture);
        myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
        button = new ImageButton(myTexRegionDrawable);
        
        stage = new Stage(new ScreenViewport()); //Set up a stage for the ui
        stage.addActor(button); //Add the button to the stage to perform rendering and take input.
        Gdx.input.setInputProcessor(stage); //Start taking input from the ui
        
        button.setPosition(400, 237);
        button.addListener( new ClickListener(Buttons.LEFT) {              
            @Override
            public void clicked(InputEvent event, float x, float y) {
                page = game;
            };
        });
        
        button.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
            	System.out.println("ON");
            	hover = true;
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
            	System.out.println("OFF");
            	hover = false;
            }
         });
		
		
		
		background = new Texture(Gdx.files.internal("menus/title blank.png"));
		
		play = new Actor();
		play.setVisible(true);
		play.setColor(255,255,255,255);
		playButton = new Button();
		
	}
	
	public void draw () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		batch.begin();
	    batch.draw(background, 0, 0);
	    batch.end();
	    
        if (hover) {
        	batch.begin();
        	batch.draw(clickedPage, 400, 237);
        	batch.end();
        }
        else {
        	stage.act(Gdx.graphics.getDeltaTime()); //Perform ui logic
            stage.draw(); //Draw the ui
        }
        
	}
	
	public void updatePage () {
		// use mouse coordinates to figure out which img from the list to use
	}
	
	public void update () {
		updatePage () ;
		draw () ;
	}
	
	public int giveNextScreen () { // idk replace the keyboard commands with cursor stuff eventually
		
		if (Gdx.input.isKeyJustPressed(Keys.S)) {
			page = shop ;
		}
		else if (Gdx.input.isKeyJustPressed(Keys.C)) {
			page = controls ;
		}
		else if (Gdx.input.isKeyJustPressed(Keys.TAB)) {
			page = credits ;
		}
		
		int temp = page;
		page = title;
		return page;
	}
}
