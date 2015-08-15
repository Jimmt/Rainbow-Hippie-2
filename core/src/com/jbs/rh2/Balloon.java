package com.jbs.rh2;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Balloon extends Actor {
	BalloonType type;

	public Balloon(BalloonType type) {
		this.type = type;
	}
	
	public Balloon(){
		type = BalloonType.cachedValues()[(int)(BalloonType.cachedValues().length * Math.random())];
	}

	@Override
	public void act(float delta) {
		super.act(delta);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
	}

}

enum BalloonType {
	BLACK("black", true), BLUE("blue", false), GREEN("green", false), RED("red", false), WHITE(
			"white", true), YELLOW("yellow", false);

	static BalloonType[] types;
	
	String color;
	boolean deadly;

	private BalloonType(String color, boolean deadly) {
		this.color = color;
		this.deadly = deadly;
	}
	
	public static BalloonType[] cachedValues(){
		if(types == null){
			types = values();
		}
		return types;
	}
	
	
}
