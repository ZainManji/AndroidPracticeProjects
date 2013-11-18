package com.thebusiness.skyfall;
 
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.InputProcessor;
 
public class MainMenuScreen implements Screen, InputProcessor {
 
	Skyfall game;
 
	//OrthographicCamera camera;
	boolean isTouched;
 
	public MainMenuScreen(Skyfall gameRef) {
		this.game = gameRef;
 
		//camera = new OrthographicCamera();
		//camera.setToOrtho(false, 800, 1200);
 
	}
 
	@Override
	public void render(float delta) {
		
		//camera.update();
		//game.batch.setProjectionMatrix(camera.combined);
 
		game.batch.begin();
		//Gdx.app.log("MY TAG", "Start drawing mainmenuscreenBG");
		game.batch.draw(
				Assets.mainMenuScreenBG,
				0,
				0,
				GameUtilities.X_MAX,
				GameUtilities.Y_MAX,
				0,
				0,
				Assets.mainMenuScreenBG.getWidth(),
				Assets.mainMenuScreenBG.getHeight(),
				false,
				false);
		//game.font.draw(game.batch, "Welcome to Skyfall", 100, 150);
		//game.font.draw(game.batch, "Tap anywhere to begin", 100, 100);
		game.batch.end();
		//Gdx.app.log("MY TAG", "End drawing mainmenuscreenBG");
 
		if (isTouched) {
			Gdx.app.log("MY TAG", "entering gamescreen");
			game.setScreen(game.gameScreen); // game.loadingScreen
		}
		
		
	}
 
	@Override
	public void resize(int width, int height) {
	}
 
	@Override
	public void show() {
			isTouched = false;
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
		isTouched = true;
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
 
