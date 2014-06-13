package pl.eastwestfm.eaststone.util;

import java.util.logging.Level;

import org.bukkit.Bukkit;

public class Logger {

	public static void log(Level level, String... args) {
		Bukkit.getLogger().log(level, "-------------[ eastStone ]-------------");
		for (String a : args)
			Bukkit.getLogger().log(level, " > " + a);
		Bukkit.getLogger().log(level, "---------------------------------------");
	}
	

}
