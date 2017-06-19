package com.patriciamarissa.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class LoseScreen {
	
	private final int title, game, shop, lose ;
	private Batch batch;
	private Texture background ;
	private Texture highscoreandcoins ;
	private Texture loseMenu;
	private Texture [] nums;
	
	private Button playButton;
	private Button shopButton;
	private Button homeButton;
	
	private Button[] buttons;
	private int buttonNum;
	private int highscore ;
	private int coins ;
	
	Music music;
	Sound clickSound;
	
	public LoseScreen (Batch batch) {
		this.batch = batch ;
		title = 1 ;
		game = 2 ;
		shop = 3 ;
		lose = 7 ;
		
		highscore = 0 ;
		coins = 0 ;
		
		music = Gdx.audio.newMusic(Gdx.files.internal("sounds/game over music.mp3"));
		clickSound = Gdx.audio.newSound(Gdx.files.internal("sounds/click.wav"));

		nums = new Texture[10];
		for(int i = 0; i < nums.length; i++){
		    String fileName = "text/" + i + ".png";
		    nums[i] = new Texture(Gdx.files.internal(fileName));
		}
		
		//buttons on the lose screen - play again, go to shop, go home
		buttonNum = 0;
		playButton = new Button(batch, new Texture(Gdx.files.internal("menus/go again w.png")),
				new Texture(Gdx.files.internal("menus/go again r.png")), 390, 273, game);
		shopButton = new Button(batch, new Texture(Gdx.files.internal("menus/go shop w.png")),
				new Texture(Gdx.files.internal("menus/go shop r.png")), 390, 207, shop);
		homeButton = new Button(batch, new Texture(Gdx.files.internal("menus/go home w.png")),
				new Texture(Gdx.files.internal("menus/go home r.png")), 390, 153, title);
		
		buttons = new Button[3];
		buttons[0] = playButton;
		buttons[1] = shopButton;
		buttons[2] = homeButton;
		
		//import images
		loseMenu = new Texture(Gdx.files.internal("menus/lose blank.png"));
		background = new Texture(Gdx.files.internal("backgrounds/losebg.png"));
		highscoreandcoins = new Texture(Gdx.files.internal("menus/highscore and cc.png"));
	}
	
	public void draw () {
		//draws buttons, score and backgrounds on screen
		batch.begin();
		batch.draw (background, 0, 0) ;
	    batch.draw(loseMenu, 300, 50);
	    batch.draw (highscoreandcoins, 700, 0) ;
	    batch.end();
	    
	    for (Button b: buttons) {
        	b.draw();
	    }
	    buttons[buttonNum].drawHoverImg();
	    
		drawNum (875, 90, coins) ;
		drawNum (875, 20, highscore) ;
	}
	
	public void drawNum(int xDisplace, int y, int num) {
		//draws numbers
		batch.begin();
		for(int i = 0; i < Integer.toString(num).length(); i++) {
			batch.draw(nums[Integer.parseInt(Integer.toString(num).substring(i, i + 1))], i * 20 + xDisplace, y);
        }
		batch.end();
	}
	
	public void updateHighScore (int hs) {
		highscore = hs ;
	}
	
	public void updateCoins (int c) {
		coins = c ;
	}
	
	public int getHighScore () {
		return highscore ;
	}
	
	public void updatePage () {
		//checks for keyboard input to update selected button
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
	}
	
	public void update () {
		music.play () ;
		updatePage () ;
		draw () ;
	}
	
	public int giveNextScreen () {
		//press button to change page
		if (Gdx.input.isKeyJustPressed(Keys.ENTER)) {
			//ENTER to play again, shop or return to start menu
			music.dispose() ;
			return buttons[buttonNum].getPageNum();
		}
		return lose;
	}
}
