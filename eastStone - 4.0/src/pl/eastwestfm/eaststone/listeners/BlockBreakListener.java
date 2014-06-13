package pl.eastwestfm.eaststone.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import pl.eastwestfm.eaststone.StonePlugin;
import pl.eastwestfm.eaststone.data.Stone;
import pl.eastwestfm.eaststone.util.Util;

public class BlockBreakListener implements Listener {

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onBlockBreak(BlockBreakEvent e) {
		Stone s = StonePlugin.getData().getStone(e.getBlock().getLocation());
		if (s == null)
			return;

		if (s.getDurability() > 0) {
			if (StonePlugin.getCfg().breakingEnable) {
				if (e.getPlayer().getItemInHand().getType().equals(Material.getMaterial(StonePlugin.getCfg().breakingItem))) {
					StonePlugin.getData().deleteStone(s);
					if (StonePlugin.getCfg().breakingDrop)
						s.getLocation().getWorld().dropItemNaturally(s.getLocation(), Util.getItem(Material.getMaterial(StonePlugin.getCfg().craftingItem), StonePlugin.getCfg().craftingName, StonePlugin.getCfg().craftingLore));
					e.getPlayer().sendMessage(Util.fixColor(StonePlugin.getCfg().messageDelete));
					return;
				}
			}
			s.regen();
			s.removeDurability(1);
		} else {
			StonePlugin.getData().deleteStone(s);
			e.getPlayer().sendMessage(Util.fixColor(StonePlugin.getCfg().messageEndGenerator));
		}
	}

}
