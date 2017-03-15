package essentials.future.code.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import essentials.future.code.main.main;

public class EnderchestCommand implements CommandExecutor {
	
	private main plugin;
	
	public EnderchestCommand(main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(player.hasPermission("essentials.enderchest")) {
				if(args.length == 1) {
					if(player.hasPermission("essentials.enderchest.others")) {
						try {
							Player target = plugin.getServer().getPlayer(args[0]);
							player.openInventory(target.getEnderChest());
						} catch(NullPointerException e) {
							player.sendMessage(plugin.playerNotFound);
						}
					} else {
						player.sendMessage(plugin.noAccess);
					}
				} else if(args.length == 0) {
					player.openInventory(player.getEnderChest());
				} else {
					player.sendMessage(plugin.prefix + "§c/enderchset <Spieler>");
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
