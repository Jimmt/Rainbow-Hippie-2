package com.jbs.rh2;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Rainbow extends HitboxActor {
	Image image;
	Player player;
	
	public Rainbow(Player player){
		super("rainbow");
		
		this.player = player;
		image = new Image(Assets.getTex("Effects/rainbow.png"));
	}
	
	public Player getPlayer(){
		return player;
	}
	
	@Override
	public void act(float delta){
		super.act(delta);
		
		image.act(delta);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha){
		super.draw(batch, parentAlpha);
		
		image.setPosition(getX(), getY() - image.getHeight() / 2);
		image.draw(batch, parentAlpha);
		
		setHitboxBounds(getX(), getY(), image.getWidth(), image.getHeight());
	}

}
