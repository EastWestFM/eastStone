package pl.eastwestfm.eaststone.database;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import pl.eastwestfm.eaststone.StonePlugin;
import pl.eastwestfm.eaststone.data.Stone;
import pl.eastwestfm.eaststone.util.Logger;

public class YAMLData implements Data {

	private String fileName;
	private YamlConfiguration data;
	
	private List<String> tempData = new ArrayList<String>();

	public YAMLData(String filename) {
		this.fileName = filename;
		this.data = YamlConfiguration.loadConfiguration(new File(StonePlugin.getInst().getDataFolder(), this.fileName + ".yml"));

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

	@Override
	public void saveStone(Stone s) {
		tempData.add(s.toString());
	}

	@Override
	public void saveStones() {
		for (Stone s : StonePlugin.getStones())
			saveStone(s);
		data.set("data.stones", tempData);
		saveData();
		tempData.clear();
	}

	@Override
	public List<Stone> loadStones() {
		List<Stone> stones = new ArrayList<Stone>();
		for (String string : this.data.getStringList("data.stones")) {
			Stone s = new Stone(string);
			StonePlugin.getStones().add(s);
		}
		return stones;
	}

	public void saveData() {
		try {
			data.save(new File(StonePlugin.getInst().getDataFolder(), this.fileName + ".yml"));
		} catch (IOException e) {
			Logger.log(Level.SEVERE, "Wystapil blad podczas zapisu pliku " + this.fileName + ".yml", "Blad: " + e.getMessage());
		}
	}

}
