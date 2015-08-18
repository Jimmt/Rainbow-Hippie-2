package com.jbs.rh2;

import net.dermetfan.gdx.graphics.g2d.AnimatedSprite;

import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class Balloon extends HitboxActor {
	BalloonType type;
	AnimatedSprite sprite;
	float speed, oscillateDist;

	public Balloon(BalloonType type) {
		super("balloon");
		this.type = type;

		if (type.deadly) {
			oscillateDist = MathUtils.random(50, 120);
		}

		createSprite();
	}

	public Balloon() {
		super("balloon");
		type = BalloonType.cachedValues()[(int) (BalloonType.cachedValues().length * Math.random())];

		if (type.deadly) {
			oscillateDist = MathUtils.random(50, 120);
		}

		createSprite();
	}

	public void createSprite() {
		sprite = new AnimatedSprite(Utils.getAnimation("Balloon/balloon_" + type.color + ".png",
				1 / 4f, 130, 250, PlayMode.LOOP));
		speed = MathUtils.random(0.5f, 2f);
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		setX(getX() - speed);
		sprite.setPosition(getX(), getY());
		sprite.update(delta);

		setHitboxBounds(getX() + 20, getY() + 115, sprite.getWidth() - 35, sprite.getHeight() - 135);

		if (oscillateDist != 0 && getActions().size == 0) {
			addAction(Actions.sequence(Actions.moveBy(0, oscillateDist, 1.0f),
					Actions.moveBy(0, -oscillateDist, 1.0f)));
		}

	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		sprite.draw(batch, parentAlpha);
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

	public static BalloonType[] cachedValues() {
		if (types == null) {
			types = values();
		}
		return types;
	}

}
