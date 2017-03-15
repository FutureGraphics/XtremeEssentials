package essentials.future.code.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import essentials.future.code.main.main;
import essentials.future.code.mysql.MySQLPlayer;

public class MoneyCommand implements CommandExecutor {

	private main plugin;

	public MoneyCommand(main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (player.hasPermission("essentials.money")) {
				if (args.length == 1) {
					if (player.hasPermission("essentials.money.others")) {
						if (MySQLPlayer.userExistsByName(args[0])) {
							player.sendMessage(plugin.prefix + "§eCoins von §c" + args[0] + " §e: "
									+ MySQLPlayer.getCoins(MySQLPlayer.getUUIDFromName(args[0])));
						} else {
							player.sendMessage(plugin.playerNotFound);
						}
					} else {
						player.sendMessage(plugin.noAccess);
					}
				} else if (args.length == 0) {
					player.sendMessage(plugin.prefix + "§eCoins: §c" + MySQLPlayer.getCoins(player.getUniqueId().toString()));
				} else {
					sender.sendMessage(plugin.prefix + "§c/money <Spieler>");
				}
			} else {
				player.sendMessage(plugin.noAccess);
			}
		} else {
			if (args.length == 1) {
				if (sender.hasPermission("essentials.money.others")) {
					if (MySQLPlayer.userExistsByName(args[0])) {
						sender.sendMessage(plugin.prefix + "§eCoins von §c" + args[0] + " §e: "
								+ MySQLPlayer.getCoins(MySQLPlayer.getUUIDFromName(args[0])));
					} else {
						sender.sendMessage(plugin.playerNotFound);
					}
				} else {
					sender.sendMessage(plugin.noAccess);
				}

			} else {
				sender.sendMessage(plugin.prefix + "§c/money <Spieler>");
			}
		}
		return true;
	}
}
