package com.jbs.rh2;

import net.dermetfan.gdx.graphics.g2d.AnimatedSprite;

import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Obstacle extends HitboxActor {
	Image obstacle;
	AnimatedSprite trail;
	float rotateAmt, speed;

	public Obstacle(float x) {
		super("obstacle");
		obstacle = new Image(Assets.getTex("Obstacle/obstacle.png"));
		trail = new AnimatedSprite(Utils.getAnimation("Obstacle/Trail.png", 1 / 4f, 114, 72,
				PlayMode.LOOP));
		
		setY(MathUtils.random(getHeight(), Constants.HEIGHT - getHeight()));
		setX(x);
		obstacle.setOrigin(obstacle.getWidth() / 2, obstacle.getHeight() / 2);
		rotateAmt = MathUtils.randomBoolean() ? MathUtils.random(-5, -0.5f) : MathUtils.random(0.5f, 5f);
		speed = MathUtils.random(3, 6);
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		
		setX(getX() - speed);
		setRotation(getRotation() + rotateAmt);
		trail.update(delta);
		obstacle.act(delta);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {	
		super.draw(batch, parentAlpha);


		
		setHitboxBounds(getX() - obstacle.getWidth() / 2, getY() - obstacle.getHeight() / 2, obstacle.getWidth(), obstacle.getHeight());
		
		obstacle.setPosition(getX() - obstacle.getWidth() / 2, getY() - obstacle.getHeight() / 2);
		obstacle.setRotation(getRotation());
		
		obstacle.draw(batch, parentAlpha);
		
		trail.setPosition(getX() + getWidth() + 75, getY() + getHeight() / 2 - trail.getHeight()
				/ 2);
		trail.draw(batch, parentAlpha);
	}
}
