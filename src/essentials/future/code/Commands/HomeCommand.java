package essentials.future.code.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import essentials.future.code.ConfigManager.PlayerManager;
import essentials.future.code.main.main;

public class HomeCommand implements CommandExecutor {

	private main plugin;

	public HomeCommand(main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (player.hasPermission("essentials.home")) {
				if (args.length == 1) {
					if (PlayerManager.homeExist(player, args[0])) {
						plugin.onPlayerTeleport(player, PlayerManager.getHome(player, args[0]),
								plugin.prefix + "§eErfolgreich zu dem Home-Punk §c" + args[0] + " §eteleportiert");
					} else {
						player.sendMessage(plugin.prefix + "§cHome-Punkt nicht gefunden");
					}
				} else if (args.length == 0) {
					if (PlayerManager.getHomeList(player).isEmpty()) {
						player.sendMessage(plugin.prefix + "§cDu besitzt keine Homes");
					} else {
						if (PlayerManager.getHomeList(player).size() > 1) {
							// Homelist
							int i = 0;
							player.sendMessage("§8┌▬▬▬▬▬§eHome-Punke§8▬▬▬▬▬┐");
							do {
								player.sendMessage("§8├§e" + PlayerManager.getHomeList(player).get(i));
								i++;
							} while (i < PlayerManager.getHomeList(player).size());
							player.sendMessage("§8└▬▬▬▬▬§eHome-Punke§8▬▬▬▬▬┘");
						} else {
							String homeName = PlayerManager.getHomeList(player).get(0);
							plugin.onPlayerTeleport(player, PlayerManager.getHome(player, homeName),
									plugin.prefix + "§eErfolgreich zu dem Home-Punk §c" + homeName + " §eteleportiert");
						}
					}
				} else {
					player.sendMessage(plugin.prefix + "§c/home [Namen]");
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
