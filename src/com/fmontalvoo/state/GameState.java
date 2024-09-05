package com.fmontalvoo.state;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.fmontalvoo.Game;
import com.fmontalvoo.assets.Animation;
import com.fmontalvoo.assets.Assets;
import com.fmontalvoo.assets.Sound;
import com.fmontalvoo.entity.Meteor;
import com.fmontalvoo.entity.MovingObject;
import com.fmontalvoo.entity.Player;
import com.fmontalvoo.entity.UFO;
import com.fmontalvoo.math.Vector;
import com.fmontalvoo.util.HUD;
import com.fmontalvoo.util.Message;
import com.fmontalvoo.util.Size;

public class GameState extends State {

	private int waves;
	private int meteors;
	private long ufoSpawner;
	private boolean gameOver;
	private long gameOverTimer;

	private final HUD hud;
	private final Sound music;
	private final Player player;

	private final List<Message> messages = new ArrayList<>();
	private final List<Animation> explosions = new ArrayList<>();
	private final List<MovingObject> movingObjects = new ArrayList<>();

	private static final long GAME_OVER_TIME = 2000;
	private static final Vector PLAYER_START_POSITION = new Vector((Game.WIDTH / 2) - (Assets.player.getWidth() / 2),
			(Game.HEIGHT / 2) - (Assets.player.getHeight() / 2));

	public GameState() {
		this.waves = 1;
		this.meteors = 1;

		this.player = new Player(new Vector(475, 281), new Vector(0, 0), Player.MAX_VELOCITY, Assets.player, this);
		this.hud = new HUD(0, this.player);

		this.movingObjects.add(this.player);

		this.music = new Sound(Assets.backgroundMusic);
		this.music.loop();
		this.music.changeVolume(-10.0f);

		this.ufoSpawner = 0;

		this.gameOver = false;

		this.gameOverTimer = 0;
	}

	@Override
	public void update(float dt) {
		for (int i = 0; i < movingObjects.size(); i++) {
			MovingObject mo = movingObjects.get(i);

			mo.update(dt);

			if (mo.isDestroyed()) {
				movingObjects.remove(i--);
			}
		}

		for (int i = 0; i < explosions.size(); i++) {
			Animation anim = explosions.get(i);

			anim.update();

			if (!anim.isRunning()) {
				explosions.remove(anim);
			}
		}

		if (gameOver) {
			gameOverTimer += dt;
		}

		if (gameOverTimer >= GAME_OVER_TIME) {
			this.music.stop();
			State.setCurrentState(new MenuState());
		}

		if (ufoSpawner >= UFO.SPAWN_RATE) {
			spawnUFO();
			ufoSpawner = 0;
		}

		ufoSpawner += dt;
		
		for (int i = 0; i < movingObjects.size(); i++) {
			if (movingObjects.get(i) instanceof Meteor) {
				return;
			}
		}

		startWave();
	}

	@Override
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

		for (int i = 0; i < movingObjects.size(); i++) {
			movingObjects.get(i).draw(g);
		}

		for (int i = 0; i < explosions.size(); i++) {
			Animation anim = explosions.get(i);

			g2d.drawImage(anim.getCurrentFrame(), (int) anim.getX(), (int) anim.getY(), null);
		}

		for (int i = 0; i < messages.size(); i++) {
			Message m = messages.get(i);

			m.draw(g2d);

			if (m.isDestroyed()) {
				messages.remove(i);
			}
		}

		hud.drawScore(g);
		hud.drawLifes(g);
	}

	public void playExplosion(Vector position) {
		explosions.add(new Animation(50,
				position.sub(new Vector(Assets.explosions[0].getWidth() >> 1, Assets.explosions[0].getHeight() >> 1)),
				Assets.explosions));
	}

	private void startWave() {
		double x, y;

		messages.add(new Message(new Vector(Game.WIDTH >> 1, Game.HEIGHT >> 1), String.format("WAVE %d", waves),
				Assets.fontBig, Color.WHITE, true, false));

		for (int i = 0; i < meteors; i++) {
			x = (i % 2 == 0) ? Math.random() * Game.WIDTH : 0;
			y = (i % 2 == 0) ? 0 : Math.random() * Game.HEIGHT;

			BufferedImage image = Assets.bigs[(int) (Math.random() * Assets.bigs.length)];

			movingObjects.add(new Meteor(new Vector(x, y), new Vector(0, 1).dir(Math.random() * (2 * Math.PI)),
					Meteor.MAX_VELOCITY * Math.random() + 1, image, Size.BIG, this));
		}
		
		waves++;
		meteors++;
	}

	private void spawnUFO() {
		int rand = (int) (Math.random() * 2);

		double x = rand == 0 ? (Math.random() * Game.WIDTH) : 0;
		double y = rand == 0 ? 0 : (Math.random() * Game.HEIGHT);

		int gameHalfWidth = Game.WIDTH >> 1;
		int gameHalfHeight = Game.HEIGHT >> 1;

		ArrayList<Vector> path = new ArrayList<Vector>();

		double posX, posY;

		// Esquina superior izquierda
		posX = Math.random() * gameHalfWidth;
		posY = Math.random() * gameHalfHeight;
		path.add(new Vector(posX, posY));

		// Esquina superior derecha
		posX = (Math.random() * gameHalfWidth) + gameHalfWidth;
		posY = Math.random() * gameHalfHeight;
		path.add(new Vector(posX, posY));

		// Esquina inferior izquierda
		posX = Math.random() * gameHalfWidth;
		posY = (Math.random() * gameHalfHeight) + gameHalfHeight;
		path.add(new Vector(posX, posY));

		// Esquina inferior derecha
		posX = (Math.random() * gameHalfWidth) + gameHalfWidth;
		posY = (Math.random() * gameHalfHeight) + gameHalfHeight;
		path.add(new Vector(posX, posY));

		movingObjects.add(new UFO(new Vector(x, y), new Vector(), UFO.MAX_VELOCITY, Assets.ufo, path, this));

	}

	public void addScore(int value, Vector position) {
		hud.addScore(value);
		messages.add(
				new Message(position, String.format("+%d score", value), Assets.fontMed, Color.WHITE, false, true));
	}

	public void gameOver() {
		Message gameOverMsg = new Message(PLAYER_START_POSITION, "GAME OVER", Assets.fontBig, Color.WHITE, true, true);

		gameOver = true;
		this.messages.add(gameOverMsg);
	}

	public List<MovingObject> getMovingObjects() {
		return movingObjects;
	}

	public Player getPlayer() {
		return player;
	}

	public List<Message> getMessages() {
		return messages;
	}

}
