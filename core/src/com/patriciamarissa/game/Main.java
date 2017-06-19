package com.patriciamarissa.game;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class Main extends ApplicationAdapter{
	SpriteBatch batch;
	
	Player player;
	Background background, background2;
	Floor floor, floor2;
	ArrayList <Platform> platforms;
	ArrayList <Hole> holes;
	ArrayList <Enemy> enemies;
	
	Shop shop;
	TitleScreen title ;
	CreditsScreen credits ;
	LoseScreen lose ;
	ControlsScreen control ;
	Story story;
	PauseScreen pause ;
	
	ShapeRenderer rend;
	Random rand;
	
	Music gameMusic;
	Sound moneySound;
		
	Texture lifeImg;
	Texture [] nums;
	
	int titleNum, gameNum, shopNum, controlsNum, creditsNum, pauseNum, loseNum, storyNum, ynNum;
	int page;
	int score, speed, money;
	int holeNum, platNum;
	long speedtimer;
	boolean isMoving;
	
	@Override
	public void create () {
		//creating objects
		batch = new SpriteBatch();
		player = new Player(batch, 10, speed);
		background = new Background(batch, 0, 3430, 600, speed);
		background2 = new Background(batch, 3430, 3430, 600, speed);
		floor = new Floor(batch, 0, 3408, 100, speed);
		floor2 = new Floor(batch, 3408, 3408, 100, speed);
		platforms = new ArrayList <Platform> () ;
		enemies = new ArrayList <Enemy> () ;
		
		shop = new Shop(batch) ;
		title = new TitleScreen (batch) ;
		pause = new PauseScreen (batch) ;
		lose = new LoseScreen (batch) ;
		control = new ControlsScreen (batch) ;
		credits = new CreditsScreen (batch) ;
		
		rend = new ShapeRenderer ();
		rand = new Random (System.currentTimeMillis());
		
		//import music and sounds
		gameMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/main game music.mp3"));
		moneySound = Gdx.audio.newSound(Gdx.files.internal("sounds/money1.wav"));

		//import images
		lifeImg = new Texture(Gdx.files.internal("sprites/lifeImg.png"));
		nums = new Texture[10];
		for(int i = 0; i < nums.length; i++){
		    String fileName = "text/" + i + ".png";
		    nums[i] = new Texture(Gdx.files.internal(fileName));
		}
		
		holes = new ArrayList<Hole>(); 
		platforms = new ArrayList<Platform>();
		
		//int representing different pages in the game
		titleNum = 1 ;
		gameNum = 2 ;
		shopNum = 3 ;
		controlsNum = 4 ;
		creditsNum = 5 ;
		pauseNum = 6 ;
		loseNum = 7 ;
		storyNum = 8;
		ynNum = 9;
		page = titleNum;
		
		score = 0;
		speed = 2; //speed on screen moving backwards
		money = 0;
		isMoving = false;
		
		holeNum = 2;
		platNum = 4;

		createHoles(holeNum); // these 4 were lengthy enough to be put into their own methods
		createPlatforms(platNum, 300, 200);
		createPlatforms(platNum, 500, 320);
		createPlatforms(platNum, 800, 440);
		runTimer () ;
	}
	
	public void runTimer () {
		// determines the time intervals between each speedup of the game. speedups occur every 10 seconds.
		// this was taken entirely from an example you gave us. thank you, sir!
		Timer.schedule (new Task () { 
			@Override public void run () {
				//System.out.println ("SPEEDUP") ;
				if (isMoving) {
					increaseSpeed (1) ;
				}
			}
		} , 10, 10) ; // first is delay to starting in seconds, second is time in between each tick in seconds
	}
	
	public void createPlatforms(int num, int x, int y) {
		//creates platnum number of platforms 
		platforms.add(new Platform(batch, speed, x, y, player.getMoneyMult(), player.deactivateFire));
		for(int i = 0; i < num; i++) {	
			platforms.add(new Platform(batch, speed, platforms.get(i).getX() + platforms.get(i).getWidth(), y,player.getMoneyMult(), player.deactivateFire));
			makeEnemy (platforms.get(i)) ;
		}
	}
	
	public void createHoles(int num) {
		//creates and adds num number of holes to the arraylist holes
		holes.add(new Hole(batch, speed, 500));
		for(int i = 0; i < num; i++) {	
			holes.add(new Hole(batch, speed, holes.get(i).getX() + holes.get(i).getWidth()));
		}
	}
	
	public void makeEnemy (Platform plat) {
		// 50% chance that an enemy aligned with the plat parameter will be made.
		// type of enemy is randomized.
		int chance = rand.nextInt (2) ;
		if (chance == 1) {
			int type = rand.nextInt (4) ;
			if (type == 2 && plat.getWidth () < 110) { // only make it a golem if the platform is large enough for the feet.
				type = 0 ;
			}
			if (type != 3) { // anything except the lion
				enemies.add (new Enemy (batch, type, plat.getX () + plat.getWidth (), plat.getY () + plat.getHeight () - 1, speed, plat, holes, player.deactivateHoles)) ;
			}
			else { // lion is always on floor
				enemies.add (new Enemy (batch, type, plat.getX () + plat.getWidth (), 100, speed, plat, holes, player.deactivateHoles)) ;
			}
		}
	}
	
	public void move() {
		//detects when keys are pressed to update player movements
		if(Gdx.input.isKeyPressed(Keys.RIGHT)){
			player.moveRight();
			if (page == gameNum) {
				isMoving = true;
			}
		}
		else if(Gdx.input.isKeyPressed(Keys.LEFT)){
			player.moveLeft();
			if (page == gameNum) {
				isMoving = true;
			}	
		}
		else {
			//player stops walking if no key is pressed
			player.endSpriteCycle();
		}
		
		if(Gdx.input.isKeyPressed(Keys.UP)){
			player.jump();
			if (page == gameNum) {
				isMoving = true;
			}	
		}
		else if(Gdx.input.isKeyPressed(Keys.DOWN) && !player.isJumping()){
			//player's y coordinate is set as the ground
			player.setGroundLvl(100);		
		}

		if (isMoving) {
			//will update player's movements/position if a key was pressed
			player.move();
		}
	}
	
	public void updatePage() {
		//checks which page the game is currently on
		//calls their respective function to draw and update
		if (page == titleNum) {
			gameMusic.pause () ;
			startMenu();
		}
		else if (page == gameNum) {
			gameMusic.pause () ;
			playGame(); 
		}
		else if (page == loseNum) {
			gameMusic.pause () ;
			loseScreen ();	
		}
		else if (page == pauseNum) {
			gameMusic.pause () ;
			pauseMenu();
		}
		else if (page == shopNum) {
			gameMusic.pause () ;
			shopMenu () ;
		}
		else if (page == ynNum) {
			gameMusic.pause () ;
			areYouSureMenu();
		}
		else if (page == controlsNum) {
			gameMusic.pause () ;
			controlScreen () ;
		}
		else if (page == creditsNum) {
			gameMusic.pause () ;
			creditsScreen () ;
		}
		else if (page == storyNum) {
			gameMusic.pause () ;
			storyPage();
		}
	}
	
	public void update() {
		updatePage();		
	}
	
	public void drawGame() {
		//draws all elements of the game
		Gdx.gl.glClearColor(255, 255, 255, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		drawBackground();
		drawFloor();
		drawPlatforms();
		if (player.deactivateHoles == false) {
			drawHoles();
		}
		drawEnemies () ;
		player.draw();
		drawNum(900, 40, score - score%10);
		drawNum(50, 550, money);
		drawLives();
	}
	
	public void playGame() {
		//checks player death
		if (player.dying()) {
			gameMusic.stop();
			System.out.println(player.getLives());
			if (player.getLives() > 0) {
				if (player.getDyingSpeed() == 0) {
					//hole or enemy will disappear if it collides with the player when respawning to prevent a death loop
					for (Hole h: holes) {
						if (h.collide(100, 100, player.getWidth(), player.getHeight())) {
							h.randPosition(1000);
							break;
						}
					}
					for (Enemy e: enemies) {
						if (e.collide(player) == true) {
							e.loseHp(420);
						}
					}
					isMoving = false;
				}
			}
			else {
				//resets all everything in the game and brings user to the
				//lose screen when player dies completely (no lives left)
				reset(true, true) ;
				page = loseNum ;
			}
			return;
		}
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			page = pauseNum;
		}
		
		gameMusic.play();
		boolean isOnPlatform = false;
		for (int i = 0; i < platforms.size(); i++) {
			if (player.getY () + player.getHeight () >= 600) { // top of screen, stop the jump
				player.stopJump () ;
			}
			if (platforms.get(i).collideTop(player)) {
				isOnPlatform = true;
				if (platforms.get(i).moneyCollision(player)) { // get that cash dolla
					money += player.getMoneyMult () + 1;
					moneySound.play();
				}
				if (platforms.get(i).fireCollision(player) && player.deactivateFire == false) {
					player.die();
				}
				if (!player.isJumping()) { // ground level is the platform the player just jumped off of
					player.setGroundLvl(platforms.get(i).getY() + platforms.get(i).getHeight());
				}	
			}
			
		}
		if (!isOnPlatform && !player.isJumping()) {
			player.setGroundLvl(100);
		}
		
		if (player.getX() < 0 || player.getY() <= 0) { // fell off the screen
			player.die();
		}
		
		if(Gdx.input.isKeyJustPressed(Keys.SPACE) && player.getLaserStrength() > 0){
			player.shoot () ;
		}
		
		if (player.deactivateHoles == false) {
			for (int i = 0; i < holes.size(); i++) {
				if (holes.get(i).collide(player)) {
					player.setGroundLvl(0);
					player.setInHole(holes.get(i));
				}
				
			}
		}
		
		for (int i = 0 ; i < enemies.size () ; i++) { 
			// removing enemies that have gone off the left or finished dying
			if (enemies.get(i).getX () + enemies.get(i).getWidth () <= 0 || enemies.get(i).isDead () == true) {
				enemies.remove(i) ;
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
			score += speed/2;
		}
		move();
		for (Enemy e : enemies) {
			if (e.collide(player) && !e.dying) {
				player.die () ;
			}
		}
	}
	
	public void increaseSpeed (int s) {
		//everything in the game moves in the opposite direction faster
		speed += s;
		player.setSpeed(player.getSpeed() + s);
		player.setMoveSpeed(speed);
		floor.setMoveSpeed(speed);
		floor2.setMoveSpeed(speed);
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
		floor.draw();
		floor2.draw();
	}
	
	public void drawBackground() {
		background.draw();
		background2.draw();
	}
	
	@Override
	public void render () {
		//game is drawn
		//calls update to update the page of the game
		try{
			Thread.sleep(33);
				
		}
		catch(InterruptedException  ex) {
			Thread.currentThread().interrupt();
		}
		
		drawGame();
		update();
		
	}
	
	public void startMenu() {
		//updates page with everything concerning the start menu
		page = title.updatePage () ;
		if (page == gameNum) {
			reset(true, false);
		}
	}
	
	public void pauseMenu() {
		//updates page with everything concerning the pause menu
		pause.update () ;
		page = pause.giveNextScreen () ;
	}
	
	public void areYouSureMenu() {
		//updates page with everything concerning the yes or no menu
		page = pause.areYouSure();
		if (page == controlsNum || page == titleNum || page == shopNum) {
			reset (true, true) ;
		}
	}
	
	public void shopMenu () {
		//updates page with everything concerning the shop
		shop.update () ;
		page = shop.giveNextScreen () ;
		if (page != shopNum) {
			shop.updatePlayersUpgrades(player.getPowers());
			money = shop.getMoney () ;
			reset(true, false);
		}
	}
	
	public void controlScreen () {
		//updates page with everything concerning the controls screen
		control.update () ;
		page = control.giveNextScreen () ;
	}
	
	public void creditsScreen () {
		//updates page with everything concerning the credits
		credits.update () ;
		page = credits.giveNextScreen () ;
	}
	
	public void loseScreen() {
		//updates page with everything concerning the lose screen
		lose.update () ;
		page = lose.giveNextScreen () ;
		if (page == gameNum) {
			reset(true, true);
		}
	}
	
	public void storyPage() {
		//updates page with everything concerning the story
		//hidden easter egg that you will regret finding
		if (story == null) {
			story = new Story(batch); //creates object when you activate story
		}
		story.update();
		page = story.giveNextScreen();
		
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
		// moves all platforms with the screen. if a platform has gone off the screen, replace it with a new one
		// that will come in from the right.
		for (int i = 0; i < platforms.size(); i++) {
			platforms.get(i).move();
			if (platforms.get(i).getX() < 0 - platforms.get(i).getWidth ()) { // went off screen to left
				int yPos = platforms.get(i).getY() ;
				Platform newplat = new Platform(batch, speed, 1000 + rand.nextInt (1000), yPos, player.getMoneyMult(), player.deactivateFire) ;
				platforms.set(i, newplat) ;
				platforms.get(i).createMoney (rand.nextInt (5)) ;
				makeEnemy (platforms.get(i)) ;
			}
		}
	}
	
	public void moveHoles() {
		//updates hole position to move backwards across the screen
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
		// enemies are moved with the screen, also do their own walk cycle unless theyre dying
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
		//shuffles the positions of the obstacles
		randomizeHoles();
		enemies.clear () ;
		randomizePlatforms();
	}
	
	public void randomizePlatforms() {
		//randomizes the positions of the platforms
		for (int j = 0; j < 3; j++) { //3 rows of platforms
			platforms.get(j * platNum).randPosition(0, 1);
			for (int i = 1 + platNum * j; i < platNum * (j+1); i++) {
				platforms.get(i).randPosition(platforms.get(i-1).getX() + platforms.get(i-1).getWidth(), 1);
				makeEnemy (platforms.get(i)) ;
			}
		}
		
		for (int i = 0 ; i < platforms.size () ; i++) {
			platforms.get(i).setFireStatus(player.deactivateFire);
		}
	}
	
	public void randomizeHoles() {
		//randomizes the positions of the holes
		holes.get(0).randPosition(0);
		for (int i = 1; i < holes.size(); i++) {
			holes.get(i).randPosition(holes.get(i-1).getX() + holes.get(i-1).getWidth());
		}
	}
	
	public void updateLasers () {
		// moves and draws the player and enemy lasers, and checks to see if theres any collision taking place
		for (int i = 0 ; i < enemies.size () ; i++) { // dealing with enemy lasers
			if (enemies.get(i).getType () == 3) { // a golem, only kind that can shoot
				ArrayList <Laser> elasers = enemies.get(i).getLasers () ;
				if (elasers.size () > 0) {
					for (int j = 0 ; j < elasers.size () ; j++) {
						elasers.get(j).move () ;
						elasers.get(j).draw () ;
						if (elasers.get(j).collide (player)) {
							elasers.get(j).doDamage (player) ;
							enemies.get(i).removeLaser (elasers.get(j)) ; // remove lasers that have collided
						}
						if (enemies.get(i).getX () + enemies.get(i).getSprite ().getWidth () <= 0) {
							// enemy went off screen to left, get rid of their lasers
							enemies.get(i).removeLaser (elasers.get(j)) ;
						}
					}
				}
			}
		}
		ArrayList <Laser> plasers = player.getLasers () ;
		if (plasers.size () > 0) { // dealing with player lasers
			for (int j = 0 ; j < plasers.size () ; j++) {
				Laser currentlas = plasers.get(j);
				currentlas.move () ;
				currentlas.draw () ;
				if (enemies.size () > 0) {
					for (int i = 0 ; i < enemies.size () ; i++) {
						if (currentlas.collide (enemies.get(i)) && enemies.get(i).dying == false && enemies.get(i).isDead() == false) {
							currentlas.doDamage (enemies.get(i)) ;
							player.removeLaser (currentlas) ;
						}
					}
				}
				if (currentlas != null && currentlas.getX () >= 1200) { // get rid of lasers that went off the right
					player.removeLaser (currentlas) ;
				}
			}
		}
	}
	
	public void reset(boolean gameOver, boolean resetUps) {
		//all elements in the game are reset to their original position and speed
		//one time use upgrades in the shop can be repurchased if you're exiting out of the main game
		background.setX(0);
		background2.setX(3430);
		floor.setX(0);
		floor2.setX(3408);
		player.reset(true);
		player.draw();
		if (gameOver) {
			lose.updateCoins (money) ;
			shop.updateCoins(money) ;
			if ((score - score%10) > lose.getHighScore ()) {
				lose.updateHighScore(score - score%10);
			}
			player.resetLives();
			if (resetUps) {
				player.resetOneTimeUps();
				shop.resetOneTimeUps () ;
			}
			shop.updateBoughtList(player.getPowers());
			score = 0;
			enemies.clear () ;
			for (int i = 0 ; i < platforms.size () ; i++) {
				platforms.get(i).updateMoneySprites (player.getMoneyMult()) ;
			}
		}
		else {
			page = gameNum;
		}
		generateCourse(); //change platform and hole positions after death
		isMoving = false;
    	resetSpeed();
	}
	
	public void resetSpeed() {
		//speeds are reset to their original speed
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
		//draws how many lives the player has on screen
		batch.begin();
		for(int i = 0; i < player.getLives() - 1; i ++) {
        	batch.draw(lifeImg, i * 45 + 18, 10);
        }
        batch.end();
	}
	
	public void drawNum(int xDisplace, int y, int num) {
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
