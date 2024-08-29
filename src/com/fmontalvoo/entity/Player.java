package com.fmontalvoo.entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.fmontalvoo.assets.Assets;
import com.fmontalvoo.input.KeyBoard;
import com.fmontalvoo.math.Vector;

public class Player extends GameObject {

	public Player(Vector position, BufferedImage image) {
		super(position, image);
	}

	@Override
	public void update() {
		if(KeyBoard.right) {
			position.x += 1;
		}
	}

	@Override
	public void draw(Graphics graphics) {
		graphics.drawImage(Assets.player, getX(), getY(), null);
	}

}
