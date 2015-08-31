package com.jbs.rh2;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class BossRainbow extends Actor {
	Image image;
	Polygon polygon, rectPolygon;

	public BossRainbow() {
		image = new Image(Assets.getTex("Effects/rainbow_boss.png"));
		setSize(image.getWidth(), image.getHeight());

		float[] vertices = { 0, 0, image.getWidth(), 0, image.getWidth(), image.getHeight(), 0,
				image.getHeight() };
		polygon = new Polygon(vertices);
		rectPolygon = new Polygon();
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		image.setSize(getWidth(), getHeight());
		image.setPosition(getX(), getY());
		image.setOrigin(getOriginX(), getOriginY());
		image.setRotation(getRotation());
		image.setColor(getColor());
		image.act(delta);

		float[] vertices = { getX(), getY(), getX() + image.getWidth(), getY(),
				getX() + image.getWidth(), getY() + image.getHeight(), getX(),
				getY() + image.getHeight() };
		polygon.setVertices(vertices);
		polygon.setOrigin(getX() + getOriginX(), getY() + getOriginY());
		polygon.setRotation(getRotation());
	}

	public boolean overlaps(Rectangle rectangle) {
		
		rectPolygon.setVertices(new float[] { rectangle.getX(), rectangle.getY(),
				rectangle.getX() + rectangle.getWidth(), rectangle.getY(),
				rectangle.getX() + rectangle.getWidth(), rectangle.getY() + rectangle.getHeight(),
				rectangle.getX(), rectangle.getY() + rectangle.getHeight() });

		return Intersector.overlapConvexPolygons(rectPolygon, polygon);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);

		image.draw(batch, parentAlpha);
	}

}
