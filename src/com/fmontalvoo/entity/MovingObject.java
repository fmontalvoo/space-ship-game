package com.fmontalvoo.entity;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import com.fmontalvoo.math.Vector;
import com.fmontalvoo.state.GameState;

public abstract class MovingObject extends GameObject {

	protected double angle;
	protected Vector velocity;
	protected final int width;
	protected final int height;
	protected final int halfWidth;
	protected final int halfHeight;
	protected final double maxVelocity;
	protected AffineTransform transform;

	public MovingObject(Vector position, Vector velocity, double maxVelocity, BufferedImage image, GameState state) {
		super(position, image, state);

		this.angle = 0;
		this.velocity = velocity;
		this.maxVelocity = maxVelocity;
		this.width = image.getWidth();
		this.height = image.getHeight();
		this.halfWidth = this.width >> 1; // width / 2
		this.halfHeight = this.height >> 1; // height / 2
	}

}
