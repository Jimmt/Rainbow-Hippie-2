package com.jbs.rh2;

import net.dermetfan.gdx.graphics.g2d.AnimatedSprite;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

public class Player extends Actor {
	AnimatedSprite sprite;
	Rectangle hitbox;

	public Player() {
		sprite = new AnimatedSprite(Utils.getAnimation("Hippies/Dave/fly.png", 1 / 7f, 200, 180,
				PlayMode.LOOP));
		setX(30);
		hitbox = new Rectangle(getX() + 10, getY() + 3, sprite.getWidth() - 10,
				sprite.getHeight() - 12);
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		setX(getStage().getCamera().position.x - Constants.WIDTH / 2);
		hitbox.set(getX() + 10, getY() + 3, sprite.getWidth() - 10, sprite.getHeight() - 12);
		sprite.setPosition(getX(), getY());
		sprite.setRotation(getRotation());
		sprite.update(delta);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);

		sprite.draw(batch, parentAlpha);
	}
}
