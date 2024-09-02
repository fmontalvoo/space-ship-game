package com.fmontalvoo.assets;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

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

}
