package com.fmontalvoo.entity;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import com.fmontalvoo.Game;
import com.fmontalvoo.assets.Assets;
import com.fmontalvoo.assets.Sound;
import com.fmontalvoo.input.KeyBoard;
import com.fmontalvoo.math.Vector;
import com.fmontalvoo.state.GameState;

public class Player extends MovingObject {

	private Vector acceleration;
	private boolean spawning, visible;
	private boolean accelerating = false;

	private final int avg;
	private final Vector heading;
	private final Sound shoot, loose;
	private long fireRate, spawnTime, flickerTime;

	private static final int FIRE_RATE = 200;
	private static final long FLICKER_TIME = 200;
	private static final double DELTA_ANGLE = 0.1;
	private static final double ACCELERATION = 0.2;
	private static final long SPAWNING_TIME = 3000;

	public int lifes;

	public static final double MAX_VELOCITY = 7;

	public Player(Vector position, Vector velocity, double maxVelocity, BufferedImage image, GameState state) {
		super(position, velocity, maxVelocity, image, state);

		this.heading = new Vector(0, 1);
		this.acceleration = new Vector();

		this.shoot = new Sound(Assets.playerShoot);
		this.loose = new Sound(Assets.playerLoose);

		this.visible = true;

		this.fireRate = 0;
		this.spawnTime = 0;
		this.flickerTime = 0;

		this.lifes = 3;
		// avg = (width + height) / 2
		this.avg = (width + height) >> 1; // Calcula promedio
	}

	@Override
	public void update(float dt) {

		fireRate += dt;

		if (KeyBoard.right) {
			angle += DELTA_ANGLE;
		}

		if (KeyBoard.left) {
			angle -= DELTA_ANGLE;
		}

		if (KeyBoard.fire && fireRate >= FIRE_RATE && !spawning) {
			state.getMovingObjects().add(0, new Laser(center().add(heading.copy().mult(width)), heading.copy(),
					Laser.MAX_VELOCITY, angle, Assets.blueLaser, state));
			shoot.play();
			fireRate = 0;
		}

		if (shoot.getFramePosition() > 8500) {
			shoot.stop();
		}

		if (KeyBoard.up) {
			accelerating = true;
			acceleration = Vector.mult(heading, ACCELERATION);
		} else if (KeyBoard.down) {
			accelerating = true;
			acceleration = Vector.mult(heading, -ACCELERATION);
		} else if (velocity.mag() != 0) {
			accelerating = false;
			acceleration = Vector.mult(velocity.copy().normalize(), -ACCELERATION / 2);
		}

		if (spawning) {
			flickerTime += dt;
			spawnTime += dt;

			if (flickerTime >= FLICKER_TIME) {
				visible = !visible;
				flickerTime = 0;
			}

			if (spawnTime >= SPAWNING_TIME) {
				spawning = false;
				visible = true;
			}
		}

		velocity.add(acceleration).limit(maxVelocity);

		heading.dir(angle - Math.PI / 2);

		position.add(velocity);

		edges();

		checkCollision();
	}

	@Override
	public void draw(Graphics graphics) {
		if (!visible) {
			return;
		}

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

	@Override
	protected void destroy() {
		lifes--;
		spawnTime = 0;
		spawning = true;
		loose.play();
		resetValues();

		if (lifes == 0) {
			state.gameOver();
			super.destroy();
		}
	}

	private void resetValues() {
		angle = 0;
		velocity = new Vector();
		position = new Vector((Game.WIDTH >> 1) - halfWidth, (Game.HEIGHT >> 1) - halfHeight);
	}

	public boolean isSpawning() {
		return spawning;
	}

}
