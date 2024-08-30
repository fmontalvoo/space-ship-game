package com.fmontalvoo.entity;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import com.fmontalvoo.Game;
import com.fmontalvoo.math.Vector;
import com.fmontalvoo.state.GameState;
import com.fmontalvoo.util.Size;

public class Meteor extends MovingObject {

	private final int avg;
	private final Size size;

	private final static double DELTA_ANGLE = 0.01;

	public final static double MAX_VELOCITY = 2;

	public Meteor(Vector position, Vector velocity, double maxVelocity, BufferedImage image, GameState state,
			Size size) {
		super(position, velocity, maxVelocity, image, state);

		this.size = size;

		this.avg = (width + height) >> 1; // Calcula promedio
	}

	@Override
	public void update() {
		position.add(velocity);

		edges();

		angle += DELTA_ANGLE;
	}

	@Override
	public void draw(Graphics graphics) {
		Graphics2D graphics2d = (Graphics2D) graphics;

		transform = AffineTransform.getTranslateInstance(getX(), getY());

		transform.rotate(angle, halfWidth, halfHeight);
		graphics2d.drawImage(image, transform, null);
	}

	public Size getSize() {
		return size;
	}

	private void edges() {
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
