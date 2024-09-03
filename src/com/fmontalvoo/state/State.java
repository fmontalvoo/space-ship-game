package com.fmontalvoo.state;

import java.awt.Graphics;

public abstract class State {

	private static State currentState = null;

	public abstract void update();

	public abstract void draw(Graphics g);

	public static State getCurrentState() {
		return currentState;
	}

	public static void setCurrentState(State currentState) {
		State.currentState = currentState;
	}
}
