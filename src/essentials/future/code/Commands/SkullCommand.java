package essentials.future.code.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import essentials.future.code.ConfigManager.ApiManager;
import essentials.future.code.main.main;

public class SkullCommand implements CommandExecutor {

	private main plugin;
	
	public SkullCommand(main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(player.hasPermission("essentials.skull")) {
				if(args.length == 1) {
					if(!ApiManager.isInventoryFull(player)) {
						player.getInventory().addItem(ApiManager.createSkull(args[0], "§e"+args[0], 1, null, 0, null, "§eKopf von §c"+args[0]));
						player.sendMessage(plugin.prefix + "§eDu hast den Kopf von §c" + args[0] + " §ebekommen");
					} else {
						player.sendMessage(plugin.prefix + "§cDein Inventar ist voll");
					}
 				} else {
					player.sendMessage(plugin.prefix + "§c/skull <Spieler>");
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
