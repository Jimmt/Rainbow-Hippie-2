package com.jbs.rh2;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class GameOverDialog extends Group {
	Label gameOverLabel, scoreLabel, bestScoreLabel;
	ImageButton replay, back;
	Image background;

	int score;

	public GameOverDialog() {
		background = new Image(Assets.getTex("UI/Endgame/window.png"));

	}

	public void show() {
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
		replay.setPosition(background.getX() + 52, background.getY());
		back = new ImageButton(backStyle);
		back.setPosition(background.getX() + 271, background.getY());

		addActor(background);
		addActor(gameOverLabel);
		addActor(scoreLabel);
		addActor(bestScoreLabel);
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

	public void setScore(int score) {
		this.score = score;

		if (score > Prefs.prefs.getInteger("bestScore")) {
			Prefs.prefs.putInteger("bestScore", score);
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
