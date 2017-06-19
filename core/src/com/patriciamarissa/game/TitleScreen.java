package com.patriciamarissa.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class TitleScreen {
	
	private final int title, game, shop, controls, credits, story ;
	private Batch batch;
	private Texture background;
	
    private Button playButton;
    private Button shopButton;
    private Button controlsButton;
    private Button creditsButton;
    private int buttonNum;
    private Button[] buttons;
    
    Sound clickSound;
	Music music;

	
	public TitleScreen (Batch batch) {
		this.batch = batch ;
		
		title = 1 ;
		game = 2 ;
		shop = 3 ;
		controls = 4 ;
		credits = 5 ;
		story = 8;
		
		//music and sounds
		clickSound = Gdx.audio.newSound(Gdx.files.internal("sounds/click.wav")); //temp
		music = Gdx.audio.newMusic(Gdx.files.internal("sounds/not main game music.mp3"));
		
		buttonNum = 0;
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
		//draws background and buttons
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
		//use arrow keys to change highlighted button
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
			//ENTER to change pages
			music.stop () ;
			music.dispose();
			clickSound.play();
			int temp = buttonNum;
			buttonNum = 0;
			return buttons[temp].getPageNum();
		}
		else if (Gdx.input.isKeyPressed(Keys.B) && Gdx.input.isKeyPressed(Keys.CONTROL_LEFT) && Gdx.input.isKeyPressed(Keys.ALT_LEFT)) {
			//special combination of keys to activate story
			music.stop () ;
			buttonNum = 0;
			return story;
		}
		return title;
	}

}
