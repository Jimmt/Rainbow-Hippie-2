package com.jbs.rh2;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class GameOverDialog extends Dialog {
	Label gameOver, score, bestScore;
	ImageButton replay, back;

	public GameOverDialog() {
		super("", Assets.getSkin());

		Image background = new Image(Assets.getTex("UI/Endgame/window.png"));
		background(background.getDrawable());
	}

	@Override
	public Dialog show(Stage stage) {
		super.show(stage);
		gameOver = new Label("GAME OVER", Assets.getLabelStyle());
		gameOver.setPosition(getX() + getWidth() / 2 - gameOver.getWidth() / 2, getY()
				+ getHeight() - gameOver.getHeight());
		stage.addActor(gameOver);

		Actor[] actors = { gameOver, score, bestScore, replay, back };
		for (Actor actor : actors) {
			if (actor != null) {
				actor.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(0.4f, Interpolation.fade)));
			}
		}
		return this;
	}

}
