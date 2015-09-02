package com.jbs.rh2;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;

public class Level2 extends LevelScreen {
	Level2Background background;
	BossHealthBar healthBar;
	float lastObstacleSpawn, obstacleTime = 4f;
	float lastBalloonSpawn, balloonTime = 3f;

	ShapeRenderer sr;

	public Level2(RH2 rh2) {
		super(rh2);

		background = new Level2Background(bgStage);
//		score = 60;
		boss = new Level2Boss(this);
		healthBar = new BossHealthBar(boss);
		healthBar.setPosition(0, Constants.HEIGHT - healthBar.getHeight());

		bgStage.addActor(background);
		stage.addActor(player);
		stage.addActor(boss);
		hudStage.addActor(healthBar);

		gameOverDialog = new GameOverDialog(rh2, 2);

		sr = new ShapeRenderer();
	}

	@Override
	public void render(float delta) {
		super.render(delta);

		if (RH2.DEBUG) {
			drawShapes();
		}

		removeOffscreen();
		
	}

	@Override
	public void spawnObstacles(float delta) {
		if (lastObstacleSpawn > obstacleTime) {
			lastObstacleSpawn = 0;
			obstacleTime -= 0.01f;
			createObstacle();
		} else {
			lastObstacleSpawn += delta;
		}
	}

	@Override
	public void spawnBalloons(float delta) {
		if (lastBalloonSpawn > balloonTime) {
			lastBalloonSpawn = 0;
			balloonTime = MathUtils.random(balloonTime - 0.5f, balloonTime + 2f);
			createBalloon();
		} else {
			lastBalloonSpawn += delta;
		}
	}

	public void drawShapes() {
		sr.setProjectionMatrix(stage.getCamera().combined);
		sr.begin(ShapeType.Line);
		sr.setColor(Color.RED);
		for (int i = 0; i < stage.getActors().size; i++) {
			if (stage.getActors().get(i) instanceof HitboxActor) {
				HitboxActor actor = (HitboxActor) (stage.getActors().get(i));
				sr.rect(actor.x, actor.y, actor.width, actor.height);
			}
		}
		Level2Boss boss2 = (Level2Boss) boss;
//		sr.rect(boss2.headHitbox.getX(), boss2.headHitbox.getY(), boss2.headHitbox.getWidth(), boss2.headHitbox.getHeight());
//		sr.rect(boss2.neckHitbox.getX(), boss2.neckHitbox.getY(), boss2.neckHitbox.getWidth(), boss2.neckHitbox.getHeight());
		sr.end();
	}

}
