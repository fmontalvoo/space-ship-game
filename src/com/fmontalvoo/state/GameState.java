package com.fmontalvoo.state;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fmontalvoo.Game;
import com.fmontalvoo.assets.Animation;
import com.fmontalvoo.assets.Assets;
import com.fmontalvoo.entity.Meteor;
import com.fmontalvoo.entity.MovingObject;
import com.fmontalvoo.entity.Player;
import com.fmontalvoo.math.Vector;
import com.fmontalvoo.util.Size;

public class GameState {

	private int meteors;

	private final List<Animation> explosions = new ArrayList<>();
	private final List<MovingObject> movingObjects = new ArrayList<>();

	public GameState() {
		this.meteors = 1;

		this.movingObjects
				.add(new Player(new Vector(475, 281), new Vector(0, 0), Player.MAX_VELOCITY, Assets.player, this));
	}

	public void update() {
		boolean hasMeteor = false;

		for (int i = 0; i < movingObjects.size(); i++) {
			MovingObject movingObject = movingObjects.get(i);

			movingObject.update();

			hasMeteor = (movingObject instanceof Meteor);
		}

		for (int i = 0; i < explosions.size(); i++) {
			Animation anim = explosions.get(i);

			anim.update();

			if (!anim.isRunning()) {
				explosions.remove(anim);
			}
		}

		if (!hasMeteor) {
			startWave();
		}
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
					Meteor.MAX_VELOCITY * Math.random() + 1, image, this, Size.BIG));
		}
		meteors++;
	}

	public List<MovingObject> getMovingObjects() {
		return movingObjects;
	}

}
