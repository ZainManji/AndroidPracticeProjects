package com.thebusiness.skyfall;

import com.badlogic.gdx.math.Rectangle;

public class Powerup extends Rectangle {
	
	enum power {
		slowMotion, TwoTimes, ThreeTimes, allBombs, noBombs, extrLife, longPaddle, shortPaddle, none
	}
	
	int duration = 5;
	power type;
	
	Powerup () {
		
	}

	
}