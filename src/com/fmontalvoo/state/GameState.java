package com.fmontalvoo.state;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.fmontalvoo.Game;
import com.fmontalvoo.assets.Assets;
import com.fmontalvoo.entity.Meteor;
import com.fmontalvoo.entity.MovingObject;
import com.fmontalvoo.entity.Player;
import com.fmontalvoo.math.Vector;
import com.fmontalvoo.util.Size;

public class GameState {

	private int meteors;

	private final List<MovingObject> movingObjects = new ArrayList<>();

	public GameState() {
		this.meteors = 1;

		this.movingObjects
				.add(new Player(new Vector(475, 281), new Vector(0, 0), Player.MAX_VELOCITY, Assets.player, this));
	}

	public void update() {
		for (int i = 0; i < movingObjects.size(); i++) {
			movingObjects.get(i).update();

			if (movingObjects.get(i) instanceof Meteor) {
				return;
			}
		}
		startWave();
	}

	public void draw(Graphics graphics) {
		Graphics2D g2d = (Graphics2D) graphics;
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

		for (int i = 0; i < movingObjects.size(); i++) {
			movingObjects.get(i).draw(graphics);
		}
	}

	private void startWave() {
		double x, y;
		for (int i = 0; i < meteors; i++) {
			x = (i % 2 == 0) ? Math.random() * Game.WIDTH : 0;
			y = (i % 2 == 0) ? 0 : Math.random() * Game.HEIGHT;

			BufferedImage image = Assets.bigs[(int) Math.random() * Assets.bigs.length];

			movingObjects.add(new Meteor(new Vector(x, y), new Vector(0, 1).dir(Math.random() * (2 * Math.PI)),
					Meteor.MAX_VELOCITY * Math.random() + 1, image, this, Size.BIG));
		}
		meteors++;
	}

	public List<MovingObject> getMovingObjects() {
		return movingObjects;
	}

}
