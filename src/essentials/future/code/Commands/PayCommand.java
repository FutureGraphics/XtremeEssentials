package essentials.future.code.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import essentials.future.code.main.main;
import essentials.future.code.mysql.MySQLPlayer;

public class PayCommand implements CommandExecutor {

	private main plugin;
	
	public PayCommand(main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(player.hasPermission("essentials.pay")) {
				if(args.length == 2) {
					try {
						Player target = plugin.getServer().getPlayer(args[0]);
						try {
							int amount = Integer.parseInt(args[1]);
							if(MySQLPlayer.getCoins(player.getUniqueId().toString()) >= amount) {
								MySQLPlayer.updateCoins(player.getUniqueId(), amount, true, player.getName());
								int targetCoins = MySQLPlayer.getCoins(target.getUniqueId().toString());
								MySQLPlayer.updateCoins(target.getUniqueId(), amount+targetCoins, false, target.getName());
								target.sendMessage(plugin.prefix + "§eDu hast von §c" + player.getName() + " §c" + amount + " §eCoins bekommen");
								player.sendMessage(plugin.prefix + "§eDu hast §c" + target.getName() + " §c" + amount + " §eCoins gegeben");
							} else {
								player.sendMessage(plugin.prefix + "§cDu hast nicht genügend Coins");
							}
						} catch(NumberFormatException e) {
							player.sendMessage(plugin.prefix + "§cDer Betrag muss eine Zahl sein");
						}
					} catch(NullPointerException e) {
						player.sendMessage(plugin.playerNotFound);
					}
				} else {
					player.sendMessage(plugin.prefix + "§c/pay <Spieler> [Betrag]");
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
