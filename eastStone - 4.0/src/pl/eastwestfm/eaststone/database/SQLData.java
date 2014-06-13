package pl.eastwestfm.eaststone.database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import pl.eastwestfm.eaststone.StonePlugin;
import pl.eastwestfm.eaststone.data.Stone;
import pl.eastwestfm.eaststone.util.Logger;

public class SQLData implements Data {

	private Connection conn = null;
	private Statement stmt = null;

	public SQLData(String database) {
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:" + StonePlugin.getInst().getDataFolder() + File.separator + database + ".db");
		} catch (ClassNotFoundException e) {
			Logger.log(Level.SEVERE, "Nie znaleziono sterownika JDBC!", "Blad: " + e.getMessage());
		} catch (SQLException e) {
			Logger.log(Level.SEVERE, "Wystapil blad podczas nawiazywania polaczenia z SQLite!", "Blad: " + e.getMessage());
		}

		try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			Logger.log(Level.SEVERE, "Wystapil blad podczas tworzenia Statement!", "Blad: " + e.getMessage());
		}
		try {
			stmt.execute("CREATE TABLE IF NOT EXISTS stones ( " + "id         INTEGER         PRIMARY KEY AUTOINCREMENT," + "world      VARCHAR( 255 )," + "x          INT( 10 )," + "y          INT( 10 )," + "z          INT( 10 )," + "durability INT( 10 ));)");
		} catch (SQLException e) {
			e.printStackTrace();
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
		update("INSERT INTO `stones` VALUES(NULL, '" + s.getWorld() + "', '" + s.getX() + "', '" + s.getY() + "', '" + s.getZ() + "', '" + s.getDurability() + "')");
	}

	@Override
	public void deleteStone(Stone s) {
		StonePlugin.getStones().remove(s);
		update("DELETE FROM `stones` WHERE `world`='" + s.getWorld() + "' AND `x`='" + s.getX() + "' AND `y`='" + s.getY() + "' AND `z`='" + s.getZ() + "'");

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
		update("UPDATE `stones` SET `durability`='" + s.getDurability() + "' WHERE `world`='" + s.getWorld() + "' AND `x`='" + s.getX() + "' AND `y`='" + s.getY() + "' AND `z`='" + s.getZ() + "'");
	}

	@Override
	public void saveStones() {
		try {
			conn.setAutoCommit(false);
			for (Stone s : StonePlugin.getStones()) {
				String q = "UPDATE `stones` SET `durability`='" + s.getDurability() + "' WHERE `world`='" + s.getWorld() + "' AND `x`='" + s.getX() + "' AND `y`='" + s.getY() + "' AND `z`='" + s.getZ() + "'";
				stmt.addBatch(q);
			}
			stmt.executeBatch();
			conn.commit();
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Stone> loadStones() {
		ResultSet rs = query("SELECT * FROM `stones`");
		List<Stone> stones = new ArrayList<Stone>();
		try {
			while (rs.next()) {
				Stone s = new Stone(rs);
				//System.out.println(s.toString());
				stones.add(s);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return stones;
	}

	public ResultSet query(String query) {
		//Logger.log(Level.INFO, query);
		try {
			return stmt.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void update(String update) {
		//Logger.log(Level.INFO, update);
		try {
			stmt.executeUpdate(update);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
