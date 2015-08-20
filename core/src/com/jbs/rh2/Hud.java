package com.jbs.rh2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Hud extends Group {
	ImageButton pause, sound;
	ImageButtonStyle pauseStyle, soundOnStyle, soundOffStyle, playStyle;
	
	Image balloonImage;
	Label score;
	
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
		LabelStyle style = new LabelStyle();
		style.font = new BitmapFont(Gdx.files.internal("UI/Font/Font.fnt"));
		style.fontColor = Color.WHITE;
		score = new Label(String.valueOf(screen.score), style);

		float margin = 10;
		balloonImage.setPosition(margin, Constants.HEIGHT - balloonImage.getHeight() - margin);
		score.setPosition(balloonImage.getX() + balloonImage.getWidth() + margin, balloonImage.getY() + balloonImage.getHeight() * 0.57f - score.getHeight() / 2f);

		
		setupListeners();
		
		
		margin = 20;
		pause.setPosition(Constants.WIDTH - pause.getWidth() - margin,
				Constants.HEIGHT - pause.getHeight() - margin);
		sound.setPosition(pause.getX() - pause.getWidth() - margin, pause.getY());

		
		addActor(pause);
		addActor(sound);
		addActor(balloonImage);
		addActor(score);
	}
	
	@Override
	public void act(float delta){
		super.act(delta);
		
		score.setText(String.valueOf(screen.score));
	}

	public void setupListeners() {
		pause.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				screen.pause();
				
				if(screen.paused){
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
