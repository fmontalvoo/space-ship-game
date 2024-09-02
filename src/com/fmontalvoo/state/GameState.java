package com.fmontalvoo.state;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.fmontalvoo.Game;
import com.fmontalvoo.assets.Animation;
import com.fmontalvoo.assets.Assets;
import com.fmontalvoo.entity.Meteor;
import com.fmontalvoo.entity.MovingObject;
import com.fmontalvoo.entity.Player;
import com.fmontalvoo.entity.UFO;
import com.fmontalvoo.math.Vector;
import com.fmontalvoo.util.Size;

public class GameState {

	private int meteors;

	private final Player player;

	private final List<Animation> explosions = new ArrayList<>();
	private final List<MovingObject> movingObjects = new ArrayList<>();

	public GameState() {
		this.meteors = 1;

		this.player = new Player(new Vector(475, 281), new Vector(0, 0), Player.MAX_VELOCITY, Assets.player, this);

		this.movingObjects.add(this.player);
	}

	public void update() {
		for (int i = 0; i < movingObjects.size(); i++) {
			movingObjects.get(i).update();
		}

		for (int i = 0; i < explosions.size(); i++) {
			Animation anim = explosions.get(i);

			anim.update();

			if (!anim.isRunning()) {
				explosions.remove(anim);
			}
		}

		for (int i = 0; i < movingObjects.size(); i++) {
			if (movingObjects.get(i) instanceof Meteor) {
				return;
			}
		}

		startWave();
		spawnUFO();
	}

	public void draw(Graphics graphics) {
		Graphics2D g2d = (Graphics2D) graphics;
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

		for (int i = 0; i < movingObjects.size(); i++) {
			movingObjects.get(i).draw(graphics);
		}

		for (int i = 0; i < explosions.size(); i++) {
			Animation anim = explosions.get(i);

			g2d.drawImage(anim.getCurrentFrame(), (int) anim.getX(), (int) anim.getY(), null);
		}

	}

	public void playExplosion(Vector position) {
		explosions.add(new Animation(50,
				position.sub(new Vector(Assets.explosion[0].getWidth() >> 1, Assets.explosion[0].getHeight() >> 1)),
				Assets.explosion));
	}

	private void startWave() {
		double x, y;
		for (int i = 0; i < meteors; i++) {
			x = (i % 2 == 0) ? Math.random() * Game.WIDTH : 0;
			y = (i % 2 == 0) ? 0 : Math.random() * Game.HEIGHT;

			BufferedImage image = Assets.bigs[(int) (Math.random() * Assets.bigs.length)];

			movingObjects.add(new Meteor(new Vector(x, y), new Vector(0, 1).dir(Math.random() * (2 * Math.PI)),
					Meteor.MAX_VELOCITY * Math.random() + 1, image, Size.BIG, this));
		}
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

	public List<MovingObject> getMovingObjects() {
		return movingObjects;
	}

	public Player getPlayer() {
		return player;
	}

}
