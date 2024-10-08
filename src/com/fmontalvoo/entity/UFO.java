package com.fmontalvoo.entity;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.List;

import com.fmontalvoo.Game;
import com.fmontalvoo.assets.Assets;
import com.fmontalvoo.assets.Sound;
import com.fmontalvoo.math.Vector;
import com.fmontalvoo.state.GameState;

public class UFO extends MovingObject {

	private int index;
	private long fireRate;
	private boolean following;
	private Vector currentNode;

	private final int avg;
	private final Sound shoot;
	private final List<Vector> path;

	private static final int SCORE = 40;
	private static final double MASS = 60;
	private static final int FIRE_RATE = 1000;
	private static final int NODE_RADIUS = 160;
	private static final double DELTA_ANGLE = 0.05;
	private static final double ANGLE_RANGE = Math.PI / 2;

	public static final int SPAWN_RATE = 10000;
	public static final double MAX_VELOCITY = 5;

	public UFO(Vector position, Vector velocity, double maxVelocity, BufferedImage image, List<Vector> path,
			GameState state) {
		super(position, velocity, maxVelocity, image, state);

		this.path = path;

		this.index = 0;
		this.following = true;
		this.avg = (width + height) >> 1;

		this.shoot = new Sound(Assets.ufoShoot);

		this.fireRate = 0;
	}

	@Override
	public void update(float dt) {
		Vector pathFollowing;

		if (following) {
			pathFollowing = followingPath();
		} else {
			pathFollowing = new Vector();
		}

		pathFollowing.mult(1 / MASS); // a = f/m;
		velocity.add(pathFollowing).limit(maxVelocity);
		position.add(velocity);

		angle += DELTA_ANGLE;

		if (fireRate >= FIRE_RATE) {
			Vector toPlayer = state.getPlayer().center().sub(center());
			toPlayer.normalize();

			double newAngle = toPlayer.angle();

			newAngle += Math.random() * ANGLE_RANGE - (ANGLE_RANGE / 2);

			if (toPlayer.x < 0) {
				newAngle = -newAngle + Math.PI;
			}

			toPlayer.dir(newAngle);

			Laser laser = new Laser(center().add(toPlayer.copy().mult(width)), toPlayer.copy(), Laser.MAX_VELOCITY,
					newAngle + (Math.PI / 2), Assets.redLaser, state);

			state.getMovingObjects().add(0, laser);

			shoot.play();

			fireRate = 0;
		}

		if (shoot.getFramePosition() > 8500) {
			shoot.stop();
		}

		fireRate += dt;

		checkCollision();

		if (edges()) {
			destroy();
		}
	}

	@Override
	public void draw(Graphics graphics) {
		Graphics2D g2d = (Graphics2D) graphics;

		transform = AffineTransform.getTranslateInstance(getX(), getY());

		transform.rotate(angle, halfWidth, halfHeight);
		g2d.drawImage(image, transform, null);

//		graphics.setColor(Color.RED);
//		for (int i = 0; i < path.size(); i++) {
//			graphics.drawRect((int) path.get(i).x, (int) path.get(i).y, 7, 7);
//		}
	}

	private Vector followingPath() {
		currentNode = path.get(index);

		double distanceToNode = currentNode.dist(center());

		if (distanceToNode < NODE_RADIUS) {
			index++;
			if (index >= path.size()) {
				following = false;
			}
		}

		return seekForce(currentNode);
	}

	private Vector seekForce(Vector target) {
		Vector desiredVelocity = Vector.sub(target, center());
		desiredVelocity.normalize().mult(maxVelocity);
		return desiredVelocity.sub(velocity);
//		return target.copy().sub(center()).normalize().mult(maxVelocity).sub(velocity);
	}

	private boolean edges() {
		return ((getX() > Game.WIDTH + avg) || (getY() > Game.HEIGHT + avg) || (getX() < -avg) || (getY() < -avg));
	}

	@Override
	protected void destroy() {
		state.addScore(SCORE, position);
		super.destroy();
	}

}
