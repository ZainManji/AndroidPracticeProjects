package com.thebusiness.quickSwipe;
 
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL10;
import com.thebusiness.quickSwipe.GameUtilities;
 
public class MainMenuScreen implements Screen, InputProcessor {
 
	QuickSwipe game;
	boolean showHighscores;
 
	public MainMenuScreen(QuickSwipe gameRef) {
		this.game = gameRef;
	}
 
	@Override
	public void render(float delta) {

		game.batch.begin();
		
		game.batch.draw(Assets.mainMenuScreenBG, 0, 0, GameUtilities.X_MAX, GameUtilities.Y_MAX, 0, 0,
				Assets.mainMenuScreenBG.getWidth(), Assets.mainMenuScreenBG.getHeight(), false, false);
		
		//If we need to show highscores on the screen, then display it
		if (showHighscores) {
			
			//This is needed to clear screen
			Gdx.gl.glClearColor(0.8f, 0.8f, 0.8f, 1);
			Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			
			FileHandle file = Gdx.files.local("data/highscores.txt");
			if(file.exists()){
				game.highscore = Long.valueOf(file.readString());
			}
			
			//Draw circle with highscore on it
			game.batch.draw(Assets.blueCircle, (GameUtilities.X_MAX - 343)/2, (GameUtilities.Y_MAX - 343)/2,
					343, 343, 0, 0, 343, 343, false, false);
			game.font.draw(game.batch, "HIGHSCORE: " + game.highscore, GameUtilities.CENTER_X-60,	GameUtilities.CENTER_Y);
		}
	
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
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		
		int screenYCorrected = GameUtilities.Y_MAX - screenY;
		
		//Used to check the coordinates of where I touch on the screen (Keep for testing purposes)
		Gdx.app.log("Touch Up x", Integer.toString(screenX));
		Gdx.app.log("Touch Up y", Integer.toString(screenYCorrected));
		
		//If we touch anywhere other than the highscores button on the screen, then don't show the highscores screen
		if (showHighscores) {
			showHighscores = false;
		}
		
		//Check if we touched the play button, and if so go to the game, or check if we touched the highscores button,
		//and if so show highscores screen
		if ((screenX > 110 && screenX < 230) && (screenYCorrected > 250 && screenYCorrected < 375)) {
			game.setScreen(game.gameScreen);
		}
		else if ((screenX > 460 && screenX < 580) && (screenYCorrected > 250 && screenYCorrected < 3750)) {
			showHighscores = true;
		}
		
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
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
}
 
