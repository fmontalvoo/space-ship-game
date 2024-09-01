package com.fmontalvoo.entity;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.List;

import com.fmontalvoo.math.Vector;
import com.fmontalvoo.state.GameState;

public abstract class MovingObject extends GameObject {

	protected double angle;
	protected Vector velocity;
	protected final double maxVelocity;
	protected AffineTransform transform;

	public MovingObject(Vector position, Vector velocity, double maxVelocity, BufferedImage image, GameState state) {
		super(position, image, state);

		this.angle = 0;
		this.velocity = velocity;
		this.maxVelocity = maxVelocity;
	}

	protected void checkCollision() {
		List<MovingObject> movingObjects = state.getMovingObjects();

		for (int i = 0; i < movingObjects.size(); i++) {
			MovingObject movingObject = movingObjects.get(i);

			if (movingObject.equals(this)) {
				continue;
			}

			double distance = movingObject.center().dist(center());

			if (movingObjects.contains(this) && distance < movingObject.halfWidth + halfWidth) {
				objectCollision(movingObject, this);
			}

		}
	}

	protected void objectCollision(MovingObject a, MovingObject b) {
		if (!(a instanceof Meteor && b instanceof Meteor)) {
			a.destroy();
			b.destroy();
		}
	}

	protected void destroy() {
		state.getMovingObjects().remove(this);
	}

	protected Vector center() {
		return position.copy().add(halfWidth, halfHeight);
	}

}
