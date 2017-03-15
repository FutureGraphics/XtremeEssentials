package essentials.future.code.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import essentials.future.code.main.main;

public class TpHereCommand implements CommandExecutor{

	private main plugin;
	
	public TpHereCommand(main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(player.hasPermission("essentials.tphere")) {
				if(args.length == 1) {
					try {
						Player target = plugin.getServer().getPlayer(args[0]);
						target.teleport(player.getLocation());
						target.sendMessage(plugin.prefix +"§eDu wurdest von §c" + player.getName() + " §eTeleportiert");
						player.sendMessage(plugin.prefix +"§eErfolgreich §c" + target.getName() + " §eTeleportiert");
					} catch(NullPointerException e) {
						player.sendMessage(plugin.playerNotFound);
					}
				} else {
					player.sendMessage(plugin.prefix +"§c/tphere <Spieler>");
				}
			} else {
				player.sendMessage(plugin.noAccess);
			}
		} else {
			sender.sendMessage(plugin.noConsole);
		}
		return true;
	}

}
