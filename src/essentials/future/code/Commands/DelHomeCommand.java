package essentials.future.code.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import essentials.future.code.ConfigManager.PlayerManager;
import essentials.future.code.main.main;

public class DelHomeCommand implements CommandExecutor {

	private main plugin;
	
	public DelHomeCommand(main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(player.hasPermission("essentials.delhome")) {
				if(args.length == 1) {
					if(PlayerManager.homeExist(player, args[0])) {
						PlayerManager.removeHome(player, args[0]);
						player.sendMessage(plugin.prefix +"§eDein Home-Punkt §c" + args[0] + " §ewurde erfolgreich gelöscht");
					} else {
						player.sendMessage(plugin.prefix +"§cZuhause nicht gefunden");
					}
				} else {
					player.sendMessage(plugin.prefix +"§c/delhome [Namen]");
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
