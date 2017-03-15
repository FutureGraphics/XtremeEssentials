package essentials.future.code.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import essentials.future.code.ConfigManager.PlayerManager;
import essentials.future.code.main.main;

public class PlayerDamageListener implements Listener {

	private main plugin;

	public PlayerDamageListener(main plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
	}

	@EventHandler
	public void damagePlayer(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			Player player = (Player) e.getEntity();
			if (PlayerManager.isGod(player)) {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onPlayerDamage(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player) {
			if (e.getDamager() instanceof Player) {
				Player player = (Player) e.getEntity();
				Player damager = (Player) e.getDamager();
				if (plugin.getConfigManager().getPvPDelay() != 0) {
					if (!(player.isOp() || damager.isOp())) {
						if (!plugin.fightList.contains(player) || !plugin.fightList.contains(damager)) {
							player.sendMessage(plugin.prefix + "§c" + damager.getName()
									+ " §4hat dich angegriffen logge dich nicht §c§l"
									+ plugin.getConfigManager().getPvPDelay() + " §4Sekunden aus!");
							damager.sendMessage(plugin.prefix + "§4Du hast §c" + player.getName()
									+ " §4angegriffen logge dich nicht §c§l" + plugin.getConfigManager().getPvPDelay()
									+ " §4Sekunden aus!");
							plugin.fightList.add(player);
							plugin.fightList.add(damager);
							startSheduler(player);
							startSheduler(damager);
						}
					}
					if(player.isFlying() || damager.isFlying()) {
						player.setFlying(false);
						player.setAllowFlight(false);
					}
				}
				
			}
		}
	}

	public void startSheduler(final Player player) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

			@Override
			public void run() {
				if (plugin.fightList.contains(player)) {
					plugin.fightList.remove(player);
					player.sendMessage("§2Du bist nicht mehr in dem Kampf und kannst dich nun ausloggen");
				}
			}
		}, 20 * plugin.getConfigManager().getPvPDelay());
	}

}
