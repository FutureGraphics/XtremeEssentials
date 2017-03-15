package essentials.future.code.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import essentials.future.code.main.main;

public class NickCommand implements CommandExecutor {

	private main plugin;
	
	public NickCommand(main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(player.hasPermission("essentials.nick")) {
				if(args.length == 2) {
					if(player.hasPermission("essentials.nick.others")) {
						try {
							Player target = plugin.getServer().getPlayer(args[0]);
							if(args[1].equalsIgnoreCase("off")) {
								target.setCustomName(target.getName());
								target.setPlayerListName(target.getName());
								player.sendMessage(plugin.prefix + "§c" + target.getName() + "§e ist nicht mehr genickt");
							} else {
								if(args[1].length() < 16) {
									target.setCustomName(args[1]);
									target.setPlayerListName(args[1]);
									player.sendMessage(plugin.prefix + "§c" + target.getName() + "§e lautet nun §c" + args[1]);
								} else {
									player.sendMessage(plugin.prefix + "§eDer Name darf nur §c16 §eZeichen lang sein");
								}
							}
						} catch(NullPointerException e) {
							player.sendMessage(plugin.playerNotFound);
						}
					} else {
						player.sendMessage(plugin.noAccess);
					}
				} else if(args.length == 1) {
					if(args[0].equalsIgnoreCase("off")) {
						player.setCustomName(player.getName());
						player.setPlayerListName(player.getName());
						player.sendMessage(plugin.prefix + "§eDu bist nicht mehr genickt");
					} else {
						if(args[0].length() < 16) {
							player.setCustomName(args[0]);
							player.setPlayerListName(args[0]);
							player.sendMessage(plugin.prefix + "§eDein neuer Name lautet §c" + args[0]);
						} else {
							player.sendMessage(plugin.prefix + "§eDer Name darf nur §c16 §eZeichen lang sein");
						}
					}
				} else {
					player.sendMessage(plugin.prefix + "§c/nick <Spieler> [Namen | off]");
				}
			} else {
				player.sendMessage(plugin.noAccess);
			}
		}
		return true;
	}

}
