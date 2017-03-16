package essentials.future.code.Listeners;

import java.util.Date;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.Scoreboard;

import essentials.future.code.ConfigManager.ApiManager;
import essentials.future.code.ConfigManager.ConfigManager;
import essentials.future.code.ConfigManager.PlayerManager;
import essentials.future.code.main.main;
import essentials.future.code.mysql.MySQLPlayer;

public class JoinListener implements Listener {

	private main plugin;

	public JoinListener(main plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		PlayerManager.createPlayerConfiguration(player);
		MySQLPlayer.createPlayerRow(player.getUniqueId(), player.getName(), "default");
		if (MySQLPlayer.isBaned(player.getUniqueId().toString())) {
			if (MySQLPlayer.getBanReason(player.getUniqueId()) != null) {
				if (MySQLPlayer.getBanTime(player.getUniqueId()) != 0) {
					// Wenn mit Zeit || kein grund
					Date mutedate = new Date();
					mutedate.setTime(MySQLPlayer.getBanTime(player.getUniqueId()));
					Date now = new Date();
					if (now.getTime() >= mutedate.getTime()) {
						// Freigeben
						MySQLPlayer.setBanned(player.getUniqueId().toString(), false);
						MySQLPlayer.setBanTime(player.getUniqueId().toString(), 0);
					} else {
						player.kickPlayer("§4Du bist bist bis zu dem \n" + "§c" + mutedate.toString() + " §gebannt");
					}
				} else {
					// Permanent || kein Grund
					player.kickPlayer("§cDu bist permanent gebannt");
				}
			} else {
				String grund = MySQLPlayer.getBanReason(player.getUniqueId());
				if (MySQLPlayer.getBanTime(player.getUniqueId()) != 0) {
					// Mit Zeit || Grund
					Date mutedate = new Date();
					mutedate.setTime(MySQLPlayer.getBanTime(player.getUniqueId()));
					Date now = new Date();
					if (now.getTime() >= mutedate.getTime()) {
						// Freigeben
						MySQLPlayer.setBanned(player.getUniqueId().toString(), false);
						MySQLPlayer.setBanTime(player.getUniqueId().toString(), 0);
						MySQLPlayer.setBanReason(player.getUniqueId().toString(), null);
					} else {
						player.kickPlayer("§4Du bist bist gebannt bis zu dem \n" + "§c" + mutedate.toString() + "\n"
								+ "§c für den Grund: " + grund);
					}
				} else {
					// permanent || Grund
					player.kickPlayer("§4Du bist permanent gebannt für den Grund:\n" + "§c" + grund);
				}
			}
		}
		if (ConfigManager.isScoreboardEnabled()) {
			Scoreboard board = ApiManager.createScoreboard(player, ConfigManager.getScorboardDisplayname(),ConfigManager.getScorboardList());
			player.setScoreboard(board);
		}
		String joinMessage = plugin.getConfigManager().getJoinMessage();
		joinMessage = joinMessage.replace("&", "§");
		joinMessage = joinMessage.replace("%player%", player.getName());
		PermissionUser user = PermissionsEx.getUser(player);
		PermissionGroup[] groups = user.getGroups();
		for(PermissionGroup group : groups) {
			user.removeGroup(group);
		}
		
		if(player.isOp()) {
			
		} else if(player.hasPermission("essentials.prefix.ultimate")) {
			player.setPlayerListName("§c" + player.getName());
		} else if(player.hasPermission("essentials.prefix.legendary")) {
			player.setPlayerListName("§3" + player.getName());
		} else if(player.hasPermission("essentials.prefix.premium")) {
			player.setPlayerListName("§e" + player.getName());
		}
		user.addGroup(MySQLPlayer.getGroupFromPlayer(player.getUniqueId().toString()));
		e.setJoinMessage(joinMessage);
		

	}

}
