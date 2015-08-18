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

public class Level1 extends LevelScreen implements InputProcessor {
	Level1Background background;
	boolean leftDown, rightDown;
	float lastObstacleSpawn, obstacleTime = 4f;
	float lastBalloonSpawn, balloonTime = 3f;

	ShapeRenderer sr;

	public Level1(RH2 rh2) {
		super(rh2);

		background = new Level1Background(bgStage);

		bgStage.addActor(background);
		stage.addActor(player);

		sr = new ShapeRenderer();

		Gdx.input.setInputProcessor(new InputMultiplexer(this, stage));
	}
	

	@Override
	public void render(float delta) {
		super.render(delta);

		if (RH2.DEBUG) {
			drawShapes();
		}

		if (lastBalloonSpawn > balloonTime) {
			lastBalloonSpawn = 0;
			balloonTime = MathUtils.random(balloonTime - 0.5f, balloonTime + 2f);
			createBalloon();
		} else {
			lastBalloonSpawn += delta;
		}

		if (lastObstacleSpawn > obstacleTime) {
			lastObstacleSpawn = 0;
			obstacleTime -= 0.01f;
			createObstacle();
		} else {
			lastObstacleSpawn += delta;
		}

		removeOffscreen();

		

		checkInput();
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

		sr.end();
	}

	public void checkInput() {
		if (leftDown) {
			if (player.getY() + player.sprite.getHeight() < Constants.HEIGHT) {
				player.setY(player.getY() + 6);
			}
		} else {
			if (player.getY() > 0) {
				player.setY(player.getY() - 6);
			}

		}
		if (rightDown) {
			player.firing = true;
		} else {
			player.firing = false;
		}

		if (Gdx.app.getType() == ApplicationType.Desktop) {
			if (Gdx.input.isButtonPressed(Buttons.RIGHT)) {
				player.firing = true;
			} else {
				player.firing = false;
			}
		}
	}

	@Override
	public boolean keyDown(int keycode) {

		return false;
	}

	@Override
	public boolean keyUp(int keycode) {

		return false;
	}

	@Override
	public boolean keyTyped(char character) {

		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (screenX <= Constants.WIDTH / 2) {
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

}
