package pl.eastwestfm.eaststone.data;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.json.simple.JSONObject;

import pl.eastwestfm.eaststone.StonePlugin;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Stone {

	private String world;
	private int x;
	private int y;
	private int z;
	private int durability;
	
	
	public Stone(Location loc) {
		this.world = loc.getWorld().getName();
		this.x = loc.getBlockX();
		this.y = loc.getBlockY();
		this.z = loc.getBlockZ();
		this.durability = StonePlugin.getCfg().generatorDurability; //TODO wartosc z cfg
	}
	
	public Stone(JSONObject jsonO) {
		this.world = (String) jsonO.get("world");
		this.x = (int) (long) jsonO.get("x");
		this.y = (int) (long) jsonO.get("y");
		this.z = (int) (long) jsonO.get("z");
		this.durability = (int) (long) jsonO.get("durability");
	}
	
	public Stone(ResultSet rs) throws SQLException {
		this.world = rs.getString("world");
		this.x = rs.getInt("x");
		this.y = rs.getInt("y");
		this.z = rs.getInt("z");
		this.durability = rs.getInt("durability");
	}
	
	public Stone(String s) {
		String[] splits = s.split("|");
		this.world = splits[0];
		this.x = Integer.parseInt(splits[1]);
		this.y = Integer.parseInt(splits[2]);
		this.z = Integer.parseInt(splits[3]);
		this.durability = Integer.parseInt(splits[4]);
	}
	
	public Location getLocation() {
		return new Location(Bukkit.getWorld(this.world), this.x, this.y, this.z);
	}
	
	public void regen() {
		Bukkit.getScheduler().runTaskLater(StonePlugin.getInst(), new Runnable() {
			@Override
			public void run() {
				Block b = Bukkit.getWorld(world).getBlockAt(getLocation());
				b.setType(Material.STONE);

			}
		}, 20 * StonePlugin.getCfg().generatorRegenTime);
	}
	
	public void removeDurability(int d) {
		this.durability -= d;
	}
	
	@Override
	public String toString() {
		return this.world + "|" + this.x + "|" +this.y + "|" + this.z + "|" + this.durability;
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject toJSONObject() {
		JSONObject o = new JSONObject();
		o.put("world", this.world);
		o.put("x", this.x);
		o.put("y", this.y);
		o.put("z", this.z);
		o.put("durability", this.durability);
		return o;
	}
}
