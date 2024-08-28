package com.fmontalvoo;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.logging.Logger;

import javax.swing.JFrame;

public class Game extends JFrame implements Runnable {

	private static final long serialVersionUID = 1L;

	public static final int WIDTH = 800, HEIGHT = 600;
	private static final int BUFFERS = 3;

	private Thread thread;
	private Graphics graphics;
	private BufferStrategy strategy;
	private static volatile boolean running = false;

	private final Canvas canvas;
	private final Logger log = Logger.getLogger(this.getClass().getName());

	public Game() {
		setResizable(false);
		setSize(WIDTH, HEIGHT);
		setLocationRelativeTo(null);
		setTitle("Space Ship Game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setVisible(true);

		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		canvas.setMaximumSize(new Dimension(WIDTH, HEIGHT));
		canvas.setMinimumSize(new Dimension(WIDTH, HEIGHT));
		canvas.setFocusable(true);

		add(canvas);
	}

	private void update() {
	}

	private void draw() {
		strategy = canvas.getBufferStrategy();

		if (strategy == null) {
			canvas.createBufferStrategy(BUFFERS);
			return;
		}

		graphics = strategy.getDrawGraphics();

		graphics.drawRect(0, 0, 70, 70);

		graphics.dispose();
		strategy.show();
	}

	@Override
	public void run() {

		while (running) {
			update();
			draw();
		}

		stop();
	}

	private synchronized void start() {
		thread = new Thread(this, "Graphics");
		thread.start();
		running = true;
	}

	private synchronized void stop() {
		try {
			thread.join();
			running = false;
		} catch (InterruptedException ex) {
			log.severe(ex.getMessage());
		}
	}

	public static void main(String[] args) {
		Game game = new Game();
		game.start();
	}

}
