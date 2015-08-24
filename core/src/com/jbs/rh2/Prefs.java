package com.jbs.rh2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class Prefs {
	static Preferences prefs;
	
	public static void init(){
		prefs = Gdx.app.getPreferences("prefs");
		
		if(prefs.contains("bestScore")){
			prefs.putInteger("bestScore", 0);
		}
	}
}
