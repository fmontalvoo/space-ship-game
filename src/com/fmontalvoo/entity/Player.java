package com.fmontalvoo.entity;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import com.fmontalvoo.assets.Assets;
import com.fmontalvoo.input.KeyBoard;
import com.fmontalvoo.math.Vector;

public class Player extends MovingObject {

	private Vector heading;

	public Player(Vector position, Vector velocity, BufferedImage image) {
		super(position, velocity, image);
		heading = new Vector(0, 1);
	}

	@Override
	public void update() {
		if (KeyBoard.right) {
			angle += Math.PI / 20;
		}

		if (KeyBoard.left) {
			angle -= Math.PI / 20;
		}

		heading = heading.dir(angle - Math.PI / 2);
	}

	@Override
	public void draw(Graphics graphics) {

		Graphics2D graphics2d = (Graphics2D) graphics;

		transform = AffineTransform.getTranslateInstance(position.x, position.y);

		transform.rotate(angle, Assets.player.getWidth() / 2, Assets.player.getHeight() / 2);

		graphics2d.drawImage(Assets.player, transform, null);

	}

}
