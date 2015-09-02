package com.jbs.rh2;

import java.util.Comparator;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class Level2Comparator implements Comparator<Actor> {

	@Override
	public int compare(Actor a, Actor b) {
		float aY = a.getY();
		float bY = b.getY();

		try {
			String nameA = a.getName();
			String nameB = b.getName();

			if (nameA.equals("background")) {
				return -1;
			}
			if (nameB.equals("background")) {
				return 1;
			}
			
			if(nameA.equals("sun") && !(nameB.equals("hill") || nameB.equals("background"))){
				return -1;
			}
			if(nameB.equals("sun") && !(nameA.equals("hill") || nameA.equals("background"))){
				return 1;
			}
			
			if (nameA.equals("topClouds0") && nameB.equals("topClouds1")) {
				return -1;
			}
			if (nameB.equals("topClouds0") && nameA.equals("topClouds1")) {
				return 1;
			}

			if (nameA.equals("hill") && nameB.startsWith("topClouds")) {
				return -1;
			}
			if (nameB.equals("hill") && nameA.startsWith("topClouds")) {
				return 1;
			}

		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}

		
		if (aY > bY) {
			return -1;
		} else {
			return 1;
		}
	}

}
