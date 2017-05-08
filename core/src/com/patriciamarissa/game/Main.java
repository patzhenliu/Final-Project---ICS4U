package com.patriciamarissa.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

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

public class Main extends ApplicationAdapter {
	SpriteBatch batch;
	Player player;
	Pixmap mask ;
	Background background, background2;
	ArrayList <Platform> platforms;
	ArrayList <Hole> holes;
	Enemy [] enemies;

	ShapeRenderer rend;
	Texture [] nums;
	int score;
	int speed;

	private Random rand = new Random(System.currentTimeMillis());
	Random rand2;
	
	String page;
	Texture titleImg;
	Texture loseImg;
	Texture lifeImg;
	Texture pauseImg;
	long speedtimer;
	
	boolean isMoving;
	
	@Override
	public void create () {
		speed = 2; //speed on screen moving backwards
		
		batch = new SpriteBatch();
		player = new Player(batch, speed);
		background = new Background(batch, 0, 1920, 1080, speed);
		background2 = new Background(batch, 1920, 1920, 1080, speed);
		page = "START";
		mask = new Pixmap (Gdx.files.internal("mask.png")) ;
		titleImg = new Texture(Gdx.files.internal("TitleImg.png"));
		loseImg = new Texture(Gdx.files.internal("loseImg.png"));
		lifeImg = new Texture(Gdx.files.internal("lifeImg.png"));
		pauseImg = new Texture(Gdx.files.internal("pause.png"));
		platforms = new ArrayList <Platform> () ;
		enemies = new Enemy [5] ;
		rand2 = new Random ();
		rend = new ShapeRenderer ();
		createPlatforms();
		createHoles();
		makeEnemies () ;
		runTimer () ;
		isMoving = false;
		
		nums = new Texture[10];
		for(int i = 0; i < nums.length; i++){
		    String fileName = "text/" + i + ".png";
		    nums[i] = new Texture(Gdx.files.internal(fileName));
		}
		score = 0;
	}
	
	public void runTimer () { // TESTING PURPOSES, THANKS SIR!
		Timer.schedule (new Task () { 
			@Override public void run () {
				//System.out.println ("tick") ;
				increaseSpeed (1) ;
				}
		} ,10, 10 ) ; // 0 is delay to starting in seconds, 1 is time in between each tick in seconds
	}
	
	public void createPlatforms() {
		platforms = new ArrayList<Platform>();
		int platNum = 4; 
		platforms.add(new Platform(batch, speed, 200, 0));
		for(int i = 1; i < platNum; i++) {	
			platforms.add(new Platform(batch, speed, 200, platforms.get(i - 1).getX() + platforms.get(i-1).getWidth()));
		}
		platforms.add(new Platform(batch, speed, 320, 0));
		for(int i = 1; i < platNum; i++) {	
			platforms.add(new Platform(batch, speed, 320, platforms.get(i - 1).getX() + platforms.get(i-1).getWidth()));
		}
		platforms.add(new Platform(batch, speed, 440, 0));
		for(int i = 1; i < platNum; i++) {	
			platforms.add(new Platform(batch, speed, 440, platforms.get(i - 1).getX() + platforms.get(i-1).getWidth()));
		}
	}
	
	public void createHoles() {
		holes = new ArrayList<Hole>();
		int holeNum = 2; 
		holes.add(new Hole(batch, speed, 500));
		for(int i = 1; i < holeNum; i++) {	
			holes.add(new Hole(batch, speed, holes.get(i - 1).getX() + holes.get(i-1).getWidth()));
		}
	}
	
	public void makeEnemies () {
		// TEST Batch batch, int t, int x, int y, int s
		for (int i = 0 ; i < enemies.length ; i ++) {
			//if (enemies [i] == null) {
				int p = rand.nextInt (platforms.size ()) ;
				int type = rand.nextInt (4) ;
				Platform plat = platforms.get (p) ;
				System.out.println ("PLATFORM" + p) ;
				if (type != 3) {
					enemies [i] = (new Enemy (batch, type, plat.getX () + plat.getWidth () - 100, plat.getY () + plat.getHeight () - 1, speed, plat)) ;
				}
				else { // lion is always on floor
					enemies [i] = (new Enemy (batch, type, plat.getX () + plat.getWidth () - 100, 100, speed, plat)) ;
				}
			//}
		}
	}
	
	public void makeEnemy (int index) {
		// TEST Batch batch, int t, int x, int y, int s
				int p = rand.nextInt (platforms.size ()) ;
				int type = rand.nextInt (5) ;
				Platform plat = platforms.get (p) ;
				System.out.println ("PLATFORM" + p) ;
				if (type != 3) {
					enemies [index] = (new Enemy (batch, type, rand.nextInt (500) + 1000, plat.getY () + plat.getHeight () - 1, speed, plat)) ;
				}
				else { // lion is always on floor
					enemies [index] = (new Enemy (batch, type, rand.nextInt (500) + 1000, 100, speed, plat)) ;
				}
	}
	
	public void move() {
		if(Gdx.input.isKeyPressed(Keys.RIGHT)){
			player.moveRight();
			if (page.equals("GAME")) {
				isMoving = true;
			}
			
		}
		
		else if(Gdx.input.isKeyPressed(Keys.LEFT)){
			player.moveLeft();
			if (page.equals("GAME")) {
				isMoving = true;
			}
				
		}
		else {
			player.endSpriteCycle();
		}
		
		if(Gdx.input.isKeyPressed(Keys.UP)){
			player.jump();
			if (page.equals("GAME")) {
				isMoving = true;
			}
				
		}
		else if(Gdx.input.isKeyPressed(Keys.DOWN) && !player.isJumping()){ //not important
			player.setGroundLvl(100);
				
		}
		
		//System.out.println(page);
		if (isMoving) {
			player.move();
		}
		
	}
	
	public void update() {
		drawLives();
		
		if (player.dying()) {
			page = "LOSE";
			if (player.getLives() > 0) {
				reset(false);
			}
			return;
		}
		else if (page.equals("LOSE") && player.getLives() == 0) {
			loseScreen();
			return;
		}
		//System.out.println(page);
		//System.out.println(player.getLives());
		//System.out.println(player.getLives());
		
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			if (page.equals("PAUSE")) {
				page = "GAME";
			}
			else {
				page = "PAUSE";
			}
		}
		
		if (page.equals("START")) {
			startMenu();
			return;
		}
		else if (page.equals("PAUSE")) {
			pauseMenu();
			return;
		}
		
		
		boolean isOnPlatform = false;
		for (int i = 0; i < platforms.size(); i++) {
			//if (platforms.get(i).collideBottom(player)) {
				//player.stopJump();
			//}
			if (player.getY () + player.getHeight () >= 600) {
				player.stopJump () ;
			}
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
		
		if (player.getX() < 0 || player.getY() <= 0) {
			player.die();
			//background.stop();
			//platforms.stop();
		}
			
		for (int i = 0; i < holes.size(); i++) {
			//System.out.println(holes.get(i).collide(player));
			
			if (holes.get(i).collide(player)) {
				player.setGroundLvl(0);
				player.setBoundaries(holes.get(i).getX() - player.getWidth()/2, holes.get(i).getX() + holes.get(i).getWidth()- player.getWidth()/2);
			}
			
		}
		
		for (int i = 0 ; i < enemies.length ; i ++) {
			if (enemies [i].getX () + enemies [i].getWidth () <= 0) {
				System.out.println ("CHANGE") ;
				enemies [i] = null ;
				makeEnemy (i) ;
			}
		}
		
		if (isMoving) {
			movePlatforms();
			moveHoles();
			moveEnemies () ;
			score += speed/2; //temp idk
		}
		move();
	}
	
	public void increaseSpeed (int s) {
		speed += s;
		player.setSpeed(player.getSpeed() + s);
		player.setMoveSpeed(speed);
		for (Platform p : platforms) {
			p.setMoveSpeed(speed);
		}
		for (Hole h : holes) {
			h.setMoveSpeed(speed);
		}
		for (Enemy e : enemies) {
			e.setSpeed(speed);
		}
	}
	
	public void drawFloor () {
		rend.begin (ShapeType.Filled) ;
		rend.setColor (0, 0, 0, 255) ; // TEMP VALUES 255, 175, 229, 255
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
		
		Gdx.gl.glClearColor(255, 255, 255, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//background.draw();
		//background2.draw();
		drawPlatforms();
		drawFloor () ;
		drawHoles();
		drawEnemies () ;
		player.draw();
		drawNum(900, 40, score - score%10);
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
	
	public void pauseMenu() {
		//temporary picture and stuff
		Gdx.gl.glClearColor(0, 0, 0, 1);
		batch.begin();
        batch.draw(pauseImg, 270, 250);
        //batch.draw(playImg, 105, 125);
        batch.end();
	}
	
	public void loseScreen() {
		//draws screen when player loses
		//checks if player hits ENTER - play again
		batch.begin();
	    batch.draw(loseImg, -100, -120);
        batch.end();
        
        if(Gdx.input.isKeyJustPressed(Keys.ENTER)){
        	reset(true);
        	page = "GAME";
        }
	}
	
	public void drawPlatforms() {
		for (int i = 0; i < platforms.size(); i++) {
			platforms.get(i).draw();
		}
	}
	
	public void drawHoles() {
		for (int i = 0; i < holes.size(); i++) {
			holes.get(i).draw();
		}
	}
	
	public void movePlatforms() {
		for (int i = 0; i < platforms.size(); i++) {
			platforms.get(i).move();
		}
	}
	
	public void moveHoles() {
		for (int i = 0; i < holes.size(); i++) {
			holes.get(i).move();
		}
	}
	
	public void drawEnemies () {
		for (Enemy e : enemies) {
			e.draw () ;
		}
	}
	
	public void moveEnemies () {
		for (Enemy e : enemies) {
			e.moveWithPlat () ;
			e.move (holes) ;
		}
	}
	
	public void reset(boolean gameOver) {
		background.setX(0);
		background2.setX(1920);
		player.reset();
		player.draw();
		if (gameOver) {
			player.resetLives();
			score = 0;
			for (int i = 0 ;  i < enemies.length ; i++) {
				enemies [i] = null ;
			}
			makeEnemies () ;
		}
		else {
			page = "GAME";
		}
		isMoving = false;
    	speed = 2;
    	player.setMoveSpeed(speed);
    	for (Platform p : platforms) {
			p.setMoveSpeed(speed);
		}

    	for (Hole h : holes) {
			h.setMoveSpeed(speed);
		}
		for (Enemy e : enemies) {
			e.setSpeed(speed);
		}

	}
	
	public void drawLives() {
		batch.begin();
		for(int i = 0; i < player.getLives() - 1; i ++) {
        	batch.draw(lifeImg, i * 45 + 18, 10);
        }
        batch.end();
	}
	
	public void drawNum(int xDisplace, int y, int num) { //i was lazy ill make it nicer
		//draws numbers
		batch.begin();
		for(int i = 0; i < Integer.toString(num).length(); i++) {
			batch.draw(nums[Integer.parseInt(Integer.toString(num).substring(i, i + 1))], i * 20 + xDisplace, y);
        }
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose () ;
		rend.dispose () ;
	}
}
