package com.fmontalvoo.entity;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Objects;

import com.fmontalvoo.assets.Assets;
import com.fmontalvoo.assets.Sound;
import com.fmontalvoo.math.Vector;
import com.fmontalvoo.state.GameState;

public abstract class MovingObject extends GameObject {

	protected double angle;
	protected Vector velocity;
	protected boolean destroyed;
	protected AffineTransform transform;

	private final Sound explosion;
	protected final double maxVelocity;

	public MovingObject(Vector position, Vector velocity, double maxVelocity, BufferedImage image, GameState state) {
		super(position, image, state);

		this.angle = 0;
		this.destroyed = false;
		this.velocity = velocity;
		this.maxVelocity = maxVelocity;

		this.explosion = new Sound(Assets.explosion);
	}

	protected void checkCollision() {
		List<MovingObject> movingObjects = state.getMovingObjects();

		for (int i = 0; i < movingObjects.size(); i++) {
			MovingObject mo = movingObjects.get(i);

			if (mo.equals(this)) {
				continue;
			}

			double distance = mo.center().dist(center());

			if (movingObjects.contains(this) && (distance < mo.halfWidth + halfWidth) && !destroyed && !mo.destroyed) {
				objectCollision(mo, this);
			}

		}
	}

	protected void objectCollision(MovingObject a, MovingObject b) {
		if (a instanceof Player && ((Player) a).isSpawning()) {
			return;
		}
		if (b instanceof Player && ((Player) b).isSpawning()) {
			return;
		}

		if (!(a instanceof Meteor && b instanceof Meteor)) {
			state.playExplosion(center());
			a.destroy();
			b.destroy();
		}
	}

	protected void destroy() {
		destroyed = true;
		if (!(this instanceof Laser)) {
			explosion.play();
		}
	}

	/**
	 * Retorna un nuevo Vector que apunta al centro de la imagen del objeto.
	 * 
	 * @return Un nuevo Vector
	 */
	protected Vector center() {
		return position.copy().add(halfWidth, halfHeight);
	}

	public boolean isDestroyed() {
		return destroyed;
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
