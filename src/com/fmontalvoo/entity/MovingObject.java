package com.fmontalvoo.entity;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import com.fmontalvoo.math.Vector;

public abstract class MovingObject extends GameObject {

	protected double angle;
	protected Vector velocity;
	protected AffineTransform transform;

	public MovingObject(Vector position, Vector velocity, BufferedImage image) {
		super(position, image);

		this.angle = 0;
		this.velocity = velocity;
	}

}
