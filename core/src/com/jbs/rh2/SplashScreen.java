package com.jbs.rh2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class SplashScreen implements Screen {
	SpriteBatch batch;
	Image splash, black;

	public SplashScreen(final RH2 rh2) {
		batch = new SpriteBatch();
		splash = new Image(Assets.getTex("JBSLogo.png"));
		splash.setSize(Constants.WIDTH, Constants.HEIGHT);

		Action changeScreenAction = new Action() {

			@Override
			public boolean act(float delta) {
				rh2.setScreen(new StartScreen(rh2));
				return true;
			}

		};

		splash.addAction(Actions.sequence(Actions.alpha(0),
				Actions.alpha(1f, 0.5f, Interpolation.pow2In), Actions.delay(1.5f),
				Actions.alpha(0, 0.5f, Interpolation.pow2Out), changeScreenAction));

// splash.setColor(1, 1, 1, 0.1f);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		splash.act(delta);

		splash.draw(batch, 1.0f);
		batch.end();

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
