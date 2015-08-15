package com.jbs.rh2;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;

public class CloudGroup extends Actor {
	Array<Image> clouds;
	String path;
	int number;
	Stage stage;
	float nextCloud = 0, velocity = 0;

	public CloudGroup(int number, Stage stage) {
		clouds = new Array<Image>();
		this.path = "Maps/Forever Land Of Happiness/0" + number + ".png";
		this.number = number;
		this.stage = stage;

		for (int i = 0; i < 2; i++) {
			Image cloud = new Image(Assets.getTex(path));
			stage.addActor(cloud);
			cloud.setPosition(i * cloud.getWidth(), number);
			cloud.setName("cloud" + number);
			clouds.add(cloud);
		}

		setVelocity(MathUtils.random(-1f, 1f));
	}

	public void removeClouds() {
		if (velocity > 0) {
			Image cloud = clouds.get(clouds.size - 1);
			if (cloud.getX() > stage.getCamera().position.x + Constants.WIDTH / 2) {
				stage.getActors().removeValue(cloud, false);
				clouds.removeIndex(clouds.size - 1);
			}
			cloud = clouds.get(0);
			if (cloud.getX() + cloud.getWidth() < stage.getCamera().position.x - Constants.WIDTH) {
				stage.getActors().removeValue(cloud, false);
				clouds.removeIndex(0);
			}
		} else {
			Image cloud = clouds.get(0);
			if (cloud.getX() + cloud.getWidth() < stage.getCamera().position.x - Constants.WIDTH
					/ 2) {
				stage.getActors().removeValue(cloud, false);
				clouds.removeIndex(0);
			}
		}
	}

	public void setVelocity(float velocity) {
		this.velocity = velocity;
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		float rightBoundX = stage.getCamera().position.x + Constants.WIDTH / 2;
		float leftBoundX = stage.getCamera().position.x - Constants.WIDTH / 2;

		if (clouds.get(0).getX() >= leftBoundX) {
			Image cloud = new Image(Assets.getTex(path));
			stage.addActor(cloud);
			cloud.setPosition(leftBoundX - cloud.getWidth(), number);
			cloud.setName("cloud" + number);
			clouds.insert(0, cloud);
		}
		if (clouds.get(clouds.size - 1).getX() + clouds.get(clouds.size - 1).getWidth() <= rightBoundX) {
			Image cloud = new Image(Assets.getTex(path));
			stage.addActor(cloud);
			cloud.setPosition(rightBoundX - 10, number);
			cloud.setName("cloud" + number);
			clouds.add(cloud);

		}

		for (Image image : clouds) {
			image.setPosition(image.getX() + velocity, image.getY());
		}

		removeClouds();
	}
}
