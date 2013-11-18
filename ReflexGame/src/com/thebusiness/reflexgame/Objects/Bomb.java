package com.thebusiness.reflexgame.Objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.thebusiness.reflexgame.Assets;
import com.thebusiness.reflexgame.Game_Screens.MainGame;

public class Bomb extends SingleGameObject {
	
	Bomb (int radius, int xCoord, int yCoord, double angle) {
		this.radius = radius;
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.angle = angle;
		this.touched = false;
		
		this.currentPicture = Assets.bomb;
	}
	
	// Uses the input sprite batch to draw the circle
	public void drawObject (SpriteBatch batch) {
    	batch.draw(
			currentPicture,
			xCoord - radius,
			yCoord - radius,
			2*radius,
			2*radius,
			0,
			0,
			currentPicture.getWidth(),
			currentPicture.getHeight(),
			false,
			false);
	}
	
	public void touchEvent (MainGame gameRef) {
		Assets.bombSound.play();
		gameRef.livesLeft = 0;
	}
}