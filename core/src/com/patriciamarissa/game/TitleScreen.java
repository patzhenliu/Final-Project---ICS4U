package com.patriciamarissa.game;

import java.util.EventListener;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
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
	
	private final int title, game, shop, controls, credits, story ;
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
    private int buttonNum;
    private Button[] buttons;
    
    Sound clickSound;
	Music music;

	
	public TitleScreen (Batch batch) {
		title = 1 ;
		game = 2 ;
		shop = 3 ;
		controls = 4 ;
		credits = 5 ;
		story = 8;
		this.batch = batch ;
		page = title;
		
		buttonNum = 0;
		
		clickSound = Gdx.audio.newSound(Gdx.files.internal("sounds/click.wav")); //temp
		music = Gdx.audio.newMusic(Gdx.files.internal("sounds/not main game music.mp3"));
		
		playButton = new Button(batch, new Texture(Gdx.files.internal("menus/playButton.png")),
				new Texture(Gdx.files.internal("menus/playHover.png")), 400, 237, game);
		shopButton = new Button(batch, new Texture(Gdx.files.internal("menus/shopButton.png")),
				new Texture(Gdx.files.internal("menus/shopHover.png")), 420, 187, shop);
		controlsButton = new Button(batch, new Texture(Gdx.files.internal("menus/controlButton.png")),
				new Texture(Gdx.files.internal("menus/controlHover.png")), 360, 137, controls);
		creditsButton = new Button(batch, new Texture(Gdx.files.internal("menus/creditsButton.png")),
				new Texture(Gdx.files.internal("menus/creditsHover.png")), 395, 87, credits);
		
		buttons = new Button[4];
		buttons[0] = playButton;
		buttons[1] = shopButton;
		buttons[2] = controlsButton;
		buttons[3] = creditsButton;
		
		background = new Texture(Gdx.files.internal("menus/title blank.png"));
		
	}
	
	public void draw () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		batch.begin();
	    batch.draw(background, 0, 0);
	    batch.end();
	    
	    for (Button b: buttons) {
	    	b.draw();
	    }
	    buttons[buttonNum].drawHoverImg();
        
	}
	
	public int updatePage () {
		music.play();
		if (Gdx.input.isKeyJustPressed(Keys.DOWN)) {
			if (buttonNum + 1 < buttons.length) {
				buttonNum += 1;
			}
			else {
				buttonNum = 0;
			}
		}
		else if (Gdx.input.isKeyJustPressed(Keys.UP)) {
			if (buttonNum - 1 >= 0) {
				buttonNum -= 1;
			}
			else {
				buttonNum = buttons.length - 1;
			}
		}
		
		draw () ;
		
		return giveNextScreen();
	}
	

	public int giveNextScreen () {
		if (Gdx.input.isKeyJustPressed(Keys.ENTER)) {
			music.stop () ;
			music.dispose();
			clickSound.play();
			return buttons[buttonNum].getPageNum();
		}
		else if (Gdx.input.isKeyPressed(Keys.B) && Gdx.input.isKeyPressed(Keys.CONTROL_LEFT) && Gdx.input.isKeyPressed(Keys.ALT_LEFT)) {
			music.stop () ;
			return story;
		}
		return title;
	}

}
