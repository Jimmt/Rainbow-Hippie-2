package com.jbs.rh2;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;

public class Level1Background extends Actor {
	Stage stage;

	Array<Image> clouds1; // bottom
	Array<Image> clouds2;
	Array<Image> clouds3;
	Array<Image> clouds4;
	Array<Image> topUpperMountains;
	Array<Image> topLowerMountains;
	Array<Image> bottomMountains;

	YComparator comparator;
	
	float[] regions;

	float nextTopUpperMountain, nextTopLowerMountain, nextBottomMountain, lastOffset;
	float nextCloud0, nextCloud1, nextCloud2, nextCloud3, nextCloud4;

	public Level1Background(Stage stage) {
		clouds1 = new Array<Image>();
		clouds2 = new Array<Image>();
		clouds3 = new Array<Image>();
		clouds4 = new Array<Image>();
		topUpperMountains = new Array<Image>();
		topLowerMountains = new Array<Image>();
		bottomMountains = new Array<Image>();

		this.stage = stage;

		nextTopUpperMountain = MathUtils.random(-258, -200);
		nextTopLowerMountain = nextTopUpperMountain + MathUtils.random(100) - 40f;

		comparator = new YComparator();

		generate();
	}

	public void generate() {
		// 40 - 80 vertical
		// 50-200 horizontal

	}

	@Override
	public void act(float delta) {
		super.act(delta);

		float boundX = stage.getCamera().position.x + Constants.WIDTH / 2;

		checkMountains(boundX);
		checkClouds(boundX);
		
		

		stage.getActors().sort(comparator);
	}

	public void checkClouds(float boundX) {
		if (boundX > nextCloud0) {
			Image cloud = new Image(Assets.getTex("Maps/Forever Land Of Happiness/00.png"));
			cloud.setPosition(nextCloud0, 0);
			cloud.setName("cloud0");
			nextCloud0 += cloud.getWidth();
			stage.addActor(cloud);
		}
		if (boundX > nextCloud1) {
			Image cloud = new Image(Assets.getTex("Maps/Forever Land Of Happiness/01.png"));
			cloud.setPosition(nextCloud1, 1);
			stage.addActor(cloud);
			cloud.setName("cloud1");
			nextCloud1 += cloud.getWidth();
		}
		if (boundX > nextCloud2) {
			Image cloud = new Image(Assets.getTex("Maps/Forever Land Of Happiness/02.png"));
			cloud.setPosition(nextCloud2, 2);
			stage.addActor(cloud);
			cloud.setName("cloud2");
			nextCloud2 += cloud.getWidth();
		}
		if (boundX > nextCloud3) {
			Image cloud = new Image(Assets.getTex("Maps/Forever Land Of Happiness/03.png"));
			cloud.setPosition(nextCloud3, 3);
			stage.addActor(cloud);
			cloud.setName("cloud3");
			nextCloud3 += cloud.getWidth();
		}
		if (boundX > nextCloud4) {
			Image cloud = new Image(Assets.getTex("Maps/Forever Land Of Happiness/04.png"));
			cloud.setPosition(nextCloud4, 4);
			stage.addActor(cloud);
			cloud.setName("cloud4");
			nextCloud4 += cloud.getWidth();
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
			topUpperMountains.add(mountain);
			stage.addActor(mountain);
			mountain.setName("mountain_bottom");
		}
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);

	}

}
