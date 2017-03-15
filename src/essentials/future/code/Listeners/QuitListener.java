package essentials.future.code.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import essentials.future.code.main.main;

public class QuitListener implements Listener {

	private main plugin;
	
	public QuitListener(main plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		final Player player = e.getPlayer();
		
		String quitMessage = plugin.getConfigManager().getQuitMessage();
		quitMessage = quitMessage.replace("&", "§");
		quitMessage = quitMessage.replace("%player%", player.getName());
		
		e.setQuitMessage(quitMessage);
		if(plugin.fightList.contains(player)) {
			player.setHealth(0D);
		}
	}

}
