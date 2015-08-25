package com.jbs.rh2;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class GameOverDialog extends Group {
	Label gameOverLabel, scoreLabel, bestScoreLabel;
	ImageButton replay, back;
	Stars stars;
	Image background;
	RH2 rh2;
	int level;

	int score;

	public GameOverDialog(RH2 rh2, int level) {
		background = new Image(Assets.getTex("UI/Endgame/window.png"));
		this.rh2 = rh2;
		this.level = level;
	}

	public void show(int score) {
		stars = new Stars(score);
		
		background.setPosition(Constants.WIDTH / 2 - background.getWidth() / 2, Constants.HEIGHT
				/ 2 - background.getHeight() / 2);

		gameOverLabel = new Label("GAME OVER", Assets.getLabelStyle());
		gameOverLabel.setPosition(
				background.getX() + background.getWidth() / 2 - gameOverLabel.getWidth() / 2,
				background.getY() + background.getHeight() - gameOverLabel.getHeight());
		scoreLabel = new Label(String.valueOf(score), Assets.getLabelStyle());
		scoreLabel.setPosition(
				background.getX() + background.getWidth() / 2 - scoreLabel.getWidth() / 2,
				gameOverLabel.getY() - 87);
		bestScoreLabel = new Label(String.valueOf(Prefs.prefs.getInteger("bestScore")),
				Assets.getLabelStyle());
		bestScoreLabel.setPosition(
				background.getX() + background.getWidth() / 2 - scoreLabel.getWidth() / 2,
				gameOverLabel.getY() - 156);

		ImageButtonStyle replayStyle = new ImageButtonStyle();
		replayStyle.up = new Image(Assets.getTex("UI/Endgame/replay_normal.png")).getDrawable();
		replayStyle.down = new Image(Assets.getTex("UI/Endgame/replay_pressed.png")).getDrawable();
		ImageButtonStyle backStyle = new ImageButtonStyle();
		backStyle.up = new Image(Assets.getTex("UI/Endgame/back_normal.png")).getDrawable();
		backStyle.down = new Image(Assets.getTex("UI/Endgame/back_pressed.png")).getDrawable();
		replay = new ImageButton(replayStyle);
		replay.setPosition(background.getX() + 52, background.getY() - 20);
		back = new ImageButton(backStyle);
		back.setPosition(background.getX() + 271, background.getY() - 20);

		setupListeners();
		
		stars.setPosition(background.getX() + background.getWidth() / 2 - stars.getWidth() / 2, background.getY() + 60f);

		addActor(background);
		addActor(gameOverLabel);
		addActor(scoreLabel);
		addActor(bestScoreLabel);
		addActor(stars);
		addActor(replay);
		addActor(back);

		Actor[] actors = { background, gameOverLabel, scoreLabel, bestScoreLabel, replay, back };
		for (Actor actor : actors) {
			if (actor != null) {
				actor.addAction(Actions.sequence(Actions.alpha(0),
						Actions.fadeIn(0.4f, Interpolation.fade)));
			}
		}
	}

	public void setupListeners() {
		replay.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				SoundManager.play("button");
				if (level == 1) {
					rh2.setScreen(new Level1(rh2));
				}
				if (level == 2) {
					rh2.setScreen(new Level2(rh2));
				}
				if (level == 3) {

				}
			}
		});
		back.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				SoundManager.play("button");
				rh2.setScreen(new StartScreen(rh2));
			}
		});
	}

	public void setScore(int score) {
		this.score = score;

		if (score > Prefs.prefs.getInteger("bestScore")) {
			Prefs.prefs.putInteger("bestScore", score);
			SoundManager.play("highscore", 0.25f);
		}
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
	}

	@Override
	public void act(float delta) {
		super.act(delta);

	}

}
