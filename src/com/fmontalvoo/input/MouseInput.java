package com.fmontalvoo.input;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseInput extends MouseAdapter {

	public static int X, Y;
	public static boolean LEFT_BUTTON;

	@Override
	public void mousePressed(MouseEvent evt) {
		if (evt.getButton() == MouseEvent.BUTTON1) {
			LEFT_BUTTON = true;
		}
	}

	@Override
	public void mouseReleased(MouseEvent evt) {
		if (evt.getButton() == MouseEvent.BUTTON1) {
			LEFT_BUTTON = false;
		}
	}

	@Override
	public void mouseDragged(MouseEvent evt) {
		X = evt.getX();
		Y = evt.getY();
	}

	@Override
	public void mouseMoved(MouseEvent evt) {
		X = evt.getX();
		Y = evt.getY();
	}

}
