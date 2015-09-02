package com.jbs.rh2;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;

public class SmallCloudGroup extends Actor {
	Array<Image> clouds;
	String path, name;
	Stage stage;
	float nextCloud = 0, velocity = 0, height;

	public SmallCloudGroup(String path, String name, float height, Stage stage) {
		clouds = new Array<Image>();
		
		this.path = path;
		this.height = height;
		this.stage = stage;
		this.name = name;

		for (int i = 0; i < 2; i++) {
			Image cloud = new Image(Assets.getTex(path));
			stage.addActor(cloud);
			cloud.setPosition(i * cloud.getWidth(), height);
			cloud.setName("cloud" + height);
			clouds.add(cloud);
		}

		setVelocity(MathUtils.random(-2f, 2f));
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
			cloud.setPosition(leftBoundX - cloud.getWidth(), height);
			cloud.setName(name);
			clouds.insert(0, cloud);
		}
		if (clouds.get(clouds.size - 1).getX() + clouds.get(clouds.size - 1).getWidth() <= rightBoundX) {
			Image cloud = new Image(Assets.getTex(path));
			stage.addActor(cloud);
			cloud.setPosition(rightBoundX - 10, height);
			cloud.setName(name);
			clouds.add(cloud);

		}

		for (Image image : clouds) {
			image.setPosition(image.getX() + velocity, image.getY());
		}

		removeClouds();
	}
}
