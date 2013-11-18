package com.thebusiness.reflexgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class Assets {
	// Backgrounds
	public static Texture menu;
	public static Texture background;
	public static Texture pause;	
	public static Texture mode;
	
	// Buttons
	public static Texture pauseButton;
	public static Texture quitButton;
	
	// Objects
	public static Texture bomb;
	public static Texture blueCircle;
	public static Texture greenCircle;
	public static Texture greyCircle;
	public static Texture lightBlueCircle;
	public static Texture orangeCircle;
	public static Texture pinkCircle;
	public static Texture redCircle;
	public static Texture yellowCircle;

	// Sounds
	public static Sound TouchSound, bombSound;
	public static Music gameMusic;
	
	// Loads all textures
	public static void load () {		
		// Backgrounds
		menu = new Texture(Gdx.files.internal("Backgrounds/MainMenuScreenBG.png"));
		pause = new Texture(Gdx.files.internal("Backgrounds/PausedScreenBG.png"));
		mode = new Texture(Gdx.files.internal("Backgrounds/ModeScreenBG.png"));
		background = new Texture(Gdx.files.internal("Backgrounds/Background.png"));
		
		// Buttons
		pauseButton = new Texture(Gdx.files.internal("Buttons/PauseButton.png"));
		quitButton = new Texture(Gdx.files.internal("Buttons/QuitButton.png"));
		
		// Objects
		bomb = new Texture(Gdx.files.internal("Objects/bomb.png"));
		blueCircle = new Texture(Gdx.files.internal("Objects/blue_circle.png"));
		greenCircle = new Texture(Gdx.files.internal("Objects/green_circle.png"));
		greyCircle = new Texture(Gdx.files.internal("Objects/grey_circle.png"));
		lightBlueCircle = new Texture(Gdx.files.internal("Objects/light_blue_circle.png"));
		orangeCircle = new Texture(Gdx.files.internal("Objects/orange_circle.png"));
		pinkCircle = new Texture(Gdx.files.internal("Objects/pink_circle.png"));
		redCircle = new Texture(Gdx.files.internal("Objects/red_circle.png"));
		yellowCircle = new Texture(Gdx.files.internal("Objects/yellow_circle.png"));
		
		// Sounds
		TouchSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/CircleTouchSound.mp3"));
		bombSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/bomb.mp3"));
		gameMusic = Gdx.audio.newMusic(Gdx.files.internal("Sounds/gameMusic.mp3"));
	}
	
	
	// Free/disposes of each texture
	public static void dispose () {
		// Backgrounds
		menu.dispose();
		pause.dispose();
		mode.dispose();
		background.dispose();
		
		// Buttons
		pauseButton.dispose();
		quitButton.dispose();
		
		// Objects
		bomb.dispose();
		blueCircle.dispose();
		greenCircle.dispose();
		greyCircle.dispose();
		lightBlueCircle.dispose();
		orangeCircle.dispose();
		pinkCircle.dispose();
		redCircle.dispose();
		yellowCircle.dispose();
		
		// Sounds
		TouchSound.dispose();
		bombSound.dispose();
		gameMusic.dispose();
	}
}