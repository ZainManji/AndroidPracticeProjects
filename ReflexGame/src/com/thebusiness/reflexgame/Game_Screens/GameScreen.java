package com.thebusiness.reflexgame.Game_Screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.TimeUtils;
import com.thebusiness.reflexgame.Assets;
import com.thebusiness.reflexgame.GameUtilities;
import com.thebusiness.reflexgame.Objects.Bomb;
import com.thebusiness.reflexgame.Objects.Circle;
import com.thebusiness.reflexgame.Objects.Ring;
import com.thebusiness.reflexgame.Objects.SingleGameObject;

public class GameScreen implements Screen, InputProcessor {

	MainGame gameRef;
	
	// Used for updating based on system time
	long previousTime = TimeUtils.nanoTime();
	long currentTime;
	
	// Time constants for updating target, poppers, and time to add another ring
	int TARGET_TIME = 6;
	int POPPER_TIME = 3;
	int TIME_TO_ADD_RING = 10;
	
	int targetTime = TARGET_TIME;
	int popperTime = POPPER_TIME;
	
	int survivalTime = 0;
	boolean gamePaused = false;
	boolean bombTouched = false;
	int toTouch;
    
	public GameScreen (MainGame gameRef) {
		this.gameRef = gameRef;
		
		
	}
	
	// Assigns poppers of the target colour to each ring in the ring list
	private void setTargetPoppers () {
		Circle temp;
		SingleGameObject randObj;
		int rand;
		int ringCount = 0;
		
		for (Ring r: gameRef.rings) {
			for (int i = 0; i < r.targetColourLimit; i++) {
				rand = (int)(Math.random()*(gameRef.rings.get(ringCount).getList().size()-1));
				randObj = gameRef.rings.get(ringCount).getList().get(rand);
				temp = new Circle(
							randObj.getRadius(),
							randObj.getXCoord(),
							randObj.getYCoord(),
							randObj.getAngle(),
							gameRef.centreTarget.getColour());
				gameRef.rings.get(ringCount).getList().set(rand, temp);
			}
			ringCount++;
		}
	}
	
	// Refreshes all poppers
    private void changeAllPoppers() {
    	
    	// Refreshes ring with colours excluding target colour 
    	for (Ring r: gameRef.rings) {
    		r.refreshWithExclude(gameRef.centreTarget);
    	}
    	
    	// Set target colour circles
    	setTargetPoppers();
    	
   	}
    
    // Adds a new ring, adds a new element in the colour limits arraylist, and refreshes the poppers.
    private void addRing() {
    	gameRef.rings.add(new Ring(14, GameUtilities.RING_RAD_INC + 
			(gameRef.rings.size() + 2) * GameUtilities.RING_RAD_INC,
			gameRef.rings.get(gameRef.rings.size() - 1).targetColourLimit + 1,
			false));
		changeAllPoppers();
		targetTime = TARGET_TIME;
    }
    
    // Refreshes poppers
    private void popperRefresh() {

    	for (Ring r: gameRef.rings) {
    		r.targetColourCircles = r.targetColourLimit;
    	}
    	
    	changeAllPoppers();
    	popperTime = POPPER_TIME;
    }
    
    // Refreshes the target
    private void targetRefresh() {
    	gameRef.centreTarget.setColourToRandom();
   
    	popperRefresh();
    	
		targetTime = TARGET_TIME;	
    }
    
    // Checks for end of game
    private boolean livesDepleted () {
    	return (gameRef.livesLeft == 0);
    }
    
    // Game loop
	@Override
	public void render(float delta) {
		
		boolean targetRefreshCheck = false;
		
		// If game is paused the game is not updated
		if (!gamePaused) {
		
		// Gets time at start of this render
			currentTime = TimeUtils.nanoTime();
		
		// Checks if the difference between the current render and the previously
		// assigned time is at least 1 second, and if so, checks if targets, poppers
		// and game time need to be updated
		
			if((int)((currentTime - previousTime)/1000000000) >= 1) {
				// Resets previous time
				previousTime = currentTime;
			
				// Updates target update time and refreshes target if necessary
				targetTime--;
				popperTime--;
				survivalTime++;
			}
		
			
			// Current levelling system
			if (survivalTime == TIME_TO_ADD_RING && previousTime == currentTime) {
				addRing();	
			}
			if (survivalTime >= TIME_TO_ADD_RING) {
				for (int i = 0; i < gameRef.rings.size(); i++) {
					if (i % 2 == 0) {
						gameRef.rings.get(i).rotateRing(0.01);
					}
					else {
						gameRef.rings.get(i).rotateRing(-0.01);
					}
				}
			}
			
			// Time checks
			if (targetTime <= 0) {
				targetRefresh();
			}
			if (popperTime <= 0) {
				for (Ring r: gameRef.rings) {
					if (r.targetColourCircles > 0) {
						gameRef.livesLeft--;
						break;
					}
				}
				if (livesDepleted()) {
					endGame();
				}
				else {
					popperRefresh();
				}
			}
		
			// Checks if rings has to be refreshed (i.e. all target colour circles are gone)
			for(Ring r: gameRef.rings) {
				if (r.targetColourCircles > 0) {
					targetRefreshCheck = true;
					break;
				}
			}
			if (!targetRefreshCheck) {
				targetRefresh();
				targetRefreshCheck = false;
			}
			
			// End of game check
			if (livesDepleted()) {
				endGame();
			}
		}
		
		// Draws all textures for this screen
		renderDraw();
	}
	
	public void renderDraw () {
		gameRef.batch.begin();
		
		gameRef.batch.draw(Assets.background, 0, 0);
		gameRef.centreTarget.drawObject(gameRef.batch);
		for (Ring r: gameRef.rings) {
			r.drawRing(gameRef.batch, gameRef.currentFrame, bombTouched);
		}
		
		gameRef.batch.draw(
				Assets.pauseButton,
				0,
				0,
				200,
				100,
				0,
				0,
				200,
				100,
				false,
				false);
		gameRef.batch.draw(
				Assets.quitButton,
				GameUtilities.X_MAX - 200,
				0,
				200,
				100,
				0,
				0,
				200,
				100,
				false,
				false);
		gameRef.font.draw(
				gameRef.batch, 
				"Target Time: " + Integer.toString(targetTime), 
				0, 
				(int) (0.95 * GameUtilities.Y_MAX));
		gameRef.font.draw(
				gameRef.batch, 
				"Popper Time: " + Double.toString(popperTime), 
				0, 
				(int) (0.90 * GameUtilities.Y_MAX));
		gameRef.font.draw(
				gameRef.batch, 
				"Score:" + Integer.toString(gameRef.score), 
				(int) (0.6 * GameUtilities.X_MAX), 
				(int) (0.9 * GameUtilities.Y_MAX));
		gameRef.font.draw(
				gameRef.batch, 
				"Time:" + Integer.toString(survivalTime), 
				(int) (0.6 * GameUtilities.X_MAX), 
				(int) (0.85 * GameUtilities.Y_MAX));
		
		
		//This section of code draws 3 circles as the live count
		for (int count = 0; count < gameRef.livesLeft; count++) {
		
		gameRef.batch.draw(Assets.bomb, 
				(int) (0.80 * GameUtilities.X_MAX + (count*(GameUtilities.X_MAX/20))), 
				(int) (0.95 * GameUtilities.Y_MAX), 
				2*GameUtilities.LIFE_RAD, 
				2*GameUtilities.LIFE_RAD, 
				0, 
				0, 
				Assets.bomb.getWidth(), 
				Assets.bomb.getHeight(), 
				false, 
				false);
		}

		// Draws the pause screen
		if (gamePaused) {	
			gameRef.batch.draw(Assets.pause, 0, 0, 500, 700, 0, 0, 500, 700, false, false);
		}

		gameRef.batch.end();
	}

    // Resets values and changes to the gameover screen
    private void endGame() {
    	targetTime = TARGET_TIME;
		//popperRefreshTime = POPPER_TIME;
		survivalTime = 0;
		gameRef.livesLeft = GameUtilities.MAX_LIVES;
		Assets.gameMusic.stop();
		gameRef.setScreen(gameRef.gameOverScreen);
    }	
	
	@Override
	public void resize(int width, int height) {
		//
	}
	
	// Sets game time, draws textures, and sets input processor
	@Override
	public void show() {
		gameRef.gameTime = GameUtilities.GAME_TIME;
		gameRef.score = 0;
		renderDraw();
		Gdx.input.setInputProcessor(this);
		
		targetRefresh();
		Assets.gameMusic.play();
		Assets.gameMusic.setLooping(true);
	}
	
	@Override
	public void hide() {
		//
	}
	
	@Override
    public void pause() {
		
		
    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    // Disposal?

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
	
	// Registers/calculates touch events
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// Accounts for reversed y axis (i.e. touch events register with 0,0 being
		// at top left of screen
		int screenYCorrected = GameUtilities.Y_MAX - screenY;
		
		// Triggers quit button
		if (screenX > GameUtilities.X_MAX - 200 && screenYCorrected < 100) {
			//Assets.dispose();
			Gdx.app.exit();
		}
		
		// if while the game is paused, there is a touch event, game starts running again.
		if (gamePaused) {
			gamePaused = false;
			Assets.gameMusic.play();
		}
		
		// Triggers Pause Screen
		if (screenX > 0 && screenX < 200 && screenYCorrected < 100) {
			gamePaused = true;
			Assets.gameMusic.pause();
		}
		
		// Checks each ring and circle for a touch event in a circle that has a colour
		// matching the target's colour
		for (Ring r: gameRef.rings) {
			for (SingleGameObject obj: r.getList()) {
				
				// Checks if the distance between the touch event and the center of
				// a given circle is greater than the radius of the circle (if so, 
				// the event is not in the given circle).
				if (obj.checkRadius(screenX,screenYCorrected)) {
				
					// Checks for matching colours between the target and the given
					// circle being checked.
					obj.touchEvent(gameRef);
				}
			}
		}
		return true;
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