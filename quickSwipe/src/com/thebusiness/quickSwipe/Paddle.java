package com.thebusiness.quickSwipe;

import com.badlogic.gdx.math.Rectangle;

public class Paddle extends Rectangle {
	
	public int index;
	public QuickSwipe.Colour colour;
	public QuickSwipe.Powerup powerup;
	
	public Paddle() {
		this.index = 1; //2nd left coordinate
		this.x = GameUtilities.CENTER_X; //center the paddle horizontally (for now)
		this.y = 120; //bottom left corner of the paddle is 20px above the bottom of the screen edge
		this.width = 70;
		this.height = 40;
		this.powerup = QuickSwipe.Powerup.none;
	}
	
}
