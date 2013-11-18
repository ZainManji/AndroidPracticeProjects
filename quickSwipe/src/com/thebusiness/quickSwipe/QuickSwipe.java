package com.thebusiness.quickSwipe;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class QuickSwipe extends Game {

	//Screens
	MainMenuScreen mainMenuScreen; 	
	//LoadingScreen loadingScreen;
	GameScreen gameScreen;
	GameOverScreen gameOverScreen;

	//Game variables
    SpriteBatch batch;
    BitmapFont font;
    OrthographicCamera camera;
    Texture spriteSheet;
    Sound sound;
    int gameTime = GameUtilities.GAME_TIME;
    int livesLeft = GameUtilities.MAX_LIVES;
    int circlesGathered = 0;
    long savedScore = 0l;
    long score = 0l;
    int savedGameTime = 0;
    enum Colour {blue, green, red, yellow};
    enum Powerup {doublePoints, triplePoints, immunity, none};
    long highscore = 0l;
	
	@Override
	public void create() {	
		
		//Set and update camera
		camera = new OrthographicCamera();
    	camera.setToOrtho(false, 800, 1200);
    	camera.update();
            
        Texture.setEnforcePotImages(false);
        batch = new SpriteBatch();
        
        //Load assets
        Assets.load();
        
        //Initialize screens
        mainMenuScreen = new MainMenuScreen(this);
		//loadingScreen = new LoadingScreen(this);
		gameScreen = new GameScreen(this);
		gameOverScreen = new GameOverScreen(this);

        //Use LibGDX's default Arial font. (Will need to find a nice font)
        font = new BitmapFont();
        
        //Enter main menu screen
        this.setScreen(mainMenuScreen);
	}

	
	@Override
	public void dispose() {
		Assets.dispose();
	}

	@Override
	public void render() {		
		super.render();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
