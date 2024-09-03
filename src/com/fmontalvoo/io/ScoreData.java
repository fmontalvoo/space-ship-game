package com.fmontalvoo.io;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ScoreData {

	private int score;
	private String date;

	public ScoreData() {

	}

	public ScoreData(int score) {
		this.score = score;

		Date today = new Date(System.currentTimeMillis());
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		date = format.format(today);

	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

}
