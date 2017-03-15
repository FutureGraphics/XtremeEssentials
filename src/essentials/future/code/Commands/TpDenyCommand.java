package essentials.future.code.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import essentials.future.code.main.main;

public class TpDenyCommand implements CommandExecutor {

	private main plugin;
	
	public TpDenyCommand(main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(player.hasPermission("essentials.tpdeny")) {
				if(plugin.tpaMap.containsKey(player)) {
					plugin.tpaMap.get(player).sendMessage(plugin.prefix +"§eDeinen anfrage wurde §cabgelehnt");
					plugin.tpaMap.remove(player);
					player.sendMessage(plugin.prefix +"§eDu hast die Anfrage abgelehnt");
				} else {
					player.sendMessage(plugin.prefix +"§cDu hast keine TP-Anfrage bekommen");
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
