package pl.eastwestfm.eaststone.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import pl.eastwestfm.eaststone.StonePlugin;

public class StoneReloadCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!sender.hasPermission("eaststone.reload")) {
			sender.sendMessage("§cNie masz praw do tej komendy! §7(eaststone.reload)");
			return false;
		}
		StonePlugin.getCfg().reloadConfig();
		sender.sendMessage("§aPrzeladowano plik konfiguracyjny!");
		return false;
	}

}
