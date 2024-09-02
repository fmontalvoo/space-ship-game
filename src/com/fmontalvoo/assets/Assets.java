package com.fmontalvoo.assets;

import java.awt.Font;
import java.awt.image.BufferedImage;

public class Assets {

	// Ships
	public static BufferedImage player;
	public static BufferedImage ufo;

	// Effects
	public static BufferedImage fire;

	// Lasers
	public static BufferedImage blueLaser, greenLaser, redLaser;

	// Meteors
	public final static BufferedImage[] bigs = new BufferedImage[4];
	public final static BufferedImage[] meds = new BufferedImage[2];
	public final static BufferedImage[] smalls = new BufferedImage[2];
	public final static BufferedImage[] tinies = new BufferedImage[2];

	// Explosion
	public final static BufferedImage[] explosion = new BufferedImage[9];

	// Numbers
	public final static BufferedImage[] numbers = new BufferedImage[11];

	// Others
	public static BufferedImage life;

	// Fonts
	public static Font fontBig;
	public static Font fontMed;

	public static void init() {
		player = Loader.imageLoader("/ships/player.png");
		ufo = Loader.imageLoader("/ships/ufo.png");

		fire = Loader.imageLoader("/effects/fire.png");

		redLaser = Loader.imageLoader("/lasers/laserRed.png");
		blueLaser = Loader.imageLoader("/lasers/laserBlue.png");
		greenLaser = Loader.imageLoader("/lasers/laserGreen.png");

		life = Loader.imageLoader("/other/life.png");

		fontBig = Loader.loadFont("/fonts/futureFont.ttf", 42);
		fontMed = Loader.loadFont("/fonts/futureFont.ttf", 20);

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

		for (int i = 0; i < explosion.length; i++) {
			explosion[i] = Loader.imageLoader(String.format("/explosion/%d.png", i));
		}

		for (int i = 0; i < numbers.length; i++) {
			numbers[i] = Loader.imageLoader(String.format("/numbers/%d.png", i));
		}
	}

}
