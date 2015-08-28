package com.jbs.rh2;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class BossRainbow extends HitboxActor {
	Image image;

	public BossRainbow() {
		super("rainbow_boss");
		image = new Image(Assets.getTex("Effects/rainbow_boss.png"));
		setSize(image.getWidth(), image.getHeight());
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		image.setSize(getWidth(), getHeight());
		image.setPosition(getX(), getY());
		image.setOrigin(getOriginX(), getOriginY());
		image.setRotation(getRotation());
		image.setColor(getColor());
		image.act(delta);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);

		image.draw(batch, parentAlpha);
	}

}
