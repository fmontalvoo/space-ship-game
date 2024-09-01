package com.fmontalvoo.entity;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import com.fmontalvoo.Game;
import com.fmontalvoo.math.Vector;
import com.fmontalvoo.state.GameState;

public class Laser extends MovingObject {

	public final static double MAX_VELOCITY = 14;

	public Laser(Vector position, Vector velocity, double maxVelocity, double angle, BufferedImage image,
			GameState state) {
		super(position, velocity, maxVelocity, image, state);

		this.angle = angle;
		this.velocity.mult(maxVelocity);
	}

	@Override
	public void update() {
		position.add(velocity);

		if (edges()) {
			destroy();
		}

		checkCollision();
	}

	@Override
	public void draw(Graphics graphics) {
		Graphics2D graphics2d = (Graphics2D) graphics;

		transform = AffineTransform.getTranslateInstance(getX() - halfWidth, getY());

		transform.rotate(angle, halfWidth, 0);

		graphics2d.drawImage(image, transform, null);
	}

	private boolean edges() {
		return ((getX() > Game.WIDTH + height) || (getY() > Game.HEIGHT + height) || (getX() < -height)
				|| (getY() < -height));
	}

	@Override
	protected Vector center() {
		return position.copy().add(halfWidth, halfWidth);
	}

}
