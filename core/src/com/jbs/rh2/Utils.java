package com.jbs.rh2;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.utils.Array;

public class Utils {

	public static Animation getAnimation(String path, float time, int width, int height,
			PlayMode mode) {
		TextureRegion[][] regionsArr = TextureRegion.split(Assets.getTex(path), width, height);

		Array<TextureRegion> regions = new Array<TextureRegion>();
		for (int i = 0; i < regionsArr.length; i++) {
			for (int j = 0; j < regionsArr[i].length; j++) {
				regions.add(regionsArr[i][j]);
			}
		}

		Animation animation = new Animation(time, regions, mode);
		return animation;
	}

	public static Animation getAnimation(String path, float time, int width, int height,
			int maxRegions, PlayMode mode) {
		TextureRegion[][] regionsArr = TextureRegion.split(Assets.getTex(path), width, height);
		int count = 0;
		Array<TextureRegion> regions = new Array<TextureRegion>();

		for (int i = 0; i < regionsArr.length; i++) {
			for (int j = 0; j < regionsArr[i].length; j++) {
				if (count < maxRegions) {
					regions.add(regionsArr[i][j]);
				}
				count++;
			}
		}
		
		regions.add(regionsArr[1][1]);

		Animation animation = new Animation(time, regions, mode);
		return animation;
	}
}
