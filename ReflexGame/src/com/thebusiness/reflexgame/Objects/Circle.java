package com.thebusiness.reflexgame.Objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.thebusiness.reflexgame.Assets;
import com.thebusiness.reflexgame.Game_Screens.MainGame;
import com.thebusiness.reflexgame.Objects.SingleGameObject.Colour;

public class Circle extends SingleGameObject{

	// Constructor without colour
	public Circle (int radius, int xCoord, int yCoord, double angle) {
		this.radius = radius;
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.angle = angle;
		this.touched = false;
		setColourToRandom();
	}
	
	// Constructor with colour
	public Circle (int radius, int xCoord, int yCoord, double angle, Colour currentColour) {
		this.radius = radius;
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.angle = angle;
		this.touched = false;
		setColour(currentColour);
	}
	
	// OVERRIDEN FUNCTION: Sets colour for circle and assigns correct picture
	public void setColour (Colour newColour) {
		this.currentColour = newColour;
		assignPicture();
	}
	
	
	// Sets circle colour to random colour
	public void setColourToRandom () {
		int randColourNum = (int)(Math.random()*7);
		Colour newColour;
		
		newColour = Circle.Colour.values()[randColourNum];
		
		if (newColour == this.currentColour) {
    		newColour = Circle.Colour.values()[(randColourNum + 1) % 8];	
   		}
		
		this.currentColour = newColour;
		assignPicture();
	}
	
	// Assigns the correct texture/image for circle based on the circle's colour
	void assignPicture () {
		switch (this.currentColour) {
		case BLUE: 
			this.currentPicture = Assets.blueCircle;
			break;			
		case GREEN: 
			this.currentPicture = Assets.greenCircle;
			break;
		case GREY: 
			this.currentPicture = Assets.greyCircle;
			break;
		case LIGHT_BLUE: 
			this.currentPicture = Assets.lightBlueCircle;
			break;
		case ORANGE: 
			this.currentPicture = Assets.orangeCircle;
			break;
		case PINK: 
			this.currentPicture = Assets.pinkCircle;
			break;
		case RED:
			this.currentPicture = Assets.redCircle;
			break;
		case YELLOW:
			this.currentPicture = Assets.yellowCircle;
		}
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
	
	// Logic when circle is touched
	public void touchEvent (MainGame gameRef) {
		if (this.getColour() == gameRef.centreTarget.getColour()) {
			Assets.TouchSound.play(1.0f);
			this.setTouched(true);
			this.setColourToRandom();
			gameRef.score++;
			
			for (Ring r: gameRef.rings) {
				if(r.getList().contains(this)) {
					r.targetColourCircles--;
				}
			}
		}
	}
}