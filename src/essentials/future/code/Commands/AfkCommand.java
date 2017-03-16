package essentials.future.code.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import essentials.future.code.main.main;

public class AfkCommand implements CommandExecutor {

	private main plugin;
	
	public List<Player> afkList = new ArrayList<Player>();
	
	public AfkCommand(main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(player.hasPermission("essentials.afk")) {
				if(args.length == 1) {
					if(player.hasPermission("essentials.afk.others")) {
						try {
							Player target = plugin.getServer().getPlayer(args[0]);
							if(afkList.contains(target)) {
								Bukkit.broadcastMessage(plugin.prefix + "§c" + target.getName() + " §e ist wieder da");
								afkList.remove(target);
							} else {
								Bukkit.broadcastMessage(plugin.prefix + "§c" + target.getName() + " §e ist afk");
								afkList.add(target);
							}
						} catch(NullPointerException e) {
							player.sendMessage(plugin.playerNotFound);
						}
					} else {
						player.sendMessage(plugin.noAccess);
					}
				} else if(args.length == 0) {
					if(afkList.contains(player)) {
						afkList.remove(player);
						Bukkit.broadcastMessage(plugin.prefix + "§c" + player.getName() + "§e ist wieder da");
					} else {
						afkList.add(player);
						Bukkit.broadcastMessage(plugin.prefix + "§c" + player.getName() + " §e ist afk");
					}
				} else {
					player.sendMessage(plugin.prefix + "§c/afk <Spieler>");
				}
			}
		} else {
			if(args.length == 1) {
				try {
					Player target = plugin.getServer().getPlayer(args[0]);
					if(afkList.contains(target)) {
						Bukkit.broadcastMessage(plugin.prefix + "§c" + target.getName() + " §e ist wieder da");
						afkList.remove(target);
					} else {
						Bukkit.broadcastMessage(plugin.prefix + "§c" + target.getName() + " §e ist afk");
						afkList.add(target);
					}
				} catch(NullPointerException e) {
					sender.sendMessage(plugin.playerNotFound);
				}
			} else {
				sender.sendMessage(plugin.prefix + "§c/afk <Spieler>");
			}
		}
		return true;
	}

}
