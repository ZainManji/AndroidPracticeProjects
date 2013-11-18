package com.thebusiness.quickSwipe;

import com.badlogic.gdx.Gdx;

public class GameUtilities {
	
	static final int X_MIN = 0;
	static final int X_MAX = Gdx.graphics.getWidth();
	static final int Y_MIN = 0;
	static final int Y_MAX = Gdx.graphics.getHeight();
	static final int CENTER_X = (int)(X_MIN + X_MAX)/2;
	static final int CENTER_Y = (int)(Y_MIN + Y_MAX)/2;
	
	//Coordinates for circles in a row
	static final int [] ROW_COORDINATE_3 = {(int)(X_MAX * 0.20), CENTER_X, (int)(X_MAX * 0.80)};
	static final int [] ROW_COORDINATE_4 = {(int)(X_MAX * 0.10), (int)(X_MAX * 0.35), (int)(X_MAX * 0.65), (int)(X_MAX * 0.85)};
	
	static final int MAX_LIVES = 3;
	static final int GAME_TIME = 0;
	static final int POWERUP_TIME = 10;

}