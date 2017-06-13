package com.patriciamarissa.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.io.*;

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
	private Sprite[] sprites;
	private int spriteCount;
	private int animationCount;
	private int coins ;
	
	private Texture speech;
	private Texture coinsImg ;
	private Texture [] nums;
	private int [] pricelist ;
	private int [] boughtlist ;
	private Upgrade[] upgrades;
	private final int numOfUpgrades = 6;
	int shopPage;
	
	/* lasers (upgradable to 3) (10, 20, 40)
	 * more life (upgradable to 6, but starts at 3) (20, 40, 80)
	 * higher jump (upgradable twice, or to whatever point hits top of screen) (10, 20, 40)
	 * increase money (upgradable) (20, 40, 80)
	 * remove fire (one time use) (10)
	 * remove holes (one time use) (10)
	 */
	
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
		speech = new Texture(Gdx.files.internal("sprites/shopSpeech.png"));
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
		createUpgrades();
		shopPage = 0;
	}
	
	private void createUpgrades() {
		upgrades = new Upgrade[numOfUpgrades];
		//try {
			//Scanner in = new Scanner(new BufferedReader(new FileReader("upgrades/upgrades.txt")));
			for(int i = 0; i < numOfUpgrades; i++) {
				int ux = i % 4;
				int uy = ((int)(i / 4) )% 2; 
				upgrades[i] = new Upgrade(batch, 120 + ux * 200, 300 - uy * 200, i, pricelist [i],
						new Texture(Gdx.files.internal("upgrades/upgrade" + i + ".png")),
						new Texture(Gdx.files.internal("upgrades/grey" + i + ".png")));
			}
			/*in.close();
		}
		catch(IOException ex) {
			System.out.println("upgrades.txt does not exist");
		}*/
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
	
	public void buy (int index, int [] powers) {
		// LIVES, LASERS, HIGH JUMP, INCREASE MONEY, KILL FIRE, KILL HOLES
		upgrades [index].buy () ;
		powers [index] += 1 ;
	}
	
	public int add (int playermoney, int c) {
		playermoney += c ;
		return playermoney ;
	}
	
	public int deduct (int playermoney, int d) {
		playermoney -= d ;
		return playermoney ;
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
	
	public void update (int playermon) { // needs the player to give an upgrade in case an upgrade is purchased
		// use mouse coordinates to figure out which img from the list to use
		// if something is already bought, grey out the image and make it unclickable
		// else make it so that you can click to buy
		// and maybe play a cha ching sound?
		for (int i = shopPage * 8; i < shopPage* 8 + 8; i++) {
			if (i >= upgrades.length) {
				break;
			}
			upgrades[i].update (playermon);
		}
		animateGhost();
		draw () ;
	}
	
	public void drawGhost() {
		batch.begin();
		batch.draw(ghostSprite, 830, 350);
		batch.draw(speech, 450, 450);
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
