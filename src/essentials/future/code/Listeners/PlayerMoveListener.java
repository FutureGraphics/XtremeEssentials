package essentials.future.code.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import essentials.future.code.main.main;

public class PlayerMoveListener implements Listener {

	private main plugin;
	
	public PlayerMoveListener(main plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		Player player = e.getPlayer();
		if(plugin.teleportList.contains(player)) {
			plugin.teleportList.remove(player);
			player.sendMessage(plugin.prefix + "§cTeleportation abgebrochen...");
		}
	}

}
