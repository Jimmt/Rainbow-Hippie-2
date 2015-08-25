package com.jbs.rh2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class LevelScreen implements Screen, InputProcessor {
	Stage bgStage, stage, hudStage, dialogStage;
	GameOverDialog gameOverDialog;
	Hud hud;

	Array<Balloon> balloons;
	Array<Obstacle> obstacles;
	Array<HitboxActor> hitboxActors;
	Collisions collisions;

	Player player;

	boolean gameOver, paused;
	boolean leftDown, rightDown;
	boolean contactBlack;
	float contactBlackTime;
	float contactBalloonX;
	Balloon contactBalloon;

	int score = 0;
	float cameraSpeed = 3f, playerSpeed = 6f, basePlayerSpeed = playerSpeed;

	public LevelScreen(RH2 rh2) {
		bgStage = new Stage(new StretchViewport(Constants.WIDTH, Constants.HEIGHT));
		stage = new Stage(new StretchViewport(Constants.WIDTH, Constants.HEIGHT));
		hudStage = new Stage(new StretchViewport(Constants.WIDTH, Constants.HEIGHT));
		dialogStage = new Stage(new StretchViewport(Constants.WIDTH, Constants.HEIGHT));

		balloons = new Array<Balloon>();
		obstacles = new Array<Obstacle>();
		hitboxActors = new Array<HitboxActor>();
		collisions = new Collisions(this);

		player = new Player();
		hitboxActors.add(player);
		hitboxActors.add(player.rainbow);

		hud = new Hud(this);
		hudStage.addActor(hud);


		Gdx.input.setInputProcessor(new InputMultiplexer(hudStage, this, stage));
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (!gameOver && !paused) {
			bgStage.act(delta);
			stage.act(delta);
			collisions.update(this, delta);
			spawnBalloons(delta);
			spawnObstacles(delta);
			scrollCamera(cameraSpeed);
		}

		bgStage.draw();
		stage.draw();

		hudStage.act(delta);
		hudStage.draw();

		dialogStage.act(delta);
		dialogStage.draw();

		if (score < 0) {
			gameOver();
		}

		if (Gdx.input.isKeyPressed(Keys.Q)) {
			gameOver();
		}

		checkInput();
	}

	public void spawnBalloons(float delta) {

	}

	public void spawnObstacles(float delta) {

	}

	public void checkInput() {
		if (leftDown) {
			if (player.getY() + player.sprite.getHeight() < Constants.HEIGHT) {
				if (playerSpeed > basePlayerSpeed) {
					playerSpeed--;

				} else {

				}
				player.setY(player.getY() + playerSpeed);

			}
		} else {
			if (player.getY() > 0) {
				player.setY(player.getY() - basePlayerSpeed);
			}

		}

		if (Gdx.app.getType() == ApplicationType.Android) {
			if (rightDown) {
				player.firing = true;
			} else {
				player.firing = false;
			}
		}

	}

	public void scrollCamera(float amount) {
		bgStage.getCamera().position.x += amount;
		stage.getCamera().position.x += amount;
	}

	public void gameOver() {
		if (!gameOver) {
			gameOver = true;
			gameOverDialog.setScore(score);
			dialogStage.addActor(gameOverDialog);
			gameOverDialog.show(score);
			Gdx.input.setInputProcessor(dialogStage);
		}
	}

	public void incrementScore() {
		score++;
	}

	public void createBalloon() {
// Balloon balloon = new Balloon();
		Balloon balloon = new Balloon(BalloonType.BLACK);
		balloons.add(balloon);
		hitboxActors.add(balloon);
		stage.addActor(balloon);
		balloon.setPosition(
				stage.getCamera().position.x + Constants.WIDTH / 2,
				MathUtils.random(balloon.sprite.getHeight(),
						Constants.HEIGHT - balloon.sprite.getHeight()));
		balloon.toBack();
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
	public boolean keyDown(int keycode) {
		if (Gdx.app.getType() == ApplicationType.Desktop) {
			player.firing = true;
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (Gdx.app.getType() == ApplicationType.Desktop) {
			player.firing = false;
		}
		return false;
	}

	@Override
	public boolean keyTyped(char character) {

		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		
		if (screenX <= Constants.WIDTH / 2) {
			playerSpeed = 15.0f;
			leftDown = true;
		} else {
			rightDown = true;
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (screenX <= Constants.WIDTH / 2) {
			leftDown = false;
		} else {
			rightDown = false;
		}
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {

		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {

		return false;
	}

	@Override
	public boolean scrolled(int amount) {

		return false;
	}

	@Override
	public void pause() {
		paused = !paused;
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
