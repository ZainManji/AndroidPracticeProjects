package com.thebusiness.reflexgame.Game_Screens;

import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.thebusiness.reflexgame.Assets;
import com.thebusiness.reflexgame.GameUtilities;
import com.thebusiness.reflexgame.Objects.Circle;
import com.thebusiness.reflexgame.Objects.Ring;


public class MainGame extends Game {
	MainMenuScreen mainMenuScreen; 	
	LoadingScreen loadingScreen;
	GameScreen gameScreen;
	GameOverScreen gameOverScreen;
	
	SpriteBatch batch;
	BitmapFont font;
	OrthographicCamera camera;
	
	
	/////////////////// Animation spritesheet parameters
	private static final int FRAME_ROWS = 1;
	private static final int FRAME_COLS = 5;
	
	Animation bombAnimation;
	Texture spriteSheet;
	TextureRegion[] frames;
	TextureRegion currentFrame;
	float stateTime;
	
	Sound sound;
	
	
	//ArrayList<Circle> allCircles = new ArrayList<Circle> ();
	public ArrayList<Ring> rings;
	public Circle centreTarget;
	public int gameTime = GameUtilities.GAME_TIME;
	public int livesLeft = GameUtilities.MAX_LIVES;
	public int score = 0;
	int radialIncrement = 75;
	// An arraylist for the limit of matching poppers per ring
	
	
	// Does information have to be passed from one screen to another? If not, how is info stored?
	// Is a camera required for ever single screen?
	@Override
	public void create() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 1200);
		camera.update();
		
		
		Texture.setEnforcePotImages(false);
		batch = new SpriteBatch();
		
		///////////////////////////////////////// Random Animation Stuff
		spriteSheet = new Texture(Gdx.files.internal("Objects/burst.png"));
		TextureRegion[][] tmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth()/FRAME_COLS, 
				spriteSheet.getHeight()/FRAME_ROWS);
		frames = new TextureRegion[FRAME_ROWS];
		int index = 0;
		for (int i = 0; i < FRAME_ROWS; i++) {
			for (int j = 0; j < FRAME_COLS; j++) {
			frames[index] = tmp [i][j];
			}
			index++;
		}
		bombAnimation = new Animation(0.5f, frames);
		stateTime = 0f;
		
		font = new BitmapFont();
		//Gdx.app.log("Check:" , Integer.toString(GameUtilities.Y_MAX));
		//Gdx.app.log("Check2: ", Float.toString((float)(GameUtilities.Y_MAX/500.0)));
		font.setScale((float)(GameUtilities.Y_MAX/500.0));
		
		rings = new ArrayList<Ring> ();
		
		
		
		
		Assets.load();
		Gdx.app.log("AssetTag", "ASSETS LOADED");
		
		mainMenuScreen = new MainMenuScreen(this);
		loadingScreen = new LoadingScreen(this);
		gameScreen = new GameScreen(this);
		gameOverScreen = new GameOverScreen(this);
		setScreen(mainMenuScreen);
		
		/* GIVEN ----------------
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		camera = new OrthographicCamera(1, h/w);
		batch = new SpriteBatch();
		
		//texture = new Texture(Gdx.files.internal("data/libgdx.png"));
		//texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		TextureRegion region = new TextureRegion(texture, 0, 0, 512, 275);
		
		sprite = new Sprite(region);
		sprite.setSize(0.9f, 0.9f * sprite.getHeight() / sprite.getWidth());
		sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
		sprite.setPosition(-sprite.getWidth()/2, -sprite.getHeight()/2);
		//------------------------*/
		
	}

	@Override
	public void dispose() {
		Assets.dispose();
		Gdx.app.log("AssetTag","ASSETS CLEARED");
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