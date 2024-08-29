package com.fmontalvoo.assets;

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

}
