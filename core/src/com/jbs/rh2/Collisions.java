package com.jbs.rh2;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;

public class Collisions {
	Array<Balloon> balloons;
	Array<Obstacle> obstacles;
	Array<HitboxActor> hitboxActors;
	LevelScreen screen;
	Balloon contactBalloon;

	boolean balloonFound;

	public Collisions(LevelScreen screen) {
		balloons = screen.balloons;
		obstacles = screen.obstacles;
		hitboxActors = screen.hitboxActors;
		this.screen = screen;
	}

	public void update(LevelScreen screen, float delta) {
		balloons = screen.balloons;
		obstacles = screen.obstacles;
		hitboxActors = screen.hitboxActors;
		this.screen = screen;

		checkBalloonCollision(delta);
		checkObstacleCollision(delta);

		if (contactBalloon != null && balloonFound
				&& contactBalloon.lastSubtractTime > contactBalloon.subtractTime) {
			screen.score -= 2;
			contactBalloon.lastSubtractTime = 0;
		}

		if (balloonFound && screen.player.rainbow.getActions().size == 0) {
			Rainbow rainbow = screen.player.rainbow;
			float distance = contactBalloon.getX() - rainbow.getX();
			rainbow.addAction(Actions.sizeTo(distance > 0 ? distance : rainbow.origWidth,
					rainbow.getHeight(), 0.2f));

		} else if (screen.player.rainbow.getActions().size == 0) {
			screen.player.rainbow.addAction(Actions.sizeTo(screen.player.rainbow.origWidth,
					screen.player.rainbow.getHeight(), 0.3f));
		}

	}

	public void checkCollisions(float delta) {
// for (int i = 0; i < hitboxActors.size; i++) {
// for (int j = 0; j < hitboxActors.size && j != i; j++) {
// if (i < hitboxActors.size && j < hitboxActors.size) {
// if
// (hitboxActors.get(i).getHitbox().overlaps(hitboxActors.get(j).getHitbox())) {
// if (!checkBalloonCollision(hitboxActors.get(i), hitboxActors.get(j))) {
// checkObstacleCollision(hitboxActors.get(i), hitboxActors.get(j));
// }
// }
// }
// }
// }
	}

	public void checkBalloonCollision(float delta) {
		balloonFound = false;
		for (int i = 0; i < balloons.size; i++) {
			Balloon balloon = balloons.get(i);
			if (screen.player.rainbow.getHitbox().overlaps(balloon.getHitbox())
					&& screen.player.rainbow.isVisible()) {
				
				if (balloon.lastSubtractTime == 1.0f) {
					screen.player.rainbow.hitSprite.setAlpha(1.0f);
					screen.player.rainbow.hitSprite.stop();
					screen.player.rainbow.hitSprite.play();
				}

				if (balloon.type == BalloonType.BLACK) {
					balloon.lastSubtractTime += delta;

					if (balloon.ghost.getActions().size == 0) {
						balloon.ghost.addAction(Actions.sizeBy(3, 3, 0.2f));
					}
				} else if (balloon.type == BalloonType.WHITE) {
					screen.hud.splatScreen();
				} else {
					screen.incrementScore();
				}
				contactBalloon = balloons.get(i);
				balloonFound = true;

				if (balloon.type != BalloonType.BLACK) {
					screen.player.rainbow.ghost.addAction(Actions.sequence(
							Actions.moveTo(
									contactBalloon.getX() - contactBalloon.sprite.getWidth() / 2,
									screen.player.rainbow.getY()
											+ screen.player.rainbow.image.getHeight() / 2 - 475
											/ 2f), Actions.delay(1f)));
// getX() + image.getWidth() - hitSprite.getWidth() / 2,
// getY() + image.getHeight() / 2 - 475 / 2f - 18
					screen.stage.getActors().removeValue(balloon, false);
					balloons.removeValue(balloon, false);
					hitboxActors.removeValue(balloon, false);
				}
			}
		}

	}

	public void checkObstacleCollision(float delta) {

		for (int i = 0; i < obstacles.size; i++) {
			if (obstacles.get(i).getHitbox().overlaps(screen.player.getHitbox())) {
				screen.gameOver();
			}
		}

	}
}
