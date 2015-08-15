package com.jbs.rh2;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;

public class Scroller extends Actor implements GestureListener {
	Stage stage;
	Array<Image> items;
	Image currentSelection;
	int currentIndex;
	float margin = 100, itemWidth = 0;
	boolean first = true;

	public Scroller(Stage stage, Image... items) {
		this.items = new Array<Image>();
		this.stage = stage;

		for (int i = 0; i < items.length; i++) {
			itemWidth = Math.max(items[i].getWidth(), itemWidth);
		}

		for (int i = 0; i < items.length; i++) {
			this.items.add(items[i]);

			items[i].setPosition(Constants.WIDTH / 2 - items[i].getWidth() / 2 + itemWidth * i + i
					* margin, Constants.HEIGHT / 2 - items[i].getHeight() / 2);
		}
		setCurrentSelection(0);
	}

	Action touchableAction = new Action() {

		@Override
		public boolean act(float delta) {
			for (int i = 0; i < items.size; i++) {
				items.get(i).setTouchable(Touchable.enabled);
			}
			return true;
		}

	};

	public void show() {
		for (int i = 0; i < items.size; i++) {
			stage.addActor(items.get(i));
			items.get(i).addAction(
					Actions.sequence(Actions.moveBy(Constants.WIDTH / 2, 0),
							Actions.moveBy(-Constants.WIDTH / 2, 0, 0.5f, Interpolation.pow3)));
		}

	}

	public void setCurrentSelection(int index) {
		currentIndex = index;
		currentSelection = items.get(currentIndex);

		for (int i = 0; i < items.size; i++) {
			items.get(i).addAction(
					Actions.moveBy(Constants.WIDTH / 2 - items.get(index).getX()
							- items.get(index).getWidth() / 2, 0, 0.2f, Interpolation.pow2));
		}

	}

	public void lock() {
		float smallestDistance = 999;
		int index = 0;

		for (int i = 0; i < items.size; i++) {
			float newSmallestDistance = Math.min(
					smallestDistance,
					Math.abs(items.get(i).getX() + items.get(i).getWidth() / 2 - Constants.WIDTH
							/ 2));
			if (newSmallestDistance != smallestDistance) {
				index = i;
			}
			smallestDistance = newSmallestDistance;
		}

		setCurrentSelection(index);
	}

	@Override
	public void act(float delta) {
		super.act(delta);

	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);

	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {

		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		if (first) {
			first = false;
		} else {
			for (int i = 0; i < items.size; i++) {
				items.get(i).setPosition(items.get(i).getX() + deltaX, items.get(i).getY());
			}
		}
		return true;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		first = true;
		lock();
		return true;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1,
			Vector2 pointer2) {
		// TODO Auto-generated method stub
		return false;
	}

}
