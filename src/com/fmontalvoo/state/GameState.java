package com.fmontalvoo.state;

import java.awt.Graphics;

import com.fmontalvoo.assets.Assets;
import com.fmontalvoo.entity.Player;
import com.fmontalvoo.math.Vector;

public class GameState {

	private final Player player;

	public GameState() {
		this.player = new Player(new Vector(100, 250), new Vector(0, 0), Assets.player);
	}

	public void update() {
		player.update();
	}

	public void draw(Graphics graphics) {
		player.draw(graphics);
	}

}
