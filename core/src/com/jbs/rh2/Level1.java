package com.jbs.rh2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;

public class Level1 extends LevelScreen {
	Level1Background background;

	float lastObstacleSpawn, obstacleTime = 4f;
	float lastBalloonSpawn, balloonTime = 3f;

	ShapeRenderer sr;

	public Level1(RH2 rh2) {
		super(rh2);

		background = new Level1Background(bgStage);
//		score = 60;
		boss = new Level1Boss(this);
		healthBar = new BossHealthBar(boss);
		healthBar.setPosition(0, Constants.HEIGHT - healthBar.getHeight());

		bgStage.addActor(background);
		stage.addActor(player);
		stage.addActor(boss);
		hudStage.addActor(healthBar);

		gameOverDialog = new GameOverDialog(rh2, 1);

		sr = new ShapeRenderer();
	}

	@Override
	public void render(float delta) {
		super.render(delta);

		if (RH2.DEBUG) {
			drawShapes();
		}

		removeOffscreen();
		
		Level1Boss level1boss = (Level1Boss) boss;
		if(level1boss.rainbow.overlaps(player.hitbox) && level1boss.rainbow.getColor().a == 1f){
			gameOver();
		}
		
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
		Level1Boss boss1 = (Level1Boss) boss;
		sr.rect(boss1.headHitbox.getX(), boss1.headHitbox.getY(), boss1.headHitbox.getWidth(), boss1.headHitbox.getHeight());
		sr.rect(boss1.neckHitbox.getX(), boss1.neckHitbox.getY(), boss1.neckHitbox.getWidth(), boss1.neckHitbox.getHeight());
		sr.polygon(boss1.rainbow.polygon.getTransformedVertices());
		sr.line(boss1.playerPosition, boss1.rainbowPosition);
		sr.circle(boss1.unicorn.getX() + boss1.unicorn.getWidth() / 2, boss1.unicorn.getY() + boss1.unicorn.getHeight() / 2, 10);
		sr.circle(player.x + player.width / 2, player.y + player.height / 2, 10);
		sr.end();
	}

}
