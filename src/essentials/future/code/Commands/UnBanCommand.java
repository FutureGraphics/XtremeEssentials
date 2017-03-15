package essentials.future.code.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import essentials.future.code.main.main;
import essentials.future.code.mysql.MySQLPlayer;

public class UnBanCommand implements CommandExecutor {

	private main plugin;
	
	public UnBanCommand(main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(player.hasPermission("essentials.unban")) {
				if(args.length == 1) {
					if(MySQLPlayer.userExistsByName(args[0])) {
						String uuid = MySQLPlayer.getUUIDFromName(args[0]);
						if(MySQLPlayer.isBaned(uuid)) {
							MySQLPlayer.setBanned(uuid, false);
							MySQLPlayer.setBanReason(uuid, null);
							MySQLPlayer.setBanTime(uuid, 0);
							player.sendMessage(plugin.prefix +"§c" + args[0] + " §eist entbannt worden");
						} else {
							player.sendMessage(plugin.prefix +"§cDieser Spieler ist nicht gebannt");
						}
					} else {
						player.sendMessage(plugin.playerNotFound);
					}
				} else {
					player.sendMessage(plugin.prefix +"§c/unban <Spieler>");
				}
			} else {
				player.sendMessage(plugin.noAccess);
			}
		} else {
			
		}
		return true;
	}

}
