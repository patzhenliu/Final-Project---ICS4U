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
	
	private Stage stage;
    private Texture myTexture;
    private TextureRegion myTextureRegion;
    private TextureRegionDrawable myTexRegionDrawable;
    private ImageButton button;
    private boolean hover;
    
    private Button playButton;
    private Button shopButton;
    private Button controlsButton;
    private Button creditsButton;

	
	public TitleScreen (Batch batch) {
		title = 1 ;
		game = 2 ;
		shop = 3 ;
		controls = 4 ;
		credits = 5 ;
		this.batch = batch ;
		page = title;
		
		playButton = new Button(batch, new Texture(Gdx.files.internal("menus/playButton.png")),
				new Texture(Gdx.files.internal("menus/playHover.png")), 400, 237);
		shopButton = new Button(batch, new Texture(Gdx.files.internal("menus/shopButton.png")),
				new Texture(Gdx.files.internal("menus/shopHover.png")), 420, 187);
		controlsButton = new Button(batch, new Texture(Gdx.files.internal("menus/controlButton.png")),
				new Texture(Gdx.files.internal("menus/controlHover.png")), 360, 137);
		creditsButton = new Button(batch, new Texture(Gdx.files.internal("menus/creditsButton.png")),
				new Texture(Gdx.files.internal("menus/creditsHover.png")), 395, 87);
		
		background = new Texture(Gdx.files.internal("menus/title blank.png"));
		
	}
	
	public void draw () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		batch.begin();
	    batch.draw(background, 0, 0);
	    batch.end();
	    playButton.draw();
	    shopButton.draw();
	    controlsButton.draw();
	    creditsButton.draw();
        
	}
	
	public int updatePage () {
		draw () ;
		return giveNextScreen();
		

	}
	

	public int giveNextScreen () { // idk replace the keyboard commands with cursor stuff eventually
		if (playButton.isClicked()) {
			return game;
		}
		else if (shopButton.isClicked()) {
				return shop;
		}
		else if (controlsButton.isClicked()) {
				return controls;
		}

		else if (creditsButton.isClicked()) {
				return credits;
		}
		return title;

		}

}
