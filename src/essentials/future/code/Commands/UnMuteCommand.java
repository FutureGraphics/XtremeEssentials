package essentials.future.code.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import essentials.future.code.main.main;
import essentials.future.code.mysql.MySQLPlayer;

public class UnMuteCommand implements CommandExecutor {

	private main plugin;
	
	public UnMuteCommand(main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(player.hasPermission("essentials.unmute")) {
				if(args.length == 1) {
					if(MySQLPlayer.userExistsByName(args[0])) {
						String uuid = MySQLPlayer.getUUIDFromName(args[0]);
						if(MySQLPlayer.isMuted(uuid) == true) {
							MySQLPlayer.setMuted(uuid, false);
							MySQLPlayer.setMuteTime(uuid, 0);
							MySQLPlayer.setMuteReason(uuid, null);
							player.sendMessage(plugin.prefix +"§c" + args[0] + " §eist entmutet worden");
						} else {
							player.sendMessage(plugin.prefix +"§cDieser Spieler ist nicht getmutet");
						}
					} else {
						player.sendMessage(plugin.playerNotFound);
					}
				} else {
					player.sendMessage(plugin.prefix +"§c/unmute <Spieler>");
				}
			} else {
				player.sendMessage(plugin.noAccess);
			}
		} else {
			
		}
		return true;
	}

}
