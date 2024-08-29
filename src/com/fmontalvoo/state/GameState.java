package com.fmontalvoo.state;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import com.fmontalvoo.assets.Assets;
import com.fmontalvoo.entity.MovingObject;
import com.fmontalvoo.entity.Player;
import com.fmontalvoo.math.Vector;

public class GameState {

	private List<MovingObject> movingObjects = new ArrayList<>();

	public GameState() {
		this.movingObjects.add(new Player(new Vector(375, 281), new Vector(0, 0), 5, Assets.player, this));
	}

	public void update() {
		for (int i = 0; i < movingObjects.size(); i++) {
			movingObjects.get(i).update();
		}
	}

	public void draw(Graphics graphics) {
		for (int i = 0; i < movingObjects.size(); i++) {
			movingObjects.get(i).draw(graphics);
		}
	}

	public List<MovingObject> getMovingObjects() {
		return movingObjects;
	}

}
