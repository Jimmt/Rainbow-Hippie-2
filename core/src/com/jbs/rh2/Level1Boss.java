package com.jbs.rh2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Level1Boss extends Boss {
	LevelScreen screen;
	Rectangle headHitbox, neckHitbox;
	Image unicorn, unicornNormal, unicornAttack;
	BossRainbow rainbow;

	int behaviorStage = 1;
	float lastFireTime, fireTime = 2f;
	float randomFactor = 20f;

	public Level1Boss(LevelScreen screen) {
		super(screen, "Forever Land of Happiness", 200);
		unicorn = new Image(Assets.getTex("Maps/Forever Land of Happiness/Boss/Normal.png"));
		unicornNormal = new Image(Assets.getTex("Maps/Forever Land of Happiness/Boss/Normal.png"));
		unicornAttack = new Image(Assets.getTex("Maps/Forever Land of Happiness/Boss/Attack.png"));
		rainbow = new BossRainbow();

		float width = (unicorn.getWidth() / unicorn.getHeight()) * Constants.HEIGHT;
		unicorn.setSize(width, Constants.HEIGHT);
		this.screen = screen;
		unicorn.addAction(Actions.moveTo(screen.stage.getCamera().position.x + Constants.WIDTH / 2,
				Constants.HEIGHT / 2 - unicorn.getHeight() / 2));
		headHitbox = new Rectangle(unicorn.getX(), unicorn.getY(), unicorn.getWidth(),
				unicorn.getHeight());
		neckHitbox = new Rectangle(unicorn.getX(), unicorn.getY(), unicorn.getWidth(),
				unicorn.getHeight());

		rainbow.setWidth(Constants.WIDTH);
		rainbow.setOrigin(rainbow.getWidth() - 43, rainbow.getHeight() / 2);
		rainbow.setColor(1, 1, 1, 0);
		addActor(rainbow);
		addActor(unicorn);
		attack();
	}

	public void show() {
		if (!active) {
			left = false;
			active = true;
			unicorn.addAction(Actions.sequence(Actions.moveBy(-unicorn.getWidth(), 0, 1f)));
			((Level1) screen).healthBar.show();
		}
	}

	Action activeAction = new Action() {
		@Override
		public boolean act(float delta) {
			active = false;
			return true;
		}
	};

	public void leave() {
		if (!left) {
			left = true;
			unicorn.addAction(Actions.sequence(Actions.moveBy(unicorn.getWidth(), 0, 1f),
					activeAction));
			((Level1) screen).healthBar.fade();
		}
	}

	Action resetFireAction = new Action(){

		@Override
		public boolean act(float delta) {
			attack();
			return true;
		}
		
	};
	
	@Override
	public void attack() {
		unicorn.setDrawable(unicornAttack.getDrawable());
		rainbow.addAction(Actions.sequence(Actions.alpha(0.1f), Actions.delay(2f), Actions.fadeIn(0.2f), Actions.delay(2.5f), Actions.fadeOut(1f), resetFireAction));
		Vector2 rainbowPosition = new Vector2(rainbow.getX() + rainbow.getOriginX(), rainbow.getY() + rainbow.getOriginY());
		Vector2 playerPosition = new Vector2(screen.player.getX() + screen.player.getWidth() / 2, screen.player.getY() + screen.player.getHeight() / 2);
		Vector2 diff = rainbowPosition.sub(playerPosition);
		rainbow.setRotation(diff.angle() + MathUtils.random(-randomFactor, randomFactor));
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		headHitbox.set(unicorn.getX() + 100, unicorn.getY() + 145f, unicorn.getWidth() - 150,
				unicorn.getHeight() - 250);
		neckHitbox.set(unicorn.getX() + 280, unicorn.getY(), unicorn.getWidth() - 280, 140);
		unicorn.setX(unicorn.getX() + screen.cameraSpeed);
		rainbow.setPosition(unicorn.getX() - rainbow.getWidth() + 300,
				unicorn.getY() + unicorn.getHeight() / 2 - rainbow.getHeight() / 2 - 75);
		unicorn.setVisible(active);


		checkBehavior();
		checkDamageTaken(delta);

		if (Gdx.input.isKeyPressed(Keys.DOWN)) {
			health--;
		}
		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			screen.score = 40;
		}
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			screen.score = 120;
		}
	}

	public void checkDamageTaken(float delta) {
		if (screen.player.rainbow.hitbox.overlaps(headHitbox)
				|| screen.player.rainbow.hitbox.overlaps(neckHitbox)) {
			health -= 1 / 60f;
		}
	}

	public void checkBehavior() {
		if (behaviorStage == 1) {
			if (screen.score >= 60) {
				show();
			}
			if (active) {
				if (health <= maxHealth / 2f) {
					leave();
					behaviorStage = 2;
				}
				if (screen.score < 60) {
					leave();
				}
			}
		} else if (behaviorStage == 2) {
			if (screen.score >= 120) {
				show();
			}
			if (active) {
				if (screen.score < 120) {
					leave();
				}
				if (health <= 0) {
					leave();
				}
			}
		}
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
	}

}
