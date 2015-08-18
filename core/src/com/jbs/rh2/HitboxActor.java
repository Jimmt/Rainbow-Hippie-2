package com.jbs.rh2;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class HitboxActor extends Actor {
	Rectangle hitbox;
	float x, y, width, height;

	public HitboxActor(String name){
		setName(name);
		hitbox = new Rectangle();
	}
	
	public Rectangle getHitbox(){
		return hitbox;
	}
	
	public void setHitboxBounds(float x, float y, float width, float height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	@Override
	public void act(float delta){
		super.act(delta);
		
		hitbox.set(x, y, width, height);
	}
}
