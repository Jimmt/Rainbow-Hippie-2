package com.jbs.rh2;

import net.dermetfan.gdx.graphics.g2d.AnimatedSprite;

import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Obstacle extends Image {
	AnimatedSprite trail;
	float rotateAmt, speed;

	public Obstacle(float x) {
		super(Assets.getTex("Obstacle/obstacle.png"));
		trail = new AnimatedSprite(Utils.getAnimation("Obstacle/Trail.png", 1 / 4f, 114, 72,
				PlayMode.LOOP));
		
		setY(MathUtils.random(getHeight(), Constants.HEIGHT - getHeight()));
		setX(x);
		setOrigin(getWidth() / 2, getHeight() / 2);
		rotateAmt = MathUtils.random(-5, 5);
		speed = MathUtils.random(3, 6);
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		trail.update(delta);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {	
		super.draw(batch, parentAlpha);

		setX(getX() - speed);
		setRotation(getRotation() + rotateAmt);
		
		trail.setPosition(getX() + getWidth() + 40, getY() + getHeight() / 2 - trail.getHeight()
				/ 2);
		trail.draw(batch, parentAlpha);
	}
}
