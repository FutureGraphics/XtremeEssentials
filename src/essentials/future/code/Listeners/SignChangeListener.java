package essentials.future.code.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import essentials.future.code.main.main;

public class SignChangeListener implements Listener {

	private main plugin;

	public SignChangeListener(main plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
	}

	@EventHandler
	public void onSignChanceEvent(SignChangeEvent e) {
		Player player = e.getPlayer();
		if (e.getLine(0).equalsIgnoreCase("[free]")) {
			if (player.hasPermission("essentials.sign.free")) {
				if (!e.getLine(1).equals("")) {
					e.setLine(0, "§8[§2Free§8]");
					e.setLine(1, e.getLine(1));
				} else {
					e.setLine(1, "§cERROR");
				}
			}
		} else if(e.getLine(0).equalsIgnoreCase("[sell]")) {
			if(player.hasPermission("essentials.sign.sell")) {
				if(!e.getLine(1).equals("") && !e.getLine(2).equals("") && !e.getLine(3).equals("")) {
					e.setLine(0, "§8[§2Sell§8]");
					e.setLine(1, e.getLine(1));
					e.setLine(2, e.getLine(2));
					e.setLine(3, e.getLine(3));
				} else {
					e.setLine(1, "§cERROR");
				}
			}
		} else if(e.getLine(0).equalsIgnoreCase("[buy]")) {
			if(player.hasPermission("essentials.sign.buy")) {
				if(!e.getLine(1).equals("") && !e.getLine(2).equals("") && !e.getLine(3).equals("")) {
					e.setLine(0, "§8[§2Buy§8]");
					e.setLine(1, e.getLine(1));
					e.setLine(2, e.getLine(2));
					e.setLine(3, e.getLine(3));
				} else {
					e.setLine(1, "§cERROR");
				}
			}
		} else if(e.getLine(0).equalsIgnoreCase("[warp]")) {
			if(player.hasPermission("essentials.sign.warp")) {
				if(!e.getLine(1).equals("")) {
					String warp = e.getLine(1);
					if(plugin.getConfigManager().warpExist(warp)) {
						e.setLine(0, "§8[§2Warp§8]");
						e.setLine(1, e.getLine(1));
					} else {
						e.setLine(1, "§cERROR");
					}
				}
			}
		}
	}

}
