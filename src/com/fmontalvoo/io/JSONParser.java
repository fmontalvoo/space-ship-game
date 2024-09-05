package com.fmontalvoo.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.fmontalvoo.Game;

public class JSONParser {
	public static List<ScoreData> readFile() throws FileNotFoundException {
		List<ScoreData> dataList = new ArrayList<>();

		File file = new File(Game.SCORE_PATH);

		if (!file.exists() || file.length() == 0) {
			return dataList;
		}

		JSONTokener parser = new JSONTokener(new FileInputStream(file));
		JSONArray jsonList = new JSONArray(parser);

		for (int i = 0; i < jsonList.length(); i++) {
			JSONObject obj = (JSONObject) jsonList.get(i);
			ScoreData data = new ScoreData();

			data.setScore(obj.getInt("score"));
			data.setDate(obj.getString("date"));

			dataList.add(data);
		}

		return dataList;
	}

	public static void writeFile(List<ScoreData> dataList) throws IOException {

		File outputFile = new File(Game.SCORE_PATH);

		outputFile.getParentFile().mkdirs();
		outputFile.createNewFile();

		JSONArray jsonList = new JSONArray();

		for (ScoreData data : dataList) {
			JSONObject obj = new JSONObject();

			obj.put("score", data.getScore());
			obj.put("date", data.getDate());

			jsonList.put(obj);
		}

		BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputFile.toURI()));
		jsonList.write(writer);
		writer.close();

	}
}
