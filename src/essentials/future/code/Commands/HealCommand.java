package essentials.future.code.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import essentials.future.code.ConfigManager.ApiManager;
import essentials.future.code.ConfigManager.ConfigManager;
import essentials.future.code.main.main;

public class HealCommand implements CommandExecutor{

	private main plugin;
	
	public HealCommand(main plugin) {
		this.plugin = plugin;
	}
	
	public List<Player> healDelay = new ArrayList<>();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(player.hasPermission("essentials.heal")) {
				if(args.length == 1) {
					if(player.hasPermission("essentials.heal.others")) {
						try {
							Player target = plugin.getServer().getPlayer(args[0]);
							ApiManager.healPlayer(target);
							target.sendMessage(plugin.prefix +"§eDein Leben wurde von §c" + player.getName() + " §eaufgefüllt");
							player.sendMessage(plugin.prefix +"§eDu hast §c" + target.getName() + " §eLeben aufgefüllt");
						} catch(NullPointerException e) {
							player.sendMessage(plugin.playerNotFound);
						}
					}  else {
						player.sendMessage(plugin.noAccess);
					}
				} else if(args.length == 0) {
					if(healDelay.contains(player)) {
						player.sendMessage(plugin.prefix + "§eDu kannst dich in §c" + ConfigManager.getHealDelay() + "§e Sekunden Heilen");
					} else {
						ApiManager.healPlayer(player);
						player.sendMessage(plugin.prefix +"§eDu wurdest geheilt");
						if(!player.isOp() || !player.hasPermission("essentials.healdelay.bypass")) {
							healDelay.add(player);
							startScheduler(player);
						}
					}

				} else {
					player.sendMessage(plugin.prefix +"§c/heal <Spieler>");
				}
			} else {
				player.sendMessage(plugin.noAccess);
			}
		} else {
			if(args.length == 1 ) {
				try {
					Player target = plugin.getServer().getPlayer(args[0]);
					ApiManager.healPlayer(target);
					target.sendMessage(plugin.prefix +"§eDein Leben wurde von §cKonsole §eaufgefüllt");
					sender.sendMessage(plugin.prefix +"§eDu hast §c" + target.getName() + " §eLeben aufgefüllt");
				} catch(NullPointerException e) {
					sender.sendMessage(plugin.playerNotFound);
				}
			} else {
				sender.sendMessage(plugin.prefix +"§c/heal <Spieler>");
			}
		}
		return true;
	}
	
	public void startScheduler(final Player player) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			
			@Override
			public void run() {
				if(healDelay.contains(player)) {
					healDelay.remove(player);
				}
			}
		},20*ConfigManager.getHealDelay());
	}

}
