package com.patriciamarissa.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class PauseScreen {
	
	private Batch batch ;
	private Texture pauseMenu, aysMenu;
	private final int TITLE, GAME, SHOP, CONTROLS, PAUSE, YESNO;
	
	private int buttonNum;
	private Button[] buttons, buttons2;
	private Button homeButton;
	private Button playButton;
	private Button controlsButton;
	private Button shopButton;
	private Button yesButton, noButton;
	
	private Sound clickSound;
	
	public PauseScreen (Batch batch) {
		TITLE = 1 ;
		GAME = 2 ;
		SHOP = 3 ;
		CONTROLS = 4 ;
		PAUSE = 6 ;
		YESNO = 9;
		this.batch = batch ;
		
		clickSound = Gdx.audio.newSound(Gdx.files.internal("sounds/click.wav"));
		
		homeButton = new Button(batch, new Texture(Gdx.files.internal("menus/pause home w.png")),
				new Texture(Gdx.files.internal("menus/pause home r.png")), 285, 273, YESNO);
		playButton = new Button(batch, new Texture(Gdx.files.internal("menus/pause play w.png")),
				new Texture(Gdx.files.internal("menus/pause play r.png")), 410, 273, GAME);
		shopButton = new Button(batch, new Texture(Gdx.files.internal("menus/pause shop w.png")),
				new Texture(Gdx.files.internal("menus/pause shop r.png")), 500, 273, YESNO);
		controlsButton = new Button(batch, new Texture(Gdx.files.internal("menus/pause controls w.png")),
				new Texture(Gdx.files.internal("menus/pause controls r.png")), 610, 273, YESNO);
		
		yesButton = new Button(batch, new Texture(Gdx.files.internal("menus/yes w.png")),
				new Texture(Gdx.files.internal("menus/yes r.png")), 370, 264, PAUSE);
		noButton = new Button(batch, new Texture(Gdx.files.internal("menus/no w.png")),
				new Texture(Gdx.files.internal("menus/no r.png")), 540, 267, PAUSE);
		
		buttons = new Button[4];
		buttons[0] = homeButton;
		buttons[1] = playButton;
		buttons[2] = shopButton;
		buttons[3] = controlsButton;
		
		buttons2 = new Button[2];
		buttons2[0] = yesButton;
		buttons2[1] = noButton;
		
		aysMenu = new Texture(Gdx.files.internal("menus/are you sure blank.png"));
		pauseMenu = new Texture(Gdx.files.internal("menus/pause blank.png"));
	}
	
	public void draw () {
		//draw buttons and backgrounds
		Gdx.gl.glClearColor(0, 0, 0, 1);
		batch.begin();
        batch.draw(pauseMenu, 270, 250);
        batch.end();
        
        for (Button b: buttons) {
        	b.draw();
        }
        buttons[buttonNum].drawHoverImg();
	}
	
	public void updatePage (Button[] buttonList) {
		//use LEFT and RIGHT to change highlighted button
		if (Gdx.input.isKeyJustPressed(Keys.RIGHT)) {
			if (buttonNum + 1 < buttonList.length) {
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
				buttonNum = buttonList.length - 1;
			}
		}
	}
	
	public void update () {
		updatePage (buttons) ;
		draw () ;
	}
	
	public int giveNextScreen () {
		//press buttons to update page
		if (Gdx.input.isKeyJustPressed(Keys.ENTER)) {
			//for home, shop and controls button it will ask
			//are you sure first
			if (buttons[buttonNum].equals(homeButton)) {
				yesButton.setPageNum(TITLE);
			}
			else if (buttons[buttonNum].equals(shopButton)) {
				yesButton.setPageNum(SHOP);
			}
			else if (buttons[buttonNum].equals(controlsButton)) {
				yesButton.setPageNum(CONTROLS);
			}
			int temp = buttonNum;
			buttonNum = 0;
			clickSound.play();
			return buttons[temp].getPageNum();
		}
		else if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			return GAME;
		}
			return PAUSE ;
	}
	
	public int areYouSure() {
		//draws menu and buttons and handles keyboard input
		updatePage(buttons2);
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		batch.begin();
        batch.draw(aysMenu, 270, 250);
        batch.end();
        
        for (Button b: buttons2) {
        	b.draw();
        }
        buttons2[buttonNum].drawHoverImg();
        
        if (Gdx.input.isKeyJustPressed(Keys.ENTER)) {
        	clickSound.play();
        	return buttons2[buttonNum].getPageNum();
		}
		return YESNO;
	}
}
