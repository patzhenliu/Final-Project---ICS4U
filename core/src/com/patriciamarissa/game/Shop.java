package com.patriciamarissa.game;

import java.util.Arrays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Shop {
	private Batch batch;
	private Texture shopImg;
	private final int title, shop ;

	private Texture spritePage;
	private Sprite ghostSprite;
	private Sprite [] sprites;
	private Texture [] lifespeech ;
	private Texture [] jumpspeech ;
	private Texture [] laserspeech ;
	private Texture [] moneyspeech ;
	private Texture firespeech ;
	private Texture holespeech ;
	private Texture currentspeech ;
	private Texture soldout ;
	private Texture yourebroke ;
	private int spriteCount;
	private int animationCount;
	private int coins ;
	private Texture coinsImg ;
	private Texture [] nums;
	private int [] pricelist ;
	private int [] boughtlist ;
	private Upgrade[] upgrades;
	private Button [] buttons ;
	private final int numOfUpgrades = 6;
	int shopPage;
	int buttonNum ;
	
	Music music;
	Sound clickSound, buySound, errorSound;
	
	public Shop(Batch batch) {
		// constructor
		this.batch = batch;
		shopImg = new Texture(Gdx.files.internal("menus/shop.png"));
		title = 1 ;
		shop = 3 ;
		coins = 0 ;
		
		music = Gdx.audio.newMusic(Gdx.files.internal("sounds/not main game music.mp3"));
		clickSound = Gdx.audio.newSound(Gdx.files.internal("sounds/click.wav"));
		buySound = Gdx.audio.newSound(Gdx.files.internal("sounds/chaching.wav"));
		errorSound = Gdx.audio.newSound(Gdx.files.internal("sounds/error.wav"));
		
		nums = new Texture[10];
		for(int i = 0; i < nums.length; i++){
		    String fileName = "text/" + i + ".png";
		    nums[i] = new Texture(Gdx.files.internal(fileName));
		}
		
		spritePage = new Texture(Gdx.files.internal("sprites/shopGhost.png"));
		coinsImg = new Texture (Gdx.files.internal("menus/coins text for shop.png")) ;

		sprites = new Sprite[5];
		sprites[0] = new Sprite(spritePage, 190, 278, 130, 120);
		sprites[1] = new Sprite(spritePage, 354, 278, 134, 120);
		sprites[2] = new Sprite(spritePage, 520, 278, 140, 120);
		sprites[3] = new Sprite(spritePage, 354, 278, 134, 120);
		sprites[4] = new Sprite(spritePage, 190, 278, 130, 120);
		
		ghostSprite = sprites[0];
		
		spriteCount = 0;
		animationCount = 5;
		pricelist = new int [6] ;
		Arrays.fill (pricelist, 10) ;
		pricelist [1] = 20 ; // increase lives
		pricelist [3] = 20 ; // increase money
		boughtlist = new int [6] ;
		Arrays.fill(boughtlist, 0) ;
		createUpgrades() ;
		createButtons () ;
		createSpeech () ;
		shopPage = 0;
	}
	
	private void createSpeech () {
		// loads in all of the ghost's speech, first goes to the speech that goes with the first box
		laserspeech = new Texture [3] ;
		lifespeech = new Texture [3] ;
		jumpspeech = new Texture [3] ;
		moneyspeech = new Texture [3] ;
		
		for (int i = 0 ; i < laserspeech.length ; i++) {
			laserspeech [i] = new Texture(Gdx.files.internal("upgrades/Newspeech/laser" + i + "SB.png")) ;
		}
		
		for (int i = 0 ; i < lifespeech.length ; i++) {
			lifespeech [i] = new Texture(Gdx.files.internal("upgrades/Newspeech/life" + i + "SB.png")) ;
		}
		
		for (int i = 0 ; i < jumpspeech.length ; i++) {
			jumpspeech [i] = new Texture(Gdx.files.internal("upgrades/Newspeech/jump" + i + "SB.png")) ;
		}
		
		for (int i = 0 ; i < moneyspeech.length ; i++) {
			moneyspeech [i] = new Texture(Gdx.files.internal("upgrades/Newspeech/money" + i + "SB.png")) ;
		}
		
		firespeech = new Texture(Gdx.files.internal("upgrades/Newspeech/fireSB.png")) ;
		holespeech = new Texture(Gdx.files.internal("upgrades/Newspeech/holeSB.png")) ;
		soldout = new Texture(Gdx.files.internal("upgrades/Newspeech/soldOutSB.png")) ;
		yourebroke = new Texture(Gdx.files.internal("upgrades/Newspeech/noMoneySB.png")) ;
		
		currentspeech = laserspeech [0] ;
	}
	
	private void createUpgrades() { // loads in all the upgrade images and makes the upgrade objects
		upgrades = new Upgrade[numOfUpgrades] ;
		for(int i = 0; i < numOfUpgrades; i++) {
			int ux = i % 4;
			int uy = ((int)(i / 4) )% 2; 
			upgrades[i] = new Upgrade(batch, 120 + ux * 200, 300 - uy * 200, i, pricelist [i],
					new Texture(Gdx.files.internal("upgrades/icons/upgrade" + i + ".png")),
					new Texture(Gdx.files.internal("upgrades/icons/grey" + i + ".png")));
		}
	}
	
	private void createButtons () { // makes a button for each box on the page
		buttonNum = 0;
		buttons = new Button[numOfUpgrades];
		
		for(int i = 0; i < numOfUpgrades; i++) {
			int ux = i % 4;
			int uy = ((int)(i / 4) )% 2; 
			buttons[i] = new Button(batch, new Texture(Gdx.files.internal("sprites/upgrade button.png")),
					new Texture(Gdx.files.internal("sprites/upgrade button.png")), 120 + ux * 200, 300 - uy * 200, shop);
		}
	}
	
	public void drawNum(int xDisplace, int y, int num) { // draws your money in the corner
		batch.begin();
		for(int i = 0; i < Integer.toString(num).length(); i++) {
			batch.draw(nums[Integer.parseInt(Integer.toString(num).substring(i, i + 1))], i * 20 + xDisplace, y);
        }
		batch.end();
	}
	
	public void updateCoins (int c) { // updates shop's coins to match player's coins
		coins = c ;
	}
	
	public void updateBoughtList (int [] playerlist) { 
		// matches the player's list; done when entering the shop, purpose is in case one time powerups have been reset
		boughtlist = playerlist ;
	}
	
	public void updatePlayersUpgrades (int [] playerlist) { 
		// makes the player's list match shop list; done when exiting the shop
		playerlist = boughtlist ;
	}
	
	public void resetOneTimeUps () {
		// completely remakes the one-time powerups, because setting their levels to 0 made them stuck there.
		int ux = 4 % 4;
		int uy = ((int)(4 / 4) )% 2; 
		int ux2 = 5 % 4;
		int uy2 = ((int)(5 / 4) )% 2; 
		upgrades [4] = new Upgrade(batch, 120 + ux * 200, 300 - uy * 200, 4, pricelist [4],
				new Texture(Gdx.files.internal("upgrades/icons/upgrade" + 4 + ".png")),
				new Texture(Gdx.files.internal("upgrades/icons/grey" + 4 + ".png")));
		upgrades [5] = new Upgrade(batch, 120 + ux2 * 200, 300 - uy2 * 200, 5, pricelist [5],
				new Texture(Gdx.files.internal("upgrades/icons/upgrade" + 5 + ".png")),
				new Texture(Gdx.files.internal("upgrades/icons/grey" + 5 + ".png")));
		updateUpgrades () ;
	}
	
	public void buy (int index, int [] powers) {
		// buys the powerup at the index
		upgrades [index].buy () ;
		powers [index] += 1 ;
		coins -= upgrades [index].price ;
		if (index < 4) { // upgradable
			upgrades [index].updatePrice () ;
		}
		buySound.play();
	}
	
	public void draw() { // draws the shop
		batch.begin();
	    batch.draw(shopImg, 0, 0);
	    batch.draw(coinsImg, 10, 555) ;
		batch.end();
		
		for (int i = shopPage * 8; i < shopPage* 8 + 8; i++) {
			if (i >= upgrades.length) {
				break;
			}
			upgrades[i].draw();
		}
		drawGhost();
		drawNum (150, 560, coins) ;
	}
	
	public void updateButtons () { // registers which key has been pressed and acts accordingly
		if (Gdx.input.isKeyJustPressed(Keys.LEFT)) {
			if (buttonNum - 1 >= 0) {
				buttonNum -= 1;
				clickSound.play();
			}
			else {
				buttonNum = buttons.length - 1;
			}
		}
		else if (Gdx.input.isKeyJustPressed(Keys.RIGHT)) {
			if (buttonNum + 1 < buttons.length) {
				buttonNum += 1;
				clickSound.play();
			}
			else {
				buttonNum = 0;
			}
		}
		else if (Gdx.input.isKeyJustPressed(Keys.DOWN)) {
			if (buttonNum + 4 < buttons.length) {
				buttonNum += 4;
				clickSound.play();
			}
		}
		else if (Gdx.input.isKeyJustPressed(Keys.UP)) {
			if (buttonNum - 4 >= 0) {
				buttonNum -= 4;
				clickSound.play();
			}
		}
		if (Gdx.input.isKeyJustPressed(Keys.ENTER)) {
			if (upgrades[buttonNum].isBuyable () == true) {
				buy (buttonNum, boughtlist) ;
			}
			else {
				errorSound.play();
			}
		}
	}
	
	public void update () { // updates everything
		music.play () ;
		animateGhost() ;
		updateButtons () ;
		updateHoverSquare () ;
		updateUpgrades () ;
		updateGhostText () ;
		draw () ;
	}
	
	public void updateUpgrades () { // changes the icon colors depending on the player's money
		for (Upgrade u : upgrades) {
			u.updateIcon(coins);
		}
	}
	
	public void updateHoverSquare () { // darkens the box thats currently selected
		for (int i = 0 ; i < upgrades.length ; i++) {
			if (buttonNum == i) {
				upgrades [i].updateSquare(true);
			}
			else {
				upgrades [i].updateSquare(false);
			}
		}
	}
	
	public void updateGhostText () { 
		// changes the ghost's text depending on the currently selected box
		// and whether or not its sold out or if the player cant afford it
		if (buttonNum == 0) {
			currentspeech = laserspeech [upgrades [buttonNum].level - 1]; 
		}
		else if (buttonNum == 1) {
			currentspeech = lifespeech [upgrades [buttonNum].level - 1]; 
		}
		else if (buttonNum == 2) {
			currentspeech = jumpspeech [upgrades [buttonNum].level - 1]; 
		}
		else if (buttonNum == 3) {
			currentspeech = moneyspeech [upgrades [buttonNum].level - 1]; 
		}
		else if (buttonNum == 4) {
			currentspeech = firespeech ;
		}
		else if (buttonNum == 5) {
			currentspeech = holespeech ;
		}
		if (upgrades [buttonNum].isBuyable () == false) {
			if (upgrades [buttonNum].isBecausePlayerBroke(coins)) {
				currentspeech = yourebroke ;
			}
			else {
				currentspeech = soldout ;
			}
		}
	}
	
	public void drawGhost() { // draws ghost
		batch.begin();
		batch.draw(ghostSprite, 830, 350);
		batch.draw(currentspeech, 450, 450);
		batch.end();
	}
	
	public void animateGhost() { // animates ghost
		if (spriteCount == 0) {
			spriteCount = sprites.length - 1;
		}
		if (spriteCount > 0) {
			animationCount--;
			if (animationCount == 0) {
				spriteCount--;
				animationCount = 5;
			}
			ghostSprite = sprites[spriteCount];
		}
	}
	
	public int giveNextScreen () { // checks to see if the player has inputted that they want to go to another screen
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			music.stop() ;
			spriteCount = 0;
			return title;
		}
		else {
			return shop ;
		}
	}
	
	public int getMoney () {
		return coins ;
	}
}
