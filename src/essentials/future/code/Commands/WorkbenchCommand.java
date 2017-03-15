package essentials.future.code.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import essentials.future.code.main.main;

public class WorkbenchCommand implements CommandExecutor {

	private main plugin;
	
	public WorkbenchCommand(main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(player.hasPermission("essentials.workbench")) {
				if(args.length == 1) {
					if(player.hasPermission("essentials.workbench.others")) {
						try {
							Player target = plugin.getServer().getPlayer(args[0]);
							target.openWorkbench(null, true);
							target.sendMessage(plugin.prefix +"§eDas Werkbank Inventar wurde geöffnet");
							player.sendMessage(plugin.prefix +"§eDem Spieler §c" + target.getName() + " §ewurde die Werkbank geöffnet");
						} catch(NullPointerException e) {
							player.sendMessage(plugin.playerNotFound);
						}
					} else {
						player.sendMessage(plugin.noAccess);
					}
				} else if(args.length == 0) {
					player.openWorkbench(null, true);
					player.sendMessage(plugin.prefix + "§eWerkbank wurde geöffnet");
				} else {
					player.sendMessage(plugin.prefix +"§c/workbench <Spieler>");
				}
			} else {
				player.sendMessage(plugin.noAccess);
			}
		} else {
			if(args.length == 1) {
				try {
					Player target = plugin.getServer().getPlayer(args[0]);
					target.openWorkbench(null, true);
					target.sendMessage(plugin.prefix +"§eDas Wekbank Inventar wurde geöffnet");
					sender.sendMessage(plugin.prefix +"§eDem Spieler §c" + target.getName() + " §ewurde die Werkbank geöffnet");
				} catch(NullPointerException e) {
					sender.sendMessage(plugin.playerNotFound);
				}
			} else {
				sender.sendMessage("§c/workbench <Spieler>");
			}
		}
		return true;
	}

}
