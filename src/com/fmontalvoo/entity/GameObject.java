package com.fmontalvoo.entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.fmontalvoo.math.Vector;

public abstract class GameObject {
	protected Vector position;
	protected BufferedImage image;

	public GameObject(Vector position, BufferedImage image) {
		this.image = image;
		this.position = position;
	}

	public abstract void update();

	public abstract void draw(Graphics graphics);

	public Vector getPosition() {
		return position;
	}

	public void setPosition(Vector position) {
		this.position = position;
	}

	public int getX() {
		return (int) this.position.x;
	}

	public int getY() {
		return (int) this.position.y;
	}

}
