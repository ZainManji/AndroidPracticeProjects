package com.thebusiness.reflexgame.Game_Screens;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.thebusiness.reflexgame.Assets;
import com.thebusiness.reflexgame.GameUtilities;

public class MainMenuScreen implements Screen, InputProcessor {

	MainGame gameRef;
	boolean isTouched;
    
	public MainMenuScreen (MainGame gameRef) {
		this.gameRef = gameRef;
	}
	
	@Override
	public void render(float delta) {
		gameRef.batch.begin();
		gameRef.batch.draw(
				Assets.menu,
				0,
				0,
				GameUtilities.X_MAX,
				GameUtilities.Y_MAX,
				0,
				0,
				Assets.menu.getWidth(),
				Assets.menu.getHeight(),
				false,
				false);
		gameRef.batch.end();
		
		if (isTouched) {
			gameRef.setScreen(gameRef.loadingScreen);
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
	public void hide() {}
	
	@Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose() {}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
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
}