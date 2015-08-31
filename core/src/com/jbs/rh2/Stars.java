package com.jbs.rh2;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;

public class Stars extends Group {
	ImageButton[] stars = new ImageButton[3];

	public Stars(int score) {
		ImageButtonStyle style = new ImageButtonStyle();
		style.up = new Image(Assets.getTex("UI/Stars/00.png")).getDrawable();
		style.checked = new Image(Assets.getTex("UI/Stars/01.png")).getDrawable();

		for (int i = 0; i < 3; i++) {
			stars[i] = new ImageButton(style);
			stars[i].setChecked(true);
			stars[i].setTouchable(Touchable.disabled);
			addActor(stars[i]);
		}

		float scale = 1.5f;

		stars[0].setSize(stars[0].getWidth() / scale, stars[0].getHeight() / scale);
		stars[0].setPosition(0, 0);
		stars[1].setPosition(stars[0].getWidth() / scale + 15, 0);
		stars[2].setSize(stars[2].getWidth() / scale, stars[2].getHeight() / scale);
		stars[2].setPosition(stars[1].getX() + stars[1].getWidth() - 28, 0);

		setWidth(stars[2].getX() + stars[2].getWidth());

	}

}
