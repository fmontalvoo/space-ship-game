package com.fmontalvoo.state;

import java.awt.Color;
import java.awt.Graphics;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.logging.Logger;

import com.fmontalvoo.Game;
import com.fmontalvoo.assets.Assets;
import com.fmontalvoo.io.JSONParser;
import com.fmontalvoo.io.ScoreData;
import com.fmontalvoo.math.Vector;
import com.fmontalvoo.ui.Action;
import com.fmontalvoo.ui.Button;
import com.fmontalvoo.util.Text;

public class ScoreState extends State {

	private final Button backButton;
	private ScoreData[] auxArray;
	private final PriorityQueue<ScoreData> highScores;
	private final Comparator<ScoreData> scoreComparator;

	private static final String DATE = "DATE";
	private static final String SCORE = "SCORE";
	private static final String BACK = "GO BACK";

	private static final Logger log = Logger.getLogger(ScoreState.class.getName());

	public ScoreState() {

		this.backButton = new Button(Assets.greyBtn.getHeight(), (Game.HEIGHT - (2 * Assets.greyBtn.getHeight())), BACK,
				new Action() {
					@Override
					public void onClick() {
						State.setCurrentState(new MenuState());
					}
				}, Assets.blueBtn, Assets.greyBtn);

		this.scoreComparator = new Comparator<ScoreData>() {
			@Override
			public int compare(ScoreData e1, ScoreData e2) {
				return e1.getScore() < e2.getScore() ? -1 : e1.getScore() > e2.getScore() ? 1 : 0;
			}
		};

		this.highScores = new PriorityQueue<ScoreData>(10, scoreComparator);

		try {
			List<ScoreData> scoreList = JSONParser.readFile();

			for (ScoreData score : scoreList) {
				highScores.add(score);
			}

			while (highScores.size() > 10) {
				highScores.poll();
			}

		} catch (FileNotFoundException ex) {
			log.severe(ex.getMessage());
		}
	}

	@Override
	public void update(float dt) {
		backButton.update();
	}

	@Override
	public void draw(Graphics g) {
		backButton.draw(g);

		auxArray = highScores.toArray(new ScoreData[highScores.size()]);

		Arrays.sort(auxArray, scoreComparator);

		Vector scorePos = new Vector(Game.WIDTH / 2 - 200, 100);
		Vector datePos = new Vector(Game.WIDTH / 2 + 200, 100);

		Text.drawText(g, SCORE, scorePos, Color.BLUE, Assets.fontBig, true);
		Text.drawText(g, DATE, datePos, Color.BLUE, Assets.fontBig, true);

		scorePos.y += 40;
		datePos.y += 40;

		for (int i = auxArray.length - 1; i > -1; i--) {
			ScoreData d = auxArray[i];

			Text.drawText(g, Integer.toString(d.getScore()), scorePos, Color.WHITE, Assets.fontMed, true);
			Text.drawText(g, d.getDate(), datePos, Color.WHITE, Assets.fontMed, true);

			scorePos.y += 40;
			datePos.y += 40;
		}
	}

}
