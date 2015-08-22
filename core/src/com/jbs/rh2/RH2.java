package com.jbs.rh2;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class RH2 extends Game {
	static boolean DEBUG;

	@Override
	public void create() {
//		DEBUG = true;

		if (DEBUG) {
			setScreen(new StartScreen(this));
		} else {
			setScreen(new SplashScreen(this));
		}

	}

}
