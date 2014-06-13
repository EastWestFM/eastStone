package pl.eastwestfm.eaststone.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Util {

	public static String fixColor(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg);
	}

	public static List<String> fixColor(List<String> msg) {
		List<String> s = new ArrayList<String>();
		for (String m : msg)
			s.add(fixColor(m));
		return s;
	}

	public static ItemStack getItem(Material m, String name, List<String> lore) {
		ItemStack item = new ItemStack(m, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(fixColor(name));
		meta.setLore(fixColor(lore));
		item.setItemMeta(meta);
		return item;
	}

	public static boolean checkItem(ItemStack i1, ItemStack i2) {
		if (!i1.getType().equals(i2.getType()))
			return false;
		if (i1.hasItemMeta() != i2.hasItemMeta())
			return false;
		if (i1.getItemMeta().getDisplayName() == null || i2.getItemMeta().getDisplayName() == null)
			return false;
		if (!i1.getItemMeta().getDisplayName().equalsIgnoreCase(i2.getItemMeta().getDisplayName()))
			return false;
		return true;
	}

}
