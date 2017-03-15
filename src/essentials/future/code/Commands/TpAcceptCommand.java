package essentials.future.code.Commands;

import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import essentials.future.code.main.main;

public class TpAcceptCommand implements CommandExecutor {

	private main plugin;
	
	public TpAcceptCommand(main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(player.hasPermission("essentials.tpaccept")) {
				if(plugin.tpaMap.containsKey(player)) {
					Map<Player, Player> tpMap = plugin.tpaMap;
					player.sendMessage(plugin.prefix +"§eDu hast die Anfrage erfolgreich akzeptiert");
					plugin.onPlayerTeleport(tpMap.get(player), player.getLocation(), plugin.prefix + "§eDu wurdest teleportiert");
					tpMap.get(player).sendMessage(plugin.prefix +"§c" + player.getName() + " §ehat deine Anfrage akzeptiert");
					tpMap.remove(player);
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
