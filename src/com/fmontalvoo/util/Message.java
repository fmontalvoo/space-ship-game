package com.fmontalvoo.util;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import com.fmontalvoo.math.Vector;
import com.fmontalvoo.state.GameState;

public class Message {

	private float alpha;
	private boolean fade;
	private final Font font;
	private final String text;
	private final Color color;
	private final boolean center;
	private final GameState state;
	private final Vector position;

	private static final float DELATA_ALPHA = 0.01f;

	public Message(Vector position, String text, Font font, Color color, boolean center, boolean fade,
			GameState state) {
		this.text = text;
		this.font = font;
		this.fade = fade;
		this.color = color;
		this.state = state;
		this.center = center;
		this.position = position;

		if (this.fade) {
			this.alpha = 1;
		} else {
			this.alpha = 0;
		}
	}

	public void draw(Graphics2D g2d) {
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		Font originalFont = g2d.getFont();
		Text.drawText(g2d, text, position, color, font, center);
		g2d.setFont(originalFont);
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));

		position.y -= 1;

		if (fade) {
			alpha -= DELATA_ALPHA;
		} else {
			alpha += DELATA_ALPHA;
		}

		if (fade && alpha < 0) {
			state.getMessages().remove(this);
		}

		if (!fade && alpha > 1) {
			alpha = 1;
			fade = true;
		}
	}

}
