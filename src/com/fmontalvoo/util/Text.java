package com.fmontalvoo.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import com.fmontalvoo.math.Vector;

public class Text {

	public static void drawText(Graphics g, String text, Vector pos, Color color, Font font, boolean center) {
		g.setColor(color);
		g.setFont(font);

		if (center) {
			FontMetrics fm = g.getFontMetrics();
			pos = Vector.sub(pos, new Vector(fm.stringWidth(text) / 2, fm.getHeight() / 2));
		}

		g.drawString(text, (int) pos.x, (int) pos.y);
	}
}
