package com.jbs.rh2;

import net.dermetfan.gdx.graphics.g2d.AnimatedSprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Player extends HitboxActor {
	AnimatedSprite sprite;
	Actor ghost;
	Image rainbow, mouth;
	float mouthX, mouthY, animSpeed = 1 / 7f;
	boolean firing, first;
	int[] offsets = { 0, 1, 2, 3, 2, 1, 0 };

	public Player() {
		super("player");

		sprite = new AnimatedSprite(Utils.getAnimation("Hippies/Dave/fly.png", animSpeed, 200, 180,
				PlayMode.LOOP));
		rainbow = new Image(Assets.getTex("Effects/rainbow.png"));
		mouth = new Image(Assets.getTex("Hippies/Cool Dave/mouth.png"));
		ghost = new Actor();

		mouthX = 115;
		mouthY = 80;

		setX(30);
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		setX(getStage().getCamera().position.x - Constants.WIDTH / 2);

		setHitboxBounds(getX() + 10, getY() + 3, sprite.getWidth() - 10, sprite.getHeight() - 12);

		sprite.setPosition(getX(), getY());
		sprite.setRotation(getRotation());
		sprite.update(delta);

		if (firing) {
			rainbow.act(delta);
			mouth.act(delta);
		}
	}
	

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);

		sprite.draw(batch, parentAlpha);

		if (firing) {
			int index = sprite.getAnimation().getKeyFrameIndex(sprite.getTime());
			int offsetY = offsets[index];

			mouth.setPosition(getX() + mouthX, getY() + mouthY + offsetY);
			rainbow.setPosition(mouth.getX(),
					mouth.getY() + mouth.getHeight() / 2 - rainbow.getHeight() / 2);

			mouth.draw(batch, parentAlpha);
			rainbow.draw(batch, parentAlpha);
		}

	}
}
