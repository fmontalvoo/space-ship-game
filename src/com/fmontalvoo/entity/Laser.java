package com.fmontalvoo.entity;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import com.fmontalvoo.Game;
import com.fmontalvoo.math.Vector;

public class Laser extends MovingObject {

	public Laser(Vector position, Vector velocity, double maxVelocity, double angle, BufferedImage image) {
		super(position, velocity, maxVelocity, image);

		this.angle = angle;
		this.velocity = velocity.mult(maxVelocity);
	}

	@Override
	public void update() {
		position.add(velocity);
	}

	@Override
	public void draw(Graphics graphics) {
		Graphics2D graphics2d = (Graphics2D) graphics;

		transform = AffineTransform.getTranslateInstance(getX() - halfWidth, getY());

		transform.rotate(angle, halfWidth, 0);

		graphics2d.drawImage(image, transform, null);
	}

	public boolean edges() {
		return ((getX() > Game.WIDTH) || (getY() > Game.HEIGHT) || (getX() < -width) || (getY() < -height));
	}

}
