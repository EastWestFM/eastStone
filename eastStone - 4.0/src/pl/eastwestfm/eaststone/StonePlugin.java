package pl.eastwestfm.eaststone;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import lombok.Getter;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import pl.eastwestfm.eaststone.commands.StoneReloadCommand;
import pl.eastwestfm.eaststone.data.Stone;
import pl.eastwestfm.eaststone.database.Data;
import pl.eastwestfm.eaststone.database.DataMode;
import pl.eastwestfm.eaststone.database.JSONData;
import pl.eastwestfm.eaststone.database.SQLData;
import pl.eastwestfm.eaststone.database.YAMLData;
import pl.eastwestfm.eaststone.listeners.BlockBreakListener;
import pl.eastwestfm.eaststone.listeners.BlockPlaceListener;
import pl.eastwestfm.eaststone.listeners.EntityExplodeListener;
import pl.eastwestfm.eaststone.listeners.PistonExtendListener;
import pl.eastwestfm.eaststone.util.Config;
import pl.eastwestfm.eaststone.util.Crafting;
import pl.eastwestfm.eaststone.util.Logger;

public class StonePlugin extends JavaPlugin {

	@Getter
	private static StonePlugin inst;

	static @Getter
	private Data data;
	
	static @Getter
	private Config cfg;

	@Getter
	private static List<Stone> stones = new ArrayList<Stone>();


	@Override
	public void onEnable() {
		inst = this;
		this.saveDefaultConfig();
		cfg = new Config();
		
		switch (DataMode.getDataMode(cfg.databaseMode)) {
		case YAML:
			data = new YAMLData(cfg.databaseName);
			break;
		case SQLITE:
			data = new SQLData(cfg.databaseName);
			break;
		case JSON:
			data = new JSONData(cfg.databaseName);
			break;
		default:
			Logger.log(Level.WARNING, "Wybrano bledny tryb zapisu danych!", "Rozpoczeto uzywanie zapisu YAML!");
			data = new YAMLData(cfg.databaseName);
			break;
		}
		
		stones = data.loadStones();

		Bukkit.getPluginManager().registerEvents(new BlockPlaceListener(), this);
		Bukkit.getPluginManager().registerEvents(new BlockBreakListener(), this);
		Bukkit.getPluginManager().registerEvents(new EntityExplodeListener(), this);
		Bukkit.getPluginManager().registerEvents(new PistonExtendListener(), this);
		
		this.getCommand("stonereload").setExecutor(new StoneReloadCommand());
		
		Crafting.createRecipe();
	}

	@Override
	public void onDisable() {

		data.saveStones();

	}

}
