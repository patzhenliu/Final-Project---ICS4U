package com.patriciamarissa.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class PauseScreen {
	
	private Batch batch ;
	private Texture pauseMenu;
	private final int title, game, shop, controls, pause ;
	
	private int buttonNum;
	private Button[] buttons;
	private Button homeButton;
	private Button playButton;
	private Button controlsButton;
	private Button shopButton;
	
	public PauseScreen (Batch batch) {
		title = 1 ;
		game = 2 ;
		shop = 3 ;
		controls = 4 ;
		pause = 6 ;
		this.batch = batch ;
		
		
		homeButton = new Button(batch, new Texture(Gdx.files.internal("menus/pause home w.png")),
				new Texture(Gdx.files.internal("menus/pause home r.png")), 285, 273, title);
		playButton = new Button(batch, new Texture(Gdx.files.internal("menus/pause play w.png")),
				new Texture(Gdx.files.internal("menus/pause play r.png")), 410, 273, game);
		shopButton = new Button(batch, new Texture(Gdx.files.internal("menus/pause shop w.png")),
				new Texture(Gdx.files.internal("menus/pause shop r.png")), 500, 273, shop);
		controlsButton = new Button(batch, new Texture(Gdx.files.internal("menus/pause controls w.png")),
				new Texture(Gdx.files.internal("menus/pause controls r.png")), 610, 273, controls);
		
		buttons = new Button[4];
		buttons[0] = homeButton;
		buttons[1] = playButton;
		buttons[2] = shopButton;
		buttons[3] = controlsButton;
		
		
		pauseMenu = new Texture(Gdx.files.internal("menus/pause blank.png"));;
	}
	
	public void draw () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		batch.begin();
        batch.draw(pauseMenu, 270, 250);
        batch.end();
        
        for (Button b: buttons) {
        	b.draw();
        }
        buttons[buttonNum].drawHoverImg();
	}
	
	public void updatePage () {
		if (Gdx.input.isKeyJustPressed(Keys.RIGHT)) {
			if (buttonNum + 1 < buttons.length) {
				buttonNum += 1;
			}
			else {
				buttonNum = 0;
			}
		}
		else if (Gdx.input.isKeyJustPressed(Keys.LEFT)) {
			if (buttonNum - 1 >= 0) {
				buttonNum -= 1;
			}
			else {
				buttonNum = buttons.length - 1;
			}
		}
	}
	
	public void update () {
		updatePage () ;
		draw () ;
	}
	
	public int giveNextScreen () { // idk replace the keyboard commands with cursor stuff eventually
		if (Gdx.input.isKeyJustPressed(Keys.ENTER)) {
			return buttons[buttonNum].getPageNum();
		}
			return pause ;
	}
}
