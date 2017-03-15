package essentials.future.code.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import essentials.future.code.main.main;

public class SetWarpCommand implements CommandExecutor {

	private main plugin;
	
	public SetWarpCommand(main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(player.hasPermission("essentials.setwarp")) {
				if(args.length == 1) {
					plugin.getConfigManager().setWarp(args[0], player.getLocation());
					player.sendMessage(plugin.prefix +"§eDu hast den Warp-Punkt §c" + args[0] + " §ehinzugefügt");
				} else {
					player.sendMessage(plugin.prefix +"§c/setwarp [Namen]");
				}
			} else {
				player.sendMessage(plugin.noAccess);
			}
		} else {
			sender.sendMessage(plugin.noConsole);
		}
		return true;
	}

}