package com.jbs.rh2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ObjectMap.Entries;
import com.badlogic.gdx.utils.ObjectMap;

public class SoundManager {
	static ObjectMap<String, Sound> sounds;
	static ObjectMap<String, Music> musics;

	private static boolean play = true;

	public static void init() {
		sounds = new ObjectMap<String, Sound>();
		musics = new ObjectMap<String, Music>();
	}

	public static void play(String name) {
		if (play)
			((Sound) sounds.get(name)).play();
	}

	public static void playMusic(String name) {
		((Music) musics.get(name)).setVolume(1f);
		if (play) {
			Entries<String, Music> entries = musics.entries();
			while (entries.hasNext()) {
				entries.next().value.pause();
			}
			((Music) musics.get(name)).play();
		}
	}

	public static void stopMusic(String name) {
		if (play)
			((Music) musics.get(name)).stop();
	}

	public static void play(String name, float volume) {
		if (play)
			((Sound) sounds.get(name)).play(volume);
	}

	public static void playRandom(String name, float volume, float min, float max) {
		if (play)
			((Sound) sounds.get(name)).play(volume, min + MathUtils.random(max - min), 0);
	}

	public static void playMusic(String name, float volume) {
		if (play) {
			Entries<String, Music> entries = musics.entries();
			while (entries.hasNext()) {
				entries.next().value.pause();
			}
			((Music) musics.get(name)).setVolume(volume);
			((Music) musics.get(name)).play();
		}
	}

	public static void loop(String name) {
		if (play)
			((Sound) sounds.get(name)).loop();
	}

	public static void loadSound(String name, String path) {
		Sound sound = Gdx.audio.newSound(Gdx.files.internal(path));
		sounds.put(name, sound);
	}

	public static void loadMusic(String name, String path) {
		Music music = Gdx.audio.newMusic(Gdx.files.internal(path));
		musics.put(name, music);
	}

	public static void setPlay(boolean play) {
		SoundManager.play = play;

		if (!play) {
			Entries<String, Music> entries = musics.entries();
			while (entries.hasNext()) {
				entries.next().value.pause();
			}
		}
	}

	public static boolean getPlay() {
		return play;
	}
}
