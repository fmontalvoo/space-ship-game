package com.fmontalvoo.assets;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Loader {

	private static final Logger log = Logger.getLogger(Loader.class.getName());

	public static BufferedImage imageLoader(String path) {
		try {
			return ImageIO.read(Loader.class.getResource(path));
		} catch (IOException ex) {
			log.severe(ex.getMessage());
		}
		return null;
	}

	public static Font loadFont(String path, int size) {
		try {
			return Font.createFont(Font.TRUETYPE_FONT, Loader.class.getResourceAsStream(path)).deriveFont(Font.PLAIN,
					size);
		} catch (FontFormatException | IOException ex) {
			log.severe(ex.getMessage());
		}
		return null;
	}

	public static Clip loadSound(String path) {
		Clip clip = null;
		try {
			clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(Loader.class.getResource(path)));
		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
			log.severe(ex.getMessage());
		}
		return clip;
	}

}
