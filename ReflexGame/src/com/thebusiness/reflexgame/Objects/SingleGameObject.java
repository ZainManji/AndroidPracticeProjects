package com.thebusiness.reflexgame.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.thebusiness.reflexgame.Game_Screens.MainGame;

public abstract class SingleGameObject {
	
	// Colour enum
	enum Colour {
		BLUE, GREEN, GREY, LIGHT_BLUE, ORANGE, PINK, RED, YELLOW
	}
	
	// Default object attributes
	int radius;
	int xCoord;
	int yCoord;
	double angle;
	boolean touched;
	Texture currentPicture;
	Colour currentColour;
	
	// Get/set methods for all variables
	
	public void setRadius (int radius) {
		this.radius = radius;
	}
	
	public int getRadius () {
		return radius;
	}
	
	public void setXCoord (int xCoord) {
		this.xCoord = xCoord;
	}
	
	public int getXCoord () {
		return xCoord;
	}
	
	public void setYCoord (int yCoord) {
		this.yCoord = yCoord;
	}
	
	public int getYCoord () {
		return yCoord;
	}
	
	public void setAngle (double angle) {
		this.angle = angle;
	}
	
	public double getAngle () {
		return angle;
	}
	
	public void setTouched (boolean touched) {
		this.touched = touched;
	}
	
	public boolean isTouched () {
		return touched;
	}
	
	public Colour getColour () {
		return currentColour;
	}
	
	// NOTE: Circle's setColour method overrides this method
	public void setColour (Colour newColour) {
		this.currentColour = newColour;
	}
	
	// Used to determine if touch event should be triggered (i.e. if touch occured in object's radius)
	public boolean checkRadius (float x, float y) {
		int calcRadius = (int) Math.sqrt((Math.pow((x - this.getXCoord()), 2) + Math.pow((y - this.getYCoord()), 2)));
		return (calcRadius <= this.getRadius());
	}
	
	// Uses the input sprite batch to draw the circle
	public abstract void drawObject (SpriteBatch batch);
	
	// Triggers touch logic for object
	public abstract void touchEvent (MainGame gameRef);
}
