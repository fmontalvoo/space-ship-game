package com.fmontalvoo.assets;

import java.awt.image.BufferedImage;

public class Assets {

	public static BufferedImage player;

	// Effects
	public static BufferedImage fire;

	// Lasers
	public static BufferedImage blueLaser, greenLaser, redLaser;

	// Meteors
	public static BufferedImage[] bigs = new BufferedImage[4];
	public static BufferedImage[] meds = new BufferedImage[2];
	public static BufferedImage[] smalls = new BufferedImage[2];
	public static BufferedImage[] tinies = new BufferedImage[2];

	public static void init() {
		player = Loader.imageLoader("/ships/player.png");

		fire = Loader.imageLoader("/effects/fire.png");

		redLaser = Loader.imageLoader("/lasers/laserRed.png");
		blueLaser = Loader.imageLoader("/lasers/laserBlue.png");
		greenLaser = Loader.imageLoader("/lasers/laserGreen.png");

		for (int i = 0; i < bigs.length; i++) {
			bigs[i] = Loader.imageLoader(String.format("/meteors/big%d.png", (i + 1)));
		}

		for (int i = 0; i < meds.length; i++) {
			meds[i] = Loader.imageLoader(String.format("/meteors/med%d.png", (i + 1)));
		}

		for (int i = 0; i < smalls.length; i++) {
			smalls[i] = Loader.imageLoader(String.format("/meteors/small%d.png", (i + 1)));
		}

		for (int i = 0; i < tinies.length; i++) {
			tinies[i] = Loader.imageLoader(String.format("/meteors/tiny%d.png", (i + 1)));
		}
	}

}
