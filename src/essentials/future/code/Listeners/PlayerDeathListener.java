package essentials.future.code.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scoreboard.Scoreboard;

import essentials.future.code.ConfigManager.ApiManager;
import essentials.future.code.ConfigManager.ConfigManager;
import essentials.future.code.main.main;
import essentials.future.code.mysql.MySQLPlayer;

public class PlayerDeathListener implements Listener {

	private main plugin;
	
	public PlayerDeathListener(main plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		if(e.getEntity() instanceof Player) {
			Player player = e.getEntity();
			if(e.getEntity().getKiller() instanceof Player) {

				Player killer = e.getEntity().getKiller();
				
				MySQLPlayer.addDeaths(player.getUniqueId());
				MySQLPlayer.addKills(killer.getUniqueId());
				
				if(plugin.getConfigManager().getPublicDeathMessage()) {
					String deathMessage = plugin.getConfigManager().getDeathMessage();
					deathMessage = deathMessage.replaceAll("&", "§");
					deathMessage = deathMessage.replaceAll("%player%", player.getName());
					deathMessage = deathMessage.replaceAll("%killer%", killer.getName());
					
					e.setDeathMessage(deathMessage);
				} else {
					e.setDeathMessage(null);
					player.sendMessage("§c" + killer.getName() + " §4hat dich getötet");
					killer.sendMessage("§4Du hast §c" + player.getName() + " §4getötet");
				}
				
				if(ConfigManager.getAmountOnDeath() != 0) {
					int coins = MySQLPlayer.getCoins(player.getUniqueId().toString());
					if(coins-ConfigManager.getAmountOnDeath() > 0) {
						MySQLPlayer.updateCoins(player.getUniqueId(), ConfigManager.getAmountOnDeath(), true, player.getName());
						player.sendMessage("§cDu hast §e" + ConfigManager.getAmountOnDeath() + " §cabgezogen bekommen");
					}
				}
				
				if(ConfigManager.getAmountOnKill() != 0) {
					int coins = MySQLPlayer.getCoins(killer.getUniqueId().toString());
					MySQLPlayer.updateCoins(killer.getUniqueId(), coins+ConfigManager.getAmountOnKill(), false, player.getName());
					killer.sendMessage("§eDu hast §c" + ConfigManager.getAmountOnKill() + " §ehinzugefügt bekommen");
				}
				for(Player players : Bukkit.getOnlinePlayers()) {
					if (ConfigManager.isScoreboardEnabled()) {
						Scoreboard board = ApiManager.createScoreboard(players,ConfigManager.getScorboardDisplayname(),ConfigManager.getScorboardList());
						players.setScoreboard(board);
					}
				}
			} else {
				e.setDeathMessage(null);
				MySQLPlayer.addDeaths(player.getUniqueId());
			}
		}
	}
}
