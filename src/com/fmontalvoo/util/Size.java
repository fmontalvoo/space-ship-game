package com.fmontalvoo.util;

import java.awt.image.BufferedImage;

import com.fmontalvoo.assets.Assets;

public enum Size {
	BIG(2, Assets.bigs), MED(2, Assets.smalls), SMALL(2, Assets.tinies), TINY(0, null);

	public int quantity;

	public BufferedImage[] images;

	private Size(int quantity, BufferedImage[] images) {
		this.quantity = quantity;
		this.images = images;
	}

}
