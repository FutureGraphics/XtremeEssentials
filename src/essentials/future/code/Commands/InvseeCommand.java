package essentials.future.code.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import essentials.future.code.main.main;

public class InvseeCommand implements CommandExecutor {

	private main plugin;
	
	public InvseeCommand(main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(player.hasPermission("essentials.invsee")) {
				if(args.length == 1) {
					try {
						Player target = plugin.getServer().getPlayer(args[0]);
						player.openInventory(target.getInventory());
					} catch(NullPointerException e) {
						player.sendMessage(plugin.playerNotFound);
					}
				} else if(args.length == 0) {
					player.sendMessage(plugin.prefix +"§c/invsee <Spieler>");
				} else {
					player.sendMessage(plugin.prefix +"§c/invsee <Spieler>");
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
