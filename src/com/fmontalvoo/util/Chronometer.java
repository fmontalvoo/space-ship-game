package com.fmontalvoo.util;

public class Chronometer {
	private long time;
	private boolean running;
	private long delta, lastTime;

	public Chronometer() {
		this.delta = 0;
		this.running = false;
		this.lastTime = System.currentTimeMillis();
	}

	public void run(long time) {
		running = true;
		this.time = time;
	}

	public void update() {
		if (running) {
			delta += System.currentTimeMillis() - lastTime;
		}

		if (delta >= time) {
			delta = 0;
			running = false;
		}

		lastTime = System.currentTimeMillis();
	}

	public boolean isRunning() {
		return running;
	}

}
