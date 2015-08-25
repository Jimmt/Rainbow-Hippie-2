package com.jbs.rh2;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Level1Boss extends Boss {
	LevelScreen screen;
	Image unicorn;
	boolean active;

	public Level1Boss(LevelScreen screen) {
		super("Forever Land of Happiness");
		unicorn = new Image(Assets.getTex("Maps/Forever Land of Happiness/Boss/Normal.png"));
		
		float width = (unicorn.getWidth() / unicorn.getHeight()) * Constants.HEIGHT;
		unicorn.setSize(width, Constants.HEIGHT);
		this.screen = screen;
		unicorn.addAction(Actions.moveTo(screen.stage.getCamera().position.x + Constants.WIDTH / 2,
				Constants.HEIGHT / 2 - unicorn.getHeight() / 2));
		addActor(unicorn);
	}

	public void show() {
		active = true;
		unicorn.addAction(Actions.sequence(Actions.moveBy(-unicorn.getWidth(), 0, 1f)));
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		unicorn.setX(unicorn.getX() + screen.cameraSpeed);
		unicorn.setVisible(active);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
	}

}
