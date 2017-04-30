package com.patriciamarissa.game;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Main extends ApplicationAdapter {
	SpriteBatch batch;
	Player player;
	Background background;
	Background background2;
	int speed;
	ArrayList<Platform> platforms;
	private Random rand = new Random(System.currentTimeMillis());
	
	boolean onStartMenu;
	Texture titleImg;
	
	@Override
	public void create () {
		speed = 2; //speed on screen moving backwards
		
		batch = new SpriteBatch();
		player = new Player(batch, speed);
		background = new Background(batch, 0, 1920, 1080, speed);
		background2 = new Background(batch, 1920, 1920, 1080, speed);
		int speed = 2;
		onStartMenu = true;
		titleImg = new Texture(Gdx.files.internal("TitleImg.png"));
		
		createPlatforms();
	}
	
	public void move() {
		if(Gdx.input.isKeyPressed(Keys.RIGHT)){
			player.moveRight();
			
		}
		
		else if(Gdx.input.isKeyPressed(Keys.LEFT)){
			player.moveLeft();
				
		}
		else {
			player.endSpriteCycle();
		}
		
		if(Gdx.input.isKeyPressed(Keys.UP)){
			player.jump();
				
		}
		else if(Gdx.input.isKeyPressed(Keys.DOWN) && !player.isJumping()){ //not important
			player.setGroundLvl(100);
				
		}
		
		player.move();
		
	}
	
	public void update() {
		if (player.dying()) {
			return;
		}
		
		if (onStartMenu) {
			startMenu();
			return;
		}
		
		boolean isOnPlatform = false;
		for (int i = 0; i < platforms.size(); i++) {
			//if (platforms.get(i).collideBottom(player)) {
				//player.stopJump();
			//}
			if (platforms.get(i).collideTop(player)) {
				isOnPlatform = true;
				if (!player.isJumping()) {
					player.setGroundLvl(platforms.get(i).getY() + platforms.get(i).getHeight());
				}	
			}
			
		}
		if (!isOnPlatform && !player.isJumping()) {
			player.setGroundLvl(100);
		}
		
		if (player.getX() < 0) {
			player.die();
			//background.stop();
			//platforms.stop();
		}
			
		player.draw();
		move();
	}

	@Override
	public void render () {
		try{
			Thread.sleep(33);
				
		}
		catch(InterruptedException  ex) {
			Thread.currentThread().interrupt();
		}
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		background.draw();
		background2.draw();
		drawPlatforms();
		update();
		
		
	}
	
	public void startMenu() {
		//draws start menu
		//checks if player hits ENTER - play
		//drawBackground();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		batch.begin();
        batch.draw(titleImg, -60, -100);
        //batch.draw(playImg, 105, 125);
        batch.end();
        
		if(Gdx.input.isKeyPressed(Keys.ENTER)) {
			//menuMusic.dispose();
			onStartMenu = false;
		}
	}
	
	public void drawPlatforms() {
		for (int i = 0; i < platforms.size(); i++) {
			platforms.get(i).draw();
		}
	}
	
	
	public void createPlatforms() {
		platforms = new ArrayList<Platform>();
		int platNum = 4; 
		platforms.add(new Platform(batch, speed, 200, 0));
		for(int i = 1; i < platNum; i++) {	
			platforms.add(new Platform(batch, speed, 200, platforms.get(i - 1).getX() + platforms.get(i-1).getLength()));
			
		}
		
		platforms.add(new Platform(batch, speed, 320, 0));
		for(int i = 1; i < platNum; i++) {	
			platforms.add(new Platform(batch, speed, 320, platforms.get(i - 1).getX() + platforms.get(i-1).getLength()));
			
		}
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
