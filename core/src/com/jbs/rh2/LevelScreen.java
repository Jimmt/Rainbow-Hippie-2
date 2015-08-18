package com.jbs.rh2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class LevelScreen implements Screen {
	Stage bgStage, stage, hudStage;
	Array<Balloon> balloons;
	Array<Obstacle> obstacles;
	Array<HitboxActor> hitboxActors;

	Player player;
	boolean gameOver;

	int score;

	public LevelScreen(RH2 rh2) {
		bgStage = new Stage(new StretchViewport(Constants.WIDTH, Constants.HEIGHT));
		stage = new Stage(new StretchViewport(Constants.WIDTH, Constants.HEIGHT));
		hudStage = new Stage(new StretchViewport(Constants.WIDTH, Constants.HEIGHT));

		balloons = new Array<Balloon>();
		obstacles = new Array<Obstacle>();
		hitboxActors = new Array<HitboxActor>();

		player = new Player();
		hitboxActors.add(player);
		hitboxActors.add(player.rainbow);
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (!gameOver) {
			bgStage.act(delta);
			stage.act(delta);
			checkCollisions();
			scrollCamera(3f);
		}
		bgStage.draw();
		stage.draw();

		hudStage.act(delta);
		hudStage.draw();

		if (Gdx.input.isKeyPressed(Keys.D)) {
			bgStage.getCamera().position.x += 5;
		}
	}

	public void scrollCamera(float amount) {
		bgStage.getCamera().position.x += amount;
		stage.getCamera().position.x += amount;
	}

	public void gameOver() {
		gameOver = true;
		// show dialog
	}

	public void incrementScore() {
		score++;
	}

	public void checkCollisions() {
		for (int i = 0; i < hitboxActors.size; i++) {
			for (int j = 0; j < hitboxActors.size && j != i; j++) {
				if (hitboxActors.get(i).getHitbox().overlaps(hitboxActors.get(j).getHitbox())) {
					if (!checkBalloonCollision(hitboxActors.get(i), hitboxActors.get(j))) {
						checkObstacleCollision(hitboxActors.get(i), hitboxActors.get(j));
					}
				}
			}
		}
	}

	public boolean checkBalloonCollision(HitboxActor a, HitboxActor b) {

		if (a.getName().equals("rainbow") && b.getName().equals("balloon")) {
			Balloon balloon = (Balloon) b;
			if (balloon.type.deadly) {
				gameOver();
			} else {
				incrementScore();
			}
			stage.getActors().removeValue(balloon, false);
			balloons.removeValue(balloon, false);
			hitboxActors.removeValue(balloon, false);

			return true;

		} else if (b.getName().equals("rainbow") && a.getName().equals("balloon")) {
			Balloon balloon = (Balloon) a;
			if (balloon.type.deadly) {
				gameOver();
			} else {
				incrementScore();
			}
			stage.getActors().removeValue(balloon, false);
			balloons.removeValue(balloon, false);
			hitboxActors.removeValue(balloon, false);

			return true;
		}
		return false;
	}

	public boolean checkObstacleCollision(HitboxActor a, HitboxActor b) {
		if (a.getName().equals("player") && b.getName().equals("obstacle")
				|| b.getName().equals("player") && a.getName().equals("obstacle")) {
			gameOver();
			return true;
		}
		return false;
	}

	public void createBalloon() {
		Balloon balloon = new Balloon();
		balloons.add(balloon);
		hitboxActors.add(balloon);
		stage.addActor(balloon);
		balloon.setPosition(
				stage.getCamera().position.x + Constants.WIDTH / 2,
				MathUtils.random(balloon.sprite.getHeight(),
						Constants.HEIGHT - balloon.sprite.getHeight()));
	}

	public void createObstacle() {
		Obstacle obstacle = new Obstacle(stage.getCamera().position.x + Constants.WIDTH / 2);
		obstacles.add(obstacle);
		hitboxActors.add(obstacle);
		stage.addActor(obstacle);
	}

	public void removeOffscreen() {
		for (int i = 0; i < balloons.size; i++) {
			if (balloons.get(i).getX() + balloons.get(i).sprite.getWidth() < stage.getCamera().position.x
					- Constants.WIDTH / 2) {
				stage.getActors().removeValue(balloons.get(i), false);
				hitboxActors.removeValue(balloons.get(i), false);
				balloons.removeIndex(i);
			}
		}
		for (int i = 0; i < obstacles.size; i++) {
			if (obstacles.get(i).getX() + obstacles.get(i).getWidth()
					+ obstacles.get(i).trail.getWidth() < stage.getCamera().position.x
					- Constants.WIDTH / 2) {
				stage.getActors().removeValue(obstacles.get(i), false);
				hitboxActors.removeValue(obstacles.get(i), false);
				obstacles.removeIndex(i);

			}
		}
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().setScreenSize(width, height);
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {

	}

}
