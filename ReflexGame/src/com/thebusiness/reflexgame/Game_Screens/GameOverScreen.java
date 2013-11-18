package com.thebusiness.reflexgame.Game_Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.thebusiness.reflexgame.Assets;
import com.thebusiness.reflexgame.GameUtilities;

public class GameOverScreen implements Screen,InputProcessor {
	
	MainGame gameRef;
	boolean isTouched;
	
	public GameOverScreen (MainGame gameRef) {
		this.gameRef = gameRef;
	}
	
	// Update/render method
	@Override
	public void render(float delta) {
		gameRef.batch.begin();
		// Draws screen background
		gameRef.batch.draw(Assets.background, 0, 0);
		
		// Draws screen text (near middle of screen)
		gameRef.font.draw(
				gameRef.batch, 
				"GAME OVER", 
				(int) (0.95* GameUtilities.CENTER_X),
				(int) (1.05 * GameUtilities.CENTER_Y));
		
		// Draws final score (near middle but below text)
		gameRef.font.draw(
				gameRef.batch,
				Integer.toString(gameRef.score), 
				(int) (0.98 * GameUtilities.CENTER_X),
				(int) (0.95 * GameUtilities.CENTER_Y));
		gameRef.batch.end();
		
		// Returns player to main menu if screen is touched
		if (isTouched) {
			gameRef.setScreen(gameRef.mainMenuScreen);
		}
	}
	
	@Override
	public void resize(int width, int height) {
		//
	}
	
	@Override
	public void show() {
		Gdx.input.setInputProcessor(this);
	}
	
	// Fix this...
	@Override
	public void hide() {
		// Fix to clear each ring of each circle as well
		gameRef.rings.clear();
    	System.gc();
		gameRef.gameTime = 600;
		gameRef.score = 0;
	}
	
	@Override
    public void pause() {
		//
	}

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
    	gameRef.rings.clear();
    	System.gc();
    	// Dispose of picture assets? Is static right option? Use assetManager?
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

	// Triggers "on touch" event
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
