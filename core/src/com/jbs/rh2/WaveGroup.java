package com.jbs.rh2;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;

public class WaveGroup extends Actor {
	Array<Image> waves;
	String path;
	int number;
	Stage stage;
	float nextWave = 0, velocity = 0;

	public WaveGroup(int number, Stage stage) {
		waves = new Array<Image>();
		this.path = "Maps/Waves of Paradise/02.png";
		this.number = number;
		this.stage = stage;
		
		for (int i = 0; i < 2; i++) {
			Image wave = new Image(Assets.getTex(path));
			setY(number * wave.getHeight() * 0.6f);
			stage.addActor(wave);
			wave.setPosition(i * wave.getWidth(), number * wave.getHeight() * 0.6f);
			wave.setName("wave" + number);
			waves.add(wave);
		}

		setVelocity(MathUtils.random(-2f, 2f));
	}

	public void removeWaves() {
		if (velocity > 0) {
			Image wave = waves.get(waves.size - 1);
			if (wave.getX() > stage.getCamera().position.x + Constants.WIDTH / 2) {
				stage.getActors().removeValue(wave, false);
				waves.removeIndex(waves.size - 1);
			}
			wave = waves.get(0);
			if (wave.getX() + wave.getWidth() < stage.getCamera().position.x - Constants.WIDTH) {
				stage.getActors().removeValue(wave, false);
				waves.removeIndex(0);
			}
		} else {
			Image wave = waves.get(0);
			if (wave.getX() + wave.getWidth() < stage.getCamera().position.x - Constants.WIDTH
					/ 2) {
				stage.getActors().removeValue(wave, false);
				waves.removeIndex(0);
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

		if (waves.get(0).getX() >= leftBoundX) {
			Image wave = new Image(Assets.getTex(path));
			stage.addActor(wave);
			wave.setPosition(leftBoundX - wave.getWidth(), number * wave.getHeight() * 0.6f);
			wave.setName("wave" + number);
			waves.insert(0, wave);
		}
		if (waves.get(waves.size - 1).getX() + waves.get(waves.size - 1).getWidth() <= rightBoundX) {
			Image wave = new Image(Assets.getTex(path));
			stage.addActor(wave);
			wave.setPosition(rightBoundX - 5, number * wave.getHeight() * 0.6f);
			wave.setName("wave" + number);
			waves.add(wave);

		}

		for (Image image : waves) {
			image.setPosition(image.getX() + velocity, image.getY());
		}

		removeWaves();
	}
}
