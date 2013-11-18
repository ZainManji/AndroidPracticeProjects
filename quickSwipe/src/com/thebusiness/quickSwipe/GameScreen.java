package com.thebusiness.quickSwipe;
 
import java.util.Iterator;
import java.util.ArrayList;
 
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.InputProcessor;
//import com.badlogic.gdx.audio.Music;
//import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.thebusiness.quickSwipe.Assets;
import com.thebusiness.quickSwipe.GameUtilities;
 
public class GameScreen implements Screen, InputProcessor, GestureListener {
	
	//Game Screen variables
	QuickSwipe game;
	int circlesPerRow;
	ArrayList<Circle> circles;
	int dropSpeed;
	int pastGameTime;
	int livesLeft;
	int randomNum;
	int randomCircleNum;
	int circlesGatheredInARow;
	long previousTime = TimeUtils.nanoTime();
	long currentTime;
	int prevCirclesGathered;
	int powerupTime;
	Paddle paddle;
	Boolean gamePaused = false;
	Boolean showTransition = false;
	Boolean showTwinkle = false;
	
	//Animation variables
	private static final int TRANSITION_ANIMATION_FRAME_COLS = 3;
	private static final int TRANSITION_ANIMATION_FRAME_ROWS = 4;
	Animation transitionAnimation;
	TextureRegion[] transitionFrames;
	TextureRegion transitionFrame;
	float transitionStateTime;
	
	private static final int TWINKLE_ANIMATION_FRAME_COLS = 12;
	private static final int TWINKLE_ANIMATION_FRAME_ROWS = 1;
	Animation twinkleAnimation;
	TextureRegion[] twinkleFrames;
	float twinkleStateTime;
	TextureRegion twinkleFrame;
	
	private static final int BIRD_ANIMATION_FRAME_COLS = 5;        
	private static final int BIRD_ANIMATION_FRAME_ROWS = 3;        
	Animation redBirdAnimation; 
	Animation blueBirdAnimation;
	Animation yellowBirdAnimation;
	Animation greenBirdAnimation;
	Animation immunityBirdAnimation;
	TextureRegion[] redFlyFrames;
	TextureRegion[] blueFlyFrames; 
	TextureRegion[] greenFlyFrames; 
	TextureRegion[] yellowFlyFrames; 
	TextureRegion[] immunityFlyFrames; 
	TextureRegion currentFrame;          
	float stateTime;                             
	
	
	//GameScreen constructor - Gets called once
	public GameScreen(QuickSwipe gameRef) {
		
		this.game = gameRef;
		
		//Animation construction for flying bird of different colours
		animationConstruction();                                   
		
		//Reinitialize all the variables to values at the beginning of a new game
		reinitialize();
 
		//Create the paddle
		paddle = new Paddle();
		
		//Set paddle's first colour
		paddle.colour = newColour(false);
		
		//Spawn the first row of circles
		spawnRow();
	}
	
	
	//Animation construction needs improvement
	public void animationConstruction() {
		
		//Create texture region from each bird colour animation sheet
		TextureRegion[][] redBird = TextureRegion.split(Assets.redBirdAnimationSheet, 
				Assets.redBirdAnimationSheet.getWidth() / BIRD_ANIMATION_FRAME_COLS, 
				Assets.redBirdAnimationSheet.getHeight() / BIRD_ANIMATION_FRAME_ROWS);
		TextureRegion[][] blueBird = TextureRegion.split(Assets.blueBirdAnimationSheet, 
				Assets.blueBirdAnimationSheet.getWidth() / BIRD_ANIMATION_FRAME_COLS, 
				Assets.blueBirdAnimationSheet.getHeight() / BIRD_ANIMATION_FRAME_ROWS);
		TextureRegion[][] greenBird = TextureRegion.split(Assets.greenBirdAnimationSheet, 
				Assets.greenBirdAnimationSheet.getWidth() / BIRD_ANIMATION_FRAME_COLS, 
				Assets.greenBirdAnimationSheet.getHeight() / BIRD_ANIMATION_FRAME_ROWS);
		TextureRegion[][] yellowBird = TextureRegion.split(Assets.yellowBirdAnimationSheet, 
				Assets.yellowBirdAnimationSheet.getWidth() / BIRD_ANIMATION_FRAME_COLS, 
				Assets.yellowBirdAnimationSheet.getHeight() / BIRD_ANIMATION_FRAME_ROWS);
		TextureRegion[][] immunityBird = TextureRegion.split(Assets.immunityBirdAnimationSheet, 
				Assets.immunityBirdAnimationSheet.getWidth() / BIRD_ANIMATION_FRAME_COLS, 
				Assets.immunityBirdAnimationSheet.getHeight() / BIRD_ANIMATION_FRAME_ROWS);
		
		TextureRegion[][] transitionFrameArray = TextureRegion.split(Assets.transitionAnimationSheet, 
				Assets.transitionAnimationSheet.getWidth() / TRANSITION_ANIMATION_FRAME_COLS, 
				Assets.transitionAnimationSheet.getHeight() / TRANSITION_ANIMATION_FRAME_ROWS);
		
		TextureRegion[][] twinkleFrameArray = TextureRegion.split(Assets.twinkleAnimationSheet, 
				Assets.twinkleAnimationSheet.getWidth() / TWINKLE_ANIMATION_FRAME_COLS, 
				Assets.twinkleAnimationSheet.getHeight() / TWINKLE_ANIMATION_FRAME_ROWS);
		
		//Create array that will hold all of the separate images in the twinkle animation
		twinkleFrames = initializeTextureRegion(TWINKLE_ANIMATION_FRAME_ROWS, TWINKLE_ANIMATION_FRAME_COLS,
				twinkleFrameArray);
        twinkleAnimation = new Animation(0.083f, twinkleFrames); 
        twinkleStateTime = 0f;
		
		//Create array that will hold all of the separate images in the transition animation
		transitionFrames = initializeTextureRegion(TRANSITION_ANIMATION_FRAME_ROWS, TRANSITION_ANIMATION_FRAME_COLS,
				transitionFrameArray);
        transitionAnimation = new Animation(0.083f, transitionFrames); 
        transitionStateTime = 0f;
		
		
		//Create array that will hold all of the separate images in the red flying bird animation
		redFlyFrames = initializeTextureRegion(BIRD_ANIMATION_FRAME_ROWS, BIRD_ANIMATION_FRAME_COLS, redBird);
        redBirdAnimation = new Animation(0.025f, redFlyFrames); 
        
        //Create array that will hold all of the separate images in the blue flying bird animation
        blueFlyFrames = initializeTextureRegion(BIRD_ANIMATION_FRAME_ROWS, BIRD_ANIMATION_FRAME_COLS, blueBird);
        blueBirdAnimation = new Animation(0.025f, blueFlyFrames);
        
        //Create array that will hold all of the separate images in the yellow flying bird animation
        yellowFlyFrames = initializeTextureRegion(BIRD_ANIMATION_FRAME_ROWS, BIRD_ANIMATION_FRAME_COLS, yellowBird);
        yellowBirdAnimation = new Animation(0.025f, yellowFlyFrames);
        
        //Create array that will hold all of the separate images in the green flying bird animation        
        greenFlyFrames = initializeTextureRegion(BIRD_ANIMATION_FRAME_ROWS, BIRD_ANIMATION_FRAME_COLS, greenBird);
        greenBirdAnimation = new Animation(0.025f, greenFlyFrames);
         
        //Create array that will hold all of the separate images in the immunity flying bird animation
        immunityFlyFrames = initializeTextureRegion(BIRD_ANIMATION_FRAME_ROWS, BIRD_ANIMATION_FRAME_COLS, immunityBird);
        immunityBirdAnimation = new Animation(0.025f, immunityFlyFrames);
        
        stateTime = 0f; 
	}
 
	
	public TextureRegion[] initializeTextureRegion(int frameRows, int frameCols, TextureRegion [][] spriteSheet) {
		TextureRegion [] tmp = new TextureRegion[frameCols * frameRows];
        int index = 0;
        for (int i = 0; i < frameRows; i++) {
        	for (int j = 0; j < frameCols; j++) {
        		tmp[index++] = spriteSheet[i][j];
			}
		}
        
        return tmp;
	}
	
	
	//Spawns/creates a row of circles (Note we don't actually store each row of circles, we store each circle in an
	//ArrayList that contains all the circles currently existing)
	private void spawnRow() {
		
		//Choose which circle in the row will contain the same colour as the paddle
		randomCircleNum = MathUtils.random(0, circlesPerRow-1);
		
		//Create all the circles in the new row
		for (int i = 0; i < circlesPerRow; i++) {
			
			Circle fallingCircle = new Circle();
			
			//Get the 'x' coordinates of each circle (3 CIRCLES PER ROW IS NOT IMPLEMENTED, ONLY 4 CIRCLES PER ROW)
			if (circlesPerRow == 3) {
				fallingCircle.x = GameUtilities.ROW_COORDINATE_3[i];
			}
			else if (circlesPerRow == 4) {
				fallingCircle.x = GameUtilities.ROW_COORDINATE_4[i];
			}
			
			//Get other attributes of the circle
			fallingCircle.y = GameUtilities.Y_MAX;
			fallingCircle.width = 50;
			fallingCircle.height = 50;
			
			//If the circle we are creating in the row is the circle we chose to match the paddle, then assign the paddle's
			//colour to that circle, else pick a different random colour for the circle. Also assign a power up to the
			//circle (We don't assign a power up to circles with different colours than the paddle)
			if (randomCircleNum == i) {
				fallingCircle.colour = paddle.colour;
				
				//If the paddle doesn't have a power up assigned to it, then randomly assign a power up to the circle
				//we are creating (power up can be none). However, if the paddle already has a power up assigned to 
				//it, then don't give a power up to the circle.
				if (paddle.powerup == QuickSwipe.Powerup.none) {
					fallingCircle.powerup = newPowerup();
				}
				else {
					fallingCircle.powerup = QuickSwipe.Powerup.none;
				}
			}
			else {
				fallingCircle.colour = newColour(false);
				fallingCircle.powerup = QuickSwipe.Powerup.none;
			}
			
			//Add circle to the list of all circles.
			circles.add(fallingCircle);
		}
	}
 
	
	
	//Update method
	@Override
	public void render(float delta) {
		
		//If the game has not been paused
		if (!gamePaused) {
			
			// clear the screen with an RGB colour. The arguments to glClearColor are the red, green
			// blue and alpha component in the range [0,1] of the colour to be used to clear the screen.
			Gdx.gl.glClearColor(0, 0.2f, 0.2f, 1);
			Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			
			//If 12 seconds has passed and the speed at which the circles drop is below 500, increase the speed at
			//which the circles drop (Max drop speed is 500) (Increase drop speed by 2%)
			if ((game.gameTime - pastGameTime > 12) && dropSpeed < 700) {
				pastGameTime = game.gameTime;
				dropSpeed = (int)(1.02*dropSpeed);
			}
			
			//This piece of code is temporary, was going to use it when implementing 3 circles per row and then as time
			//passes goes to 4 circles per row. (Does not affect the game right now)
			if (game.circlesGathered > 20) {
				circlesPerRow = 4;
			}
			
			//Change paddle colour after user catches 3 circles of the correct colour
			if ((game.circlesGathered - prevCirclesGathered) > 3) {
				paddle.colour = newColour(true);
				//Play new paddle colour sound
				Assets.dingSound.play();
				prevCirclesGathered = game.circlesGathered;
			}
			
			//Get current time in nanoseconds
			currentTime = TimeUtils.nanoTime();
			
			//Check when to spawn next row (i.e. when no row exists, or when only one row exists and has dropped a certain
			//amount on the screen)
			if ((circles.isEmpty()) || ((circles.get(0).y < 0.3*GameUtilities.Y_MAX) && (circles.size() == circlesPerRow))) {
				spawnRow();
			}
		    
			//Move the circles down the screen, remove any circles that are beneath the bottom edge of the screen
			//or that hit the paddle.
		    Iterator<Circle> iter = circles.iterator();
		    while (iter.hasNext()) {
		    	Circle fallingCircle = iter.next();
		        fallingCircle.y -= dropSpeed * Gdx.graphics.getDeltaTime();
		        
		        //Remove circle if below the screen (Note circle size is 50px in height)
		        if(fallingCircle.y + 50 < 0) {
		        	iter.remove();
		        }
		        
		        //Circle hits paddle
		        if(fallingCircle.overlaps(paddle)) { 
		        	
		        	//If the circle's colour is the paddle colour, or the paddle has the immunity power up, or if the
		        	//circle has a power up that is not 'none', then increase score, play pop sound, etc., else lose a life
		        	//vibrate phone, etc.
		        	if ((fallingCircle.colour == paddle.colour) || (paddle.powerup == QuickSwipe.Powerup.immunity) ||
		        			(fallingCircle.powerup != QuickSwipe.Powerup.none)) {
		        		
		        		//Play good sound
		        		Assets.catchSound.play();
		        		
		        		//ADD BIRD SPINNING ANIMATION HERE
		        		showTwinkle = true;
		        		
		        		
		        		game.circlesGathered++;
		        		circlesGatheredInARow++;
		        		
		        		//Check if circle has a power up, and if so, change paddle's power up to the circle's power up
		        		//and reset the power up time
		        		if (fallingCircle.powerup != QuickSwipe.Powerup.none) {
		        			paddle.powerup = fallingCircle.powerup;
		        			powerupTime = GameUtilities.POWERUP_TIME;
		        		}
		        		
		        		incrementScore();
		        	}
		        	else if (fallingCircle.colour != paddle.colour) {
		        		
		        		//Play bad sound
		        		showTransition = true;
		        		Gdx.input.vibrate(200);
		        		circlesGatheredInARow = 0;
		        		game.livesLeft--;
		        		paddle.powerup = QuickSwipe.Powerup.none;
		        	}
		        	
		            iter.remove();
		        }
		    }
		    
		    
		    //Update time every second
		    if((int)((currentTime - previousTime)/1000000000) >= 1) {
		    	
		    	// Resets previous time
		    	previousTime = currentTime;
		    	
		    	//Update game time
		    	game.gameTime++;
		    	pastGameTime = 0;
		    	
		    	//Decrement power up time as long as it is greater than 0, once it hits 0 then remove the power up from
		    	//paddle
		    	if (powerupTime > 0) {
		    		powerupTime--;
		    	}
		    	else {
		    		paddle.powerup = QuickSwipe.Powerup.none;
		    	}
		    }
	
		    
		    //If user loses all lives, reinitialize all variables to that of a new game, and move to the game over screen
		    if (game.livesLeft <= 0) {
				reinitialize();
				//Assets.gameMusic.stop();
				game.setScreen(game.gameOverScreen);
			}
		}
		
		//Draw all the objects to the screen
		drawObjects();
	}
	
	
	
	//Increase score
	public void incrementScore() {
		
		//Multiplier increases by 1 every 5 circles gathered in a row
		int comboMultiplier = circlesGatheredInARow / 5;
		if (comboMultiplier == 0) {
			comboMultiplier = 1;
		}
	
		//Applies appropriate powerup to score
		if (paddle.powerup == QuickSwipe.Powerup.doublePoints) {
			game.score += (50*2)*comboMultiplier;
		} 
		else if (paddle.powerup == QuickSwipe.Powerup.triplePoints) {
			game.score += (50*3)*comboMultiplier;
		}
		else {
			game.score += 50*comboMultiplier;
		}
		
	}
	
	
	//Chooses and returns a new random power up
	public QuickSwipe.Powerup newPowerup() { 
		
		//Pick a random number with probability 1/20
		int randNum;
		randNum = MathUtils.random(1, 20);
		
		//Returns a power up
		if (randNum == 2) {
			return QuickSwipe.Powerup.doublePoints;
		}
		else if (randNum == 3) {
			return QuickSwipe.Powerup.triplePoints;
		}
		else if (randNum == 4) {
			return QuickSwipe.Powerup.immunity;
		}
		else {
			return QuickSwipe.Powerup.none;
		}
	}
	
	
	//Chooses and returns a new random colour. Parameter is a boolean indicating whether we are changing
	//the paddle colour or a circle colour. (Paddle colour can't be the same as previous paddle colour)
	public QuickSwipe.Colour newColour(Boolean changePaddle) {
		
		int randomColumn;
		
		//Gets index of last circle in the array
		int index = circles.size() - 1;
		
		//Checks if we need to change the paddle colour
		if (changePaddle) {
			
			//Picks a random circle in the newest row of circles with a colour not the same as the paddle, and makes
			//that the paddle colour
			randomColumn = MathUtils.random(0, circlesPerRow-1);
			while (circles.get(index - randomColumn).colour == paddle.colour) {
				randomColumn = MathUtils.random(0, circlesPerRow-1);
			}
			
			return circles.get(index - randomColumn).colour;
		}
		
		//Pick a colour for a circle that is different than the paddle colour and returns that colour
		randomNum = MathUtils.random(0, 3);
		while (QuickSwipe.Colour.values()[randomNum] == paddle.colour) {
			randomNum = MathUtils.random(0, 3);
		}

		return QuickSwipe.Colour.values()[randomNum];
	}
	
	
	
	//initialize all of the variables that get reset/intialized at the beginning of a new game
	public void reinitialize() {
		
		dropSpeed = 300;
		pastGameTime = 0;
		circlesPerRow = 4;
		prevCirclesGathered = 0;
		game.livesLeft = GameUtilities.MAX_LIVES;
		game.savedScore = game.score;
		game.score = 0;
		game.circlesGathered = 0;
		prevCirclesGathered = 0;
		game.savedGameTime = game.gameTime;
		game.gameTime = 0;
		circles = new ArrayList<Circle>();
		powerupTime = 0;
		circlesGatheredInARow = 0;
	}
	
	
	
	//Draw all the objects to the screen
	public void drawObjects() {
		
		//Get the stateTime for the animation of the flying bird
		stateTime += Gdx.graphics.getDeltaTime();                      
        
		//Get the image of the correct coloured bird from the respective animation sheet
        if (paddle.powerup == QuickSwipe.Powerup.immunity) {
        	currentFrame = immunityBirdAnimation.getKeyFrame(stateTime, true);
		}
		else if (paddle.colour == QuickSwipe.Colour.blue) {
			currentFrame = blueBirdAnimation.getKeyFrame(stateTime, true);
		}
		else if (paddle.colour == QuickSwipe.Colour.green) {
			currentFrame = greenBirdAnimation.getKeyFrame(stateTime, true);
		}
		else if (paddle.colour == QuickSwipe.Colour.red) {
			currentFrame = redBirdAnimation.getKeyFrame(stateTime, true);
		}
		else if (paddle.colour == QuickSwipe.Colour.yellow) {
			currentFrame = yellowBirdAnimation.getKeyFrame(stateTime, true);
		}
		
        //
        if (showTransition) {
        	transitionStateTime += Gdx.graphics.getDeltaTime(); 
        	transitionFrame = transitionAnimation.getKeyFrame(transitionStateTime, true);
        	
        	if (transitionStateTime > 1.0) {
        		showTransition = false;
        		transitionStateTime = 0f;
        	}
        }
        
        //
        if (showTwinkle) {
        	twinkleStateTime += Gdx.graphics.getDeltaTime(); 
        	twinkleFrame = twinkleAnimation.getKeyFrame(twinkleStateTime, true);
        	
        	if (twinkleStateTime > 1.0) {
        		showTwinkle = false;
        		twinkleStateTime = 0f;
        	}
        }
        
		game.batch.begin();
		
		//Background
		game.batch.draw(Assets.cartoonSky, 0, 0, GameUtilities.X_MAX, GameUtilities.Y_MAX, 0, 0, 1024, 768, false, false);
		
		//Scores, stats, etc
		game.font.draw(game.batch, "Score: " + game.score, GameUtilities.CENTER_X, 740);
		game.font.draw(game.batch, "Circles Collected: " + game.circlesGathered, GameUtilities.CENTER_X, 700);
		game.font.draw(game.batch, "Lives Left: " + game.livesLeft, GameUtilities.CENTER_X, 720);
		game.font.draw(game.batch, "Game Time: " + Integer.toString(game.gameTime), GameUtilities.CENTER_X, 680);
		game.font.draw(game.batch, "Powerup Time: " + Integer.toString(powerupTime), GameUtilities.CENTER_X, 660);
		game.font.draw(game.batch, "Combo Multiplier: " + Integer.toString(circlesGatheredInARow/5) + "x", GameUtilities.CENTER_X, 640);
		
		//Draw twinkle animation if needed
		if (showTwinkle) {
			game.batch.draw(twinkleFrame, paddle.x, paddle.y, 60, 60);
		}
		
		//Draw blowing up transition animation if needed
		if (showTransition) {
			game.batch.draw(transitionFrame, paddle.x - 25, paddle.y, 100, 100);
		}
		
		//Draw correct coloured paddle (Bird in this case) (size was originally 50x50)
		game.batch.draw(currentFrame, paddle.x, paddle.y, 55, 55);		

		//Draw the row of circles
		for (Circle fallingCircle: circles) {
			
			//Draw correct colour of circle/powerup (SIZES were originally 50x50)
			if (fallingCircle.powerup == QuickSwipe.Powerup.doublePoints) {
				game.batch.draw(Assets.capsule, fallingCircle.x, fallingCircle.y, 55,
						55,	0, 0, 551, 551, false, false);
			}
			else if (fallingCircle.powerup == QuickSwipe.Powerup.immunity) {
				game.batch.draw(Assets.star, fallingCircle.x, fallingCircle.y, 55,
						55,	0, 0, 225, 225, false, false);
			}
			else if (fallingCircle.powerup == QuickSwipe.Powerup.triplePoints) {
				game.batch.draw(Assets.mushroom, fallingCircle.x, fallingCircle.y, 55,
						55,	0, 0, 300, 300, false, false);
			}
			else if (fallingCircle.colour == QuickSwipe.Colour.blue) {
				game.batch.draw(Assets.blueCircle, fallingCircle.x, fallingCircle.y, 55,
						55,	0, 0, 343, 343, false, false);
			}
			else if (fallingCircle.colour == QuickSwipe.Colour.green) {
				game.batch.draw(Assets.greenCircle, fallingCircle.x, fallingCircle.y, 55,
						55,	0, 0, 343, 343, false, false);
			}
			else if (fallingCircle.colour == QuickSwipe.Colour.red) {
				game.batch.draw(Assets.redCircle, fallingCircle.x, fallingCircle.y, 55,
						55,	0, 0, 343, 343, false, false);
			}
			else if (fallingCircle.colour == QuickSwipe.Colour.yellow) {
				game.batch.draw(Assets.yellowCircle, fallingCircle.x, fallingCircle.y, 55,
						55,	0, 0, 343, 343, false, false);
			}
		}
		
		//Pause and Quit Button
		game.batch.draw(Assets.pauseButton, 0, 0, 50, 50, 0, 0, 259, 194, false, false);
		game.batch.draw(Assets.quitButton, (GameUtilities.X_MAX - 50), 0, 50, 50, 0, 0, 144, 144, false, false);
		
		//Draws Pause screen if game is paused
		if (gamePaused) {
			//Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 0f);
			//Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			game.batch.draw(Assets.pause, GameUtilities.CENTER_X/2, GameUtilities.CENTER_Y/2,
					GameUtilities.CENTER_X, GameUtilities.CENTER_Y, 0, 0, 500, 700, false, false);
		}
		
		game.batch.end();
	}
 
	
	
	@Override
	public void resize(int width, int height) {
	}
 
	@Override
	public void show() {
		//Assets.gameMusic.play();
		//Assets.gameMusic.setLooping(true);
		Gdx.input.setInputProcessor(new GestureDetector(this));
	}
 
	@Override
	public void hide() {
	}
 
	@Override
	public void pause() {
	}
 
	@Override
	public void resume() {
	}
 
	@Override
	public void dispose() {
		Assets.dispose();
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

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		// TODO Auto-generated method stub
		
		float screenYCorrected = (float)(GameUtilities.Y_MAX) - y;
		
		//Checks the coordinates of where I tap on the screen (for testing purposes)
		//Gdx.app.log("TAP", "In Tap");
		//Gdx.app.log("X", Float.toString(x));
		//Gdx.app.log("Y", Float.toString(screenYCorrected));
		
		//If there is a touch event while the game is paused, then the game starts running again.
		if (gamePaused) {
			gamePaused = false;
			//Assets.gameMusic.play();
		}
		
		//Triggers Pause Screen if pause button is touched, or triggers main menu screen if home button is touched
		if (x < 50 && screenYCorrected < 50) {
			gamePaused = true;
			//Assets.gameMusic.pause();
		}
		else if (x > (GameUtilities.X_MAX - 50) && screenYCorrected < 50) {
			reinitialize();
			//Assets.gameMusic.stop();
			game.setScreen(game.mainMenuScreen);
		}
		return true;
	}

	@Override
	public boolean longPress(float x, float y) {
		// TODO Auto-generated method stub
		return false;
	}

	
	//Moves paddle left by 1 slot
	public void movePaddleLeft() {
		
		//Ignore the 3 circles per row for now
		//We reference paddle.index = 0 as far left coordinate and paddle.index = 3 as far right coordinate for 4
		//circles per row
		if (circlesPerRow == 3) {
			if (paddle.index != 0) {
				paddle.x = (float)(GameUtilities.ROW_COORDINATE_3[paddle.index - 1]);
				paddle.index = paddle.index - 1;
			}
		}
		else if (circlesPerRow == 4) {
			if (paddle.index != 0) {
				paddle.x = (float)(GameUtilities.ROW_COORDINATE_4[paddle.index - 1]);
				paddle.index = paddle.index - 1;
			}
		}
	}
	
	//Moves paddle right by 1 slot
	public void movePaddleRight() {
		
		//Ignore the 3 circles per row for now
		if (circlesPerRow == 3) {
			if (paddle.index != 2) {
				paddle.x = (float)(GameUtilities.ROW_COORDINATE_3[paddle.index + 1]);
				paddle.index = paddle.index + 1;
			}
		}
		else if (circlesPerRow == 4) {
			if (paddle.index != 3) {
				paddle.x = (float)(GameUtilities.ROW_COORDINATE_4[paddle.index + 1]);
				paddle.index = paddle.index + 1;
			}
		}
	}
	
	
	//Checks if screen is swiped left or right
	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		
		if (!gamePaused) {
			if(Math.abs(velocityX)>Math.abs(velocityY)){
	            if (velocityX > 0) {
	                movePaddleRight();
	            } else if (velocityX < 0) {
	            	movePaddleLeft();
	            }
			} else {
				// Ignore the input, because we don't care about up/down swipes.
			}
		}
		return true;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
			Vector2 pointer1, Vector2 pointer2) {
		// TODO Auto-generated method stub
		return false;
	}
 
}