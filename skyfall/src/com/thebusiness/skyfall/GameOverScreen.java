package com.thebusiness.skyfall;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;

public class GameOverScreen implements Screen,InputProcessor {
	
	Skyfall game;
	boolean isTouched;
	
	public GameOverScreen (Skyfall gameRef) {
		this.game = gameRef;
	}

	
	// Update/render method
	@Override
	public void render(float delta) {
		game.batch.begin();
		// Draws screen background
		//game.batch.draw(Assets.background, 0, 0);
		
		// Draws screen text (near middle of screen)
		game.font.draw(
				game.batch, 
				"GAME OVER", 
				(int) (0.95* GameUtilities.CENTER_X),
				(int) (1.05 * GameUtilities.CENTER_Y));
		
		game.batch.draw(Assets.gameOver, 0, 0, GameUtilities.X_MAX,
				GameUtilities.Y_MAX,
				0,
				0,
				800,
				1200,
				false,
				false);
		
		game.font.draw(game.batch, "Score: " + game.savedScore, GameUtilities.CENTER_X,
				GameUtilities.CENTER_Y);
		game.font.draw(game.batch, "Lasted: " + game.savedGameTime, GameUtilities.CENTER_X,
				GameUtilities.CENTER_Y + 20);
		
		// Draws final score (near middle but below text)
		//game.font.draw(
		//		game.batch,
				//Integer.toString(game.score), 
		//		(int) (0.98 * GameUtilities.CENTER_X),
		//		(int) (0.95 * GameUtilities.CENTER_Y));
		game.batch.end();
		
		// Returns player to main menu if screen is touched
		Gdx.app.log("MY TAG", "Going to main menu");
		if (isTouched) {
			game.setScreen(game.mainMenuScreen);
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
		//gameRef.rings.clear();
    	System.gc();
		game.gameTime = 0;
		game.livesLeft = GameUtilities.MAX_LIVES;
		
		//gameRef.score = 0;
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
    	//gameRef.rings.clear();
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
