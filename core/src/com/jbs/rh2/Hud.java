package com.jbs.rh2;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Hud extends Group {
	ImageButton pause, sound;
	ImageButtonStyle pauseStyle, soundOnStyle, soundOffStyle, playStyle;

	public Hud() {

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

		setupListeners();

		float margin = 20;
		sound.setPosition(Constants.WIDTH - sound.getWidth() - margin,
				Constants.HEIGHT - sound.getHeight() - margin);
		pause.setPosition(sound.getX() - sound.getWidth() - margin, sound.getY());

		addActor(pause);
		addActor(sound);
	}

	public void setupListeners() {
		pause.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {

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
