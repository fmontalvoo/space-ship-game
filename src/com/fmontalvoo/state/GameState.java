package com.fmontalvoo.state;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;

import com.fmontalvoo.assets.Assets;
import com.fmontalvoo.entity.MovingObject;
import com.fmontalvoo.entity.Player;
import com.fmontalvoo.math.Vector;

public class GameState {

	private final List<MovingObject> movingObjects = new ArrayList<>();

	public GameState() {
		this.movingObjects.add(new Player(new Vector(375, 281), new Vector(0, 0), 5, Assets.player, this));
	}

	public void update() {
		for (int i = 0; i < movingObjects.size(); i++) {
			movingObjects.get(i).update();
		}
	}

	public void draw(Graphics graphics) {
		Graphics2D g2d = (Graphics2D) graphics;
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

		for (int i = 0; i < movingObjects.size(); i++) {
			movingObjects.get(i).draw(graphics);
		}
	}

	public List<MovingObject> getMovingObjects() {
		return movingObjects;
	}

}
