package com.fmontalvoo.assets;

import java.awt.image.BufferedImage;

public class Assets {

	public static BufferedImage player;

	public static void init() {
		player = Loader.imageLoader("/ships/player.png");
	}

}
