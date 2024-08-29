package com.fmontalvoo.entity;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import com.fmontalvoo.Game;
import com.fmontalvoo.assets.Assets;
import com.fmontalvoo.input.KeyBoard;
import com.fmontalvoo.math.Vector;
import com.fmontalvoo.state.GameState;

public class Player extends MovingObject {

	private Vector heading;
	private Vector acceleration;
	private boolean accelerating = false;
	private int avg;

	private final GameState state;
	private long time, lastTime;

	private final double ACCELERATION = 0.2;
	private final double DELTA_ANGLE = 0.1;

	public Player(Vector position, Vector velocity, double maxVelocity, BufferedImage image, GameState state) {
		super(position, velocity, maxVelocity, image);
		heading = new Vector(0, 1);
		acceleration = new Vector();

		this.state = state;
		this.time = 0;
		this.lastTime = System.currentTimeMillis();

		// avg = (width + height) / 2
		this.avg = (width + height) >> 1; // Calcula promedio
	}

	@Override
	public void update() {

		time += System.currentTimeMillis() - lastTime;
		lastTime = System.currentTimeMillis();

		if (KeyBoard.right) {
			angle += DELTA_ANGLE;
		}

		if (KeyBoard.left) {
			angle -= DELTA_ANGLE;
		}

		if (KeyBoard.fire && time > 200) {
			state.getMovingObjects().add(0,
					new Laser(center().add(heading.copy().mult(width)), heading.copy(), 10, angle, Assets.blueLaser));
			time = 0;
		}

		if (KeyBoard.up) {
			accelerating = true;
			acceleration = heading.copy().mult(ACCELERATION);
		} else if (KeyBoard.down) {
			accelerating = true;
			acceleration = heading.copy().mult(-ACCELERATION);
		} else if (velocity.mag() != 0) {
			accelerating = false;
			acceleration = velocity.copy().normalize().mult(-ACCELERATION / 2);
		}

		velocity.add(acceleration);

		velocity.limit(maxVelocity);

		heading.dir(angle - Math.PI / 2);

		position.add(velocity);

		edges();
	}

	@Override
	public void draw(Graphics graphics) {

		Graphics2D graphics2d = (Graphics2D) graphics;

		if (accelerating) {
			AffineTransform at1 = AffineTransform.getTranslateInstance(getX() + halfWidth + 5,
					getY() + halfHeight + 10);
			AffineTransform at2 = AffineTransform.getTranslateInstance(getX() + 5, getY() + halfHeight + 10);

			at1.rotate(angle, -5, -10);
			at2.rotate(angle, halfWidth - 5, -10);

			graphics2d.drawImage(Assets.fire, at1, null);
			graphics2d.drawImage(Assets.fire, at2, null);
		}

		transform = AffineTransform.getTranslateInstance(getX(), getY());

		transform.rotate(angle, halfWidth, halfHeight);
		graphics2d.drawImage(image, transform, null);

	}

	public Vector center() {
		return position.copy().add(halfWidth, halfHeight);
	}

	public void edges() {
		if (getX() > Game.WIDTH) {
			setX(-avg);
		}
		if (getY() > Game.HEIGHT) {
			setY(-avg);
		}

		if (getX() < -avg) {
			setX(Game.WIDTH);
		}
		if (getY() < -avg) {
			setY(Game.HEIGHT);
		}
	}

}
