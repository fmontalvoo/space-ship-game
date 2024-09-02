package com.fmontalvoo.entity;

import java.awt.Graphics;

import com.fmontalvoo.assets.Assets;
import com.fmontalvoo.math.Vector;

public class HUD {

	private int score;
	private final Player player;

	public HUD(int score, Player player) {
		this.score = score;
		this.player = player;
	}

	public void addScore(int value) {
		score += value;
	}

	public void drawScore(Graphics g) {
		Vector pos = new Vector(850, 25);

		String scoreToString = Integer.toString(score);

		for (int i = 0; i < scoreToString.length(); i++) {
			g.drawImage(Assets.numbers[Integer.parseInt(scoreToString.substring(i, i + 1))], (int) pos.x, (int) pos.y,
					null);
			pos.x += 20;
		}
	}

	public void drawLifes(Graphics g) {
		Vector lifePos = new Vector(25, 25);

		g.drawImage(Assets.life, (int) lifePos.x, (int) lifePos.y, null);

		g.drawImage(Assets.numbers[10], (int) (lifePos.x + 40), (int) (lifePos.y + 5), null);

		String lifesToString = Integer.toString(this.player.lifes);

		Vector pos = new Vector(lifePos.x, lifePos.y);

		for (int i = 0; i < lifesToString.length(); i++) {
			int number = Integer.parseInt(lifesToString.substring(i, i + 1));
			if (number < 0) {
				break;
			}
			g.drawImage(Assets.numbers[number], (int) (pos.x + 60), (int) (pos.y + 5), null);
			pos.x += 20;
		}
	}

}
