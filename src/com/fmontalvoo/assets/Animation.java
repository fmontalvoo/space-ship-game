package com.fmontalvoo.assets;

import java.awt.image.BufferedImage;

import com.fmontalvoo.math.Vector;

public class Animation {

	private final int velocity;
	private final Vector position;
	private final BufferedImage[] frames;

	private int index;
	private boolean running;
	private long time, lastTime;

	public Animation(int velocity, Vector position, BufferedImage[] frames) {
		this.frames = frames;
		this.velocity = velocity;
		this.position = position;

		this.time = 0;
		this.index = 0;
		this.running = true;
		this.lastTime = System.currentTimeMillis();
	}

	public void update() {
		time += System.currentTimeMillis() - lastTime;
		lastTime = System.currentTimeMillis();

		if (time > velocity) {
			index++;
			time = 0;

			if (index >= frames.length) {
				running = false;
			}
		}
	}

	public Vector getPosition() {
		return position;
	}

	public boolean isRunning() {
		return running;
	}

	public BufferedImage getCurrentFrame() {
		return this.frames[index];
	}

	public double getX() {
		return this.position.x;
	}

	public double getY() {
		return this.position.y;
	}

}
