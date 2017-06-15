package com.patriciamarissa.game;

import java.util.Arrays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Shop {
	private Batch batch;
	private Texture shopImg;
	private final int title, game, shop ;

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
	
	public Shop(Batch batch) {
		this.batch = batch;
		shopImg = new Texture(Gdx.files.internal("menus/shop.png"));
		title = 1 ;
		game = 2 ;
		shop = 3 ;
		coins = 0 ;
		
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
		
		currentspeech = laserspeech [0] ;
	}
	
	private void createUpgrades() {
		upgrades = new Upgrade[numOfUpgrades] ;
		for(int i = 0; i < numOfUpgrades; i++) {
			int ux = i % 4;
			int uy = ((int)(i / 4) )% 2; 
			upgrades[i] = new Upgrade(batch, 120 + ux * 200, 300 - uy * 200, i, pricelist [i],
					new Texture(Gdx.files.internal("upgrades/icons/upgrade" + i + ".png")),
					new Texture(Gdx.files.internal("upgrades/icons/grey" + i + ".png")));
		}
	}
	
	private void createButtons () {
		buttonNum = 0;
		buttons = new Button[numOfUpgrades];
		
		for(int i = 0; i < numOfUpgrades; i++) {
			int ux = i % 4;
			int uy = ((int)(i / 4) )% 2; 
			buttons[i] = new Button(batch, new Texture(Gdx.files.internal("sprites/upgrade button.png")),
					new Texture(Gdx.files.internal("sprites/upgrade button.png")), 120 + ux * 200, 300 - uy * 200, shop);
		}
	}
	
	public void drawNum(int xDisplace, int y, int num) {
		batch.begin();
		for(int i = 0; i < Integer.toString(num).length(); i++) {
			batch.draw(nums[Integer.parseInt(Integer.toString(num).substring(i, i + 1))], i * 20 + xDisplace, y);
        }
		batch.end();
	}
	
	public void updateCoins (int c) {
		coins = c ;
	}
	
	public void updateBoughtList (int [] playerlist) {
		boughtlist = playerlist ;
	}
	
	public void updatePlayersUpgrades (int [] playerlist) {
		playerlist = boughtlist ;
	}
	
	public void buy (int index, int [] powers) {
		// LIVES, LASERS, HIGH JUMP, INCREASE MONEY, KILL FIRE, KILL HOLES
		upgrades [index].buy () ;
		powers [index] += 1 ;
		coins -= upgrades [index].price ;
		if (index < 4) {
			upgrades [index].updatePrice () ;
		}
	}
	
	public void draw() {
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
	
	public void updateButtons () {
		if (Gdx.input.isKeyJustPressed(Keys.LEFT)) {
			if (buttonNum - 1 >= 0) {
				buttonNum -= 1;
			}
			else {
				buttonNum = buttons.length - 1;
			}
		}
		else if (Gdx.input.isKeyJustPressed(Keys.RIGHT)) {
			if (buttonNum + 1 < buttons.length) {
				buttonNum += 1;
			}
			else {
				buttonNum = 0;
			}
		}
		else if (Gdx.input.isKeyJustPressed(Keys.DOWN)) {
			if (buttonNum + 4 < buttons.length) {
				buttonNum += 4;
			}
		}
		else if (Gdx.input.isKeyJustPressed(Keys.UP)) {
			if (buttonNum - 4 >= 0) {
				buttonNum -= 4;
			}
		}
		if (Gdx.input.isKeyJustPressed(Keys.ENTER)) {
			if (upgrades[buttonNum].isBuyable () == true) {
				buy (buttonNum, boughtlist) ;
			}
		}
	}
	
	public void update (int playermon) { // needs the player to give an upgrade in case an upgrade is purchased
		// use mouse coordinates to figure out which img from the list to use
		// if something is already bought, grey out the image and make it unclickable
		// else make it so that you can click to buy
		// and maybe play a cha ching sound?
		for (int i = shopPage * 8; i < shopPage* 8 + 8; i++) {
			if (i >= upgrades.length) {
				break;
			}
			upgrades[i].update (playermon) ;
		}
		animateGhost() ;
		updateButtons () ;
		updateHoverSquare () ;
		updateGhostText () ;
		draw () ;
	}
	
	public void updateHoverSquare () {
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
			currentspeech = soldout ;
		}
	}
	
	public void drawGhost() {
		batch.begin();
		batch.draw(ghostSprite, 830, 350);
		batch.draw(currentspeech, 450, 450);
		batch.end();
	}
	
	public void animateGhost() {
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
	
	public int giveNextScreen () { // idk replace the keyboard commands with cursor stuff eventually
		if (Gdx.input.isKeyJustPressed(Keys.G)) {
			spriteCount = 0;
			return game ;
		}
		else if (Gdx.input.isKeyJustPressed(Keys.T)) {
			spriteCount = 0;
			return title ;
		}
		else if (Gdx.input.isKeyJustPressed(Keys.LEFT)) {
			if (shopPage > 0){
				shopPage -= 1;
			}
			return shop;
		}
		else if (Gdx.input.isKeyJustPressed(Keys.RIGHT)) {
			//System.out.println(shopPage);
			if (shopPage < (int)(upgrades.length / 8 ) - 1){
				shopPage += 1;
			}
			return shop;
		}
		else {
			return shop ;
		}
	}
}
