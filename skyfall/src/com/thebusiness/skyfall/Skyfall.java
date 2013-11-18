package com.thebusiness.skyfall;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class Skyfall extends Game {
	
		MainMenuScreen mainMenuScreen; 	
		//LoadingScreen loadingScreen;
		GameScreen gameScreen;
		GameOverScreen gameOverScreen;

        SpriteBatch batch;
        BitmapFont font;
        OrthographicCamera camera;
        Texture spriteSheet;
        Sound sound;
        int gameTime = GameUtilities.GAME_TIME;
        int livesLeft = GameUtilities.MAX_LIVES;
        int circlesGathered = 0;
        int savedScore = 0;
        int savedGameTime = 0;


        public void create() {
        	
        	
        	
        	camera = new OrthographicCamera();
        	camera.setToOrtho(false, 800, 1200);
        	camera.update();
                
            Texture.setEnforcePotImages(false);
            batch = new SpriteBatch();
            
            Gdx.app.log("MY TAG", "loading assets");
            
            Assets.load();
            Gdx.app.log("MY TAG", "finished loading assets");
            
            mainMenuScreen = new MainMenuScreen(this);
    		//loadingScreen = new LoadingScreen(this);
    		gameScreen = new GameScreen(this);
    		gameOverScreen = new GameOverScreen(this);

            
            //Use LibGDX's default Arial font.
            font = new BitmapFont();
            
            Gdx.app.log("MY TAG", "entering main menu screen");
            this.setScreen(mainMenuScreen);
            
            
        }

        @Override
        public void render() {
                super.render(); 
        }
        
        @Override
        public void dispose() {
                Assets.dispose();
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