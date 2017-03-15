package essentials.future.code.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import essentials.future.code.main.main;

public class DelWarpCommand implements CommandExecutor {

	private main plugin;
	
	public DelWarpCommand(main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(player.hasPermission("essentials.delwarp")) {
				if(args.length == 1) {
					if(plugin.getConfigManager().warpExist(args[0])) {
						plugin.getConfigManager().removeWarp(args[0]);
						player.sendMessage(plugin.prefix +"§eWarp-Punkt §c" + args[0] + " §ewurde erfolgreich entfernt");
					} else {
						player.sendMessage(plugin.prefix +"§cWarp konnte nicht gefunden werden");
					}
				} else {
					player.sendMessage(plugin.prefix +"§c/delwarp [Namen]");
				}
			} else {
				player.sendMessage(plugin.noAccess);
			}
		} else {
			if(args.length == 1) {
				if(plugin.getConfigManager().warpExist(args[0])) {
					plugin.getConfigManager().removeWarp(args[0]);
					sender.sendMessage(plugin.prefix +"§eWarp-Punkt §c" + args[0] + " §ewurde erfolgreich entfernt");
				} else {
					sender.sendMessage(plugin.prefix +"§cWarp konnte nicht gefunden werden");
				}
			} else {
				sender.sendMessage(plugin.prefix +"§c/delwarp [Namen]");
			}
		}
		return true;
	}

}
