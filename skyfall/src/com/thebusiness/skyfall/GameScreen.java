package com.thebusiness.skyfall;
 
import java.util.Iterator;
import java.util.ArrayList;
 
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.InputProcessor;
//import com.badlogic.gdx.audio.Music;
//import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;
 
public class GameScreen implements Screen, InputProcessor {
	
	private static final float MAX_HORIZONTAL_SPEED = 6; //Moves at most 60px per second
	
	Skyfall game;
	int dropSpeed;
	int respawnTime;
	int pastGameTime;
	int livesLeft;
	int randomNum;
	Texture blueCircle;
	Texture paddleAbsorber;
	OrthographicCamera camera;
	Rectangle paddle;
	ArrayList<Circle> circles;
	long previousTime = TimeUtils.nanoTime();
	long currentTime;
	long lastDropTime;
	SpriteBatch batch;
	//Sound dropSound;
	//Music gameMusic;
	
	
	//This gets called once (Constructor)
	public GameScreen(Skyfall gameRef) {
		Gdx.app.log("MY TAG", "in gamescreen constructor");
		
		this.game = gameRef;
 
		//These variables will adjust as gameTime passes
		dropSpeed = 200;
		pastGameTime = 0;
		respawnTime = 1000000000;
 
		// create a Rectangle to represent the paddle
		paddle = new Rectangle();
		paddle.x = GameUtilities.CENTER_X - 70; //center the paddle horizontally
		paddle.y = 50; //bottom left corner of the paddle is 20px above the bottom of the screen edge
		paddle.width = 140;
		paddle.height = 160;
 
		// create the circles array and spawn the first circle
		circles = new ArrayList<Circle>();
		spawnCircle();
	}
 
	private void spawnCircle() {
		Circle fallingCircle = new Circle();
		
		fallingCircle.x = MathUtils.random(0, 480-50); //480 is width of screen, 50 is width of circle
		fallingCircle.y = 1200; //top of screen
		fallingCircle.width = 50;
		fallingCircle.height = 50;
		lastDropTime = TimeUtils.nanoTime();
		
		//Change the probability (i.e. 5) as gameTime increases, and the more power ups we have
		randomNum = MathUtils.random(0, 7); 
		
		if ((randomNum < 1) && (game.circlesGathered > 10)) { //introduce bombs after collecting 10+ circles
			fallingCircle.colour = 1; // 1 represents a bomb
		}
		else if ((randomNum < 2) && (game.circlesGathered > 25)) {
			//randomly generate a power
			//fallingCircle.powerUp.type = power.
			
		}
		else {
			fallingCircle.colour = 0; // 0 represents normal circle
		}
		
		circles.add(fallingCircle);
	}
 
	
	
	//Update method
	@Override
	public void render(float delta) {
		
		// clear the screen with a dark blue colour. The arguments to glClearColor are the red, green
		// blue and alpha component in the range [0,1] of the colour to be used to clear the screen.
		//Gdx.app.log("MY TAG", "Start Clear Screen Colour");
		Gdx.gl.glClearColor(0, 0, 0.8f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		//Gdx.app.log("MY TAG", "End Clearn Screen COlour");
		
		//Adjust game settings based on how much time has passed in the game
		if (game.gameTime - pastGameTime > 15) {
			pastGameTime = game.gameTime;
			respawnTime = (int)(respawnTime/1.03);
			dropSpeed = (int)(1.03*dropSpeed);
		}
		
		currentTime = TimeUtils.nanoTime();
		
		game.batch.begin();
		//game.batch.draw(Assets.sky,0, 0, GameUtilities.X_MAX, GameUtilities.Y_MAX, 
		//		0, 0, 900, 1300, false, false);
		game.font.draw(game.batch, "Circles Collected: " + game.circlesGathered, 
				GameUtilities.CENTER_X, 700);
		game.font.draw(game.batch, "Lives Left: " + game.livesLeft, GameUtilities.CENTER_X, 720);
		game.font.draw(game.batch, "Game Time: " + Integer.toString(game.gameTime), 
				GameUtilities.CENTER_X, 680);
		game.batch.draw(Assets.mamaKangaroo, paddle.x, paddle.y, 
				140, 100, 0, 0, 140, 160, false, false);
		//Gdx.app.log("MY TAG", "Drawed paddleAbsorber");
		for (Circle fallingCircle: circles) { //this array will be type Circle
			
			//Check the colour of the circle and if it's a power up or not
			if (fallingCircle.colour == 0) { //draw it as a normal circle
				game.batch.draw(Assets.joey, fallingCircle.x, fallingCircle.y, 50,
						50,
						0,
						0,
						250,
						250,
						false,
						false);
			}
			else if (fallingCircle.colour == 1) { //draw it as a bomb
				game.batch.draw(Assets.croc, fallingCircle.x, fallingCircle.y, 50,
						66,
						0,
						0,
						359,
						473,
						false,
						false);
			}
		}
		game.batch.end();
		
		//process user input (them tilting the screen). Only consider horizontal movement of phone
		float accelX = Gdx.input.getAccelerometerX(); //returns value between [-10,10]
		accelX = accelX*MAX_HORIZONTAL_SPEED*(-1);
		paddle.x = paddle.x + accelX;
		
		//Check if the paddle is inside the screen and make sure it stays inside the screen
		if (paddle.x < 0) {
			paddle.x = 0;
		} else if (paddle.x > (GameUtilities.X_MAX - 140)) { //X_MAX is screen width size and 140 is width size of paddle
			paddle.x = GameUtilities.X_MAX - 140;
		}
		
		// check if we need to create a new drop circle
	    if(TimeUtils.nanoTime() - lastDropTime > respawnTime) {
	    	spawnCircle();
	    }
	    
	    // move the falling circles, remove any that are beneath the bottom edge of
	    // the screen or that hit the paddle. In the later case we play back
	    // a sound effect as well.
	    Iterator<Circle> iter = circles.iterator();
	    while(iter.hasNext()) {
	    	Circle fallingCircle = iter.next();
	        fallingCircle.y -= dropSpeed * Gdx.graphics.getDeltaTime(); //Change dropSpeed respective to circ colour
	        
	        if((fallingCircle.y + 50 < 0) && (fallingCircle.colour != 1)) {
	        	game.livesLeft--;
	        	iter.remove();
	        }
	        
	        if(fallingCircle.overlaps(paddle)) { //Will have to imitate the overlaps method for circle
	        	
	        	//Check also if it is a power up and act accordingly
	        	
	        	if (fallingCircle.colour == 1) {
	        		//play bad absorb sound
	        		game.livesLeft--;
	        	}
	        	else if (fallingCircle.colour == 0) {
	        		//play good absorb sound
	        		game.circlesGathered++; //Will change to a score, blue circles = +1, green circle = +2, etc.
	        	}
	        	
	        	//absorbSound.play();
	            iter.remove();
	        }
	    }
	    
	    if((int)((currentTime - previousTime)/1000000000) >= 1) {
	    	// Resets previous time
	    	previousTime = currentTime;
	    	
	    	//Update game time
	    	game.gameTime++;
	    	pastGameTime = 0;
	    }

	    
	    if (game.livesLeft <= 0) {
	    	Gdx.app.log("MY TAG", "No Lives left");
			game.livesLeft = GameUtilities.MAX_LIVES;
			game.savedScore = game.circlesGathered;
			game.circlesGathered = 0;
			game.savedGameTime = game.gameTime;
			game.gameTime = 0;
			pastGameTime = 0;
			dropSpeed = 200;
			respawnTime = 1000000000;
			//Assets.gameMusic.stop();
			game.setScreen(game.gameOverScreen);
			circles = new ArrayList<Circle>();
			//Assets.gameMusic.stop();
		}
	}
 
	@Override
	public void resize(int width, int height) {
	}
 
	@Override
	public void show() {
		// start the playback of the background music
		// when the screen is shown
		//Assets.gameMusic.play();
		//Assets.gameMusic.setLooping(true);
		Gdx.input.setInputProcessor(this);
		//Gdx.app.log("MY TAG", "Show entered");
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
		//gameMusic.dispose();
		//absorbSound.dispose();
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