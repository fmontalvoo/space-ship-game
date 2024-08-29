package com.fmontalvoo.entity;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import com.fmontalvoo.math.Vector;

public abstract class MovingObject extends GameObject {

	protected int width;
	protected int height;
	protected int halfWidth;
	protected int halfHeight;
	protected double angle;
	protected Vector velocity;
	protected double maxVelocity;
	protected AffineTransform transform;

	public MovingObject(Vector position, Vector velocity, double maxVelocity, BufferedImage image) {
		super(position, image);

		this.angle = 0;
		this.velocity = velocity;
		this.maxVelocity = maxVelocity;
		this.width = image.getWidth();
		this.height = image.getHeight();
		this.halfWidth = this.width >> 1; // width / 2
		this.halfHeight = this.height >> 1; // height / 2
	}

}
