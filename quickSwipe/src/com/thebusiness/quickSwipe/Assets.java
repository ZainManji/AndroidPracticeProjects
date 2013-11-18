package com.thebusiness.quickSwipe;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import com.badlogic.gdx.math.Rectangle;


public class Assets {
	
		//circles
		static Texture blueCircle;
		static Texture greenCircle;
		static Texture redCircle;
		static Texture yellowCircle;
		
		//screens
		static Texture mainMenuScreenBG;
		static Texture gameOver;
		static Texture cartoonSky;
		static Texture pause;

		//power ups
		static Texture star;
		static Texture capsule;
		static Texture mushroom;
		
		//buttons
		static Texture pauseButton;
		static Texture quitButton;
		
		//animation sheets
		static Texture redBirdAnimationSheet;
		static Texture greenBirdAnimationSheet;
		static Texture yellowBirdAnimationSheet;
		static Texture blueBirdAnimationSheet;
		static Texture immunityBirdAnimationSheet;
		static Texture transitionAnimationSheet;
		static Texture twinkleAnimationSheet;
		
		//sounds and music
		static Sound catchSound;
		static Sound dingSound;
		//static Music gameMusic;

		
		//load assets
		public static void load() {
			
			//load screens
			mainMenuScreenBG = new Texture(Gdx.files.internal("HomeScreen.png"));
			gameOver = new Texture(Gdx.files.internal("gameOver.png"));
			pause = new Texture(Gdx.files.internal("PausedScreenBG.png"));
			cartoonSky = new Texture(Gdx.files.internal("CartoonSky.jpg"));
			
			//load circles
			blueCircle = new Texture(Gdx.files.internal("blue_circle.png"));
			greenCircle = new Texture(Gdx.files.internal("green_circle.png"));
			redCircle = new Texture(Gdx.files.internal("red_circle.png"));
			yellowCircle = new Texture(Gdx.files.internal("yellow_circle.png"));
			
			//load power ups
			star = new Texture(Gdx.files.internal("immunity.gif"));
			capsule = new Texture(Gdx.files.internal("doublePoints.gif"));
			mushroom = new Texture(Gdx.files.internal("mushroom.gif"));
			
			//load buttons
			quitButton = new Texture(Gdx.files.internal("QuitButton.gif"));
			pauseButton = new Texture(Gdx.files.internal("PauseButton.gif"));
			
			//load sounds and music
			catchSound = Gdx.audio.newSound(Gdx.files.internal("Blop.mp3"));
			dingSound = Gdx.audio.newSound(Gdx.files.internal("Ding.wav"));
			//gameMusic = Gdx.audio.newMusic(Gdx.files.internal("The Weeknd - The Party & The After Party.mp3"));
			
			//load animation sheets
			redBirdAnimationSheet = new Texture(Gdx.files.internal("redBirdAnimation.gif"));
			greenBirdAnimationSheet = new Texture(Gdx.files.internal("greenBirdAnimation.gif"));
			yellowBirdAnimationSheet = new Texture(Gdx.files.internal("yellowBirdAnimation.gif"));
			blueBirdAnimationSheet = new Texture(Gdx.files.internal("blueBirdAnimation.gif"));
			immunityBirdAnimationSheet = new Texture(Gdx.files.internal("immunityBirdAnimation.gif"));	
			transitionAnimationSheet = new Texture(Gdx.files.internal("transitionAnimation.png"));
			twinkleAnimationSheet = new Texture(Gdx.files.internal("twinkleAnimation.gif"));
		}
		
		public static void dispose() {
			
			//dispose of screens
			mainMenuScreenBG.dispose();
			gameOver.dispose(); 
			pause.dispose();
			cartoonSky.dispose();
			
			//dispose of circles
			blueCircle.dispose(); 
			greenCircle.dispose();
			redCircle.dispose();
			yellowCircle.dispose();
			
			//dispose of power ups
			star.dispose(); 
			capsule.dispose();
			mushroom.dispose(); 
			
			//dispose of buttons
			quitButton.dispose(); 
			pauseButton.dispose();
			
			//dispose of sounds and music
			catchSound.dispose(); 
			dingSound.dispose();
			//gameMusic.dispose();
			
			//dispose of animation sheets
			redBirdAnimationSheet.dispose();
			greenBirdAnimationSheet.dispose(); 
			yellowBirdAnimationSheet.dispose();
			blueBirdAnimationSheet.dispose(); 
			immunityBirdAnimationSheet.dispose();
			twinkleAnimationSheet.dispose();
		}
}