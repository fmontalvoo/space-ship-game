package com.fmontalvoo.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyBoard implements KeyListener {

	private static final int NUMBER_OF_KEYS = 50;
	private final boolean[] keys = new boolean[NUMBER_OF_KEYS];

	public static boolean up, down, left, right, fire;

	public KeyBoard() {
		up = false;
		down = false;
		left = false;
		right = false;
		fire = false;
	}

	public void update() {
		up = keys[KeyEvent.VK_UP];
		down = keys[KeyEvent.VK_DOWN];
		left = keys[KeyEvent.VK_LEFT];
		right = keys[KeyEvent.VK_RIGHT];
		fire = keys[KeyEvent.VK_SPACE];
	}

	@Override
	public void keyPressed(KeyEvent evt) {
		keys[evt.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent evt) {
		keys[evt.getKeyCode()] = false;
	}

	@Override
	public void keyTyped(KeyEvent evt) {

	}

}
