package com.thebusiness.quickSwipe;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;

public class GameOverScreen implements Screen,InputProcessor {
	
	QuickSwipe game;
	boolean isTouched;
	
	public GameOverScreen (QuickSwipe gameRef) {
		this.game = gameRef;
	}

	
	// Update method
	@Override
	public void render(float delta) {
		
		//Deal with highscores
		FileHandle file = Gdx.files.local("data/highscores.txt");
				
		//Write highscore to local file
		if(game.savedScore > game.highscore){
			file.writeString(Long.toString(game.savedScore), false);
			game.highscore = game.savedScore;
		}
		
		//Draw game over screen and game ending stas
		game.batch.begin();
		game.batch.draw(Assets.gameOver, 0, 0, GameUtilities.X_MAX, GameUtilities.Y_MAX, 0, 0, 800, 1200, false, false);
		
		game.font.draw(game.batch, "HIGHSCORE: " + game.highscore, GameUtilities.CENTER_X,	GameUtilities.CENTER_Y - 20);
		game.font.draw(game.batch, "This game's score: " + game.savedScore, GameUtilities.CENTER_X,	GameUtilities.CENTER_Y);
		game.font.draw(game.batch, "Lasted: " + game.savedGameTime, GameUtilities.CENTER_X,	GameUtilities.CENTER_Y + 20);		
		
		game.batch.end();
	}
	
	@Override
	public void resize(int width, int height) {
	
	}
	
	@Override
	public void show() {
		Gdx.input.setInputProcessor(this);
	}
	
	@Override
	public void hide() {
    	System.gc();
    	
    	//Not sure if this is needed right now
		game.gameTime = 0;
		game.livesLeft = GameUtilities.MAX_LIVES;
	}
	
	@Override
    public void pause() {
		
	}

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
    	System.gc();
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
		
		game.setScreen(game.mainMenuScreen);
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
