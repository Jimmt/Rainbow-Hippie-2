package com.jbs.rh2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FillViewport;

public class StartScreen implements Screen, InputProcessor {
	RH2 rh2;
	Stage stage;
	Image logo, black;
	Label startText;
	Scroller scroller;
	Image[] items;
	ShopDialog shopDialog;

	float moveTime = 0.5f;

	public StartScreen(RH2 rh2) {
		this.rh2 = rh2;
		stage = new Stage(new FillViewport(Constants.WIDTH, Constants.HEIGHT));

		black = new Image(Assets.getTex("black.png"));
		black.setFillParent(true);
		black.setColor(new Color(1, 1, 1, 0.8f));

		shopDialog = new ShopDialog();

		logo = new Image(Assets.getTex("logo.png"));
		logo.setPosition(Constants.WIDTH / 2 - logo.getWidth() / 2,
				Constants.HEIGHT - logo.getHeight() - 30);

		LabelStyle style = new LabelStyle();
		style.font = new BitmapFont(Gdx.files.internal("UI/Font/Font.fnt"));
		style.fontColor = Color.WHITE;
		startText = new Label("-TAP TO START-", style);
		startText.setPosition(Constants.WIDTH / 2 - startText.getPrefWidth() / 2, logo.getY()
				- startText.getPrefHeight() - 40);
		startText.addAction(Actions.moveBy(0, 15, moveTime / 2));
		addHoverActions();

		Image[] temp = { new Image(Assets.getTex("UI/Shop/Icon.png")),
				new Image(Assets.getTex("UI/Levels/Level 1/00.png")),
				new Image(Assets.getTex("UI/Levels/Level 2/01.png")) };
		items = temp;
		setupListeners();
		scroller = new Scroller(stage, items);

		stage.addActor(logo);
		stage.addActor(startText);

		Gdx.input.setInputProcessor(this);
	}

	public void setupListeners() {
		items[0].addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				shopDialog.show(stage);
			}
		});
		items[1].addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				rh2.setScreen(new Level1(rh2));
			}
		});
		items[2].addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				rh2.setScreen(new Level2(rh2));
			}

		});
	}

	@Override
	public void show() {

	}

	public void addHoverActions() {
		Interpolation interp = Interpolation.pow2;
		startText.addAction(Actions.sequence(Actions.moveBy(0, -15, moveTime, interp),
				Actions.moveBy(0, 15, moveTime, interp)));
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act(delta);
		stage.draw();

		if (startText.getActions().size == 0) {
			addHoverActions();
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

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	public void darkenScreen() {
		stage.addActor(black);
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		darkenScreen();
		Gdx.input
				.setInputProcessor(new InputMultiplexer(new GestureDetector(scroller), stage, this));
		stage.addActor(scroller);
		scroller.show();
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
