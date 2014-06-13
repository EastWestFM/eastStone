package pl.eastwestfm.eaststone.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import pl.eastwestfm.eaststone.StonePlugin;
import pl.eastwestfm.eaststone.data.Stone;
import pl.eastwestfm.eaststone.util.Util;

public class BlockPlaceListener implements Listener {

	@EventHandler(ignoreCancelled=true, priority=EventPriority.HIGHEST)
	public void onBlockPlace(BlockPlaceEvent e) {

		ItemStack c = Util.getItem(Material.getMaterial(StonePlugin.getCfg().craftingItem), StonePlugin.getCfg().craftingName, StonePlugin.getCfg().craftingLore);
		ItemStack h = e.getItemInHand();

		// if (!c.equals(h))
		// return;
		if (!Util.checkItem(c, h))
			return;

		Player p = e.getPlayer();
		Stone s = StonePlugin.getData().getStone(e.getBlock().getLocation());
		if (s != null) {
			s.regen();
			p.sendMessage(Util.fixColor(StonePlugin.getCfg().messageCantCreate));
			e.setCancelled(true);
			return;
		}
		StonePlugin.getData().createStone(e.getBlock().getLocation());
		e.getBlock().setType(Material.STONE);
		p.sendMessage(Util.fixColor(StonePlugin.getCfg().messageCreate));

	}

}
