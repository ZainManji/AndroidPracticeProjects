package com.thebusiness.skyfall;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import com.badlogic.gdx.math.Rectangle;


public class Assets {
		//static Texture blueCircle;
		static Texture paddleAbsorber;
		static Texture mainMenuScreenBG;
		//static Texture greenCircle;
		//static Texture sky;
		static Texture croc;
		static Texture gameOver;
		static Texture mamaKangaroo;
		static Texture joey;
		
		static Sound TouchSound;
		
		//static Music gameMusic;

		
		public static void load() {
			
			Gdx.app.log("MY TAG", "in the load method");
			
			// load the images for the droplet and the bucket, 64x64 pixels each
			//blueCircle = new Texture(Gdx.files.internal("blueCircle.png"));
			paddleAbsorber = new Texture(Gdx.files.internal("paddle.png"));
			mainMenuScreenBG = new Texture(Gdx.files.internal("HomeScreen.png"));
			//greenCircle = new Texture(Gdx.files.internal("greenCircle.png"));
			//sky = new Texture(Gdx.files.internal("SkyBG.png"));
			croc = new Texture(Gdx.files.internal("crocodile.png"));
			gameOver = new Texture(Gdx.files.internal("gameOver.png"));
			mamaKangaroo = new Texture(Gdx.files.internal("MamaKangaroo.png"));
			joey = new Texture(Gdx.files.internal("joey.png"));
			
			//gameMusic = Gdx.audio.newMusic(Gdx.files.internal("The Weeknd - The Party & The After Party.mp3"));
		}
		
		public static void dispose() {
			//blueCircle.dispose();
			paddleAbsorber.dispose();
			//greenCircle.dispose();
			mainMenuScreenBG.dispose();
			//sky.dispose();
			gameOver.dispose();
			croc.dispose();
			mamaKangaroo.dispose();
			joey.dispose();
		}
}