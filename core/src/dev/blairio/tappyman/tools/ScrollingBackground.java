package dev.blairio.tappyman.tools;

import dev.blairio.tappyman.TappyMan;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ScrollingBackground {

	public static final int DEFAULT_SPEED = 80;
	public static final int ACCELERATION = 50;
	public static final int GOAL_REACH_ACCELERATION = 200;

	Texture image;
	float x1, x2;
	int speed; // pix/sec
	int goalSpeed;
	float imageScale;
	boolean speedFixed;

	public ScrollingBackground (String texturePath) {
		image = new Texture(texturePath);
		x1 = 0;
		x2 = TappyMan.WORLD_WIDTH;
		speed = 0;
		goalSpeed = DEFAULT_SPEED;
		imageScale = TappyMan.WORLD_WIDTH / image.getWidth();
		speedFixed = true;
	}

	public void updateAndRender (float deltaTime, SpriteBatch batch) {
		// speed adjustment to reach goal
		if (speed < goalSpeed) {
			speed += GOAL_REACH_ACCELERATION * deltaTime;
			if (speed > goalSpeed)
				speed = goalSpeed;
		} else if (speed > goalSpeed) {
			speed -= GOAL_REACH_ACCELERATION * deltaTime;
			if (speed < goalSpeed)
				speed = goalSpeed;
		}

		if (!speedFixed)
			speed += ACCELERATION * deltaTime;

		x1 -= speed * deltaTime;
		x2 -= speed * deltaTime;

		// redraw image as it disappears off screen
		if (x1 + image.getWidth() * imageScale <= 0)
			x1 = x2 + image.getWidth() * imageScale;

		if (x2 + image.getWidth() * imageScale <= 0)
			x2 = x1 + image.getWidth() * imageScale;

		// change to swap between horizontal and vertical scroll
		batch.draw(image, x1, 0, TappyMan.WORLD_WIDTH, image.getWidth() * imageScale);
		batch.draw(image, x2, 0, TappyMan.WORLD_WIDTH, image.getWidth() * imageScale);
	}

	public void setSpeed (int goalSpeed) {
		this.goalSpeed = goalSpeed;
	}

	public void setSpeedFixed (boolean speedFixed) {
		this.speedFixed = speedFixed;
	}
	
}