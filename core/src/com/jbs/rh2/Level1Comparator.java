package com.jbs.rh2;

import java.util.Comparator;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class Level1Comparator implements Comparator<Actor> {
	float[] cloudBounds = { 0, 177, 301, 353, 467, 643 };

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
			if (nameA.equals("light")) {
				return -1;
			}
			if (nameB.equals("light")) {
				return 1;
			}

			if (nameA.startsWith("cloud") && nameB.equals("mountain_bottom")) {
				int index = Integer.parseInt(nameA.substring(5, 6));
				aY = cloudBounds[index];
				bY += b.getHeight() / 2;
			}
			if (nameB.startsWith("cloud") && nameA.equals("mountain_bottom")) {
				int index = Integer.parseInt(nameB.substring(5, 6));
				bY = cloudBounds[index];
				aY += a.getHeight() / 2;
			}

		} catch (NullPointerException ex) {

		}

		if (aY > bY) {
			return -1;
		} else {
			return 1;
		}
	}

}
