package com.thebusiness.reflexgame;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.thebusiness.reflexgame.Objects.Circle;
import com.thebusiness.reflexgame.Objects.Ring;



public class CircAnimation extends Game {
	
	private static final int FRAME_ROWS = 3;
	
	Animation circAnimation;
	Texture spriteSheet;
	TextureRegion[] frames;
	SpriteBatch spriteBatch;
	TextureRegion currentFrame;
	float stateTime;

	@Override
	public void create() {
		spriteSheet = new Texture(Gdx.files.internal("explosion.png"));
		TextureRegion[][] tmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth()/FRAME_ROWS, 
				spriteSheet.getHeight());
		frames = new TextureRegion[FRAME_ROWS];
		for (int i = 0; i < FRAME_ROWS; i++) {
			frames[i] = tmp [i][1];
		}
		circAnimation = new Animation(0.33f, frames);
		spriteBatch = new SpriteBatch();
		stateTime = 0f;
		
	}
	
	@Override
	public void render() {
		
			
				
		
	
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
