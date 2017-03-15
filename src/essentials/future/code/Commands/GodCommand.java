package essentials.future.code.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import essentials.future.code.ConfigManager.ApiManager;
import essentials.future.code.ConfigManager.PlayerManager;
import essentials.future.code.main.main;

public class GodCommand implements CommandExecutor {

	private main plugin;
	
	public GodCommand(main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(player.hasPermission("essentials.god")) {
				if(args.length == 1) {
					if(player.hasPermission("essentials.god.others")) {
						try {
							Player target = plugin.getServer().getPlayer(args[0]);
							if(PlayerManager.isGod(target)) {
								PlayerManager.setGod(target, false);
								target.sendMessage(plugin.prefix +"§cUnsterblichkeit §cdeaktiviert");
								player.sendMessage(plugin.prefix +"§c" + target.getName() + " §ewurde Unsterblichkeit §cdeaktiviert");
							} else {
								PlayerManager.setGod(target, true);
								ApiManager.healPlayer(target);
								target.sendMessage(plugin.prefix +"§cUnsterblichkeit §caktiviert");
								player.sendMessage(plugin.prefix +"§c" + target.getName() + " §ewurde Unsterblichkeit §c aktiviert");
							}
						} catch(NullPointerException e) {
							player.sendMessage(plugin.playerNotFound);
						}
					} else {
						player.sendMessage(plugin.noAccess);
					}
				} else if(args.length == 0) {
					if(PlayerManager.isGod(player)) {
						PlayerManager.setGod(player, false);
						player.sendMessage(plugin.prefix +"§cUnsterblichkeit §cdeaktiviert");
					} else {
						PlayerManager.setGod(player, true);
						ApiManager.healPlayer(player);
						player.sendMessage(plugin.prefix +"§eUnsterblichkeit §caktiviert");
					}
				} else {
					player.sendMessage(plugin.prefix +"§c/god <Spieler>");
				}
			} else {
				player.sendMessage(plugin.noAccess);
			}
		} else {
			if(args.length == 1) {
				try {
					Player target = plugin.getServer().getPlayer(args[0]);
					if(PlayerManager.isGod(target)) {
						PlayerManager.setGod(target, false);
						target.sendMessage(plugin.prefix +"§cUnsterblichkeit §cdeaktiviert");
						sender.sendMessage(plugin.prefix +"§c" + target.getName() + " §cwurde Unsterblichkeit §cdeaktiviert");
					} else {
						PlayerManager.setGod(target, true);
						target.sendMessage(plugin.prefix +"§eUnsterblichkeit §caktiviert");
						ApiManager.healPlayer(target);
						sender.sendMessage(plugin.prefix +"§c" + target.getName() + " §ewurde Unsterblichkeit §c aktiviert");
					}
				} catch(NullPointerException e) {
					sender.sendMessage(plugin.playerNotFound);
				}
			} else {
				sender.sendMessage(plugin.prefix +"§c/god <Spieler>");
			}
		}
		return true;
	}
	
	

}
