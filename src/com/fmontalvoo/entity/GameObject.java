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
