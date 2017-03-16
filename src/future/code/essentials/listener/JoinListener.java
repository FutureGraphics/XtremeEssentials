package future.code.essentials.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import future.code.essentials.main.main;
import future.code.essentials.manager.ApiManager;
import future.code.essentials.manager.MainManager;
import future.code.essentials.manager.PlayerManager;

public class JoinListener implements Listener {

	private main plugin;
	
	public JoinListener(main plugin) {
		this.plugin = plugin;
		this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		
		//Joinmessage
		if(MainManager.getJoinMessage() != null) {
			String joinMessage = ApiManager.encryptFormat(MainManager.getJoinMessage());
			joinMessage = joinMessage.replace("%player%", player.getName());
			event.setJoinMessage(joinMessage);
		}
		//MOTD
		if(MainManager.getMOTD() != null) {
			String motd = ApiManager.encryptFormat(MainManager.getMOTD());
			motd = motd.replace("%player%", player.getName());
			player.sendMessage(motd);
		}
		//PlayerConfiguration
		PlayerManager.createPlayerConfiguration(player, MainManager.getDefaultAmount());
		
		
	}
	
}
