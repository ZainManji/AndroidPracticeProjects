package com.thebusiness.skyfall;

import com.badlogic.gdx.math.Rectangle;
import com.thebusiness.skyfall.Powerup.power;

public class Circle extends Rectangle {
	
	
	public int colour;
	public Powerup powerUp = new Powerup();

	public Circle() {
		this.colour = 0; //0 is normal circle, 1 is bomb, 2+ will be powerups
		this.powerUp.type = power.none;
		
	}
	

}
