package pl.eastwestfm.eaststone.database;

import java.util.List;

import org.bukkit.Location;

import pl.eastwestfm.eaststone.data.Stone;

public interface Data {

	public void createStone(Location loc);
	
	public void deleteStone(Stone s);
	
	public Stone getStone(Location loc);
	
	public boolean isStone(Location loc);
	
	public void saveStone(Stone s);
	
	public void saveStones();
		
	public List<Stone> loadStones();
	
}
