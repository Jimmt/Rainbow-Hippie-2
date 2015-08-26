package com.jbs.rh2;

import com.badlogic.gdx.scenes.scene2d.Group;

public class Boss extends Group {
	LevelScreen screen;
	String levelName;
	float health, maxHealth;
	boolean active, left;
	
	public Boss(LevelScreen screen, String levelName, float health) {
		this.levelName = levelName;
		this.health = health;
		this.maxHealth = health;
		this.screen = screen;
	}
	
	@Override
	public void act(float delta){
		super.act(delta);
	}
	
	public void attack(){
		
	}
}
