package com.fmontalvoo.state;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import com.fmontalvoo.Game;
import com.fmontalvoo.assets.Assets;
import com.fmontalvoo.ui.Action;
import com.fmontalvoo.ui.Button;

public class MenuState extends State {

	private static final String PLAY = "PLAY";
	private static final String EXIT = "EXIT";

	private final List<Button> buttons = new ArrayList<>();

	public MenuState() {
		int width = Assets.greyBtn.getWidth();
		int height = Assets.greyBtn.getHeight();
		int halfWidth = width >> 1;
		int halfHeight = height >> 1;

		buttons.add(new Button((Game.WIDTH >> 1) - halfWidth, (Game.HEIGHT >> 1) - height, PLAY, new Action() {
			@Override
			public void onClick() {
				State.setCurrentState(new GameState());

			}
		}, Assets.blueBtn, Assets.greyBtn));

		buttons.add(new Button((Game.WIDTH >> 1) - halfWidth, (Game.HEIGHT >> 1) + halfHeight, EXIT, new Action() {
			@Override
			public void onClick() {
				System.exit(0);

			}
		}, Assets.blueBtn, Assets.greyBtn));
	}

	@Override
	public void update() {
		for (Button button : buttons) {
			button.update();
		}
	}

	@Override
	public void draw(Graphics g) {
		for (Button button : buttons) {
			button.draw(g);
		}
	}

}
