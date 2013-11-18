package com.thebusiness.reflexgame;

import com.badlogic.gdx.Gdx;

public class GameUtilities {
	
	// Sizing
	public static final int X_MIN = 0;
	public static final int X_MAX = Gdx.graphics.getWidth();
	public static final int Y_MIN = 0;
	public static final int Y_MAX = Gdx.graphics.getHeight();
	public static final int CENTER_X = (int)(X_MIN + X_MAX)/2;
	public static final int CENTER_Y = (int)(Y_MIN + Y_MAX)/2;
	
	// Increase in ring radius
	public static final int RING_RAD_INC = 50;
	
	// Circle radius
	public static final int CIRC_RAD = 25;
	//Circle radius for lives
	public static final int LIFE_RAD = 8;
	public static final int MAX_LIVES = 3;
	
	// Game time
	public static final int GAME_TIME = 30;
	
	// Number of Objects (By Type)
	public static final int NUM_COLOURS = 8;
	public static final int NUM_POWERUPS = 1;
}
