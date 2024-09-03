package com.fmontalvoo.state;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.logging.Logger;

import com.fmontalvoo.Game;
import com.fmontalvoo.assets.Assets;
import com.fmontalvoo.assets.Loader;
import com.fmontalvoo.math.Vector;
import com.fmontalvoo.util.Text;

public class LoadingState extends State {

	private Font font;
	private Thread loading;

	private static final int LOADING_BAR_WIDTH = 500;
	private static final int LOADING_BAR_HEIGHT = 50;
	private static final Logger log = Logger.getLogger(LoadingState.class.getName());

	public LoadingState(Thread loading) {
		this.loading = loading;
		this.loading.start();
		this.font = Loader.loadFont("/fonts/futureFont.ttf", 38);
	}

	@Override
	public void update() {
		if (Assets.loaded) {
			try {
				State.setCurrentState(new MenuState());
				loading.join();
			} catch (InterruptedException ex) {
				log.severe(ex.getMessage());
			}
		}
	}

	@Override
	public void draw(Graphics g) {
		int halfWindowWidth = Game.WIDTH >> 1;
		int halfWindowHeight = Game.HEIGHT >> 1;

		int halfBarWidth = LOADING_BAR_WIDTH >> 1;
		int halfBarHeight = LOADING_BAR_HEIGHT >> 1;

		GradientPaint gradient = new GradientPaint(
				(halfWindowWidth - halfBarWidth),
				(halfWindowHeight - halfBarHeight), 
				Color.WHITE, 
				(halfWindowWidth + halfBarWidth),
				(halfWindowHeight + halfBarHeight), 
				Color.BLUE);

		Graphics2D g2d = (Graphics2D) g;

		g2d.setPaint(gradient);

		float percentage = (Assets.count / Assets.MAX_COUNT);

		g2d.fillRect((halfWindowWidth - halfBarWidth), (halfWindowHeight - halfBarHeight),
				(int) (LOADING_BAR_WIDTH * percentage), LOADING_BAR_HEIGHT);

		g2d.drawRect((halfWindowWidth - halfBarWidth), (halfWindowHeight - halfBarHeight), LOADING_BAR_WIDTH,
				LOADING_BAR_HEIGHT);

		Text.drawText(g2d, "SPACE SHIP GAME", new Vector(halfWindowWidth, halfWindowHeight - 50), Color.WHITE, font,
				true);
		Text.drawText(g2d, "LOADING...", new Vector(halfWindowWidth, halfWindowHeight + 40), Color.WHITE, font, true);
	}

}
