package com.fmontalvoo.entity;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import com.fmontalvoo.assets.Assets;
import com.fmontalvoo.input.KeyBoard;
import com.fmontalvoo.math.Vector;

public class Player extends MovingObject {

	private Vector heading;
	private Vector acceleration;

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
			acceleration = heading.copy().mult(ACCELERATION);
		} else if (KeyBoard.down) {
			acceleration = heading.copy().mult(-ACCELERATION);
		} else if (velocity.mag() != 0) {
			acceleration = velocity.copy().normalize().mult(-ACCELERATION / 2);
		}

		velocity.add(acceleration);

		velocity.limit(maxVelocity);

		heading.dir(angle - Math.PI / 2);

		position.add(velocity);

	}

	@Override
	public void draw(Graphics graphics) {

		Graphics2D graphics2d = (Graphics2D) graphics;

		transform = AffineTransform.getTranslateInstance(position.x, position.y);

		transform.rotate(angle, Assets.player.getWidth() / 2, Assets.player.getHeight() / 2);

		graphics2d.drawImage(Assets.player, transform, null);

	}

}
