package com.jbs.rh2;

import java.util.HashMap;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Assets {
	static HashMap<String, Texture> textureCache = new HashMap<String, Texture>();
	static Skin skin;

	public static Skin getSkin() {
		if (skin == null) {
			skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
		}
		return skin;
	}

	public static Texture getTex(String path) {
		return getTex(path, FileType.Internal);
	}

	public static Texture getTex(String path, FileType fileType) {
		if (textureCache.containsKey(path))
			return textureCache.get(path);

		Texture tex = new Texture(Gdx.files.getFileHandle(path, fileType));
		tex.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		textureCache.put(path, tex);
		return tex;
	}

}
