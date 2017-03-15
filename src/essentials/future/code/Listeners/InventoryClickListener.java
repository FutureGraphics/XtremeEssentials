package essentials.future.code.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

import essentials.future.code.main.main;

public class InventoryClickListener implements Listener {

	private main plugin;
	
	public InventoryClickListener(main plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		Player player = (Player) e.getWhoClicked();
		if(e.getInventory().getType() == InventoryType.PLAYER) {
			if(!player.hasPermission("essentials.invsee.change")) {
				e.setCancelled(true);
			}
		}
	}

}
