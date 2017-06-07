package com.patriciamarissa.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class Main extends ApplicationAdapter implements InputProcessor{
	// BEHOLD A USELESS COMMENT
	SpriteBatch batch;
	Player player;
	//Pixmap mask ;
	Background background, background2;
	Floor floor, floor2;
	ArrayList <Platform> platforms;
	ArrayList <Hole> holes;
	Enemy [] enemies;
	Shop shop;
	TitleScreen title ;
	CreditsScreen credits ;
	LoseScreen lose ;
	ControlsScreen control ;
	Story story;
	//MainGame game ;
	PauseScreen pause ;
	
	int titlenum, gamenum, shopnum, controlsnum, creditsnum, pausenum, losenum, storynum ;

	ShapeRenderer rend;
	Texture [] nums;
	int score;
	int speed;
	int money;

	private Random rand = new Random(System.currentTimeMillis());
	Random rand2;
	
	int page;
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
		player = new Player(batch, 10, speed);
		background = new Background(batch, 0, 3430, 600, speed);
		background2 = new Background(batch, 3430, 3430, 600, speed);
		floor = new Floor(batch, 0, 3408, 100, speed);
		floor2 = new Floor(batch, 3408, 3408, 100, speed);
		//mask = new Pixmap (Gdx.files.internal("mask.png")) ;
		lifeImg = new Texture(Gdx.files.internal("sprites/lifeImg.png"));
		platforms = new ArrayList <Platform> () ;
		enemies = new Enemy [4] ;
		rand2 = new Random ();
		rend = new ShapeRenderer ();
		createPlatforms();
		createHoles();
		makeEnemies () ;
		runTimer () ;
		isMoving = false;
		money = 0;
		
		titlenum = 1 ;
		gamenum = 2 ;
		shopnum = 3 ;
		controlsnum = 4 ;
		creditsnum = 5 ;
		pausenum = 6 ;
		losenum = 7 ;
		storynum = 8;
		page = gamenum;
		
		shop = new Shop(batch) ;
		title = new TitleScreen (batch) ;
		pause = new PauseScreen (batch) ;
		lose = new LoseScreen (batch) ;
		control = new ControlsScreen (batch) ;
		credits = new CreditsScreen (batch) ;
		story = new Story(batch);
		
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
		platforms.add(new Platform(batch, speed, 200, 300, player.getMoneyMult()));
		for(int i = 1; i < platNum; i++) {	
			platforms.add(new Platform(batch, speed, 200, platforms.get(i - 1).getX() + platforms.get(i-1).getWidth(), player.getMoneyMult()));
		}
		platforms.add(new Platform(batch, speed, 320, 500, player.getMoneyMult()));
		for(int i = 1; i < platNum; i++) {	
			platforms.add(new Platform(batch, speed, 320, platforms.get(i - 1).getX() + platforms.get(i-1).getWidth(), player.getMoneyMult()));
		}
		platforms.add(new Platform(batch, speed, 440, 800, player.getMoneyMult()));
		for(int i = 1; i < platNum; i++) {	
			platforms.add(new Platform(batch, speed, 440, platforms.get(i - 1).getX() + platforms.get(i-1).getWidth(), player.getMoneyMult()));
		}
		randomizePlatforms () ;
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
			int p = rand.nextInt (platforms.size ()) ;
			int type = rand.nextInt (4) ;
			Platform plat = platforms.get (p) ;
			if (type == 2 && plat.getWidth () < 110) { // only make it a golem if the platform is large enough for the feet.
				type = 0 ;
			}
			if (type != 3) {
				enemies [i] = (new Enemy (batch, type, plat.getX () + plat.getWidth (), plat.getY () + plat.getHeight () - 1, speed, plat, holes)) ;
			}
			else { // lion is always on floor
				enemies [i] = (new Enemy (batch, type, plat.getX () + plat.getWidth (), 100, speed, plat, holes)) ;
				// player.setBoundaries(holes.get(i).getX() - player.getWidth()/2, holes.get(i).getX() + holes.get(i).getWidth()- player.getWidth()/2);
			}
		}
	}
	
	public void makeEnemy (int index, Platform plat) {
		int type = rand.nextInt (4) ;
		if (type == 2 && plat.getWidth () < 110) { // only make it a golem if the platform is large enough for the feet.
			type = 0 ;
		}
		if (type != 3) { // rand.nextInt (300) + 1200
			enemies [index] = (new Enemy (batch, type, plat.getX () + plat.getWidth (), plat.getY () + plat.getHeight () - 1, speed, plat, holes)) ;
		}
		else { // lion is always on floor
			// CHANGE THE X SO IT AVOIDS THE HOLES
			enemies [index] = (new Enemy (batch, type, plat.getX () + plat.getWidth (), 100, speed, plat, holes)) ;
		}
	}
	
	public void move() {
		
		if(Gdx.input.isKeyPressed(Keys.RIGHT)){
			player.moveRight();
			if (page == gamenum) {
				isMoving = true;
			}
		}
		
		else if(Gdx.input.isKeyPressed(Keys.LEFT)){
			player.moveLeft();
			if (page == gamenum) {
				isMoving = true;
			}
				
		}
		else {
			player.endSpriteCycle();
		}
		
		if(Gdx.input.isKeyPressed(Keys.UP)){
			player.jump();
			if (page == gamenum) {
				isMoving = true;
			}
				
		}
		else if(Gdx.input.isKeyPressed(Keys.DOWN) && !player.isJumping()){ //not important
			player.setGroundLvl(100);
				
		}
		
		//System.out.println(isMoving);
		if (isMoving) {
			player.move();
		}
	}
	
	public void update() {
		drawLives();
		
		if (player.dying()) {
			if (player.getLives() > 0) {
				reset(false);
			}
			else {
				reset(true) ;
				page = losenum ;
			}
			return;
		}
		
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			if (page == losenum) {
				page = gamenum;
			}
			else if (page == gamenum){
				page = pausenum;
			}
			else if (page == pausenum) {
				page = gamenum;
			}
		}
		
		if (page == titlenum) {
			startMenu();
			return;
		}
		else if (page == losenum) {
			loseScreen ();
			return;
		}
		else if (page == pausenum) {
			pauseMenu();
			return;
		}
		else if (page == shopnum) {
			shopMenu () ;
			return ;
		}
		else if (page == controlsnum) {
			controlScreen () ;
			return ;
		}
		else if (page == creditsnum) {
			creditsScreen () ;
			return ;
		}
		else if (page == storynum) {
			story.update();
			page = story.giveNextScreen();
			resetSpeed(); //will put if statement or something because you dont need to do this every time
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
				if (platforms.get(i).moneyCollision(player)) {
					money += 1 * (player.getMoneyMult () + 1) ;
				}
				if (platforms.get(i).fireCollision(player)) {
					player.die();
				}
				//System.out.println(platforms.get(i).getWidth());
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
		
		if (player.deactivateHoles == false) {
			for (int i = 0; i < holes.size(); i++) {
				if (holes.get(i).collide(player)) {
					player.setGroundLvl(0);
					player.setInHole(holes.get(i));
				}
				
			}
		}
		
		for (int i = 0 ; i < enemies.length ; i ++) { // removing enemies that have gone off the left or finished dying
			if (enemies [i] == null || enemies [i].getX () + enemies [i].getWidth () <= 0 || enemies [i].isDead () == true) {
				for (int j = 0 ; j < platforms.size () ; j++) {
					if (platforms.get (j).offRight()) {
						enemies [i] = null ;
						makeEnemy (i, platforms.get(j)) ;
					}
				}
			}
		}
		
		if (isMoving) {
			movePlatforms();
			moveHoles();
			moveEnemies () ;
			background.move();
			background2.move();
			floor.move();
			floor2.move();
			updateLasers () ;
			score += speed/2; //temp idk
		}
		move();
		for (Enemy e : enemies) {
			if (e.collide(player) && !e.dying) {
				player.die () ;
				//e.loseHp(10); // just to ensure they die
			}
		}
	}
	
	public void increaseSpeed (int s) {
		speed += s;
		player.setSpeed(player.getSpeed() + s);
		player.setMoveSpeed(speed);
		floor.setMoveSpeed(speed);
		floor2.setMoveSpeed(speed); //ignore that gap in the floor its not important
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
		background.draw();
		background2.draw();
		floor.draw();
		floor2.draw();
		drawPlatforms();
		if (player.deactivateHoles == false) {
			drawHoles();
		}
		drawEnemies () ;
		player.draw();
		drawNum(900, 40, score - score%10);
		drawNum(50, 550, money);
		update();
		
	}
	
	public void startMenu() {
		//draws start menu
		//checks if player hits ENTER - play
		//drawBackground();
		//if (mouse.isOver(playButton, 200, 300)) {
			//page = "GAME";
		//}
		
		/*Gdx.gl.glClearColor(0, 0, 0, 1);
		batch.begin();
        batch.draw(titleImg, 0, 0);
        //batch.draw(playImg, 105, 125);
        batch.end();
        //playButton.draw(batch, 1);
		if (Gdx.input.isKeyPressed(Keys.ENTER)) {
			//menuMusic.dispose();
			page = "GAME";
		}
		else if (Gdx.input.isKeyPressed(Keys.S)) {
			//menuMusic.dispose();
			page = "SHOP";
		}*/
		title.update () ;
		page = title.getPage () ;
		if (page == gamenum) {
			reset(true);
		}
	}
	
	public void pauseMenu() {
		pause.update () ;
		page = pause.giveNextScreen () ;
	}
	
	public void shopMenu () {
		shop.update (money) ;
		page = shop.giveNextScreen () ;
	}
	
	public void controlScreen () {
		control.update () ;
		page = control.giveNextScreen () ;
	}
	
	public void creditsScreen () {
		credits.update () ;
		page = credits.giveNextScreen () ;
	}
	
	public void loseScreen() {
		lose.update () ;
		page = lose.giveNextScreen () ;
		if (page == gamenum) {
			reset(true);
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
			if (platforms.get(i).getX() < 0 - platforms.get(i).getWidth ()) {
				int height = platforms.get(i).getY() ;
				int index = i - 1 ;
				if (i == 0) {
					index = platforms.size () - 1 ;
				}
				platforms.set(i, new Platform(batch, speed, height, platforms.get(index).getX() + platforms.get(index).getWidth(), player.getMoneyMult())) ;
			}
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
			if (!e.dying) {
				e.move () ;
			}
			else {
				e.animateDeath () ;
			}
		}
	}
	
	public void generateCourse() {
		randomizePlatforms();
		randomizeHoles();
		randomizeEnemies () ;
	}
	
	public void randomizePlatforms() {
		platforms.get(0).randPosition(0, 1);
		for (int i = 1; i < 4; i++) {
			platforms.get(i).randPosition(platforms.get(i-1).getX() + platforms.get(i-1).getWidth(), 1);
		}
		platforms.get(4).randPosition(0, 2);
		for (int i = 5; i < 8; i++) {
			platforms.get(i).randPosition(platforms.get(i-1).getX() + platforms.get(i-1).getWidth(), 2);
		}
		platforms.get(8).randPosition(0, 3);
		for (int i = 9; i < 12; i++) {
			platforms.get(i).randPosition(platforms.get(i-1).getX() + platforms.get(i-1).getWidth(), 3);
		}
	}
	
	public void randomizeHoles() {
		holes.get(0).randPosition(0);
		for (int i = 1; i < holes.size(); i++) {
			holes.get(i).randPosition(holes.get(i-1).getX() + holes.get(i-1).getWidth());
		}
	}
	
	public void randomizeEnemies () {
		for (int i = 0 ; i < enemies.length ; i ++) {
			enemies [i] = null ;
		}
		makeEnemies () ;
	}
	
	public void updateLasers () { // ADD! PLAYER! LASERS!
		for (int i = 0 ; i < enemies.length ; i++) {
			if (enemies [i].getType () == 3) { // a golem
				ArrayList <Laser> elasers = enemies [i].getLasers () ;
				if (elasers.size () > 0) {
					for (int j = 0 ; j < elasers.size () ; j++) {
						elasers.get(j).move () ;
						elasers.get(j).draw () ;
						if (elasers.get(j).collide (player)) {
							elasers.get(j).doDamage (player) ;
							enemies [i].removeLaser (elasers.get(j)) ;
						}
						if (enemies [i].getX () + enemies [i].getSprite ().getWidth () <= 0) {
							enemies [i].removeLaser (elasers.get(j)) ;
						}
					}
				}
			}
		}
		// PLAYER LASERS TO BE ADDED
	}
	
	public void reset(boolean gameOver) {
		background.setX(0);
		background2.setX(3430);
		floor.setX(0);
		floor2.setX(3408);
		player.reset();
		player.draw();
		generateCourse(); //change platform and hole positions after death
		if (gameOver) {
			player.resetLives();
			player.resetOneTimeUps();
			score = 0;
			for (int i = 0 ;  i < enemies.length ; i++) {
				enemies [i] = null ;
			}
			makeEnemies () ;
		}
		else {
			page = gamenum;
		}
		isMoving = false;
    	resetSpeed();

	}
	
	public void resetSpeed() {
		player.setSpeed(10);
		speed = 2;
    	
    	floor.setMoveSpeed(speed);
    	floor2.setMoveSpeed(speed);
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

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}
