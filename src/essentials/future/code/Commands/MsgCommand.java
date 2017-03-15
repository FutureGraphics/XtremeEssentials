package essentials.future.code.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import essentials.future.code.main.main;
import essentials.future.code.mysql.MySQLPlayer;

public class MsgCommand implements CommandExecutor{

	private main plugin;
	
	public MsgCommand(main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(player.hasPermission("essentials.msg")) {
				if(MySQLPlayer.isMuted(player.getUniqueId().toString())) {
					player.sendMessage(plugin.prefix +"§cDu bist gemutet");
				} else {
					if(args.length >= 2) {
						String message = " ";
						for(int i = 1; i < args.length; i++) {
							message += args[i] + " ";
						}
						try {
							Player target = plugin.getServer().getPlayer(args[0]);
							if(!target.getName().equals(player.getName())) {
								String layout = plugin.getConfigManager().getMsg();
								layout = layout.replaceAll("%player%", target.getName());
								layout = layout.replaceAll("%sender%", "§aIch");
								layout = layout.replaceAll("%message%", message);
								layout = layout.replaceAll("&", "§");
								String layout2 = plugin.getConfigManager().getMsg();
								layout2 = layout2.replaceAll("%player%", "§aMir");
								layout2 = layout2.replaceAll("%sender%", player.getName());
								layout2 = layout2.replaceAll("%message%", message);
								layout2 = layout2.replaceAll("&", "§");
								player.sendMessage(layout);
								target.sendMessage(layout2);
							} else {
								player.sendMessage(plugin.prefix + "§cDu kannst dir selbst keine Nachricht Senden");
							}

						} catch(NullPointerException e) {
							player.sendMessage(plugin.playerNotFound);
						}
					} else {
						player.sendMessage(plugin.prefix +"§c/msg <Spieler> [Nachricht]");
					}
				}
			} else {
				player.sendMessage(plugin.noAccess);
			}
		} else {
			
		}
		return true;
	}

}
