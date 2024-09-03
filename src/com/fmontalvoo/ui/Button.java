package com.fmontalvoo.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.fmontalvoo.assets.Assets;
import com.fmontalvoo.input.MouseInput;
import com.fmontalvoo.math.Vector;
import com.fmontalvoo.util.Text;

public class Button {

	private boolean mouseIn;
	private final String text;
	private final Action action;
	private final Rectangle boundingBox;
	private final BufferedImage mouseInImg;
	private final BufferedImage mouseOutImg;

	public Button(int x, int y, String text, Action action, BufferedImage mouseInImg, BufferedImage mouseOutImg) {
		this.text = text;
		this.action = action;
		this.mouseInImg = mouseInImg;
		this.mouseOutImg = mouseOutImg;

		this.boundingBox = new Rectangle(x, y, this.mouseInImg.getWidth(), this.mouseInImg.getHeight());
	}

	public void update() {
		if (boundingBox.contains(MouseInput.X, MouseInput.Y)) {
			mouseIn = true;
		} else {
			mouseIn = false;
		}

		if (mouseIn && MouseInput.LEFT_BUTTON) {
			action.onClick();
		}
	}

	public void draw(Graphics g) {
		if (mouseIn) {
			g.drawImage(mouseInImg, boundingBox.x, boundingBox.y, null);
		} else {
			g.drawImage(mouseOutImg, boundingBox.x, boundingBox.y, null);
		}

		Text.drawText(g, text, new Vector(boundingBox.getX() + boundingBox.getWidth() / 2,
				boundingBox.getY() + boundingBox.getHeight()), Color.BLACK, Assets.fontMed, true);

	}

}
