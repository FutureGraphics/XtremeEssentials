package essentials.future.code.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import essentials.future.code.ConfigManager.PlayerManager;
import essentials.future.code.main.main;

public class SetHomeCommand implements CommandExecutor {

	private main plugin;
	
	public SetHomeCommand(main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(player.hasPermission("essentials.sethome")) {
				if(args.length == 1) {
					if(player.isOp() || player.hasPermission("essentials.sethome.unlimited")) {
						
						//=> Wenn Spieler OP ist
						
						PlayerManager.setHome(player, args[0], player.getLocation());
						player.sendMessage(plugin.prefix +"§eDein Home Punkt §c" + args[0] + " §ewurde gesetzt");
						
					} else if(player.hasPermission("essentials.sethome.vip")) {
						
						//=> Wenn Spieler VIP IST
						
						if(PlayerManager.getHomeList(player).size() <= plugin.getConfigManager().getHomes("VIP")) {
							PlayerManager.setHome(player, args[0], player.getLocation());
							player.sendMessage(plugin.prefix +"§eDein Home Punkt §c" + args[0] + " §ewurde gesetzt");
						} else {
							player.sendMessage(plugin.prefix +"§cDu kannst nur bis zu §c§l" + plugin.getConfigManager().getHomes("VIP") + " §cHome gleichzeit besitzten");
						}
						
					} else if(player.hasPermission("essentials.sethome.default")) {
						
						//Wenn Spieler Default ist
						
						if(PlayerManager.getHomeList(player).size() <= plugin.getConfigManager().getHomes("Default")) {
							PlayerManager.setHome(player, args[0], player.getLocation());
							player.sendMessage(plugin.prefix +"§eDein Home Punkt §c" + args[0] + " §ewurde gesetzt");
						} else {
							player.sendMessage(plugin.prefix +"§cDu kannst nur bis zu §c§l" + plugin.getConfigManager().getHomes("Default") + " §cHome gleichzeit besitzten");
						}
					}
				} else if(args.length == 0) {
					
					if(player.isOp() || player.hasPermission("essentials.sethome.unlimited")) {
						
						//Wenn Spieler OP ist
						
						PlayerManager.setHome(player, null, player.getLocation());
						player.sendMessage(plugin.prefix +"§eDein Home Punkt §chome §ewurde gesetzt");
					} else if(player.hasPermission("essentials.sethome.vip")) {
						
						//Wenn Spieler VIP IST
						
						if(PlayerManager.getHomeList(player).size() <= plugin.getConfigManager().getHomes("VIP")) {
							PlayerManager.setHome(player, null, player.getLocation());
							player.sendMessage(plugin.prefix +"§eDein Home Punkt §chome §ewurde gesetzt");
						} else {
							player.sendMessage(plugin.prefix +"§cDu kannst nur bis zu §c§l" + plugin.getConfigManager().getHomes("VIP") + " §cHome gleichzeit besitzten");
						}
					} else if(player.hasPermission("essentials.sethome.default")) {
						
						//Wenn Spieler default ist
						
						if(PlayerManager.getHomeList(player).size() <= plugin.getConfigManager().getHomes("Default")) {
							PlayerManager.setHome(player, null, player.getLocation());
							player.sendMessage(plugin.prefix +"§eDein Home Punkt §chome §ewurde gesetzt");
						} else {
							player.sendMessage(plugin.prefix +"§cDu kannst nur bis zu §c§l" + plugin.getConfigManager().getHomes("Default") + " §cHome gleichzeit besitzten");
						}
					}
				} else {
					player.sendMessage(plugin.prefix +"§c/sethome [Namen]");
				}
			} else {
				player.sendMessage(plugin.noAccess);
			}
		}
		return true;
	}

}
