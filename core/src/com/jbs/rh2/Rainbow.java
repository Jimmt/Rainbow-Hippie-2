package com.jbs.rh2;

import net.dermetfan.gdx.graphics.g2d.AnimatedSprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Rainbow extends HitboxActor {
	Image image;
	AnimatedSprite hitSprite;
	Actor ghost;
	Player player;
	boolean drawHit;
	float origWidth;
	float lastShowTime, showTime = 0.2f;

	public Rainbow(Player player) {
		super("rainbow");

		this.player = player;
		image = new Image(Assets.getTex("Effects/rainbow.png"));
		hitSprite = new AnimatedSprite(Utils.getAnimation("Effects/rainbowhit.png", 1 / 15f, 500,
				475, 5, PlayMode.NORMAL));
		hitSprite.setAlpha(0.0f);
		origWidth = image.getWidth();
		setSize(image.getWidth(), image.getHeight());
		setVisible(false);
		ghost = new Actor();
	}

	public Player getPlayer() {
		return player;
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		image.act(delta);
		hitSprite.update(delta);
		ghost.act(delta);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);

		image.setSize(getWidth(), getHeight());
		image.setPosition(getX(), getY());
		image.draw(batch, parentAlpha);

		if (Gdx.input.isKeyPressed(Keys.F1)) {
			player.rainbow.hitSprite.stop();
			player.rainbow.hitSprite.play();
		}

		if (ghost.getActions().size == 0) {
			hitSprite.setPosition(getX() + image.getWidth() - hitSprite.getWidth() / 2, getY()
					+ image.getHeight() / 2 - 475 / 2f - 18);
		} else {
			hitSprite.setPosition(ghost.getX(), ghost.getY());
		}

		hitSprite.draw(batch);

		setHitboxBounds(getX(), getY(), image.getWidth(), image.getHeight());
	}

}
