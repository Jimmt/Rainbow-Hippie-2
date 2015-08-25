package com.jbs.rh2;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class BossHealthBar extends Group {
	Image icon;
	Image empty, bar;
	Boss boss;

	boolean active;

	public BossHealthBar(Boss boss) {
		this.boss = boss;
		icon = new Image(Assets.getTex("Maps/" + boss.levelName + "/Boss/Icon.png"));
		empty = new Image(Assets.getTex("Maps/" + boss.levelName + "/Boss/Barempty.png"));
		bar = new Image(Assets.getTex("Maps/" + boss.levelName + "/Boss/HPBar.png"));

		empty.setPosition(icon.getWidth() - 50f, icon.getHeight() / 2 - empty.getHeight() / 2);
		bar.setPosition(empty.getX() + 20f, empty.getY() + empty.getHeight() / 2 - bar.getHeight() / 2 + 5f);

		addActor(empty);
		addActor(bar);
		addActor(icon);
	}

	@Override
	public float getHeight() {
		return icon.getHeight();
	}

	public void show() {
		active = true;
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		setVisible(active);
	}

}
