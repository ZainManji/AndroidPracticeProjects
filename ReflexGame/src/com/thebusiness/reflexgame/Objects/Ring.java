package com.thebusiness.reflexgame.Objects;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.thebusiness.reflexgame.GameUtilities;

public class Ring {
	// List of objects in ring
	ArrayList<SingleGameObject> objectList;
	// List of indices that contain circles in the ring
	ArrayList<Integer> circleList;	
	// Maximum number of circles of the target colour for this ring
	public int targetColourLimit;
	// Current number of circles of the target colour in this ring
	public int targetColourCircles;
	
	int numObjects;
	int ringRadius;
	double angleIncrement;
	
	public Ring (int numObjects, int ringRadius, int targetColourLimit, boolean circlesOnly) {
		this.numObjects = numObjects;
		this.ringRadius = ringRadius;
		this.targetColourLimit = targetColourLimit;
		targetColourCircles = targetColourLimit;
		
		angleIncrement = 2 * Math.PI / numObjects;
		objectList = new ArrayList<SingleGameObject> ();
		circleList = new ArrayList<Integer> ();
		
		// Creates a ring of objects, only of circles if specified
		if (circlesOnly) {
			// Adds specified number of circles with random colours to rings
			for (int i = 0;  i < numObjects; i++) {
				objectList.add(new Circle(
	    			GameUtilities.CIRC_RAD, 
	    			GameUtilities.CENTER_X + (int) (ringRadius * Math.cos(angleIncrement*i)), 
	    			GameUtilities.CENTER_Y + (int) (ringRadius * Math.sin(angleIncrement*i)),
	    			angleIncrement*i,
	    			Circle.Colour.values()[(int)(Math.random()*7)]));
			}
		}
		else {
			refresh();
		}
	}
	
	// Get methods for lists
	
	public ArrayList<SingleGameObject> getList () {
		return objectList;
	}
	
	public ArrayList<Integer> getCircles () {
		return circleList;
	}
	
	// Clears all circles in the ring
	public void clear () {
		objectList.clear();
	}
	
	// NEED TO FIX TO ALSO REFRESH BOMBS
	// Changes all circles' colours in the ring
	public void refresh () {
		int temp;
		
		objectList.clear();
		circleList.clear();
		
		// Adds specified number of circles with random colours to rings
		for (int i = 0;  i < numObjects; i++) {
			temp = (int)(Math.random()*8);
			
			if (temp == 8) {
				objectList.add(new Bomb(
					GameUtilities.CIRC_RAD,
					GameUtilities.CENTER_X + (int) (ringRadius * Math.cos(angleIncrement*i)), 
    				GameUtilities.CENTER_Y + (int) (ringRadius * Math.sin(angleIncrement*i)),
    				angleIncrement*i));
			}
			else {
				objectList.add(new Circle(
    				GameUtilities.CIRC_RAD, 
    				GameUtilities.CENTER_X + (int) (ringRadius * Math.cos(angleIncrement*i)), 
    				GameUtilities.CENTER_Y + (int) (ringRadius * Math.sin(angleIncrement*i)),
    				angleIncrement*i,
    				Circle.Colour.values()[(int)(Math.random()*7)]));
				circleList.add(i);
			}
    	}
	}
	
	// Creates a ring of objects excluding the colour of the input circle
	public void refreshWithExclude (Circle exclude) {
		int temp;
		int rand;
		
		objectList.clear();
		circleList.clear();
		
		// Adds specified number of circles with random colours to rings
		for (int i = 0;  i < numObjects; i++) {
			temp = (int)(Math.random()*8);
			
			if (temp == 8) {
				objectList.add(new Bomb(
					GameUtilities.CIRC_RAD,
					GameUtilities.CENTER_X + (int) (ringRadius * Math.cos(angleIncrement*i)), 
    				GameUtilities.CENTER_Y + (int) (ringRadius * Math.sin(angleIncrement*i)),
    				angleIncrement*i));
			}
			else {
				rand = (int)(Math.random()*(GameUtilities.NUM_COLOURS - 1));
				while (rand == exclude.getColour().ordinal()) {
					rand = (int)(Math.random()*(GameUtilities.NUM_COLOURS - 1));
				}
				
				objectList.add(new Circle(
    				GameUtilities.CIRC_RAD, 
    				GameUtilities.CENTER_X + (int) (ringRadius * Math.cos(angleIncrement*i)), 
    				GameUtilities.CENTER_Y + (int) (ringRadius * Math.sin(angleIncrement*i)),
    				angleIncrement*i,
    				Circle.Colour.values()[rand]));
				circleList.add(i);
			}
    	}
	}
	
	// Checks if any circle in the ring has the input colour
	public boolean containsColour (SingleGameObject.Colour chkColour) {
		for (SingleGameObject obj: getList()) {
			if (obj.getColour() == chkColour) {
				return true;
			}
		}
		return false;
	}
	
	// Rotates rings by rotation angle
	public void rotateRing (double rotationAngle) {
		for (SingleGameObject obj: getList()) {
			obj.setAngle(obj.getAngle() + rotationAngle);
			obj.setXCoord(GameUtilities.CENTER_X + ((int)(ringRadius * Math.cos(obj.getAngle()))));
			obj.setYCoord(GameUtilities.CENTER_Y + ((int)(ringRadius * Math.sin(obj.getAngle()))));
			
		}
	}
	// Draws each object in the ring
	public void drawRing (SpriteBatch batch, TextureRegion currentFrame, boolean bombTouched) {
		for (SingleGameObject obj: objectList) {
			obj.drawObject(batch);
    	}
	}
}
