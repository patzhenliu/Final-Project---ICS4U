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
	private Texture page ;
	private Texture clickedPage;
	private Texture [] pages;
	
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
		pages = new Texture [5] ;
		this.batch = batch ;
		
		clickedPage = new Texture(Gdx.files.internal("menus/playHover.png"));
		
		myTexture = new Texture(Gdx.files.internal("menus/playButton.png"));
        myTextureRegion = new TextureRegion(myTexture);
        myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
        button = new ImageButton(myTexRegionDrawable);
        
        stage = new Stage(new ScreenViewport()); //Set up a stage for the ui
        stage.addActor(button); //Add the button to the stage to perform rendering and take input.
        Gdx.input.setInputProcessor(stage); //Start taking input from the ui
        
        button.setPosition(360, 237);
        button.addListener( new ClickListener(Buttons.LEFT) {              
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("PLAY");
            };
        });
        
        button.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
            	hover = true;
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
            	hover = false;
            }
         });
		
		
		
		pages [0] = new Texture(Gdx.files.internal("menus/Title0.png"));
		pages [1] = new Texture(Gdx.files.internal("menus/Title1.png"));
		pages [2] = new Texture(Gdx.files.internal("menus/Title2.png"));
		pages [3] = new Texture(Gdx.files.internal("menus/Title3.png"));
		pages [4] = new Texture(Gdx.files.internal("menus/Title4.png"));
		
		page = pages [0] ;
		
		play = new Actor();
		play.setVisible(true);
		play.setColor(255,255,255,255);
		playButton = new Button();
		
	}
	
	public void draw () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		batch.begin();
	    batch.draw(page, 0, 0);
	    batch.end();
	  
	    
	    stage.act(Gdx.graphics.getDeltaTime()); //Perform ui logic
        stage.draw(); //Draw the ui
        if (hover) {
        	batch.begin();
        	batch.draw(clickedPage, 360, 237);
        	batch.end();
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
		if (Gdx.input.isKeyJustPressed(Keys.ENTER)) { //isButtonPressed(Input.Buttons.LEFT)
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
