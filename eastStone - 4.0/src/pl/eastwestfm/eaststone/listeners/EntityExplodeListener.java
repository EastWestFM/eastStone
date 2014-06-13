package pl.eastwestfm.eaststone.listeners;

import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

import pl.eastwestfm.eaststone.StonePlugin;
import pl.eastwestfm.eaststone.data.Stone;

public class EntityExplodeListener implements Listener {

	@EventHandler
	public void onExplode(EntityExplodeEvent e) {
		List<Block> blocks = e.blockList();
		if (!(blocks.isEmpty())) {
			for (Block b : blocks) {
				Stone s = StonePlugin.getData().getStone(b.getLocation());
				if (s != null)
					StonePlugin.getData().deleteStone(s);
			}
		}
	}
}
