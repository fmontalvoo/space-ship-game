package com.fmontalvoo.assets;

import java.awt.image.BufferedImage;

public class Assets {

	public static BufferedImage player;
	
	// Effects
	public static BufferedImage fire;

	public static void init() {
		player = Loader.imageLoader("/ships/player.png");
		
		fire = Loader.imageLoader("/effects/fire.png");
	}

}
