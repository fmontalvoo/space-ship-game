package com.fmontalvoo.entity;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Objects;

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
			state.playExplosion(center());
			a.destroy();
			b.destroy();
		}
	}

	protected void destroy() {
		state.getMovingObjects().remove(this);
	}

	/**
	 * Retorna un nuevo Vector que apunta al centro de la imagen del objeto.
	 * 
	 * @return Un nuevo Vector
	 */
	protected Vector center() {
		return position.copy().add(halfWidth, halfHeight);
	}

	@Override
	public int hashCode() {
		return Objects.hash(angle, maxVelocity, transform, velocity);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MovingObject other = (MovingObject) obj;
		return Double.doubleToLongBits(angle) == Double.doubleToLongBits(other.angle)
				&& Double.doubleToLongBits(maxVelocity) == Double.doubleToLongBits(other.maxVelocity)
				&& Objects.equals(transform, other.transform) && Objects.equals(velocity, other.velocity);
	}

}
