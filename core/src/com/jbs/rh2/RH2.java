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
		DEBUG = true;

		Prefs.init();
		SoundManager.init();
		SoundManager.setPlay(false); //FOR DEBUGGING, RE-ENABLE
		
		SoundManager.loadSound("death", "SFX/hippie/death.wav");
		SoundManager.loadSound("point", "SFX/hippie/point.wav");
		SoundManager.loadSound("shoot", "SFX/hippie/shoot.wav");
		SoundManager.loadSound("tap", "SFX/hippie/tap.mp3");
		
		SoundManager.loadSound("buy", "SFX/shop/Buy.wav");
		SoundManager.loadSound("cha ching", "SFX/shop/cha ching.wav");
		SoundManager.loadSound("error", "SFX/shop/error.wav");
		SoundManager.loadSound("scroll", "SFX/shop/Scroll.wav");
		
		SoundManager.loadSound("button", "SFX/ui/buttonclick.wav");
		SoundManager.loadSound("gameover", "SFX/ui/gameover.wav");
		SoundManager.loadSound("highscore", "SFX/ui/highscore.wav");
		SoundManager.loadSound("swipe1", "SFX/ui/swipe1.wav");
		SoundManager.loadSound("swipe2", "SFX/ui/swipe2.wav");
		SoundManager.loadSound("swipe3", "SFX/ui/swipe3.wav");
		SoundManager.loadSound("win", "SFX/ui/win.wav");
		
		SoundManager.loadMusic("music", "SFX/music/music.wav");
		
		if (DEBUG) {
			setScreen(new StartScreen(this));
		} else {
			setScreen(new SplashScreen(this));
		}

	}

}
