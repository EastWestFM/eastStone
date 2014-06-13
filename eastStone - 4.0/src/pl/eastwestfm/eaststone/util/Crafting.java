package pl.eastwestfm.eaststone.util;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ShapedRecipe;

import pl.eastwestfm.eaststone.StonePlugin;

public class Crafting {

	private static Config c = StonePlugin.getCfg();
	
	@SuppressWarnings("deprecation")
	public static void createRecipe() {
		
		ShapedRecipe r = new ShapedRecipe(Util.getItem(Material.getMaterial(c.craftingItem), c.craftingName, c.craftingLore));
		r.shape("123", "456", "789");
		r.setIngredient('1', Material.getMaterial(c.craftingSlot_1));
		r.setIngredient('2', Material.getMaterial(c.craftingSlot_2));
		r.setIngredient('3', Material.getMaterial(c.craftingSlot_3));
		r.setIngredient('4', Material.getMaterial(c.craftingSlot_4));
		r.setIngredient('5', Material.getMaterial(c.craftingSlot_5));
		r.setIngredient('6', Material.getMaterial(c.craftingSlot_6));
		r.setIngredient('7', Material.getMaterial(c.craftingSlot_7));
		r.setIngredient('8', Material.getMaterial(c.craftingSlot_8));
		r.setIngredient('9', Material.getMaterial(c.craftingSlot_9));
		
		Bukkit.addRecipe(r);
		
	}
	
}
