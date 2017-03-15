package essentials.future.code.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import essentials.future.code.main.main;

public class SpawnCommand implements CommandExecutor {

	private main plugin;
	
	public SpawnCommand(main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(player.hasPermission("essentials.spawn")) {
				if(args.length == 1) {
					try {
						if(player.hasPermission("essentials.spawn.others")) {
							
							//Wenn der Spieler andere Teleportieren darf
							
							try {
								Player target = plugin.getServer().getPlayer(args[0]);
								if(plugin.getConfigManager().getSpawn() != null) {
									target.teleport(plugin.getConfigManager().getSpawn());
									target.sendMessage(plugin.prefix +"§eDu bist beim Spawn");
									player.sendMessage(plugin.prefix +"§c" + target.getName() + " §ewurde zum Spawn teleportiert");
								} else {
									player.sendMessage(plugin.prefix +"§cDer Spawn wurde noch nicht gesetzt");
								}
							} catch(NullPointerException e) {
								player.sendMessage(plugin.playerNotFound);
							}
						}  else {
							player.sendMessage(plugin.noAccess);
						}
						
					} catch(NullPointerException e) {
						player.sendMessage(plugin.playerNotFound);
					}
				} else if(args.length == 0){
					
					//Wenn der Spieler zum spawn möchte
					
					if(plugin.getConfigManager().getSpawn() != null) {
						plugin.onPlayerTeleport(player, plugin.getConfigManager().getSpawn(), plugin.prefix +"§eDu bist beim Spawn");
					} else {
						player.sendMessage(plugin.prefix +"§cDer Spawn wurde noch nicht gesetzt");
					}
				} else {
					player.sendMessage(plugin.prefix +"§c/spawn <Spieler>");
				}
			} else {
				player.sendMessage(plugin.noAccess);
			}
		} else {
			if(args.length == 1) {
				try {
					Player target = plugin.getServer().getPlayer(args[0]);
					if(plugin.getConfigManager().getSpawn() != null) {
						target.teleport(plugin.getConfigManager().getSpawn());
						target.sendMessage(plugin.prefix +"§eDu bist beim Spawn");
						sender.sendMessage(plugin.prefix +"§c" + target.getName() + " §ewurde zum Spawn teleportiert");
					} else {
						sender.sendMessage(plugin.prefix +"§cDer Spawn wurde noch nicht gesetzt");
					}
				} catch(NullPointerException e) {
					sender.sendMessage(plugin.playerNotFound);
				}
			} else {
				sender.sendMessage(plugin.prefix +"§c/spawn <Spieler>");
			}
		}
		return true;
	}

}
