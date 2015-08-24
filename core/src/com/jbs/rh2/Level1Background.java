package com.jbs.rh2;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;

public class Level1Background extends Actor {
	Stage stage;

	CloudGroup clouds0;
	CloudGroup clouds1;
	CloudGroup clouds2;
	CloudGroup clouds3;
	CloudGroup clouds4;
	CloudGroup[] cloudGroups;
	Array<Image> topUpperMountains;
	Array<Image> topLowerMountains;
	Array<Image> bottomMountains;

	YComparator comparator;

	Image light;
	Image background;

	float nextTopUpperMountain, nextTopLowerMountain, nextBottomMountain, lastOffset;

	public Level1Background(Stage stage) {
		clouds0 = new CloudGroup(0, stage);
		clouds1 = new CloudGroup(1, stage);
		clouds2 = new CloudGroup(2, stage);
		clouds3 = new CloudGroup(3, stage);
		clouds4 = new CloudGroup(4, stage);

		CloudGroup[] temp = { clouds0, clouds1, clouds2, clouds3, clouds4 };
		cloudGroups = temp;

		topUpperMountains = new Array<Image>();
		topLowerMountains = new Array<Image>();
		bottomMountains = new Array<Image>();

		this.stage = stage;

		nextTopUpperMountain = MathUtils.random(-258, -200);
		nextTopLowerMountain = nextTopUpperMountain + MathUtils.random(100) - 40f;

		comparator = new YComparator();

		light = new Image(Assets.getTex("Effects/light.png"));
		light.setSize(Constants.WIDTH, Constants.HEIGHT);
		light.setName("light");
		background = new Image(Assets.getTex("white.png"));
		Color lighterColor = new Color(51 / 255f, 177 / 255f, 204 / 255f, 1.0f);
		Color darkerColor = new Color(54 / 255f, 154 / 255f, 174 / 255f, 1.0f);
		background.setColor(darkerColor);
		background.setSize(Constants.WIDTH + 10, Constants.HEIGHT);
		background.setName("background");

		stage.addActor(background);
		stage.addActor(light);
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		float boundX = stage.getCamera().position.x + Constants.WIDTH / 2;

		checkMountains(boundX);
		removeMountains();

		for (int i = 0; i < cloudGroups.length; i++) {
			cloudGroups[i].act(delta);
		}

		light.setX(stage.getCamera().position.x - Constants.WIDTH / 2);
		background.setX(stage.getCamera().position.x - Constants.WIDTH / 2);

		stage.getActors().sort(comparator);
	}

	public void removeMountains() {
		for (int i = 0; i < topUpperMountains.size; i++) {
			Image mountain = topUpperMountains.get(i);
			if (mountain.getX() + mountain.getWidth() < stage.getCamera().position.x
					- Constants.WIDTH / 2) {
				stage.getActors().removeValue(mountain, false);
				topUpperMountains.removeIndex(i);
			}
		}
		for (int i = 0; i < topLowerMountains.size; i++) {
			Image mountain = topLowerMountains.get(i);
			if (mountain.getX() + mountain.getWidth() < stage.getCamera().position.x
					- Constants.WIDTH / 2) {
				stage.getActors().removeValue(mountain, false);
				topLowerMountains.removeIndex(i);
			}
		}
		for (int i = 0; i < bottomMountains.size; i++) {
			Image mountain = bottomMountains.get(i);
			if (mountain.getX() + mountain.getWidth() < stage.getCamera().position.x
					- Constants.WIDTH / 2) {
				stage.getActors().removeValue(mountain, false);
				bottomMountains.removeIndex(i);
			}
		}
	}

	public void checkMountains(float boundX) {
		if (boundX > nextTopUpperMountain) {
			Image mountain = new Image(Assets.getTex("Maps/Forever Land Of Happiness/06.png"));
			mountain.setPosition(nextTopUpperMountain, Constants.HEIGHT - mountain.getHeight()
					- MathUtils.random(10f, 50f));
			lastOffset = MathUtils.random(150, 300);
			nextTopUpperMountain += lastOffset;
			topUpperMountains.add(mountain);
			stage.addActor(mountain);
			mountain.setName("mountain_top");
		}
		if (boundX > nextTopLowerMountain) {
			Image mountain = new Image(Assets.getTex("Maps/Forever Land Of Happiness/06.png"));
			mountain.setPosition(nextTopUpperMountain, Constants.HEIGHT - mountain.getHeight()
					- MathUtils.random(60f, 100f));
			nextTopUpperMountain += lastOffset;
			topLowerMountains.add(mountain);
			nextTopLowerMountain += lastOffset + MathUtils.random(100) - 40f;
			stage.addActor(mountain);
			mountain.setName("mountain_top");
		}
		if (boundX > nextBottomMountain) {
			Image mountain = new Image(Assets.getTex("Maps/Forever Land Of Happiness/05.png"));
			float y = MathUtils.random(-mountain.getHeight() / 2, mountain.getHeight() / 2);
			mountain.setPosition(nextTopUpperMountain, y);
			lastOffset = MathUtils.random(200, 500);
			nextTopUpperMountain += lastOffset;
			bottomMountains.add(mountain);
			stage.addActor(mountain);
			mountain.setName("mountain_bottom");
		}
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);

	}

}
