package com.jbs.rh2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class Level1 extends LevelScreen {
	Level1Background background;

	public Level1(RH2 rh2) {
		super(rh2);

		background = new Level1Background(stage);
		stage.addActor(background);
		
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render(float delta) {
		super.render(delta);
	}

}
