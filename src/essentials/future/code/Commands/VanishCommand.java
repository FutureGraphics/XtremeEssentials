package essentials.future.code.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import essentials.future.code.main.main;

public class VanishCommand implements CommandExecutor {

	private main plugin;

	public VanishCommand(main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (player.hasPermission("essentials.vanish")) {
				if (args.length == 1) {
					if(player.hasPermission("essentials.vanish.others")) {
						try {
							Player target = plugin.getServer().getPlayer(args[0]);
							if (plugin.vanishList.contains(target)) {
								plugin.vanishList.remove(target);
								target.sendMessage(plugin.prefix +"§cDu bist nicht mehr im Vanish");
								for (Player all : Bukkit.getOnlinePlayers()) {
									all.showPlayer(target);
								}
								player.sendMessage(plugin.prefix +"§c" + target.getName() + " §eist nun nicht länger im Vanish");
							} else {
								plugin.vanishList.add(target);
								target.sendMessage(plugin.prefix +"§eDu bist nun im Vanish");
								for (Player all : Bukkit.getOnlinePlayers()) {
									all.hidePlayer(target);
								}
								player.sendMessage(plugin.prefix +"§c" + target.getName() + " §eist nun im Vanish");
							}
						} catch(NullPointerException e) {
							player.sendMessage(plugin.playerNotFound);
						}
					} else {
						player.sendMessage(plugin.noAccess);
					}
				} else if (args.length == 0) {
					if (plugin.vanishList.contains(player)) {
						plugin.vanishList.remove(player);
						player.sendMessage(plugin.prefix +"§eDu bist nicht mehr im Vanish");
						for (Player all : Bukkit.getOnlinePlayers()) {
							all.showPlayer(player);
						}
					} else {
						plugin.vanishList.add(player);
						player.sendMessage(plugin.prefix +"§eDu bist nun im Vanish");
						for (Player all : Bukkit.getOnlinePlayers()) {
							all.hidePlayer(player);
						}
					}
				} else {
					player.sendMessage(plugin.prefix +"§c/vanish <Spieler>");
				}
			} else {
				player.sendMessage(plugin.noAccess);
			}
		} else {
			if(args.length == 1) {
				try {
					Player target = plugin.getServer().getPlayer(args[0]);
					if (plugin.vanishList.contains(target)) {
						plugin.vanishList.remove(target);
						target.sendMessage(plugin.prefix +"§eDu bist nicht mehr im Vanish");
						for (Player all : Bukkit.getOnlinePlayers()) {
							all.showPlayer(target);
						}
						sender.sendMessage(plugin.prefix +"§c" + target.getName() + " §eist nun nicht länger im Vanish");
					} else {
						plugin.vanishList.add(target);
						target.sendMessage(plugin.prefix +"§eDu bist nun im Vanish");
						for (Player all : Bukkit.getOnlinePlayers()) {
							all.hidePlayer(target);
						}
						sender.sendMessage(plugin.prefix +"§a" + target.getName() + " §eist nun im Vanish");
					}
				} catch(NullPointerException e) {
					sender.sendMessage(plugin.playerNotFound);
				}
			} else {
				sender.sendMessage(plugin.prefix +"§c/vanish <Spieler>");
			}
		}
		return true;
	}

}
