package com.thebusiness.reflexgame.Game_Screens;


import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.TimeUtils;
import com.thebusiness.reflexgame.Assets;
import com.thebusiness.reflexgame.GameUtilities;
import com.thebusiness.reflexgame.Objects.Circle;
import com.thebusiness.reflexgame.Objects.Ring;

public class LoadingScreen implements Screen, InputProcessor {
	
    MainGame gameRef;
    boolean isTouched;
    boolean bombTouched = false;

    // Individual counter for each ring to update the colours sequentially
	ArrayList<Integer> colourChanger = new ArrayList<Integer>();
	
	// Used to update time by second
	long previousTime = TimeUtils.nanoTime();
	long currentTime;
	
	public LoadingScreen (MainGame gameRef) {
		this.gameRef = gameRef;
	}
	
	// Update method
	@Override
	public void render(float delta) {
		renderDraw();
		
		// Sets the screen to the game screen if the screen is touched
		if (isTouched) {
			gameRef.setScreen(gameRef.gameScreen);
		}
		else {
	    	// Change later to control initialization of circles
	    	// Fix enumerated types later 
	    	
			// Sets variable to current render's system time
	    	currentTime = TimeUtils.nanoTime();
	    	
	    	// Increases each ring's sequential counter every 1/20 of a second
	    	// and updates one circle's colour in each ring
	    	if ((currentTime - previousTime)/1000000000.0 >= 0.05) {
	    		// Resets previous time
	    		previousTime = currentTime;
	    		
	    		// Change target's colour
	    		gameRef.centreTarget.setColourToRandom();
	    		
	    		// Iterates through each ring to update a circle in it
	    		for (int i = 0; i < gameRef.rings.size(); i++) {
	    			
	    			// 	Avoids out of range exception by resetting if max index is
	    			// exceeded for a given ring
	    			if (colourChanger.get(i) >= gameRef.rings.get(i).getList().size()) {
	    				colourChanger.set(i, 0);
	    			}
	    			// Increment a rings sequential counter and changes the circle's
	    			// colour for a given ring
	    			((Circle) gameRef.rings.get(i).getList().get(colourChanger.get(i))).setColourToRandom();
	    			colourChanger.set(i, colourChanger.get(i) + 1);
	    		}
	    	}
		}
	}
	
	public void renderDraw () {
		gameRef.batch.begin();
		gameRef.batch.draw(
				Assets.background,
				0,
				0,
				GameUtilities.X_MAX,
				GameUtilities.Y_MAX,
				0,
				0,
				Assets.background.getWidth(),
				Assets.background.getHeight(),
				false,
				false);
		gameRef.centreTarget.drawObject(gameRef.batch);
    	for (Ring r: gameRef.rings) {
    		r.drawRing(gameRef.batch, gameRef.currentFrame, bombTouched);
    	}
    	gameRef.font.draw(gameRef.batch, "Touch screen to begin", GameUtilities.CENTER_X, (int) (0.95 * GameUtilities.Y_MAX));
    	gameRef.batch.end();
	}
	
	@Override
	public void resize(int width, int height) {
		//
	}
	
	@Override
	public void show() {
		Gdx.input.setInputProcessor(this);
		isTouched = false;
		gameRef.centreTarget = new Circle(
    			GameUtilities.CIRC_RAD, 
    			GameUtilities.CENTER_X, 
    			GameUtilities.CENTER_Y,
    			0
		);
		
		// Adds rings to the game
		gameRef.rings.add(new Ring(
				8,
				GameUtilities.RING_RAD_INC + 
					(gameRef.rings.size() + 1) * GameUtilities.RING_RAD_INC,
				1,
				true));
		/*gameRef.rings.add(new Ring(14,GameUtilities.RING_RAD_INC + 
				(gameRef.rings.size() + 2) * GameUtilities.RING_RAD_INC));*/
		
		// Creates a sequential counter for each ring
		for (int i = 0; i < gameRef.rings.size(); i++) {
			//Gdx.app.log("NewTag", Integer.toString(i));
			colourChanger.add(0);
		}
	}
	
	@Override
	public void hide() {
		//
	}
	
	@Override
    public void pause() {
		//
	}

    @Override
    public void resume() {
    	//
    }

    @Override
    public void dispose() {

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
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		isTouched = true;
		return true;
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