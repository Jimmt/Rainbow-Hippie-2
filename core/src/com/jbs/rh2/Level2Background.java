package com.jbs.rh2;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;

public class Level2Background extends Actor {
	Stage stage;

	SmallCloudGroup topClouds0, topClouds1, bottomClouds;
	WaveGroup waves0;
	WaveGroup waves1;
	WaveGroup waves2;
	WaveGroup waves3;
	WaveGroup[] waveGroups;
	Array<Image> hills;

	Level2Comparator comparator;
	Image light, background, sunImage;
	Group sun;

	float nextHill;

	public Level2Background(Stage stage) {
		setName("level2background");
		
		waves0 = new WaveGroup(0, stage);
		waves1 = new WaveGroup(1, stage);
		waves2 = new WaveGroup(2, stage);
		waves3 = new WaveGroup(3, stage);
		WaveGroup[] temp = { waves0, waves1, waves2, waves3 };
		waveGroups = temp;
		
		topClouds0 = new SmallCloudGroup("Maps/Waves Of Paradise/00.png",  "topClouds0", waves3.getY() + 60, stage);
		topClouds1 = new SmallCloudGroup("Maps/Waves Of Paradise/01.png", "topClouds1", waves3.getY() + 70, stage);
		bottomClouds = new SmallCloudGroup("Maps/Waves Of Paradise/01.png", "bottomClouds", -1, stage);
		
		hills = new Array<Image>();
		this.stage = stage;
		comparator = new Level2Comparator();

		sun = new Group();
		light = new Image(Assets.getTex("Effects/light.png"));
		light.setSize(Constants.WIDTH, Constants.HEIGHT);
		light.setPosition(-Constants.WIDTH / 2, -Constants.HEIGHT / 2);
		sun.addActor(light);
		sunImage = new Image(Assets.getTex("C:/Users/Austin/repos/rh2/android/assets/Maps/Waves Of Paradise/sun.png"));
		sunImage.setPosition(-sunImage.getWidth() / 2 + 10, -sunImage.getHeight() / 2 + 15);
		sun.addActor(sunImage);
		sun.setName("sun");
	
		background = new Image(Assets.getTex("white.png"));
		Color lighterColor = new Color(51 / 255f, 177 / 255f, 204 / 255f, 1.0f);
		Color darkerColor = new Color(54 / 255f, 154 / 255f, 174 / 255f, 1.0f);
		background.setColor(darkerColor);
		background.setSize(Constants.WIDTH + 10, Constants.HEIGHT);
		background.setName("background");

		stage.addActor(background);
		stage.addActor(sun);
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		float boundX = stage.getCamera().position.x + Constants.WIDTH / 2;

		checkHills(boundX);
		removeHills();
		
		for (int i = 0; i < waveGroups.length; i++) {
			waveGroups[i].act(delta);
		}
		topClouds0.act(delta);
		topClouds1.act(delta);
		bottomClouds.act(delta);

		background.setX(stage.getCamera().position.x - Constants.WIDTH / 2);
		sun.setPosition(stage.getCamera().position.x, stage.getCamera().position.y);

		stage.getActors().sort(comparator);
	}

	public void removeHills() {
		for (int i = 0; i < hills.size; i++) {
			Image hill = hills.get(i);
			if (hill.getX() + hill.getWidth() < stage.getCamera().position.x
					- Constants.WIDTH / 2) {
				stage.getActors().removeValue(hill, false);
				hills.removeIndex(i);
			}
		}
	}

	public void checkHills(float boundX) {
		if (boundX > nextHill) {
			Image hill = new Image(Assets.getTex("Maps/Waves of Paradise/04.png"));
			hill.setPosition(nextHill, Constants.HEIGHT - hill.getHeight()
					+ MathUtils.random(100f, 200f));
			nextHill += hill.getWidth() + MathUtils.random(-100, 500);
			hills.add(hill);
			stage.addActor(hill);
			hill.setName("hill");
		}
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);

	}
}
