package essentials.future.code.Listeners;

import java.util.Date;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import essentials.future.code.ConfigManager.ApiManager;
import essentials.future.code.main.main;
import essentials.future.code.mysql.MySQLPlayer;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class PlayerChatListener implements Listener {

	private main plugin;
	
	public PlayerChatListener(main plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
	}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {
		Player player = e.getPlayer();
		if(MySQLPlayer.isMuted(player.getUniqueId().toString())) {
			if(MySQLPlayer.getMuteReason(player.getUniqueId()) != null) {
				if(MySQLPlayer.getMuteTime(player.getUniqueId()) != 0) {
					//Wenn mit Zeit || kein grund
					Date mutedate = new Date();
					mutedate.setTime(MySQLPlayer.getMuteTime(player.getUniqueId()));
					Date now = new Date();
					if(now.getTime() >= mutedate.getTime()) {
						//Freigeben
						MySQLPlayer.setMuted(player.getUniqueId().toString(), false);
						MySQLPlayer.setMuteTime(player.getUniqueId().toString(), 0);
					} else {
						e.setCancelled(true);
						player.sendMessage(plugin.prefix + "§8│§cDu bist bist gemutet bis zu dem \n"
								+ "§8│§e" + mutedate.toString() + " §4gemutet");
					}
				} else {
					//Permanent || kein Grund
					e.setCancelled(true);
					player.sendMessage(plugin.prefix + "§cDu bist permanent gemutet");
				}
			} else {
				String grund = MySQLPlayer.getMuteReason(player.getUniqueId());
				if(MySQLPlayer.getMuteTime(player.getUniqueId()) != 0) {
					//Mit Zeit || Grund
					Date mutedate = new Date();
					mutedate.setTime(MySQLPlayer.getMuteTime(player.getUniqueId()));
					Date now = new Date();
					if(now.getTime() >= mutedate.getTime()) {
						//Freigeben
						MySQLPlayer.setMuted(player.getUniqueId().toString(), false);
						MySQLPlayer.setMuteTime(player.getUniqueId().toString(), 0);
						MySQLPlayer.setMuteReason(player.getUniqueId().toString(), null);
					} else {
						e.setCancelled(true);
						player.sendMessage(plugin.prefix + "§8│§cDu bist bist gemutet bis zu dem \n"
								+ "§8│§c" + mutedate.toString() + "\n"
								+ "§8│§e für den Grund: " + grund);
					}
				} else {
					//permanent || Grund
					e.setCancelled(true);
					player.sendMessage(plugin.prefix + "§8│§cDu bist permanent gemutet für den Grund:\n"
							+ "§8│§e" + grund);
				}
			}
		} else {
			String message = e.getMessage();
			PermissionUser user = PermissionsEx.getUser(player);
			String prefix = user.getPrefix().replace("&", "§");
			String name = player.getCustomName();
			if(name == null) {
				name = player.getName();
			}
		if(!player.hasPermission("essentials.add")) {
			if(message.contains(".") || message.contains(":")) {
				for(String domain : ApiManager.getAddList()) {
					if(message.endsWith(domain)) {
						player.sendMessage("§8[§eReport§8] §eBitte sende keine Werbung!");
						e.setCancelled(true);
					}
				}

			}
		}

			e.setFormat(prefix + "" + name + "§8 » §f" + message);
		}
	}

}
