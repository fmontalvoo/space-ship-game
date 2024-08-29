package com.fmontalvoo;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.logging.Logger;

import javax.swing.JFrame;

import com.fmontalvoo.assets.Assets;
import com.fmontalvoo.state.GameState;

public class Game extends JFrame implements Runnable {

	private static final long serialVersionUID = 1L;

	private static final int BUFFERS = 3; // Numero de buffers
	public static final int WIDTH = 800, HEIGHT = 600; // Alto y ancho

	// Variables para controlar los frames por segundo
	private double delta = 0;
	private int averageFPS = FPS;
	private static final int FPS = 60;
	private static final int NS_PER_SECOND = 1_000_000_000; // Nanosegundos por segundo
	private static final double TARGET_TIME = NS_PER_SECOND / FPS;

	private Thread thread;
	private Graphics graphics;
	private BufferStrategy strategy;
	private static volatile boolean running = false;

	private GameState gameState;

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

	private void init() {
		Assets.init();
		gameState = new GameState();
	}

	private void update() {
		gameState.update();
	}

	private void draw() {
		strategy = canvas.getBufferStrategy();

		if (strategy == null) {
			canvas.createBufferStrategy(BUFFERS);
			return;
		}

		graphics = strategy.getDrawGraphics();
//		---------------------------------------------------------------------

		graphics.setColor(Color.BLACK);
		graphics.fillRect(0, 0, WIDTH, HEIGHT);

		gameState.draw(graphics);

		graphics.setColor(Color.WHITE);
		graphics.drawString(String.format("FPS: %d", averageFPS), 0, 10);

//		---------------------------------------------------------------------
		graphics.dispose();
		strategy.show();
	}

	@Override
	public void run() {

		long now = 0;
		long time = 0;
		int frames = 0;
		long lastTime = System.nanoTime();

		init();

		while (running) {

			now = System.nanoTime();
			time += (now - lastTime);
			delta += (now - lastTime) / TARGET_TIME;
			lastTime = now;

			if (delta >= 1) {
				update();
				draw();
				delta--;
				frames++;
			}

			if (time >= NS_PER_SECOND) {
				averageFPS = frames;
				time = 0;
				frames = 0;
			}

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
