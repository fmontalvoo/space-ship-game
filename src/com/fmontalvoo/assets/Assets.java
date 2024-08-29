package com.fmontalvoo.assets;

import java.awt.image.BufferedImage;

public class Assets {

	public static BufferedImage player;

	// Effects
	public static BufferedImage fire;

	// Lasers
	public static BufferedImage blueLaser, greenLaser, redLaser;

	public static void init() {
		player = Loader.imageLoader("/ships/player.png");

		fire = Loader.imageLoader("/effects/fire.png");
		
		redLaser = Loader.imageLoader("/lasers/laserRed.png");
		blueLaser = Loader.imageLoader("/lasers/laserBlue.png");
		greenLaser = Loader.imageLoader("/lasers/laserGreen.png");
	}

}
