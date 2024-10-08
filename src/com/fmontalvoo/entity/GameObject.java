package com.fmontalvoo.entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.fmontalvoo.math.Vector;
import com.fmontalvoo.state.GameState;

public abstract class GameObject {

	protected Vector position;
	protected final GameState state;
	protected final BufferedImage image;

	protected final int width;
	protected final int height;
	protected final int halfWidth;
	protected final int halfHeight;

	public GameObject(Vector position, BufferedImage image, GameState state) {
		this.image = image;
		this.state = state;
		this.position = position;

		this.width = image.getWidth();
		this.height = image.getHeight();
		this.halfWidth = this.width >> 1; // width / 2
		this.halfHeight = this.height >> 1; // height / 2
	}

	public abstract void update(float dt);

	public abstract void draw(Graphics graphics);

	public Vector getPosition() {
		return position;
	}

	public void setPosition(Vector position) {
		this.position = position;
	}

	public void setX(double x) {
		this.position.x = x;
	}

	public double getX() {
		return this.position.x;
	}

	public void setY(double y) {
		this.position.y = y;
	}

	public double getY() {
		return this.position.y;
	}

}
