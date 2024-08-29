package com.fmontalvoo.entity;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import com.fmontalvoo.Game;
import com.fmontalvoo.assets.Assets;
import com.fmontalvoo.input.KeyBoard;
import com.fmontalvoo.math.Vector;

public class Player extends MovingObject {

	private Vector heading;
	private Vector acceleration;
	private boolean accelerating = false;

	private final double ACCELERATION = 0.2;
	private final double DELTA_ANGLE = 0.1;

	public Player(Vector position, Vector velocity, double maxVelocity, BufferedImage image) {
		super(position, velocity, maxVelocity, image);
		heading = new Vector(0, 1);
		acceleration = new Vector();
	}

	@Override
	public void update() {
		if (KeyBoard.right) {
			angle += DELTA_ANGLE;
		}

		if (KeyBoard.left) {
			angle -= DELTA_ANGLE;
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

		transform = AffineTransform.getTranslateInstance(getX(), getY());

		int halfX = width >> 1; // width / 2
		int halfY = height >> 1; // height / 2

		if (accelerating) {
			AffineTransform at1 = AffineTransform.getTranslateInstance(getX() + halfX + 5, getY() + halfY + 10);
			AffineTransform at2 = AffineTransform.getTranslateInstance(getX() + 5, getY() + halfY + 10);

			at1.rotate(angle, -5, -10);
			at2.rotate(angle, halfX - 5, -10);

			graphics2d.drawImage(Assets.fire, at1, null);
			graphics2d.drawImage(Assets.fire, at2, null);
		}

		transform.rotate(angle, halfX, halfY);
		graphics2d.drawImage(Assets.player, transform, null);

	}

	public void edges() {
		// avg = (width + height) / 2
		int avg = (width + height) >> 1; // Calcula promedio

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
