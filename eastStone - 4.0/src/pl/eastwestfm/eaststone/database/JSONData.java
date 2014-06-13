package pl.eastwestfm.eaststone.database;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import pl.eastwestfm.eaststone.StonePlugin;
import pl.eastwestfm.eaststone.data.Stone;

public class JSONData implements Data {

	private static JSONArray tempData = new JSONArray();
	
	private File jsonFile;
	private JSONParser jsonParser;
		
	public JSONData(String filename) {
		this.jsonFile = new File(StonePlugin.getInst().getDataFolder(), filename + ".json");
		this.jsonParser = new JSONParser();
		if(!this.jsonFile.exists()) {
			try {
				this.jsonFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		Bukkit.getScheduler().runTaskTimerAsynchronously(StonePlugin.getInst(), new Runnable() {

			@Override
			public void run() {
				saveStones();
			}

		}, 20 * 10, 20 * 60 * StonePlugin.getCfg().databaseAutoSaveTime);
	}
	
	@Override
	public void createStone(Location loc) {
		Stone s = new Stone(loc);
		StonePlugin.getStones().add(s);
	}

	@Override
	public void deleteStone(Stone s) {
		StonePlugin.getStones().remove(s);
	}

	@Override
	public Stone getStone(Location loc) {
		for (Stone s : StonePlugin.getStones())
			if (s.getLocation().equals(loc))
				return s;
		return null;
	}

	@Override
	public boolean isStone(Location loc) {
		for (Stone s : StonePlugin.getStones())
			if (s.getLocation().equals(loc))
				return true;
		return false;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void saveStone(Stone s) {
		tempData.add(s.toJSONObject());
	}

	@Override
	public void saveStones() {
		for (Stone s : StonePlugin.getStones())
			saveStone(s);
		saveData();
		tempData.clear();
	}

	@Override
	public List<Stone> loadStones() {
		List<Stone> stones = new ArrayList<Stone>();
		try {
			Object o = jsonParser.parse(new FileReader(jsonFile.getAbsolutePath()));
			JSONArray jsonArray = (JSONArray) o;
			for(Object ob : jsonArray) {
				JSONObject jsonO = (JSONObject) ob;
				Stone s = new Stone(jsonO);
				stones.add(s);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return stones;
	}
	
	public void saveData() {
		FileWriter f;
		try {
			f = new FileWriter(this.jsonFile.getAbsolutePath());
			f.write(tempData.toJSONString());
			f.flush();
			f.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
