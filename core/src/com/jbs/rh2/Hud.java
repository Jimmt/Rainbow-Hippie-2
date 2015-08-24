package com.jbs.rh2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;

public class Hud extends Group {
	ImageButton pause, sound;
	ImageButtonStyle pauseStyle, soundOnStyle, soundOffStyle, playStyle;

	Image balloonImage;
	Label score;

	Array<Image> splatImages;
	boolean test;
	LevelScreen screen;

	public Hud(LevelScreen screen) {
		this.screen = screen;

		pauseStyle = new ImageButtonStyle();
		pauseStyle.up = new Image(Assets.getTex("UI/Ingame/pause_normal.png")).getDrawable();
		pauseStyle.down = new Image(Assets.getTex("UI/Ingame/pause_pressed.png")).getDrawable();
		soundOnStyle = new ImageButtonStyle();
		soundOnStyle.up = new Image(Assets.getTex("UI/Ingame/soundon_normal.png")).getDrawable();
		soundOnStyle.down = new Image(Assets.getTex("UI/Ingame/soundon_pressed.png")).getDrawable();
		soundOffStyle = new ImageButtonStyle();
		soundOffStyle.up = new Image(Assets.getTex("UI/Ingame/soundoff_normal.png")).getDrawable();
		soundOffStyle.down = new Image(Assets.getTex("UI/Ingame/soundoff_pressed.png"))
				.getDrawable();
		playStyle = new ImageButtonStyle();
		playStyle.up = new Image(Assets.getTex("UI/Ingame/play_normal.png")).getDrawable();
		playStyle.down = new Image(Assets.getTex("UI/Ingame/play_pressed.png")).getDrawable();

		pause = new ImageButton(pauseStyle);
		sound = new ImageButton(soundOnStyle);

		balloonImage = new Image(Assets.getTex("UI/Popped/00.png"));
		score = new Label(String.valueOf(screen.score), Assets.getLabelStyle());

		float margin = 10;
		balloonImage.setPosition(margin, Constants.HEIGHT - balloonImage.getHeight() - margin);
		score.setPosition(balloonImage.getX() + balloonImage.getWidth() + margin,
				balloonImage.getY() + balloonImage.getHeight() * 0.57f - score.getHeight() / 2f);

		setupListeners();

		margin = 20;
		pause.setPosition(Constants.WIDTH - pause.getWidth() - margin,
				Constants.HEIGHT - pause.getHeight() - margin);
		sound.setPosition(pause.getX() - pause.getWidth() - margin, pause.getY());

		addActor(pause);
		addActor(sound);
		addActor(balloonImage);
		addActor(score);

		splatImages = new Array<Image>();
	}

	Action removeAction = new Action() {

		@Override
		public boolean act(float delta) {
			splatImages.removeValue((Image) this.getActor(), false);
			return true;
		}

	};

	public void splatScreen() {
		float margin = 30;
		int x = MathUtils.random(10, 11);
		int y = MathUtils.random(6, 7);
		
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				Image splat = new Image(Assets.getTex("Effects/Splat/0" + (int) (Math.random() * 5)
						+ "_Splat.png"));
				splat.setPosition(margin + i * (splat.getWidth() * 0.83f - MathUtils.random(20, 40)),
						margin + j * (splat.getHeight() * 0.83f - MathUtils.random(10, 30)));
				float time = MathUtils.random(2.5f, 3.5f);
				splat.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(0.2f),
						Actions.delay(time), Actions.fadeOut(0.4f), removeAction));
				splatImages.add(splat);
			}
		}
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);

		if (Gdx.input.isKeyPressed(Keys.S) && !test) {
			splatScreen();
			test = true;
		}

		for (Image image : splatImages) {
			image.draw(batch, parentAlpha);
		}
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		score.setText(String.valueOf(screen.score));

		for (Image image : splatImages) {
			image.act(delta);
		}
	}

	public void setupListeners() {
		pause.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				screen.pause();

				if (screen.paused) {
					pause.setStyle(playStyle);
				} else {
					pause.setStyle(pauseStyle);
				}
			}
		});
		sound.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (sound.getStyle().equals(soundOnStyle)) {
					sound.setStyle(soundOffStyle);
				} else {
					sound.setStyle(soundOnStyle);
				}
			}
		});
	}

}
