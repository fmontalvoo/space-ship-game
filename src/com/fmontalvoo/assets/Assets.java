package com.fmontalvoo.assets;

import java.awt.Font;
import java.awt.image.BufferedImage;

import javax.sound.sampled.Clip;

public class Assets {

	public static float count = 0;
	public static boolean loaded = false;
	public static final float MAX_COUNT = 46;

	// Ships
	public static BufferedImage player;
	public static BufferedImage ufo;

	// Effects
	public static BufferedImage fire;

	// Lasers
	public static BufferedImage blueLaser, greenLaser, redLaser;

	// Meteors
	public static final BufferedImage[] bigs = new BufferedImage[4];
	public static final BufferedImage[] meds = new BufferedImage[2];
	public static final BufferedImage[] smalls = new BufferedImage[2];
	public static final BufferedImage[] tinies = new BufferedImage[2];

	// Explosion
	public static final BufferedImage[] explosions = new BufferedImage[9];

	// Numbers
	public static final BufferedImage[] numbers = new BufferedImage[11];

	// Others
	public static BufferedImage life;

	// Fonts
	public static Font fontBig;
	public static Font fontMed;

	// UI
	public static BufferedImage blueBtn;
	public static BufferedImage greyBtn;

	// Sounds
	public static Clip backgroundMusic, explosion, playerShoot, playerLoose, ufoShoot;

	public static void init() {
		ufo = loadImage("/ships/ufo.png");
		player = loadImage("/ships/player.png");

		fire = loadImage("/effects/fire.png");

		redLaser = loadImage("/lasers/laserRed.png");
		blueLaser = loadImage("/lasers/laserBlue.png");
		greenLaser = loadImage("/lasers/laserGreen.png");

		life = loadImage("/other/life.png");

		blueBtn = loadImage("/ui/blue_button.png");
		greyBtn = loadImage("/ui/grey_button.png");

		fontBig = loadFont("/fonts/futureFont.ttf", 42);
		fontMed = loadFont("/fonts/futureFont.ttf", 20);

		for (int i = 0; i < bigs.length; i++) {
			bigs[i] = loadImage(String.format("/meteors/big%d.png", (i + 1)));
		}

		for (int i = 0; i < meds.length; i++) {
			meds[i] = loadImage(String.format("/meteors/med%d.png", (i + 1)));
		}

		for (int i = 0; i < smalls.length; i++) {
			smalls[i] = loadImage(String.format("/meteors/small%d.png", (i + 1)));
		}

		for (int i = 0; i < tinies.length; i++) {
			tinies[i] = loadImage(String.format("/meteors/tiny%d.png", (i + 1)));
		}

		for (int i = 0; i < explosions.length; i++) {
			explosions[i] = loadImage(String.format("/explosion/%d.png", i));
		}

		for (int i = 0; i < numbers.length; i++) {
			numbers[i] = loadImage(String.format("/numbers/%d.png", i));
		}

		ufoShoot = loadSound("/sounds/ufoShoot.wav");
		explosion = loadSound("/sounds/explosion.wav");
		playerShoot = loadSound("/sounds/playerShoot.wav");
		playerLoose = loadSound("/sounds/playerLoose.wav");
		backgroundMusic = loadSound("/sounds/backgroundMusic.wav");

		loaded = true;
	}

	public static BufferedImage loadImage(String path) {
		count++;
		return Loader.loadImage(path);
	}

	public static Font loadFont(String path, int size) {
		count++;
		return Loader.loadFont(path, size);
	}

	public static Clip loadSound(String path) {
		count++;
		return Loader.loadSound(path);
	}

}
