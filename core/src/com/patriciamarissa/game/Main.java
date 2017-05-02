package com.patriciamarissa.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class Main extends ApplicationAdapter {
	SpriteBatch batch;
	Player player;
	Background background, background2;
	ArrayList <Platform> platforms ;
	ArrayList <Enemy> enemies ;

	ShapeRenderer rend ;

	int speed;

	private Random rand = new Random(System.currentTimeMillis());
	Random rand2 ;
	
	String page;
	Texture titleImg;
	Texture loseImg;
	long speedtimer ;
	
	@Override
	public void create () {
		speed = 2; //speed on screen moving backwards
		
		batch = new SpriteBatch();
		player = new Player(batch, speed);
		background = new Background(batch, 0, 1920, 1080, speed);
		background2 = new Background(batch, 1920, 1920, 1080, speed);
		int speed = 2;
		page = "START";
		titleImg = new Texture(Gdx.files.internal("TitleImg.png"));
		loseImg = new Texture(Gdx.files.internal("loseImg.png"));
		platforms = new ArrayList <Platform> () ;
		enemies = new ArrayList <Enemy> () ;
		rand2 = new Random ();
		rend = new ShapeRenderer ();
		createPlatforms();
		makeEnemies () ; // REMOVE LATER
		runTimer () ;
	}
	
	public void runTimer () { // TESTING PURPOSES, THANKS SIR!
		Timer.schedule (new Task () { 
			@Override public void run () {
				System.out.println ("tick") ;
				increaseSpeed (1) ;
				}
		} ,10, 10 ) ; // 0 is delay to starting in seconds, 1 is time in between each tick in seconds
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
	
	public void makeEnemies () {
		//int type = rand.nextInt (5) ;
		// for the x and y, need to take into account where platforms are and make it based off that?
		// TEST
		enemies.add (new Enemy (batch, 0, 600, 400, speed)) ;
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
			page = "LOSE";
			return;
		}
		else if (page.equals("LOSE") && player.getLives() == 0) {
			loseScreen();
			return;
		}
	
		System.out.println(player.getLives());
		
		if (page.equals("START")) {
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
	
	public void increaseSpeed (int s) {
		speed += s;
		player.setSpeed(player.getSpeed() + s);
		player.setMoveSpeed(speed);
		for (Platform p : platforms) {
			p.setMoveSpeed(speed);
		}
		for (Enemy e : enemies) {
			e.addSpeed(speed);
		}
	}
	
	public void drawFloor () {
		rend.begin (ShapeType.Filled) ;
		rend.setColor (255, 175, 229, 255) ; // TEMP VALUES
		rend.rect (0, 0, 1200, 100) ;
		rend.end () ;
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
		drawFloor () ;
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
			page = "GAME";
		}
	}
	
	public void loseScreen() {
		//draws screen when player loses
		//checks if player hits ENTER - play again
		batch.begin();
	    batch.draw(loseImg, -100, -120);
        batch.end();
        
        if(Gdx.input.isKeyJustPressed(Keys.ENTER)){
        	reset();
        	page = "GAME";
        }
	}
	
	public void drawPlatforms() {
		for (int i = 0; i < platforms.size(); i++) {
			platforms.get(i).draw();
		}
	}
	
	public void reset() {
		background.setX(0);
		background2.setX(1920);
		player.reset();
		player.resetLives();
    	speed = 2;
    	player.setMoveSpeed(speed);
    	for (Platform p : platforms) {
			p.setMoveSpeed(speed);
		}
		for (Enemy e : enemies) {
			e.addSpeed(speed);
		}
	}
	
	public void drawLives() {
		return;
	}
	
	
	/*public void createPlatforms() {
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
	}*/
	
	@Override
	public void dispose () {
		batch.dispose () ;
		rend.dispose () ;
	}
}



/* BIT MANIPULATION
 * accessing pixel colors. use a pixmap.
 * Pixmap mask ;
 * mask = new Pixmap ("filename") ;
 * int color = mask.getPixel (x, y) ;
 * that'll be in rgba form
 * int red = ( color >> 24 ) ;
 * shift it over 24 bits
 * int green = (color >> 16 ) & OxFf ;
 * int blue = (color >> 8 ) & OxFf ;
 * int alpha = color & OxFf ;
 */
