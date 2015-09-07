package com.jbs.rh2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Level2Boss extends Boss {
	LevelScreen screen;
	Rectangle headHitbox, neckHitbox;
	Image body, head, mouth;
	Group monster;
	BossRainbow rainbow;
	Vector2 rainbowPosition, playerPosition, diff;

	int behaviorStage = 1;
	float lastFireTime, fireTime = 2f;
	float randomFactor = 20f, monsterHeight;
	boolean attack = false;

	public Level2Boss(LevelScreen screen) {
		super(screen, "Waves of Paradise", 400);
		body = new Image(Assets.getTex("Maps/Waves of Paradise/Boss/body.png"));
		head = new Image(Assets.getTex("Maps/Waves of Paradise/Boss/head.png"));
		mouth = new Image(Assets.getTex("Maps/Waves of Paradise/Boss/mouth.png"));
		monster = new Group();
		rainbow = new BossRainbow();
		head.setY(333);
		mouth.setPosition(127, 200);
		body.setX(345);
		monster.addActor(body);
		monster.addActor(mouth);
		monster.addActor(head);
		addActor(monster);

//		float width = (unicorn.getWidth() / unicorn.getHeight()) * Constants.HEIGHT;
//		unicorn.setSize(width, Constants.HEIGHT);
		this.screen = screen;
		monsterHeight = (head.getY() + head.getHeight());
		monster.addAction(Actions.moveTo(screen.stage.getCamera().position.x + Constants.WIDTH / 2,
				Constants.HEIGHT / 2 - monsterHeight / 2));
		headHitbox = new Rectangle();
		neckHitbox = new Rectangle();

		rainbow.setWidth(Constants.WIDTH);
		rainbow.setOrigin(rainbow.getWidth() - 43, rainbow.getHeight() / 2);
		rainbow.setColor(1, 1, 1, 0);
		addActor(rainbow);

		rainbowPosition = new Vector2();
		playerPosition = new Vector2();
		diff = new Vector2();
// screen.score = 120;
	}

	public void show() {
		if (!active) {
			rainbow.setRotation(MathUtils.random(-45, 45));
			left = false;
			active = true;
			monster.addAction(Actions.sequence(Actions.moveBy(-head.getWidth() + 82, 0, 1f)));
			((Level2) screen).healthBar.show();
			attack();
			attack = true;
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
			monster.addAction(Actions.sequence(Actions.moveBy(head.getWidth() - 82, 0, 1f),
					activeAction));
			((Level2) screen).healthBar.fade();
			attack = false;
		}
	}

	Action resetFireAction = new Action() {
		@Override
		public boolean act(float delta) {
			if (attack) {
				attack();
			}
			return true;
		}

	};

	Action normalUnicornAction = new Action() {
		@Override
		public boolean act(float delta) {
//			unicorn.setDrawable(unicornNormal.getDrawable());
			return true;
		}
	};

	Action attackUnicornAction = new Action() {
		@Override
		public boolean act(float delta) {
//			unicorn.setDrawable(unicornAttack.getDrawable());
			return true;
		}
	};

	@Override
	public void attack() {
		if (behaviorStage == 1) {
			rainbow.addAction(Actions.sequence(normalUnicornAction, Actions.alpha(0.1f),
					Actions.delay(1.5f), attackUnicornAction, Actions.fadeIn(0.2f),
					Actions.delay(2.0f), normalUnicornAction, Actions.fadeOut(0.2f),
					Actions.delay(MathUtils.random(0.5f, 1f)), resetFireAction));
			rainbow.setRotation(diff.angle() + MathUtils.random(-randomFactor, randomFactor));
		} else if (behaviorStage == 2) {
			int multiplier = 1;
			if (180 + rainbow.getRotation() < diff.angle()) {
				multiplier = -1;
			}
			System.out.println(rainbow.getRotation() + " " + diff.angle());
			rainbow.addAction(Actions.sequence(normalUnicornAction, Actions.alpha(0.1f),
					Actions.delay(1.5f), attackUnicornAction, Actions.fadeIn(0.2f),
					Actions.rotateBy(multiplier * MathUtils.random(5f, 10f), 2f),
					normalUnicornAction, Actions.fadeOut(0.2f), resetFireAction));
		}
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		headHitbox.set(monster.getX(), monster.getY() + head.getY(), head.getWidth(), head.getHeight());
		neckHitbox.set(monster.getX() + body.getX(), monster.getY(), body.getWidth(), body.getHeight());
		monster.setX(monster.getX() + screen.cameraSpeed);
		rainbow.setPosition(monster.getX() - rainbow.getWidth() + 300,
				monster.getY() + monsterHeight / 2 - rainbow.getHeight() / 2 - 75);
		monster.setVisible(active);

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

		rainbowPosition.set(monster.getX() + mouth.getX(),
				monster.getY() + mouth.getY());
		playerPosition.set(screen.player.x + screen.player.width / 2, screen.player.y
				+ screen.player.height / 2);
		diff.set(rainbowPosition.cpy().sub(playerPosition));
	}

	public void checkDamageTaken(float delta) {
		if (screen.player.rainbow.hitbox.overlaps(headHitbox)
				|| screen.player.rainbow.hitbox.overlaps(neckHitbox)) {
			health -= 1 / 60f;
		}
	}

	/*
	 * exactly the same as the unicorn boss except:
	 * Spawns at 100 balloons or above.
	 * Boss still leaves once it loses half its HP, but comes back when the
	 * player pops 200 balloons or more.
	 * The boss has a total of 400 HP.
	 */
	public void checkBehavior() {
		if (behaviorStage == 1) {
			if (screen.score >= 100) {
				show();
			}
			if (active) {
				if (health <= maxHealth / 2f) {
					leave();
					behaviorStage = 2;
				}
				if (screen.score < 100) {
					leave();
				}
			}
		} else if (behaviorStage == 2) {
			if (screen.score >= 200) {
				show();
			}
			if (active) {
				if (screen.score < 200) {
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
