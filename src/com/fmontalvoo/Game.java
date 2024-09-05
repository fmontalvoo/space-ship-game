package com.fmontalvoo;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.filechooser.FileSystemView;

import com.fmontalvoo.assets.Assets;
import com.fmontalvoo.input.KeyBoard;
import com.fmontalvoo.input.MouseInput;
import com.fmontalvoo.state.LoadingState;
import com.fmontalvoo.state.State;

public class Game extends JFrame implements Runnable {

	private static final long serialVersionUID = -1072612083774705497L;

	public static final int WIDTH = 1000, HEIGHT = 600; // Alto y ancho
	public static final String SCORE_PATH = FileSystemView.getFileSystemView().getDefaultDirectory().getPath()
			+ "\\Space_Ship_Game\\data.json";

	// Variables para controlar los frames por segundo
	private double delta = 0;

	private static final int FPS = 60;
	private static final int BUFFERS = 3; // Numero de buffers
	private static final int NS_PER_SECOND = 1_000_000_000; // Nanosegundos por segundo
	private static final double TARGET_TIME = NS_PER_SECOND / FPS;

	private Thread thread;
	private Graphics graphics;
	private KeyBoard keyBoard;
	private MouseInput mouseInput;
	private int averageFPS = FPS;
	private BufferStrategy strategy;
	private volatile boolean running = false;

	private final Canvas canvas;
	private final Logger log = Logger.getLogger(this.getClass().getName());

	public Game() {
		setResizable(false);
		setSize(WIDTH, HEIGHT);
		setLocationRelativeTo(null);
		setTitle("Space Ship Game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		canvas = new Canvas();
		keyBoard = new KeyBoard();
		mouseInput = new MouseInput();

		canvas.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		canvas.setMaximumSize(new Dimension(WIDTH, HEIGHT));
		canvas.setMinimumSize(new Dimension(WIDTH, HEIGHT));
		canvas.setFocusable(true);

		add(canvas);
		canvas.addKeyListener(keyBoard);
		canvas.addMouseListener(mouseInput);
		canvas.addMouseMotionListener(mouseInput);

		setVisible(true);
	}

	private void init() {
		Thread loading = new Thread(new Runnable() {
			@Override
			public void run() {
				Assets.init();
			}
		});

		State.setCurrentState(new LoadingState(loading));
	}

	private void update(float dt) {
		keyBoard.update();
		State.getCurrentState().update(dt);
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
		Font originalFont = graphics.getFont();

		State.getCurrentState().draw(graphics);

		graphics.setFont(originalFont);
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
			long diff = now - lastTime;
			time += diff;
			delta += diff / TARGET_TIME;
			lastTime = now;

			if (delta >= 1) {
				update((float) (delta * TARGET_TIME * 0.000001f)); // Convierte a milisegundos
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
