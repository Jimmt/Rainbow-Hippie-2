package com.jbs.rh2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Level1Boss extends Boss {
	LevelScreen screen;
	Rectangle headHitbox, neckHitbox;
	Image unicorn, unicornAttack;
	int behaviorStage = 1;
	float damageTime = 0.01f, lastDamageTick = damageTime;

	public Level1Boss(LevelScreen screen) {
		super(screen, "Forever Land of Happiness", 100);
		unicorn = new Image(Assets.getTex("Maps/Forever Land of Happiness/Boss/Normal.png"));
		unicornAttack = new Image(Assets.getTex("Maps/Forever Land of Happiness/Boss/Attack.png"));

		float width = (unicorn.getWidth() / unicorn.getHeight()) * Constants.HEIGHT;
		unicorn.setSize(width, Constants.HEIGHT);
		this.screen = screen;
		unicorn.addAction(Actions.moveTo(screen.stage.getCamera().position.x + Constants.WIDTH / 2,
				Constants.HEIGHT / 2 - unicorn.getHeight() / 2));
		headHitbox = new Rectangle(unicorn.getX(), unicorn.getY(), unicorn.getWidth(),
				unicorn.getHeight());
		neckHitbox = new Rectangle(unicorn.getX(), unicorn.getY(), unicorn.getWidth(),
				unicorn.getHeight());

		addActor(unicorn);
	}

	public void show() {
		if (!active) {
			active = true;
			unicorn.addAction(Actions.sequence(Actions.moveBy(-unicorn.getWidth(), 0, 1f)));
		}
	}

	public void leave() {
		if (!left) {
			left = true;
			unicorn.addAction(Actions.sequence(Actions.moveBy(unicorn.getWidth(), 0, 1f)));
		}
	}

	@Override
	public void attack() {

	}

	@Override
	public void act(float delta) {
		super.act(delta);

		headHitbox.set(unicorn.getX() + 100, unicorn.getY() + 145f, unicorn.getWidth() - 150,
				unicorn.getHeight() - 250);
		neckHitbox.set(unicorn.getX() + 280, unicorn.getY(), unicorn.getWidth() - 280, 140);
		unicorn.setX(unicorn.getX() + screen.cameraSpeed);

		unicorn.setVisible(active);

		checkBehavior();
		checkDamageTaken(delta);

		if (Gdx.input.isKeyPressed(Keys.DOWN)) {
			health--;
		}
		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			screen.score = 40;
		}
	}

	public void checkDamageTaken(float delta) {
		if (screen.player.rainbow.hitbox.overlaps(headHitbox)
				|| screen.player.rainbow.hitbox.overlaps(neckHitbox)) {
			if (lastDamageTick > damageTime) {
				health -= 0.1f;
				lastDamageTick = 0;
			} else {
				lastDamageTick += delta;
			}
		}
	}

	public void checkBehavior() {
		if (behaviorStage == 1) {
			if (screen.score >= 60) {
				show();
				((Level1) screen).healthBar.show();
			}
			if (active) {
				if (health <= maxHealth / 2f) {
					leave();
					behaviorStage = 2;
					((Level1) screen).healthBar.fade();
				}
				if (screen.score < 60) {
					leave();
					((Level1) screen).healthBar.fade();
				}
			}
		} else if (behaviorStage == 2) {
			if (screen.score >= 120) {
				show();
				((Level1) screen).healthBar.show();
			}
			if (active) {
				if (screen.score < 120) {
					leave();
					((Level1) screen).healthBar.fade();
				}
			}
		}
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
	}

}
